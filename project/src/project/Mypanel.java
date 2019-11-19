package project;
import java.awt.Graphics;
import javax.swing.JPanel;
public class Mypanel extends JPanel
{
private static final long serialVersionUID = 1L;
protected void paintComponent(Graphics g){ //스윙 컴포넌트가 자신의 모양을 그리는메서드
super.paintComponent(g);
Snow.snow.repaint(g);
}
}