package Game2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.atomic.AtomicInteger;

public class Square extends Barrier {
	public double[] px;
	public double[] py;
	public double r;
	public double theta;// 正方形的放置角度
	

	public Square() {
	}

	public Square(double _x, double _y, double _vx, double _vy, int _collisionnum, double _r, double _theta) {
		x = _x;
		y = _y;
		vx = _vx;
		vy = _vy;
		type = SQUARE;
		r = _r;
		theta = _theta;
		collisionnum=new AtomicInteger(_collisionnum);
		px = new double[4];
		py = new double[4];
		px[0] = x + r * Math.cos(theta);
		px[1] = x + r * Math.cos(theta + Math.PI / 2);
		px[2] = x + r * Math.cos(theta + Math.PI);
		px[3] = x + r * Math.cos(theta + Math.PI / 2 * 3);
		py[0] = y + r * Math.sin(theta);
		py[1] = y + r * Math.sin(theta + Math.PI / 2);
		py[2] = y + r * Math.sin(theta + Math.PI);
		py[3] = y + r * Math.sin(theta + Math.PI / 2 * 3);
	}

	@Override
	public void paintBarrier(Graphics g) {
		if(y>960)return;
		int[] _px = new int[4];
		int[] _py = new int[4];
		px[0] = x + r * Math.cos(theta);
		px[1] = x + r * Math.cos(theta + Math.PI / 2);
		px[2] = x + r * Math.cos(theta + Math.PI);
		px[3] = x + r * Math.cos(theta + Math.PI / 2 * 3);
		py[0] = y + r * Math.sin(theta);
		py[1] = y + r * Math.sin(theta + Math.PI / 2);
		py[2] = y + r * Math.sin(theta + Math.PI);
		py[3] = y + r * Math.sin(theta + Math.PI / 2 * 3);
		for (int i = 0; i < 4; i++) {
			_px[i] = (int) px[i];
			_py[i] = (int) py[i];
		}
	
		if(collisionnum.get()>100) {
			g.setColor(Color.red);
		}
		else if(collisionnum.get()>50) {
			g.setColor(Color.yellow);
		}
		else g.setColor(Color.BLUE);
		g.drawPolygon(_px, _py, 4);
		g.fillPolygon(_px, _py, 4);
		super.paintBarrier(g);
		g.setColor(Color.BLACK);
		
	}
}
