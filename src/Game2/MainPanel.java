package Game2;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;

public class MainPanel extends JPanel
{
    private MainFrame frame;

    public MainPanel(MainFrame frame) {
        super();
        this.frame = frame;
        this.setDoubleBuffered(true);
        Screen s = new Screen(this);
        Thread screenThread = new Thread(s);
        screenThread.setPriority(Thread.MAX_PRIORITY);
        screenThread.start();
    }

    @Override
    public void paintComponent(Graphics gp) {
        GameStart.mainPanel.setFocusable(true);
        gp.clearRect(0, 0, this.getSize().width, this.getSize().height);
        // 解决引导线问题
        if (GameStart.isStart && (!GameStart.isLaunch)) {
            String posStr = GameStart.mousePosField.getText();
            int num1 = 0, num2 = 0;
            int pos = posStr.indexOf(",");
            try {
                num1 = Integer.parseInt(posStr.substring(pos + 1));
                num2 = Integer.parseInt(posStr.substring(0, pos));
            } catch (Exception e) {
                // TODO: handle exception
            }
            // System.out.println(num1 + " " + num2);
            gp.drawLine(GameStart.WIDTH / 2, GameStart.INTERVAL * 2, num2, num1);
        }
        // 再次掉落的平台线条
        for (int i = 1; i <= 10; ++i) {
            gp.drawLine(GameStart.middlePos - (int) (GameStart.platLength * GameStart.INTERVAL),
                    GameStart.HEIGHT - GameStart.INTERVAL - i, GameStart.middlePos + (int) (GameStart.platLength * GameStart.INTERVAL),
                    GameStart.HEIGHT - GameStart.INTERVAL - i);
        }
        gp.setClip(GameStart.INTERVAL, GameStart.INTERVAL,
                GameStart.mainPanel.getSize().width - 2 * GameStart.INTERVAL + 1,
                GameStart.mainPanel.getSize().height - 2 * GameStart.INTERVAL + 1);
        gp.drawLine(GameStart.INTERVAL, 200, GameStart.WIDTH, 200);
        if (!GameStart.isLaunch) {
            GameStart.mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    if (!GameStart.isLaunch) {
                        int mouseX = e.getX();
                        int mouseY = e.getY();
                        gp.drawLine(GameStart.WIDTH / 2, GameStart.INTERVAL * 2, mouseX, mouseY);
                    }
                }
            });
        }
        synchronized (GameStart.barriers) {
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
        for (Ball b : GameStart.balls2) {
            if (b.isIn) {
                b.paintBall(gp);
            }
        }
        Screen.drawWall(gp);
    }
}
