package Game2;

import java.awt.Graphics;
import javax.swing.JPanel;

public class scorepanel extends JPanel {
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, this.getSize().width, this.getSize().height);
		g.drawString(""+GameStart.score+"",
				30,  this.getSize().height/2);
	}
	
	String getText() {
	    Integer res = GameStart.score.get();
	    return res.toString();
	}
}
class Showscore extends Thread{
	scorepanel scopanel;
	public Showscore(scorepanel scopanel_) {
		scopanel = scopanel_;
	}
	public void run() {
		while(true) {
			scopanel.repaint();
			try {
				Thread.sleep(GameStart.screenStep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}