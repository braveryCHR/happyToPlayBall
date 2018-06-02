package Game2;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Screen extends Thread {
	MainPanel mp;
	Screen(MainPanel mp_){
		mp = mp_;
	}
	@Override
	public void run() {
		while (true) {
			mp.repaint();
			try {
				Thread.sleep(GameStart.screenStep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	static void repaint() {
		Graphics2D gp = (Graphics2D) GameStart.mainPanel.getGraphics();
		GameStart.mainPanel.getGraphics().clearRect(GameStart.INTERVAL + 1, GameStart.INTERVAL + 1,
				GameStart.WIDTH - 2 * GameStart.INTERVAL - 2, GameStart.HEIGHT - 3 * GameStart.INTERVAL - 2);
		gp.clipRect(GameStart.INTERVAL + 1, GameStart.INTERVAL + 1, GameStart.WIDTH - 2 * GameStart.INTERVAL - 2,
				GameStart.HEIGHT - 3 * GameStart.INTERVAL - 2);
		// »­Ö¸Ê¾Ïß
		if (!GameStart.isLaunch) {

			GameStart.mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					if (!GameStart.isLaunch) {
					    System.out.println("I am sb");
						int mouseX = e.getX();
						int mouseY = e.getY();
						gp.clipRect(GameStart.INTERVAL + 1, GameStart.INTERVAL + 1,
								GameStart.WIDTH - 2 * GameStart.INTERVAL - 2, 250);
						gp.drawLine(GameStart.WIDTH / 2, GameStart.INTERVAL * 2, mouseX, mouseY);
					}
				}
			});

		}
		synchronized (GameStart.barriers)
		{
			for (Barrier b : GameStart.barriers) {
				if (b.collisionnum.get() > 0) {
					b.paintBarrier(gp);
					
				}
			}
		}

		for (Ball b : GameStart.balls) {
			if (b.isIn)
				b.paintBall(gp);
		}
		drawWalls();
		 String posText = GameStart.mousePosField.getText();
         System.out.println(posText);
		if (GameStart.isStart&&!GameStart.isLaunch) {
		    //String posText = GameStart.mousePosField.getText();
		    System.out.println(posText);
		}
	}

	public static void drawBarriers() {

		GameStart.barriers.add(new Triangle(250, 300, 0, 0, 25, 25, 2));
		Graphics2D gp = (Graphics2D) GameStart.mainPanel.getGraphics();
		for (Barrier b : GameStart.barriers) {
			b.paintBarrier(gp);
		}
	}

	public static void drawWalls() {
		Graphics2D gp = (Graphics2D) GameStart.mainPanel.getGraphics();
		GameStart.horBottomWall.drawWall(gp);
		GameStart.horTopWall.drawWall(gp);
		GameStart.verLeftWall.drawWall(gp);
		GameStart.verRightWall.drawWall(gp);
	}
	
	public static void drawWall(Graphics g) {
		Graphics2D gp = (Graphics2D)g;
		GameStart.horBottomWall.drawWall(gp);
		GameStart.horTopWall.drawWall(gp);
		GameStart.verLeftWall.drawWall(gp);
		GameStart.verRightWall.drawWall(gp);
	}

}
