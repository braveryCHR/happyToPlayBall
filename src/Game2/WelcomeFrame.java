package Game2;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class WelcomeFrame
{
    public static JFrame welcomeFrame = new JFrame();
    public static PicturePanel picturePanel = new PicturePanel();

    // 初始化窗口
    public static void initFrame() {
        // 利用JPanel添加背景图片
        welcomeFrame.setSize(560, 1000);
        welcomeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        welcomeFrame.setTitle("弹弹");
        welcomeFrame.setVisible(true);
        welcomeFrame.add(picturePanel);
        welcomeFrame.setLocationRelativeTo(null);
        welcomeFrame.setSize(560, 1000);
        picturePanel.setVisible(true);
    }

    static void bgmusicStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    MusicPlay2 backgroundMusic = new MusicPlay2(
                            WelcomeFrame.class.getResourceAsStream("/backgroundMusic2.wav"));
                    try {
                        backgroundMusic.play();
                    } catch (Exception e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(93000);
                    } catch (InterruptedException e) {
                        // TODO 自动生成的 catch 块
                        e.printStackTrace();
                    }
                }
                // 使用音频播放器播放声音
            }
        }).start();
        return;
    }

    public static void main(String[] args) throws Exception {
        initFrame();
        bgmusicStart();
        GameStart.gameMode = 0;
        PicturePanel.easyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                GameStart.gameMode = 0;
                GameStart.ballSpeed = 2.0;
                PicturePanel.gameModeLabel
                        .setText("<html><p><font size=\"5\">您目前选择了入门模式<br>请放心娱乐~" + "</font></p></html>");
            }
        });
        PicturePanel.middleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                GameStart.gameMode = 1;
                GameStart.ballSpeed = 0.9;
                PicturePanel.gameModeLabel
                        .setText("<html><p><font size=\"5\">您目前选择了进阶模式<br>请锻炼技巧~" + "</font></p></html>");
            }
        });
        PicturePanel.hardButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                GameStart.gameMode = 2;
                GameStart.ballSpeed = 0.9;
                PicturePanel.gameModeLabel
                        .setText("<html><p><font size=\"5\">您目前选择了地狱模式<br>请大胆冒险~" + "</font></p></html>");
            }
        });
        PicturePanel.startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                try {
                    System.out.println("12321");
                    GameStart.mainFrame.setVisible(true);
                    welcomeFrame.setVisible(false);
                } catch (Exception e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
            }
        });
        GameStart.gameInit();
        GameStart.gameStart();
    }
}

class PicturePanel extends JPanel
{
    ImageIcon icon = new ImageIcon(this.getClass().getResource("/welcomeImage.jpg"));
    Image img = icon.getImage();
    public static JButton easyButton = new JButton("无阻力\n入门模式");
    public static JButton middleButton = new JButton("重力场\n进阶模式");
    public static JButton hardButton = new JButton("磁力场\n地狱模式");
    public static JButton startButton = new JButton("开始游戏");
    public static JLabel gameModeLabel = new JLabel(
            "<html><p><font size=\"5\">您目前选择了入门模式<br>请放心娱乐~" + "</font></p></html>");

    @Override
    protected void paintComponent(Graphics g) {
        setLayout(null);
        // TODO 自动生成的方法存根
        super.paintComponent(g);
        setSize(560, 960);
        g.drawImage(img, 0, 0, 560, 960, icon.getImageObserver());
        add(gameModeLabel);
        gameModeLabel.setSize(200, 100);
        gameModeLabel.setVisible(true);
        gameModeLabel.setLocation(200, 550);
        gameModeLabel.setForeground(Color.red);
        // 添加三种模式，第一无阻力模式
        add(easyButton);
        easyButton.setSize(180, 50);
        easyButton.setVisible(true);
        easyButton.setLocation(180, 650);
        // 重力模式
        add(middleButton);
        middleButton.setSize(180, 50);
        middleButton.setVisible(true);
        middleButton.setLocation(80, 720);
        // 电磁场模式
        add(hardButton);
        hardButton.setSize(180, 50);
        hardButton.setVisible(true);
        hardButton.setLocation(290, 720);
        add(startButton);
        startButton.setSize(180, 50);
        startButton.setVisible(true);
        startButton.setLocation(180, 790);
    }
}
