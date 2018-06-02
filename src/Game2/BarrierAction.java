package Game2;

public class BarrierAction extends Thread {
	Barrier obj;
	MainPanel mainPanel = GameStart.mainPanel;

	public BarrierAction(Barrier _obj) {
		obj = _obj;
	}
	public BarrierAction() {}
	

	public void run() {
		while (true) {
			for (Barrier b : GameStart.barriers) {
					b.updatePos();
				
			}
			Barrier b = GameStart.barriers.get(0);
			if (b.dis >= Barrier.totalDis) {	//已经完成移动
				b.isMov = false;
				GameStart.remainbarr.getAndDecrement();
				return ;
			}
			try {
				Thread.sleep(GameStart.ballStep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
