package Game2;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class MainFrame extends JFrame
{
    public String title = "ª∂¿÷µØµØ«Ú";
    public  Graphics jg;
    
    public MainFrame() {
        super();
        init();
        jg = this.getGraphics();
    }
    
    void init()
    {
        setTitle(title);
        setSize(GameStart.WIDTH, GameStart.HEIGHT + 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.getContentPane().setBackground(java.awt.Color.LIGHT_GRAY);
    }
}
