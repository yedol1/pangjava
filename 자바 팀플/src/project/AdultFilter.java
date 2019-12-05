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

public class AdultFilter implements ActionListener, KeyListener {
	public static AdultFilter adultfilter;
	public final int WIDTH = 400, HEIGHT = 600;
	public Kidpanel kidpanel;
	public Rectangle kid;
	public Rectangle kid1;
	public Rectangle adult1;
	public Rectangle adult;
	public ArrayList<Rectangle> kidadult;
	public Random rand;
	public boolean gameOver, started = true;
	public boolean gameOverSound = false;
	public int score, life = 3, buttonclick;
	public Clip clipBackGroundMusic; // 배경음악 클립
	public Clip clip; // 효과음 클립

	public AdultFilter() {
		JFrame jframe = new JFrame();
		Timer timer = new Timer(20, this);
		kidpanel = new Kidpanel();
		rand = new Random();
		jframe.add(kidpanel);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setSize(WIDTH, HEIGHT);
		jframe.setResizable(false);
		jframe.addKeyListener(this);
		jframe.setTitle("산타의 백수 퇴치");
		kid = new Rectangle(170, 100, 64, 64);
		kid1 = new Rectangle(170, 140, 64, 64);
		adult = new Rectangle(170, 120, 64, 64);
		adult1 = new Rectangle(170, 160, 64, 64);
		kidadult = new ArrayList<Rectangle>();
		kidadult.add(kid);
		kidadult.add(adult);
		kidadult.add(kid);
		kidadult.add(adult);
		kidadult.add(kid);
		kidadult.add(adult);
		kidadult.add(kid);
		timer.start();
		loadMusic("audio/adult_bgm.wav");
		clipBackGroundMusic.start();
	}

	public void repaint(Graphics g) {
		ImageIcon i6 = new ImageIcon("image/background4.png");
		Image image6 = i6.getImage();
		g.drawImage(image6, 0, 0, WIDTH, HEIGHT, 0, 0, 480, 750, null);
		if (buttonclick == -1) {
			ImageIcon i3 = new ImageIcon("image/button.png");
			Image image3 = i3.getImage();
			ImageIcon i4 = new ImageIcon("image/button1.png");
			Image image4 = i4.getImage();
			g.drawImage(image4, 30, 400, 94, 464, 64, 0, 0, 64, null);
			g.drawImage(image3, 280, 400, 344, 464, 0, 0, 64, 64, null);
		}
		if (buttonclick == 1) {
			ImageIcon i3 = new ImageIcon("image/button.png");
			Image image3 = i3.getImage();
			ImageIcon i4 = new ImageIcon("image/button1.png");
			Image image4 = i4.getImage();
			g.drawImage(image3, 30, 400, 94, 464, 64, 0, 0, 64, null);
			g.drawImage(image4, 280, 400, 344, 464, 0, 0, 64, 64, null);
		}
		if (buttonclick == 0) {
			ImageIcon i3 = new ImageIcon("image/button.png");
			Image image3 = i3.getImage();
			g.drawImage(image3, 30, 400, 94, 464, 64, 0, 0, 64, null);
			g.drawImage(image3, 280, 400, 344, 464, 0, 0, 64, 64, null);
		}
		ImageIcon i1 = new ImageIcon("image/kid.png");
		Image image1 = i1.getImage();
		ImageIcon i2 = new ImageIcon("image/adult.png");
		Image image2 = i2.getImage();
		ImageIcon i3 = new ImageIcon("image/kid1.png");
		Image image3 = i3.getImage();
		ImageIcon i4 = new ImageIcon("image/adult1.png");
		Image image4 = i4.getImage();
		if (score > 50) {
			g.drawImage(image3, 30, 220, 90, 280, 0, 0, 130, 130, null);
			g.drawImage(image4, 280, 220, 340, 280, 0, 0, 130, 130, null);
		}
		g.drawImage(image1, 30, 320, 90, 380, 0, 0, 130, 130, null);
		g.drawImage(image2, 280, 320, 340, 380, 0, 0, 130, 130, null);
		for (int i = 0; i < life; i++) {
			ImageIcon i5 = new ImageIcon("image/like.png");
			Image image5 = i5.getImage();
			g.drawImage(image5, 30 + i * 40, 30, 60 + i * 40, 60, 0, 0, 130, 130, null);
		}
		for (int i = kidadult.size() - 1; i >= 0; i--) {
			Rectangle kidadult1 = kidadult.get(i);
			if (score < 50) {
				if (kidadult1 == kid) {
					g.drawImage(image1, kid.x, HEIGHT - i * 40 - 230, kid.x + kid.width,
							HEIGHT - i * 40 - 230 + kid.height, 0, 0, 128, 128, null);
				}
				if (kidadult1 == adult) {
					g.drawImage(image2, adult.x, HEIGHT - i * 40 - 230, adult.x + adult.width,
							HEIGHT - i * 40 - 230 + adult.height, 0, 0, 128, 128, null);
				}
			} else {
				if (kidadult1 == kid) {
					g.drawImage(image1, kid.x, HEIGHT - i * 40 - 230, kid.x + kid.width,
							HEIGHT - i * 40 - 230 + kid.height, 0, 0, 128, 128, null);
				}
				if (kidadult1 == adult) {
					g.drawImage(image2, adult.x, HEIGHT - i * 40 - 230, adult.x + adult.width,
							HEIGHT - i * 40 - 230 + adult.height, 0, 0, 128, 128, null);
				}
				if (kidadult1 == kid1) {
					g.drawImage(image3, kid.x, HEIGHT - i * 40 - 230, kid.x + kid.width,
							HEIGHT - i * 40 - 230 + kid.height, 0, 0, 128, 128, null);
				}
				if (kidadult1 == adult1) {
					g.drawImage(image4, adult.x, HEIGHT - i * 40 - 230, adult.x + adult.width,
							HEIGHT - i * 40 - 230 + adult.height, 0, 0, 128, 128, null);
				}
			}
		}
// ImageIcon i5 = new ImageIcon("image/santaback.png");
// Image image5 = i5.getImage();
// g.drawImage(image5, 140, 470, 270, 590, 0, 0, 1372, 1378, null);
//
		g.setColor(Color.white);
		g.setFont(new Font("Arial", 1, 30));
		if (!gameOver && started) {
			g.drawString("Score", WIDTH - 130, 40);
			g.drawString(String.valueOf(score), WIDTH - 93, 70);
		}
		if (gameOver) {
			g.setFont(new Font("Arial", 1, 45));
			g.drawString("Game Over!", 70, HEIGHT / 2);
		}
		if (gameOver && !gameOverSound) {
			g.setFont(new Font("Arial", 1, 45));
			g.drawString("Game Over!", 70, HEIGHT / 2);
			clipBackGroundMusic.stop();
			loadAudio("audio/snow_dies.wav");
			clip.start();
			gameOverSound = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
// TODO Auto-generated method stub
		kidpanel.repaint();
	}

	public void leftmove() {
		if (gameOver) {
			score = 0;
			life = 3;
			kidadult.clear();
			kidadult.add(kid);
			kidadult.add(adult);
			kidadult.add(kid);
			kidadult.add(adult);
			kidadult.add(kid);
			kidadult.add(adult);
			kidadult.add(kid);
			gameOver = false;
			gameOverSound = false;
			clipBackGroundMusic.stop();
			loadMusic("audio/adult_bgm.wav");
			clipBackGroundMusic.start();
		} else {
			if (score < 50) {
				if (kidadult.get(0) == kid) {
					score++;
					kidadult.remove(0);
					loadAudio("audio/adult_correct.wav");
					clip.start();
					int random = rand.nextInt(2);
					if (random == 0) {
						kidadult.add(adult);
					} else {
						kidadult.add(kid);
					}
				} else {
					life--;
					kidadult.remove(0);
					loadAudio("audio/adult_wrong.wav");
					clip.start();
					int random = rand.nextInt(2);
					if (random == 0) {
						kidadult.add(adult);
					} else {
						kidadult.add(kid);
					}
				}
				if (life <= 0) {
					gameOver = true;
				}
			} else {
				if (kidadult.get(0) == kid || kidadult.get(0) == kid1) {
					score++;
					kidadult.remove(0);
					loadAudio("audio/adult_correct.wav");
					clip.start();
					int random = rand.nextInt(4);
					if (random == 0) {
						kidadult.add(adult);
					} else if (random == 1) {
						kidadult.add(kid);
					} else if (random == 2) {
						kidadult.add(kid1);
					} else {
						kidadult.add(adult1);
					}
				} else {
					life--;
					kidadult.remove(0);
					loadAudio("audio/adult_wrong.wav");
					clip.start();
					int random = rand.nextInt(4);
					if (random == 0) {
						kidadult.add(adult);
					} else if (random == 1) {
						kidadult.add(kid);
					} else if (random == 2) {
						kidadult.add(kid1);
					} else {
						kidadult.add(adult1);
					}
				}
				if (life <= 0) {
					gameOver = true;
				}
			}
		}
	}

	public void rightmove() {
		if (gameOver) {
			score = 0;
			life = 3;
			kidadult.clear();
			kidadult.add(kid);
			kidadult.add(adult);
			kidadult.add(kid);
			kidadult.add(adult);
			kidadult.add(kid);
			kidadult.add(adult);
			kidadult.add(kid);
			gameOver = false;
			gameOverSound = false;
			clipBackGroundMusic.stop();
			loadMusic("audio/adult_bgm.wav");
			clipBackGroundMusic.start();
		} else {
			if (score < 50) {
				if (kidadult.get(0) == adult) {
					score++;
					kidadult.remove(0);
					loadAudio("audio/adult_correct.wav");
					clip.start();
					int random = rand.nextInt(2);
					if (random == 0) {
						kidadult.add(adult);
					} else {
						kidadult.add(kid);
					}
				} else {
					life--;
					kidadult.remove(0);
					loadAudio("audio/adult_wrong.wav");
					clip.start();
					int random = rand.nextInt(2);
					if (random == 0) {
						kidadult.add(adult);
					} else {
						kidadult.add(kid);
					}
				}
				if (life <= 0) {
					gameOver = true;
				}
			} else {
				if (kidadult.get(0) == adult || kidadult.get(0) == adult1) {
					score++;
					kidadult.remove(0);
					loadAudio("audio/adult_correct.wav");
					clip.start();
					int random = rand.nextInt(4);
					if (random == 0) {
						kidadult.add(adult);
					} else if (random == 1) {
						kidadult.add(kid);
					} else if (random == 2) {
						kidadult.add(kid1);
					} else {
						kidadult.add(adult1);
					}
				} else {
					life--;
					kidadult.remove(0);
					loadAudio("audio/adult_wrong.wav");
					clip.start();
					int random = rand.nextInt(4);
					if (random == 0) {
						kidadult.add(adult);
					} else if (random == 1) {
						kidadult.add(kid);
					} else if (random == 2) {
						kidadult.add(kid1);
					} else {
						kidadult.add(adult1);
					}
				}
				if (life <= 0) {
					gameOver = true;
				}
			}
		}
	}

	public static void main(String[] args) {
// TODO Auto-generated method stub
		adultfilter = new AdultFilter();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			buttonclick = -1;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			buttonclick = 1;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			leftmove();
			buttonclick = 0;
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			rightmove();
			buttonclick = 0;
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