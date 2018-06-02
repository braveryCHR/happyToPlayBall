package Game2;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class GameStart
{
    public static final int ballStep = 3;// 小球位置多久刷新一次
    public static final int screenStep = 5;// 屏幕多久刷新一次
    public static double ballSpeed = 1.8;// 小球初速度
    public static final int squareSize = 30;// 方形尺寸
    public static final int circleSize = 30;// 圆形尺寸
    public static final int triangleSize = 30;// 三角形尺寸
    public static final int ballSize = 13;// s小球尺寸
    public static final double gravity = 0.003;// 重力产生的加速度
    public static int gameMode = 2;// 游戏模式
    public static final int WIDTH = 560;
    public static final int HEIGHT = 870;
    public static final int INTERVAL = 40;
    public static final int launchPosX = 280; // 小球发射的坐标
    public static final int launchPosY = 50;
    public static final double wallColliLoss = 0.99;// 和墙碰撞之后的速度余量
    public static final double envOppoLoss = 0.99;// 环境阻力
    public static final int launchInterval = 100;
    public static final double platLength = 1.0;//黑洞长度系数 
    static boolean isLaunch = false;
    static boolean isStart = false;
    public static Vector<Ball> balls = new Vector<Ball>();
    public static Vector<Barrier> barriers = new Vector<Barrier>();
    public static Vector<Ball> balls2 = new Vector<Ball>();// 存新增加的球
    static MainFrame mainFrame = new MainFrame();
    static MainPanel mainPanel = new MainPanel(mainFrame);
    static scorepanel scorePanel = new scorepanel();;
    public static JTextField scoreBoard;
    public static TextField mousePosField = new TextField("NaN,NaN");
    public static AtomicInteger score = new AtomicInteger(0);// 得分
    public static AtomicInteger remainBallNum;// 未结束运动的小球数
    public static AtomicInteger curBallNum;// 当前玩家的总小球数目
    public static AtomicInteger reamainBarrier;
    public static AtomicInteger barrierNum;
    public static AtomicInteger remainbarr;
    static Wall verLeftWall = new Wall(INTERVAL, INTERVAL, INTERVAL, HEIGHT - INTERVAL);
    static Wall verRightWall = new Wall(WIDTH - INTERVAL, INTERVAL, WIDTH - INTERVAL, HEIGHT - INTERVAL);
    static Wall horTopWall = new Wall(INTERVAL, INTERVAL, WIDTH - INTERVAL, INTERVAL);
    static Wall horBottomWall = new Wall(INTERVAL, HEIGHT - INTERVAL, WIDTH - INTERVAL, HEIGHT - INTERVAL);
    static int rounds = 0;
    static int ballnumTobe;
    public static MusicPlay2 crashMusic;
    public static MusicPlay backgroundMusic;
    public static HgihScore highScoreDialog = new HgihScore();
    public static getYourName getNameDialog = new getYourName();
    public static ArrayList<UserScore> userScores = new ArrayList<UserScore>();
    public static JButton startButton = new JButton("开始");
    public static JButton scoreButton = new JButton("高分");
    public static JButton returnButton = new JButton("返回");
    public static Showscore showScore = new Showscore(scorePanel);
    public static int middlePos = 280;

    public static void gameStart() throws Exception {
        // 游戏主要的流程框架
        while (true) {
            if (!isStart) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            while (true) {
                getNewBarr();
                while (remainBallNum.get() > 0) {
                    try {
                        Thread.sleep(ballStep);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                movBarriers();
                if (test()) {
                    getNameDialog.setVisible(true);
                    break;
                }
                huanyuan();
                rounds++;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
            clear();
        }
    }

    public static void lanchBalls(int mouseX, int mouseY) {
        new Thread(new Runnable() {
            public void run() {
                for (Ball b : GameStart.balls) {
                    b.InitSpeed(mouseX, mouseY);
                    try {
                        Thread.sleep(launchInterval);
                    } catch (InterruptedException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        }).start();
        return;
    }

    public static void gameInit() {
        Font font = new Font("楷体", Font.PLAIN, 18);
        initGobalFont(font);
        mainFrame.setVisible(false);
        highScoreDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        highScoreDialog.setLocationRelativeTo(null);
        getNameDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        getNameDialog.setLocationRelativeTo(null);
        mainFrame.setLayout(null);
        // mainFrame.setVisible(true);
        // 添加主要游戏区域
        mainFrame.add(mainPanel);
        mainPanel.setBounds(0, 0, WIDTH, HEIGHT);
        mainPanel.setLayout(null);
        // 添加分数面板
        mainFrame.add(scorePanel);
        scorePanel.setBounds(330, HEIGHT + 10, 90, 30);
        Thread scorethread = new Thread(showScore);
        scorethread.start();
        // 添加开始游戏按钮
        mainFrame.add(startButton);
        startButton.setBounds(10, HEIGHT + 10, 100, 30);
        // 添加高分榜按钮
        mainFrame.add(scoreButton);
        scoreButton.setBounds(120, HEIGHT + 10, 100, 30);
        // 添加鼠标位置区域
        mousePosField.setBounds(230, HEIGHT + 10, 90, 30);
        mousePosField.setFont(null);
        mainFrame.add(mousePosField);
        // 添加返回按钮
        mainFrame.add(returnButton);
        returnButton.setBounds(430, HEIGHT + 10, 100, 30);
        // welcomePanel.setVisible(true);
        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                mainFrame.setVisible(false);
                WelcomeFrame.welcomeFrame.setVisible(true);
                clear();
                System.out.println("2222222");
                // init();
            }
        });
        scoreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                String res = "";
                int count = 1;
                for (UserScore ele : userScores) {
                    res += count;
                    res += ": ";
                    res += ele.name;
                    res += "  ";
                    res += ele.score;
                    res += "\n";
                }
                if (res.equals(""))
                    res += "无人上榜\n";
                System.out.println(res);
                highScoreDialog.userScoreArea.setText(res);
                highScoreDialog.setVisible(true);
                mainFrame.setFocusable(true);
            }
        });
        // 点击start游戏开始
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                init();
                score = new AtomicInteger(0);
                isStart = true;
                mainPanel.setFocusable(true);
            }
        });
        // 显示鼠标位置
        mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int mouseX = e.getX();
                int mouseY = e.getY();
                if (!GameStart.isLaunch && isStart) {
                    mousePosField.setText(mouseX + "," + mouseY);
                }
                middlePos = mouseX;
            }
        });
        // 增加发射事件
        GameStart.mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!GameStart.isLaunch) {
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    GameStart.isLaunch = true;
                    BallAction ballAction = new BallAction();
                    Thread ballThread = new Thread(ballAction);
                    ballThread.start();
                    lanchBalls(mouseX, mouseY);
                    mainFrame.setFocusable(true);
                }
            }
        });
    }

    // 每一局开始前初始化
    static void init() {
        System.out.println("init");
        clear();
        balls.add(new Ball(WIDTH / 2, INTERVAL * 2, 0, 0, ballSize));
        balls.add(new Ball(WIDTH / 2, INTERVAL * 2, 0, 0, ballSize));
        balls.add(new Ball(WIDTH / 2, INTERVAL * 2, 0, 0, ballSize));
        GameStart.barriers.add(new Triangle(240, 700, 1, 1, 3, GameStart.triangleSize, Math.PI));
        GameStart.barriers.add(new Square(250, 400, 1, 1, 3, GameStart.squareSize, 30));
        curBallNum = new AtomicInteger(3);
        remainBallNum = new AtomicInteger(curBallNum.get());
        barrierNum = new AtomicInteger(2);
        remainbarr = new AtomicInteger(barrierNum.get());
        ballnumTobe = curBallNum.get();
        Thread.currentThread().setPriority(10);
    }

    // 每一局结束后还原
    static void clear() {
        // score = new AtomicInteger(0);
        rounds = 0;
        isStart = false;
        isLaunch = false;
        balls.clear();
        barriers.clear();
    }

    // 每一轮结束还原
    static void huanyuan() {
        remainBallNum = new AtomicInteger(curBallNum.get());
        isLaunch = false;
        for (Ball ball : balls2) {
            balls.add(ball);
        }
        balls2.clear();
        for (Ball b : balls) {
            b.x = WIDTH / 2;
            b.y = INTERVAL * 2;
            b.vx = 0;
            b.vy = 0;
            b.isIn = true;
        }
    }

    // 物体上移
    public static void movBarriers() throws InterruptedException {
        remainbarr = new AtomicInteger(barriers.size());
        for (Barrier b : barriers) {
            b.isMov = true;
            b.dis = 0;
        }
        BarrierAction barrierAction = new BarrierAction();
        Thread barrierThread = new Thread(barrierAction);
        barrierThread.start();
        barrierThread.join();
    }

    static int hardcollideNums() {
        int t = 0;
        if (rounds < 10) {
            t = (int) Math.pow(rounds, 1.4);
        } else {
            t = 4 * rounds;
        }
        return (int) (Math.random() * rounds) + t;
    }

    static int easycollideNums() {// 小于100
        if (rounds < 10) {
            return 3 + (int) (Math.random() * 5);
        } else
            return 10 + (int) (Math.random() * 20);
    }

    // 随机生成次数
    static int collideNums() {
        double chance;
        chance = Math.random();
        if (chance > 0.9) {
            return hardcollideNums();
        } else
            return easycollideNums();
    }

    static boolean addBall() {
        if (ballnumTobe <= rounds * 0.8 && ballnumTobe <= 6)
            return true;
        return false;
    }

    // 随机生成物体
    public static void getNewBarr() {
        int type;
        int time;
        int nums;
        nums = collideNums();
        int[] a = new int[6];
        for (int i = 0; i < 6; i++) {
            a[i] = 0;
        }
        time = (int) (Math.random() * 3) + 2;// 随机出个数
        for (int i = 0; i < time; ++i) {
            int tmp;
            type = (int) (Math.random() * 3);// 随机种类
            // 使随机出现较为均匀且不会重叠，将横向分为6个区域 保证每次每个区域只出现一个
            while (true) {
                tmp = (int) (Math.random() * 6);
                if (a[tmp] == 0) {
                    a[tmp]++;
                    break;
                }
            }
            int x = (int) (Math.random() * 20) + tmp * 80 + 70;
            int y = (int) (Math.random() * 10 + mainPanel.getSize().height + 10);
            double theta = Math.random() * 2 * Math.PI;
            if (addBall() || type == 0) {// circle
                nums = collideNums();
                if (addBall()) {
                    GameStart.barriers.add(new Circle(x, y, 1, 1, 1, GameStart.circleSize, true));
                    ballnumTobe++;
                } else
                    GameStart.barriers.add(new Circle(x, y, 1, 1, nums, GameStart.circleSize));
            } else if (type == 1) {// tri
                nums = collideNums();
                GameStart.barriers.add(new Triangle(x, y, 1, 1, nums, GameStart.triangleSize, theta));
            } else if (type == 2) {// sq
                nums = collideNums();
                GameStart.barriers.add(new Square(x, y, 1, 1, nums, GameStart.squareSize, theta));
            }
        }
    }

    // 测试是否结束
    static boolean test() {
        for (Barrier b : barriers) {
            if (b.collisionnum.get() > 0) {
                if (b.y - 200 <= 30)
                    return true;
            }
        }
        return false;
    }

    // 设置全局字体
    public static void initGobalFont(Font font) {
        FontUIResource fontResource = new FontUIResource(font);
        for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, fontResource);
            }
        }
    }
}
