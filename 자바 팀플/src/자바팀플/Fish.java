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
	public static int speed;
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
		timerfishbone = new Timer(900, new ActionListener() { // 생선뼈 객체 생성
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
		penguin = new Rectangle(WIDTH / 2, HEIGHT - 175, 100, 100);
		/* 신필 펭귄사이즈 -30 ㄱㄱ */
		timer2.start();
		timerfish0.start();
		timerheart.start();
		timerfishbone.start();
		// 배경음악 재생
//		loadMusic("audio/snow_bgm.wav");
//		clipBackGroundMusic.start();
//		clipBackGroundMusic.loop(clipBackGroundMusic.LOOP_CONTINUOUSLY);
	}

	public class Mypanel extends JPanel {
		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) { // 스윙 컴포넌트가 자신의 모양을 그리는메서드
			super.paintComponent(g);
			Fish.fish.repaint(g);
		}
	}

	public void repaint(Graphics g) {
		ImageIcon i2 = new ImageIcon("image/penguin.jpg");
		Image image2 = i2.getImage();
		if (xMotion > 0) {
			g.drawImage(image2, penguin.x, penguin.y, penguin.x + 100, penguin.y + 100, 0, 0, 320, 391, null);
		} else {
			g.drawImage(image2, penguin.x, penguin.y, penguin.x + 100, penguin.y + 100, 0, 0, 320, 391, null);
		}
		if (yMotion > 0) {
			g.drawImage(image2, penguin.x, penguin.y, penguin.x + 100, penguin.y + 100, 0, 0, 320, 391, null);
		} else {
			g.drawImage(image2, penguin.x, penguin.y, penguin.x + 100, penguin.y + 100, 0, 0, 320, 391, null);
		}

		for (int i = 0; i < life; i++) {
			ImageIcon i3 = new ImageIcon("image/heart.png");
			Image image3 = i3.getImage();
			g.drawImage(image3, 30 + i * 70, 30, 90 + i * 70, 90, 0, 0, 60, 60, null);
		}
		for (Rectangle fish : fish0) {
			ImageIcon i0 = new ImageIcon("image/fish.png");
			Image image0 = i0.getImage();
			g.drawImage(image0, fish.x, fish.y, fish.x + 40, fish.y + 40, 0, 0, 379, 373, null);
		}
		for (Rectangle heart : heart0) {
			ImageIcon i0 = new ImageIcon("image/heart.png");
			Image image0 = i0.getImage();
			g.drawImage(image0, heart.x, heart.y, heart.x + 40, heart.y + 40, 0, 0, 40, 40, null);
		}
		for (Rectangle fishbone : fishbone0) {
			ImageIcon i0 = new ImageIcon("image/fishbone.png");
			Image image0 = i0.getImage();
			g.drawImage(image0, fishbone.x, fishbone.y, fishbone.x + 40, fishbone.y + 40, 0, 0, 400, 467, null);
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
			fish0.clear();
			fishbone0.clear();
			heart0.clear();
			timerfish0.stop();
			timerheart.stop();
			timerfishbone.stop();
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		if(score==0) speed = 1;
		if(score==10) speed=2;
		if(score==20) speed=3;
		if(score==30) speed=4;
		if(score==40) speed=5;
		if(score==50) speed=6;
		if (started) {
			for (int i = 0; i < fish0.size(); i++) {
				Rectangle fish = fish0.get(i);
				fish.y += speed;
			}
		}
		if (started) {
			for (int i = 0; i < heart0.size(); i++) {
				Rectangle heart = heart0.get(i);
				heart.y += speed + 1;
			}
		}
		if (started) {
			for (int i = 0; i < fishbone0.size(); i++) {
				Rectangle fishbone = fishbone0.get(i);
				fishbone.y += speed + 1;
			}
		}
		penguin.x += xMotion;
		if (penguin.x < 0) {
			penguin.x = 0;
		}
		if (penguin.x > WIDTH - 100) {
			penguin.x = WIDTH - 100;
		}
		penguin.y += yMotion;
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
				score++;
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
				loadAudio("audio/heart.wav");
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

	public void Xrightmove(boolean A) {
		
		if (!started) {
//			life = 3;
//			score = 0;
//			started = true;
		} else if (!gameOver) {
			if (A == true)
				xMotion = 5;
			else
				xMotion = 0;
		}
	}

	public void Xleftmove(boolean A) {
		
		if (!started) {
//			life = 3;
//			score = 0;
//			started = true;
		} else if (!gameOver) {
			if (A == true)
				xMotion = -5;
			else
				xMotion = 0;
		}
	}

	public void Yupmove(boolean A) {
		
		if (!started) {
//			life = 3;
//			score = 0;
//			started = true;
		} else if (!gameOver) {
			if (A == true)
				yMotion = -5;
			else
				yMotion = 0;
		}
	}

	public void Ydownmove(boolean A) {
		
		if (!started) {
//			life = 3;
//			score = 0;
//			started = true;
		} else if (!gameOver) {
			if (A == true)
				yMotion = 5;
			else
				yMotion = 0;
		}
	}

	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			Xleftmove(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			Xrightmove(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			Yupmove(true);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			Ydownmove(true);
		}
	}

	public void keyReleased(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			Xleftmove(false);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			Xrightmove(false);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			Yupmove(false);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			Ydownmove(false);
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
