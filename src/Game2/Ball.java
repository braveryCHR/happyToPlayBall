package Game2;
import java.awt.Color;
import java.awt.Graphics;

public class Ball
{
    public double x;
    public double y;
    public double vx;
    public double vy;
    public double r;
    public boolean isIn ;
    public int collidebefore = -5;
    public boolean hascollided = false;
    
    public Ball(double _x,double _y,double _vx,double _vy,double _r) {
        x=_x;
        y=_y;
        vx=_vx;
        vy=_vy;
        r=_r;
        isIn = true;
    }
    
    public void InitSpeed(int mouseX,int mouseY)
    {
        double subX = mouseX - GameStart.launchPosX;
        double subY = mouseY - GameStart.launchPosY;
        double absLen = Math.sqrt((subX*subX+subY*subY));
        double vx = subX/absLen*GameStart.ballSpeed;
        double vy = subY/absLen*GameStart.ballSpeed;
        this.vx=vx;
        this.vy=vy;
    }
    public void init() {
       collidebefore = -5;
       hascollided = false;
       vx = 0;
       vy = 0;
       x = GameStart.WIDTH / 2;
       y  = GameStart.INTERVAL * 2;
       
    }
    public void updatePos() {
        x=x+vx*GameStart.ballStep;
        y=y+vy*GameStart.ballStep;
    }
    public void setSpeed(double _vx,double _vy) {
        vx=_vx;
        vy=_vy;
    }
    public void paintBall(Graphics g)
    {
        g.drawOval((int)(x-r), (int)(y-r), 2*(int)r,2*(int)r);
        g.setColor(Color.black);
        g.fillOval((int)(x-r), (int)(y-r), 2*(int)r,2*(int)r);
    }
}
