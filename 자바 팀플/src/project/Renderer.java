package project;

import java.awt.Graphics;
import javax.swing.JPanel;

public class Renderer extends JPanel {
	private static final long serialVersionUID = 1L;

	protected void paintComponent(Graphics g) { // ���� ������Ʈ�� �ڽ��� ����� �׸��� �޼���
		super.paintComponent(g);
		FlappyBird.flappyBird.repaint(g);
	}
}