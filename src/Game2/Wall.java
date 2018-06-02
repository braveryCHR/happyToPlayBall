package Game2;

import java.awt.Graphics2D;

public class Wall
{
    int x1;
    int x2;
    int y1;
    int y2;
    public Wall(int x1,int y1,int x2,int y2) {
        this.x1=x1;
        this.x2=x2;
        this.y1=y1;
        this.y2=y2;
    }
    public void drawWall(Graphics2D gp)
    {
        gp.drawLine(x1, y1, x2, y2);
    }
}
