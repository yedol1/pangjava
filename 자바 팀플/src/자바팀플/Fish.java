package 자바팀플;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fish implements ActionListener, KeyListener {
	public static Fish fish;
	public final int WIDTH = 800, HEIGHT = 1000;
	public Mypanel panel;
	public ArrayList<Rectangle> fish0;
	public ArrayList<Rectangle> heart0;
	public ArrayList<Rectangle> fishbone0;
	public Rectangle penguin;
	public Random rand;
	public boolean gameOver = false, started = true;
	public int life = 3, score = 0, xMotion, yMotion;
	public Clip clip;
	public Clip clipBackGroundMusic;
	public static Timer timerheart;
	public static Timer timerfish0;
	public static Timer timerfishbone;
	public static int speed=1;
	public static int penguinwidth = 80;
	public static int penguinheight = 90;
	public static int cnt=0;
	public static int sx=0;
	public static int sy=0;
	public static int p_speed = 5; //팽귄스피드
	public boolean gameOverSound = false;
	public static ImageIcon i1 = new ImageIcon("image/back1.jpg");		//배경 이미지
	public static ImageIcon iheart1 = new ImageIcon("image/heart1.png");//현재 라이프 이미지삽입		
	public static ImageIcon penguin1= new ImageIcon("image/penguin1.png");//펭귄이미지
	public static ImageIcon penguin2= new ImageIcon("image/penguin2.png");
	public static ImageIcon penguin3= new ImageIcon("image/penguin3.png");
	public static ImageIcon ifish = new ImageIcon("image/fish.png");
	public static ImageIcon iheart0 = new ImageIcon("image/heart0.png");
	public static ImageIcon ifishbone = new ImageIcon("image/fishbone.png");
	public static ImageIcon image_3 = new ImageIcon("image/penguin_dead.jpg"); //죽은팽귄
	public static int fishbonedelay = 1600;
	public Fish() {
		JFrame jframe = new JFrame();
		timerfish0 = new Timer(1500, new ActionListener() { // 생선 객체 생성
			public void actionPerformed(ActionEvent arg0) {
				int dropfish = rand.nextInt(WIDTH - 80);
				fish0.add(new Rectangle(dropfish + 40, 0, 40, 40));
			}
		});
		timerheart = new Timer(6000, new ActionListener() { // 하트 객체 생성
			public void actionPerformed(ActionEvent arg0) {
				int dropheart = rand.nextInt(WIDTH - 80);
				heart0.add(new Rectangle(dropheart + 40, 0, 40, 40));
			}
		});
		timerfishbone = new Timer(fishbonedelay, new ActionListener() { // 생선뼈 객체 생성
			public void actionPerformed(ActionEvent arg0) {
				int dropfishbone = rand.nextInt(WIDTH - 80);
				fishbone0.add(new Rectangle(dropfishbone + 40, 0, 40, 40));
			}
		});
		Timer timer2 = new Timer(10, this);
		panel = new Mypanel();
		rand = new Random();
		jframe.add(panel);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setSize(WIDTH, HEIGHT);
		Dimension frameSize = jframe.getSize();// 모니터 크기
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();// (모니터화면 가로 - 프레임화면 가로) / 2, (모니터화면 세로 -
																			// 프레임화면 세로) / 2
		jframe.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
		jframe.setResizable(false);// 화면 크기 조절 차단
		jframe.addKeyListener(this);
		jframe.setTitle("펭귄 게임");
		fish0 = new ArrayList<Rectangle>();
		heart0 = new ArrayList<Rectangle>();
		fishbone0 = new ArrayList<Rectangle>();
		penguin = new Rectangle(WIDTH / 2, HEIGHT - 175, penguinwidth, penguinheight);
		timer2.start();
		timerfish0.start();
		timerheart.start();
		timerfishbone.start();

		loadMusic("audio/bgm.wav");
		clipBackGroundMusic.start();
		clipBackGroundMusic.loop(clipBackGroundMusic.LOOP_CONTINUOUSLY);
	}

	public class Mypanel extends JPanel {
		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) { // 스윙 컴포넌트가 자신의 모양을 그리는메서드
			super.paintComponent(g);
			Fish.fish.repaint(g);
		}
	}

	public void repaint(Graphics g) {
		
		Image image1 = i1.getImage();
		//g.drawImage(image1, 0, 0, WIDTH, HEIGHT, null);
		g.drawImage(image1, 0, 0, WIDTH, HEIGHT, 0, 0+sy, 113, 500+sy, null);
		sy++;
		if(sy==300) sy=0;
		
		Image image2;
		if(cnt%6 ==0 || cnt%6 ==1) {image2 = penguin1.getImage();}
		else if(cnt%6 ==2 || cnt%6 ==3) {image2 = penguin2.getImage();}
		else {image2 = penguin3.getImage();}
		
		cnt++;
		if(!gameOver)g.drawImage(image2, penguin.x, penguin.y, penguin.x + penguinwidth, penguin.y + penguinheight, 0, 0, 2403, 2365, null);
		// 팽귄리페인트
		for (int i = 0; i < life; i++) {
			
			Image image3 = iheart1.getImage();
			g.drawImage(image3, 30 + i * 70, 30, 90 + i * 70, 90, 0, 0, 800, 800, null);
		}
		for (Rectangle fish : fish0) {
			
			Image image0 = ifish.getImage();
			g.drawImage(image0, fish.x, fish.y, fish.x + 40, fish.y + 40, 0, 0, 400, 400, null);
		}
		for (Rectangle heart : heart0) {
			
			Image image0 = iheart0.getImage();
			g.drawImage(image0, heart.x, heart.y, heart.x + 40, heart.y + 40, 0, 0, 400, 400, null);
		}
		for (Rectangle fishbone : fishbone0) {
			
			Image image0 = ifishbone.getImage();
			g.drawImage(image0, fishbone.x, fishbone.y, fishbone.x + 40, fishbone.y + 40, 0, 0, 400, 400, null);
		}
		g.setColor(Color.black);
		g.setFont(new Font("Arial", 1, 80));
		if (!gameOver && started) { // score 표기
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 70);
		}
		if (gameOver) {//game over 되면
			g.drawString("Game Over!", 170, 200);
			g.setFont(new Font("Arial", 1, 100));
			g.drawString(String.valueOf(score), 370, 300);
			Image image3 = image_3.getImage(); //죽은팽귄
			g.drawImage(image3, penguin.x, penguin.y, penguin.x + penguinwidth, penguin.y + penguinheight, 0, 0, 2403, 2365, null);
			fish0.clear();
			fishbone0.clear();
			heart0.clear();
			timerfish0.stop();
			timerheart.stop();
			timerfishbone.stop();
			
		}
		if (gameOver && !gameOverSound) {
			loadAudio("audio/fail.wav");
			clip.start();
			gameOverSound = true;
		}

	}

	public void actionPerformed(ActionEvent arg0) {
		
		if(score==100) {speed=2;
		/*timerfishbone = new Timer(1400, new ActionListener() { // 생선뼈 객체 생성
			public void actionPerformed(ActionEvent arg0) {
				int dropfishbone = rand.nextInt(WIDTH - 80);
				fishbone0.add(new Rectangle(dropfishbone + 40, 0, 40, 40));
			}
		});*/
		timerfishbone.start();
		}
		if(score==200) speed=4;
		if(score==300) speed=6;
		if(score==400) speed=8;
		if(score==500) speed=12;
		if (started) {
			for (int i = 0; i < fish0.size(); i++) {
				Rectangle fish = fish0.get(i);
				fish.y += speed;
			}
		}
		if (started) {
			for (int i = 0; i < heart0.size(); i++) {
				Rectangle heart = heart0.get(i);
				heart.y += speed;
			}
		}
		if (started) {
			for (int i = 0; i < fishbone0.size(); i++) {
				Rectangle fishbone = fishbone0.get(i);
				fishbone.y += speed+speed/2;
			}
		}
		if(!gameOver) {
		if(KeyUp == true) penguin.y -= p_speed;
		if(KeyDown == true)penguin.y += p_speed;
		if(KeyLeft == true) penguin.x -= p_speed;
		if(KeyRight == true) penguin.x += p_speed;
		}
		if (penguin.x < 0) {
			penguin.x = 0;
		}
		if (penguin.x > WIDTH - 100) {
			penguin.x = WIDTH - 100;
		}
		if (penguin.y < 0) {
			penguin.y = 0;
		}
		if (penguin.y > HEIGHT - 140) {
			penguin.y = HEIGHT - 140;
		}

		/* 충돌시 반응 */

//		for (Rectangle fish : fish0) {
//			if (fish.intersects(penguin)) {
//				score++;
//				fish0.remove(fish);
//				//loadAudio("audio/snow_minus.wav");
//				//clip.start();
//			}
//		}
		for (Iterator<Rectangle> it = fish0.iterator(); it.hasNext();) {
			Rectangle value = it.next();
			if (value.intersects(penguin)) {
				score=score+10;
				it.remove();
				loadAudio("audio/fish.wav");
				clip.start();
			}
		}
		for (Iterator<Rectangle> it = heart0.iterator(); it.hasNext();) {
			Rectangle value = it.next();
			if (value.intersects(penguin)) {
				if (life < 3) {
					life++;
				}
				it.remove();
				loadAudio("audio/fish.wav");
				clip.start();
			}
		}
		for (Iterator<Rectangle> it = fishbone0.iterator(); it.hasNext();) {
			Rectangle value = it.next();
			if (value.intersects(penguin)) {
				life--;
				it.remove();
				loadAudio("audio/fishbone.wav");
				clip.start();
			}
			if (life <= 0) {
				gameOver = true;
			}
		}
		panel.repaint();
	}

	boolean KeyUp = false; //키보드 입력 처리를 위한 변수
	boolean KeyDown = false;
	boolean KeyLeft = false;
	boolean KeyRight = false;

	public void keyPressed(KeyEvent e){
		// 키보드가 눌러졌을때 이벤트 처리하는 곳
		if(!gameOver) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP :
		KeyUp = true;
		break;
		case KeyEvent.VK_DOWN :
		KeyDown = true;
		break;
		case KeyEvent.VK_LEFT :
		KeyLeft = true;
		break;
		case KeyEvent.VK_RIGHT :
		KeyRight = true;
		break;
		}
		}
	}
		public void keyReleased(KeyEvent e){
		// 키보드가 눌러졌다가 때어졌을때 이벤트 처리하는 곳
		if(!gameOver) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP :
		KeyUp = false;
		break;
		case KeyEvent.VK_DOWN :
		KeyDown = false;
		break;
		case KeyEvent.VK_LEFT :
		KeyLeft = false;
		break;
		case KeyEvent.VK_RIGHT :
		KeyRight = false;
		break;
		}
		}
		}
	public void keyTyped(KeyEvent arg0) {
	}

	private void loadAudio(String pathName) {
		try {
			clip = AudioSystem.getClip();
			File audioFile = new File(pathName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadMusic(String pathName) {
		try {
			clipBackGroundMusic = AudioSystem.getClip();
			File audioFile = new File(pathName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
			clipBackGroundMusic.open(audioStream);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		fish = new Fish();
	}

}
