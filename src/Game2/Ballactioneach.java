package Game2;


public class Ballactioneach extends Thread {
	int collidebefore = -5;
	boolean hascollided;// 记录是否碰撞过，未碰撞过走直线。
	MainPanel mainPanel;
	Ball ball;

	public Ballactioneach(Ball ball) {
		hascollided = false;
		this.ball = ball;
		this.mainPanel = GameStart.mainPanel;
		collidebefore = ball.collidebefore;
		hascollided = ball.hascollided;
	}

	@Override
	public void run() {
		while (true) {
			try {
				BallAction.hadleCollision(ball);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ball.updatePos();
			BallAction.refreshSpeed(ball);
			if (BallAction.outOfBound(ball)) {
				if (ball.x >= GameStart.WIDTH / 2 - 2 * GameStart.INTERVAL
						&& ball.x <= GameStart.WIDTH / 2 + 2 * GameStart.INTERVAL) {
					ball.y = 0;
					ball.vy *= 0.5;
					double dice = Math.random();
					if (dice < 0.5) {
						ball.x -= GameStart.INTERVAL * (1 + 3 * Math.random());
					} else {
						ball.x += GameStart.INTERVAL * (1 + 3 * Math.random());
					}
				} else {
					System.out.println("here");
					ball.isIn = false;
					GameStart.remainBallNum.getAndDecrement();
					ball.init();
					return;
				}
			}
			try {
				Thread.sleep(GameStart.ballStep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
	