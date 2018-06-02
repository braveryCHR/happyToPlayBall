package Game2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.concurrent.atomic.AtomicInteger;

public class Barrier {
	public static final int TRIANGLE=10;
	public static final int SQUARE=11;
	public static final int CIRCLE=12;
	public static final double totalDis = 100;
	public double x;
	public double y;
    public double vx;
    public double vy;
    public boolean isMov = false; //是否正在移动
    public double dis = 0;	//已经移动的距离
    int type;
    public AtomicInteger collisionnum;
    public Barrier() {}
    public Barrier(double _x,double _y,double _vx,double _vy,int _collisionnum) {
        x=_x;
        y=_y;
        vx=_vx;
        vy=_vy;
        collisionnum=new AtomicInteger(_collisionnum);
    }
    public void updatePos() {
    	dis += vy*GameStart.ballStep;
        y = y - vy*GameStart.ballStep;
    }
    public void setSpeed(double _vx,double _vy) {
        vx=_vx;
        vy=_vy;
    }
    public void paintBarrier(Graphics g) {
    	g.setColor(Color.WHITE);
    		g.drawString(""+collisionnum.get(), (int)x -2, (int)y);
    		g.setColor(Color.BLACK);
    }
    public void paintnum(Graphics2D g ) {
    	g.drawString(""+collisionnum.get(), (int)x -1, (int)y);
    }
}
