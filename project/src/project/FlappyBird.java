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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

public class FlappyBird implements ActionListener, MouseListener, KeyListener {
	public static FlappyBird flappyBird;
	public final int WIDTH = 1500, HEIGHT = 800; // 창 크기
	public Renderer renderer;
	public Rectangle bird; // 새 모양
	public int ticks, yMotion, score; // ?? , y축 속도, 점수
	public ArrayList<Rectangle> columns; // 장애물 어레이 리스트
	public Random rand; // 랜덤함수J
	public boolean gameOver, started = true; // 게임 오버와, 게임 시작
	public boolean gameOverSound = false;
	Clip clipBackGroundMusic; // 배경음악 클립
	Clip clip; // 효과음 클립

	public FlappyBird() {
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20, this);
		renderer = new Renderer();
		rand = new Random();
		jframe.add(renderer);
		jframe.setTitle("Flappy Bird"); // 타이틀 제목
// jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.addMouseListener(this); // 마우스 리스트
		jframe.addKeyListener(this); // 키 리스너
		jframe.setSize(WIDTH, HEIGHT); // 창 화면 크기
		jframe.setVisible(true);
		jframe.setResizable(false); // 화면 창을 늘릴 수 있음
		bird = new Rectangle(WIDTH / 2 - 10, 100, 80, 60); // Rectangle(x위치,y위치,x크기,y크기)
		columns = new ArrayList<Rectangle>(); // 여러개를 만들어야 하므로 어레이리스트로 구현
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		loadMusic("audio/flappy_bgm.wav");
		clipBackGroundMusic.start();
		timer.start();
	}

	public void addColumn(boolean start) {
		int space = 200;
		int width = 100;
		int height = 200 + rand.nextInt(350);
		if (start) {
			columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height, width, height));
			columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width, HEIGHT - height - space));
		} else {
			columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height, width, height));
			columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
		}
	}

	public void paintColumn(Graphics g, Rectangle column) {
		ImageIcon i3 = new ImageIcon("image/blick.png");
		Image image3 = i3.getImage();
		g.drawImage(image3, column.x, column.y, column.x + column.width, column.y + column.height, 0, 0, 190,
				column.height / 2, null);
	}

	public void jump() {
		if (gameOver) {
			bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 80, 60);
			columns.clear();
			yMotion = 0;
			score = 0;
			addColumn(true);
			addColumn(true);
			addColumn(true);
			addColumn(true);
			gameOver = false;
			gameOverSound = false;
			clipBackGroundMusic.stop();
			loadMusic("audio/flappy_bgm.wav");
			clipBackGroundMusic.start();
		}
		if (!started) {
			started = true;
		} else if (!gameOver) {
			if (yMotion > 0) {
				yMotion = 0;
			}
			yMotion -= 10;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
// TODO Auto-generated method stub
		ticks++;
		int speed = 10;
		if (started) {
			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				column.x -= speed;
			}
			if (ticks % 2 == 0 && yMotion < 15) {
				yMotion += 2;
			}
			for (int i = 0; i < columns.size(); i++) {
				Rectangle column = columns.get(i);
				if (column.x + column.width < 0) {
					columns.remove(column);
					if (column.y == 0) {
						addColumn(false);
					}
				}
			}
			bird.y += yMotion;
			for (Rectangle column : columns) {
				if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 10
						&& bird.x + bird.width / 2 < column.x + column.width / 2 + 10) {
					score++;
				}
				if (column.intersects(bird)) // 칼럼과 새가 충동할 때 (교집합의의미)
				{
					gameOver = true;
					if (bird.x <= column.x) {
						bird.x = column.x - bird.width;
					} else {
						if (column.y != 0) {
							bird.y = column.y - bird.height;
						} else if (bird.y < column.height) {
							bird.y = column.height;
						}
					}
				}
			}
			if (bird.y > HEIGHT - 120 || bird.y < 0) {
				gameOver = true;
			}
			if (bird.y + yMotion >= HEIGHT - 120) {
				bird.y = HEIGHT - 120 - bird.height;
			}
		}
		renderer.repaint();
	}

	public void repaint(Graphics g) {
// TODO Auto-generated method stub
		ImageIcon i4 = new ImageIcon("image/background2.png");
		Image image4 = i4.getImage();
		g.drawImage(image4, 0, 0, WIDTH, HEIGHT, 0, 0, 1251, 702, null);
		ImageIcon i3 = new ImageIcon("image/santa2.png");
		Image image3 = i3.getImage();
		g.drawImage(image3, bird.x, bird.y, bird.x + bird.width, bird.y + bird.height, 120, 20, 10, 95, null);
		for (Rectangle column : columns) {
			paintColumn(g, column);
		}
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 100));
		if (!started) {
			g.drawString("Click to start!", 75, HEIGHT / 2 - 50);
		}
		if (gameOver) {
			g.drawString("Game Over!", 450, HEIGHT / 2 - 50);
		}
		if (gameOver && !gameOverSound) {
			g.drawString("Game Over!", 450, HEIGHT / 2 - 50);
			clipBackGroundMusic.stop();
			loadAudio("audio/flappy_dies.wav");
			clip.start();
			gameOverSound = true;
		}
		if (!gameOver && started) {
			g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
// TODO Auto-generated method stub
		jump();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();
			loadAudio("audio/flappy_jump.wav");
			clip.start();
		}
	}

private void loadMusic(String pathName) {
try {
clipBackGroundMusic = AudioSystem.getClip();
File audioFile = new File(pathName);
AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
clipBackGroundMusic.open(audioStream);
}
catch (LineUnavailableException e) { e.printStackTrace(); }
catch (UnsupportedAudioFileException e) { e.printStackTrace(); }
catch (IOException e) { e.printStackTrace(); }
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
	public void mouseEntered(MouseEvent arg0) {
// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
// TODO Auto-generated method stub
	}
}