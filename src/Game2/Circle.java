package Game2;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.atomic.AtomicInteger;

public class Circle extends Barrier
{
	public double r;
	public boolean add;
	public Circle() {}
    public Circle(double _x,double _y,double _vx,double _vy,int _collisionnum,double _r,boolean _add) {
        x=_x;
        y=_y;
        vx=_vx;
        vy=_vy;
        type=CIRCLE;
        r=_r;
        collisionnum=new AtomicInteger(_collisionnum);
        add = _add;
    }
    public Circle(double _x,double _y,double _vx,double _vy,int _collisionnum,double _r) {
        x=_x;
        y=_y;
        vx=_vx;
        vy=_vy;
        type=CIRCLE;
        r=_r;
        collisionnum=new AtomicInteger(_collisionnum);
        add = false;
    }
	@Override
	public void paintBarrier(Graphics g) {	
		if(y > 960)return;
		 if(collisionnum.get()>100) {
				g.setColor(Color.red);
			}
			else if(collisionnum.get()>50) {
				g.setColor(Color.yellow);
			}
			else g.setColor(Color.PINK);
		 g.drawOval((int)(x-r), (int)(y-r), 2*(int)r,2*(int)r);
		 g.fillOval((int)(x-r), (int)(y-r), 2*(int)r,2*(int)r);
		 g.setColor(Color.BLACK);
		 if(add) {
			 g.drawString("Add", (int)x, (int)y);
			 return;
		 }
		 super.paintBarrier(g);
    }
}
