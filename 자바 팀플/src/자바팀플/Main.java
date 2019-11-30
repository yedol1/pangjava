package �ڹ�����;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Main extends JFrame {
	ImageIcon bgImage;
	JLabel bgLabel;
	Clip clip; // ����� Ŭ�� �����

	public Main() {
		setTitle("�ö��� ���");
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
		JButton btn1 = new JButton("START!");
		btn1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
// TODO Auto-generated method stub	
				clip.stop();
				// if(FlappyBird.flappyBird.clipBackGroundMusic.isActive()==true)
				// FlappyBird.flappyBird.clipBackGroundMusic.stop();
				Fish.fish = new Fish();// �������ӿ���
			}
		});

		// ��ó: https://betatester.tistory.com/26 [It's My Real Life]
		/*
		 * ��ư ��ġ �� ũ�� ���� ��ư ���� ���� ��ư ���� ���� ����
		 */
		btn1.setBounds(223, 673, 93, 64);
		btn1.setBackground(Color.RED);
// btn1.setForeground(Color.WHITE);
		/*
		 * ��ư 2 �� ���� 2 �������
		 */
		/*
		 * JButton btn2 = new JButton("���� ���� ����"); btn2.addMouseListener(new
		 * MouseAdapter() {
		 * 
		 * @Override public void mousePressed(MouseEvent e) { /* TODO Auto-generated
		 * method stub clip.stop(); FlappyBird.flappyBird = new FlappyBird(); } });
		 */
		/*
		 * ��ư ��ġ �� ũ�� ���� ��ư ���� ���� ��ư ���� ���� ����
		 */
		/*
		 * btn2.setBounds(334, 583, 143, 124); btn2.setBackground(Color.BLUE);
		 */
// btn2.setForeground(Color.WHITE);
		/*
		 * ��ư 3 �� ���� 3 ����
		 */
		/*
		 * JButton btn3 = new JButton("�����ġ"); btn3.addMouseListener(new MouseAdapter()
		 * {
		 * 
		 * @Override public void mousePressed(MouseEvent e) { clip.stop();
		 * AdultFilter.adultfilter = new AdultFilter(); } });
		 */
		/*
		 * ��ư ��ġ �� ũ�� ���� ��ư ���� ���� ��ư ���� ���� ����
		 */
		/*
		 * btn3.setBounds(477, 620, 120, 87); btn3.setBackground(Color.GREEN);
		 */
// btn3.setForeground(Color.WHITE);
		/*
		 * ���ȭ��
		 */
		bgLabel.setBounds(0, 0, 800, 1000);// ���ȭ�� ũ�⼳��
		/*
		 * �����̳ʿ� ��ư ���̱�
		 */
		container.add(btn1);
		// container.add(btn2);
		// container.add(btn3);
// container.add(btn4);
		container.add(bgLabel);
		setSize(800, 1000);// �����̳�ũ�⼳��
		screenSizeLocation(); // ȭ���� �׻� ����ο��Ը���
		setVisible(true);
		// 1. ������Ʈ ����

		JTextField jtf = new JTextField();// 2. ��ũ�ѹٰ� ���� ������Ʈ�� ��ũ�ѹ� �߰�
		// JScrollPane jsp = new JScrollPane(������Ʈ��ü��);
		JScrollPane jsp = new JScrollPane(jtf);

		// 3. ������������Ʈ�� ����
		add("Center", jsp);

	}

	public void screenSizeLocation() {
		Dimension frameSize = getSize(); // ������Ʈ ũ��
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // ����� ȭ���� ũ�� ���ϱ� // (�����ȭ�� ���� - ������ȭ�� ����) /
																			// 2, (�����ȭ�� ���� - ������ȭ�� ����) / 2
		setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
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