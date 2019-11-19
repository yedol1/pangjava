package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Snow implements ActionListener, KeyListener {
	public static Snow snow;
	public final int WIDTH = 1200, HEIGHT = 800;
	public Mypanel panel;
	public Rectangle santa;
	public ArrayList<Rectangle> snows;
	public ArrayList<Rectangle> snows1;
	public ArrayList<Rectangle> hearts;
	public ArrayList<Rectangle> presents;
	public Random rand;
	public boolean gameOver = false, started = true;
	public int xMotion, score, life = 3;
	public Clip clip;
	public Clip clipBackGroundMusic;
	public boolean gameOverSound = false;

	public Snow() {
		JFrame jframe = new JFrame();
		Timer timersnow = new Timer(1000, new ActionListener() { // 传 按眉 积己
			@Override
			public void actionPerformed(ActionEvent arg0) {
// TODO Auto-generated method stub
				int rainsnow = rand.nextInt(WIDTH - 230);
				snows.add(new Rectangle(rainsnow + 100, 10, 32, 32));
			}
		});
		Timer timersnow1 = new Timer(1000, new ActionListener() { // 传 按眉 积己
			@Override
			public void actionPerformed(ActionEvent arg0) {
// TODO Auto-generated method stub
				int rainsnow1 = rand.nextInt(WIDTH - 230);
				snows1.add(new Rectangle(rainsnow1 + 100, -200, 32, 32));
			}
		});
		Timer timerheart = new Timer(700, new ActionListener() { // 传 按眉 积己
			@Override
			public void actionPerformed(ActionEvent arg0) {
// TODO Auto-generated method stub
				int rainheart = rand.nextInt(WIDTH - 230);
				hearts.add(new Rectangle(rainheart + 100, 10, 32, 32));
			}
		});
		Timer timerpresent = new Timer(1000, new ActionListener() { // 急拱 按眉 积己
			@Override
			public void actionPerformed(ActionEvent arg0) {
// TODO Auto-generated method stub
				int rainpresent = rand.nextInt(WIDTH - 230);
				presents.add(new Rectangle(rainpresent + 100, 0, 32, 32));
			}
		});
		Timer timer2 = new Timer(20, this);
		panel = new Mypanel();
		rand = new Random();
		jframe.add(panel);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.setResizable(false); // 拳搁 农扁 炼例 瞒窜
		jframe.addKeyListener(this);
		jframe.setTitle("传 乔窍扁 霸烙");
		santa = new Rectangle(WIDTH / 2, HEIGHT - 175, 100, 100);
		snows = new ArrayList<Rectangle>();
		snows1 = new ArrayList<Rectangle>();
		presents = new ArrayList<Rectangle>();
		hearts = new ArrayList<Rectangle>();
		timersnow.start();
		timersnow1.start();
		timerpresent.start();
		timerheart.start();
		timer2.start();
		timersnow1.start();
		/*
		 * 硅版澜厩 犁积
		 */
		loadMusic("audio/snow_bgm.wav");
		clipBackGroundMusic.start();
		clipBackGroundMusic.loop(clipBackGroundMusic.LOOP_CONTINUOUSLY);
	}

	public void repaint(Graphics g) {
		ImageIcon i1 = new ImageIcon("image/snowbg.jpg");
		Image image1 = i1.getImage();
		g.drawImage(image1, 0, 0, WIDTH, HEIGHT, null);
		g.setColor(Color.white);
		g.fillRect(0, HEIGHT - 80, WIDTH, 120);
		ImageIcon i2 = new ImageIcon("image/santa1.png");
		Image image2 = i2.getImage();
		if (xMotion > 0) {
			g.drawImage(image2, santa.x, santa.y, santa.x + 100, santa.y + 100, 0, 0, 300, 300, null);
		} else {
			g.drawImage(image2, santa.x, santa.y, santa.x + 100, santa.y + 100, 300, 0, 0, 300, null);
		}
		for (int i = 0; i < life; i++) {
			ImageIcon i3 = new ImageIcon("image/like.png");
			Image image3 = i3.getImage();
			g.drawImage(image3, 30 + i * 70, 30, 90 + i * 70, 90, 0, 0, 130, 130, null);
		}
		for (Rectangle snow : snows) {
			ImageIcon i4 = new ImageIcon("image/bomb.png");
			Image image4 = i4.getImage();
			g.drawImage(image4, snow.x, snow.y, snow.x + 32, snow.y + 32, 0, 0, 60, 60, null);
		}
		for (Rectangle snow : snows1) {
			ImageIcon i4 = new ImageIcon("image/bomb.png");
			Image image4 = i4.getImage();
			g.drawImage(image4, snow.x, snow.y, snow.x + 32, snow.y + 32, 0, 0, 60, 60, null);
		}
		for (Rectangle present : presents) {
			ImageIcon i3 = new ImageIcon("image/gift.png");
			Image image3 = i3.getImage();
			g.drawImage(image3, present.x, present.y, present.x + 32, present.y + 32, 0, 0, 60, 60, null);
		}
		for (Rectangle heart : hearts) {
			ImageIcon i3 = new ImageIcon("image/like.png");
			Image image3 = i3.getImage();
			g.drawImage(image3, heart.x, heart.y, heart.x + 32, heart.y + 32, 0, 0, 130, 130, null);
		}
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));
		if (!started) {
			g.drawString("Click to start!", 250, HEIGHT / 2 - 50);
		}
		if (!gameOver && started) {
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}
		if (gameOver) {
			g.drawString("Game Over!", 300, HEIGHT / 2 - 50);
			g.setFont(new Font("Arial", 1, 50));
			g.drawString(String.valueOf(score), 300, HEIGHT / 2 - 200);
		}
		if (gameOver && !gameOverSound) {
			clipBackGroundMusic.stop();
			loadAudio("audio/snow_dies.wav");
			clip.start();
			gameOverSound = true;
		}
	}

	public void Xrightmove() {
		if (gameOver) {
			snows.clear();
			snows1.clear();
			presents.clear();
			hearts.clear();
			score = 0;
			life = 3;
			clipBackGroundMusic.start();
			gameOver = false;
			gameOverSound = false;
		}
		if (!started) {
			life = 3;
			score = 0;
			started = true;
		} else if (!gameOver) {
			xMotion = 5;
		}
	}

	public void Xleftmove() {
		if (gameOver) {
			snows.clear();
			snows1.clear();
			presents.clear();
			hearts.clear();
			score = 0;
			life = 3;
			clipBackGroundMusic.start();
			gameOver = false;
			gameOverSound = false;
		}
		if (!started) {
			life = 3;
			score = 0;
			started = true;
		} else if (!gameOver) {
			xMotion = -5;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
// TODO Auto-generated method stub
		int speed = 10;
		if (started) {
			for (int i = 0; i < snows.size(); i++) {
				Rectangle snow = snows.get(i);
				snow.y += speed;
			}
			for (int i = 0; i < snows1.size(); i++) {
				Rectangle snow = snows1.get(i);
				snow.y += speed;
			}
			for (int i = 0; i < presents.size(); i++) {
				Rectangle present = presents.get(i);
				present.y += speed;
			}
			for (int i = 0; i < hearts.size(); i++) {
				Rectangle heart = hearts.get(i);
				heart.y += speed;
			}
			santa.x += xMotion;
			if (santa.x < 0) {
				santa.x = 0;
			}
			if (santa.x > WIDTH - 100) {
				santa.x = WIDTH - 100;
			}
			panel.repaint();
			for (Rectangle snow : snows) {
				if (snow.intersects(santa)) {
					life--;
					snows.remove(snow);
					loadAudio("audio/snow_minus.wav");
					clip.start();
				}
				if (life <= 0) {
					gameOver = true;
				}
			}
			for (Rectangle snow1 : snows1) {
				if (snow1.intersects(santa)) {
					life--;
					snows1.remove(snow1);
					loadAudio("audio/snow_minus.wav");
					clip.start();
				}
				if (life <= 0) {
					gameOver = true;
				}
			}
			for (Rectangle present : presents) {
				if (present.intersects(santa)) {
					score++;
					presents.remove(present);
					loadAudio("audio/snow_present.wav");
					clip.start();
				}
			}
			for (Rectangle heart : hearts) {
				if (heart.intersects(santa)) {
					if (life >= 4) {
// life=4;
						score++;
					} else {
						life++;
					}
					hearts.remove(heart);
					loadAudio("audio/snow_heart.wav");
					clip.start();
				}
			}
		}
		panel.repaint();
	}

	public static void main(String[] args) {
		snow = new Snow();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_A) {
			Xleftmove();
		}
		if (arg0.getKeyCode() == KeyEvent.VK_D) {
			Xrightmove();
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

	@Override
	public void keyTyped(KeyEvent arg0) {
// TODO Auto-generated method stub
	}
}