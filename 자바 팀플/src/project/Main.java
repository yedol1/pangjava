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
	Clip clip; // 오디오 클립 만들기

	public Main() {
		setTitle("1 조");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container container = getContentPane();
		container.setLayout(null);
		/*
		 * 백그라운드 이미지
		 */
		bgImage = new ImageIcon("image/main.png");
		bgLabel = new JLabel(bgImage);
		loadAudio("audio/main.wav"); // loadAudio 함수 호출
		clip.start(); // 오디오 클립 실행
		clip.loop(clip.LOOP_CONTINUOUSLY); // 오디오 클립 무한 반복 : 음악이 끝나면처음부터 다시 반복해줍니다.
		/*
		 * 버튼 1 과 게임 1 연결
		 */
		JButton btn1 = new JButton("폭탄 피하기");
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
		 * 버튼 위치 및 크기 설정 버튼 색깔 지정 버튼 글자 색깔 지정
		 */
		btn1.setBounds(203, 620, 131, 87);
		btn1.setBackground(Color.RED);
// btn1.setForeground(Color.WHITE);
		/*
		 * 버튼 2 과 게임 2 연결운전
		 */
		JButton btn2 = new JButton("운전 면허 시험");
		btn2.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
// TODO Auto-generated method stub
				clip.stop();
				FlappyBird.flappyBird = new FlappyBird();
			}
		});
		/*
		 * 버튼 위치 및 크기 설정 버튼 색깔 지정 버튼 글자 색깔 지정
		 */
		btn2.setBounds(334, 583, 143, 124);
		btn2.setBackground(Color.BLUE);
// btn2.setForeground(Color.WHITE);
		/*
		 * 버튼 3 과 게임 3 연결
		 */
		JButton btn3 = new JButton("백수퇴치");
		btn3.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				clip.stop();
				AdultFilter.adultfilter = new AdultFilter();
			}
		});
		/*
		 * 버튼 위치 및 크기 설정 버튼 색깔 지정 버튼 글자 색깔 지정
		 */
		btn3.setBounds(477, 620, 120, 87);
		btn3.setBackground(Color.GREEN);
// btn3.setForeground(Color.WHITE);
		/*
		 * 배경화면
		 */
		bgLabel.setBounds(0, 0, 790, 800);
		/*
		 * 컨테이너에 버튼 붙이기
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
	 * loadAudio 함수 오디오 클립에 오디오 스트림 객체를 AudioInputStream 으로 생성한 후 오디오 클립과 오디오 스트림을
	 * clip.open() 으로 연결한다. 이 함수를 거친 후 clip 은 오디오 스트림으로부터 오디오 데이터를 받아 재생할 수 있는 상태가
	 * 된다. clip.start(); 메서드를 호출하면 오디오 재생을 시작한다.
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