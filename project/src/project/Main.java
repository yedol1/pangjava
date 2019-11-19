package project;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame {
	ImageIcon bgImage;
	JLabel bgLabel;
	Clip clip; // ����� Ŭ�� �����

	public Main() {
		setTitle("1 ��");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = getContentPane();
		container.setLayout(null);
		/*
		 * ��׶��� �̹���
		 */
		bgImage = new ImageIcon("image/main.png");
		bgLabel = new JLabel(bgImage);
		loadAudio("audio/main.wav"); // loadAudio �Լ� ȣ��
		clip.start(); // ����� Ŭ�� ����
		clip.loop(clip.LOOP_CONTINUOUSLY); // ����� Ŭ�� ���� �ݺ� : ������ ������ó������ �ٽ� �ݺ����ݴϴ�.
		/*
		 * ��ư 1 �� ���� 1 ����
		 */
		JButton btn1 = new JButton("��ź ���ϱ�");
		btn1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
// TODO Auto-generated method stub	
				clip.stop();
			//	if(FlappyBird.flappyBird.clipBackGroundMusic.isActive()==true)
			//		FlappyBird.flappyBird.clipBackGroundMusic.stop();
				Snow.snow = new Snow();
			}
		});
		/*
		 * ��ư ��ġ �� ũ�� ���� ��ư ���� ���� ��ư ���� ���� ����
		 */
		btn1.setBounds(203, 620, 131, 87);
		btn1.setBackground(Color.RED);
// btn1.setForeground(Color.WHITE);
		/*
		 * ��ư 2 �� ���� 2 �������
		 */
		JButton btn2 = new JButton("���� ���� ����");
		btn2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
// TODO Auto-generated method stub
				clip.stop();
				FlappyBird.flappyBird = new FlappyBird();
			}
		});
		/*
		 * ��ư ��ġ �� ũ�� ���� ��ư ���� ���� ��ư ���� ���� ����
		 */
		btn2.setBounds(334, 583, 143, 124);
		btn2.setBackground(Color.BLUE);
// btn2.setForeground(Color.WHITE);
		/*
		 * ��ư 3 �� ���� 3 ����
		 */
		JButton btn3 = new JButton("�����ġ");
		btn3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				clip.stop();
				AdultFilter.adultfilter = new AdultFilter();
			}
		});
		/*
		 * ��ư ��ġ �� ũ�� ���� ��ư ���� ���� ��ư ���� ���� ����
		 */
		btn3.setBounds(477, 620, 120, 87);
		btn3.setBackground(Color.GREEN);
// btn3.setForeground(Color.WHITE);
		/*
		 * ���ȭ��
		 */
		bgLabel.setBounds(0, 0, 790, 800);
		/*
		 * �����̳ʿ� ��ư ���̱�
		 */
		container.add(btn1);
		container.add(btn2);
		container.add(btn3);
// container.add(btn4);
		container.add(bgLabel);
		setSize(780, 800);
		setVisible(true);
	}

	/*
	 * loadAudio �Լ� ����� Ŭ���� ����� ��Ʈ�� ��ü�� AudioInputStream ���� ������ �� ����� Ŭ���� ����� ��Ʈ����
	 * clip.open() ���� �����Ѵ�. �� �Լ��� ��ģ �� clip �� ����� ��Ʈ�����κ��� ����� �����͸� �޾� ����� �� �ִ� ���°�
	 * �ȴ�. clip.start(); �޼��带 ȣ���ϸ� ����� ����� �����Ѵ�.
	 */
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

	public static void main(String[] args) {
		new Main();
	}
}