package Game2;
public class BallAction extends Thread
{
    MainPanel mainPanel;

    public BallAction(Ball ball) {
        ball.hascollided = false;
        this.mainPanel = GameStart.mainPanel;
    }

    public BallAction() {
    }

    @Override
    public void run() {
        while (true) {
            synchronized (GameStart.balls) {
                for (Ball b : GameStart.balls) {// 对每个球的碰撞情况经行处理
                    if (!b.isIn)
                        continue;// 球已出界不处理
                    try {
                        hadleCollision(b);
                    } catch (Exception e1) {
                        // TODO 自动生成的 catch 块
                        e1.printStackTrace();
                    }
                    b.updatePos();
                    refreshSpeed(b);
                    if (outOfBound(b)) {
                        if (b.x >= GameStart.middlePos - (int)(GameStart.platLength  * GameStart.INTERVAL)
                                && b.x <= GameStart.middlePos + (int)(GameStart.platLength * GameStart.INTERVAL)) {
                            b.y = 0;
                            if (GameStart.gameMode==1||GameStart.gameMode==2)
                                b.vy *= 1.5;
                            double dice = Math.random();
                            if (dice < 0.5) {
                                b.x -= GameStart.INTERVAL * (1 + 3 * Math.random());
                            } else {
                                b.x += GameStart.INTERVAL * (1 + 3 * Math.random());
                            }
                        } else {
                            b.isIn = false;
                            GameStart.remainBallNum.getAndDecrement();
                            b.init();
                            continue;
                        }
                    }
                }
            }
            if (GameStart.remainBallNum.get() == 0)
                return;
            try {
                Thread.sleep(GameStart.ballStep);
            } catch (InterruptedException e) {
                // TODO 自动生成的 catch 块
                e.printStackTrace();
            }
        }
    }

    public static boolean outOfBound(Ball ball) {
        if (ball.y > GameStart.HEIGHT - GameStart.INTERVAL) {
            // System.out.println("chujie");
            return true;
        }
        return false;
    }

    private static double pointToLine(double x1, double y1, double x2, double y2, double x0, double y0)// 计算点到线段的最短距离
    {
        double space = 0;
        double a, b, c;
        a = lineSpace(x1, y1, x2, y2);// 线段的长度
        b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离
        c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离
        if (c * c >= a * a + b * b) {
            space = b;
            return space;
        }
        if (b * b >= a * a + c * c) {
            space = c;
            return space;
        }
        double p = (a + b + c) / 2;// 半周长
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
        space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
        return space;
    }

    // 计算两点之间的距离
    private static double lineSpace(double x1, double y1, double x2, double y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return lineLength;
    }

    public static void Collidepoint(double _x, double _y, Ball ball) {
        double dx = ball.x - _x;
        double dy = ball.y - _y;
        if (ball.vx * dx + ball.vy * dy >= 0) {
            dx = -dx;
            dy = -dy;
        }
        double m = ((ball.vx * dx + ball.vy * dy) / Math.sqrt(dx * dx + dy * dy));
        ball.vx = ball.vx - m * 2 * dx / Math.sqrt(dx * dx + dy * dy);
        ball.vy = ball.vy - m * 2 * dy / Math.sqrt(dx * dx + dy * dy);
    }

    public static void Collideline(double _x1, double _y1, double _x2, double _y2, Ball ball) {
        double dx = _y2 - _y1;
        double dy = _x1 - _x2;
        if (ball.vx * dx + ball.vy * dy >= 0) {
            dx = -dx;
            dy = -dy;
        }
        double m = ((ball.vx * dx + ball.vy * dy) / Math.sqrt(dx * dx + dy * dy));
        ball.vx = ball.vx - m * 2 * dx / Math.sqrt(dx * dx + dy * dy);
        ball.vy = ball.vy - m * 2 * dy / Math.sqrt(dx * dx + dy * dy);
    }

    synchronized static boolean barrierCollision(Ball ball) {
        for (int i = 0; i < GameStart.barriers.size(); i++) {
            if (ball.collidebefore == i)
                continue;
            Barrier _x = GameStart.barriers.get(i);
            if (_x.collisionnum.get() == 0)
                continue;
            if (_x.type == Barrier.CIRCLE) {
                Circle x = (Circle) _x;
                // 判断是否需要加球
                if (lineSpace(ball.x, ball.y, x.x, x.y) < x.r + ball.r) {
                    Collidepoint(x.x, x.y, ball);
                    ball.collidebefore = i;
                    if (x.add) {
                        Ball b2 = new Ball(ball.x, ball.y, ball.vx, ball.vy, ball.r);
                        GameStart.curBallNum.getAndIncrement();
                        GameStart.remainBallNum.getAndIncrement();
                        ball.collidebefore = i;
                        GameStart.balls2.add(b2);
                        Ballactioneach ballthread = new Ballactioneach(b2);
                        Thread thread = new Thread(ballthread);
                        thread.start();
                    }
                    x.collisionnum.getAndDecrement();
                    GameStart.score.getAndAdd(5);
                    if (x.collisionnum.get() == 0) {
                        GameStart.barrierNum.getAndDecrement();
                        ball.collidebefore = 0;
                    }
                    return true;
                }
            }
            if (_x.type == Barrier.SQUARE) {
                Square x = (Square) _x;
                int cnt = 0;
                int[] mem = new int[2];
                for (int j = 0; j < 4; j++) {
                    if (pointToLine(x.px[j], x.py[j], x.px[(j + 1) % 4], x.py[(j + 1) % 4], ball.x, ball.y) < ball.r) {
                        mem[cnt] = j;
                        cnt++;
                    }
                }
                if (cnt == 1) {
                    Collideline(x.px[mem[0]], x.py[mem[0]], x.px[(mem[0] + 1) % 4], x.py[(mem[0] + 1) % 4], ball);
                    ball.collidebefore = i;
                    x.collisionnum.getAndDecrement();
                    GameStart.score.getAndAdd(5);
                    if (x.collisionnum.get() == 0) {
                        // GameStart.barriers.remove(i);
                        GameStart.barrierNum.getAndDecrement();
                        ball.collidebefore = 0;
                    }
                    return true;
                }
                if (cnt == 2) {
                    if (mem[0] != 0)
                        Collidepoint(x.px[mem[0] + 1], x.py[mem[0] + 1], ball);
                    else if (mem[1] == 3)
                        Collidepoint(x.px[0], x.py[0], ball);
                    else
                        Collidepoint(x.px[1], x.py[1], ball);
                    ball.collidebefore = i;
                    x.collisionnum.getAndDecrement();
                    GameStart.score.getAndAdd(5);
                    if (x.collisionnum.get() == 0) {
                        // GameStart.barriers.remove(i);
                        GameStart.barrierNum.getAndDecrement();
                        ball.collidebefore = 0;
                    }
                    return true;
                }
            }
            if (_x.type == Barrier.TRIANGLE) {
                Triangle x = (Triangle) _x;
                int cnt = 0;
                int[] mem = new int[2];
                for (int j = 0; j < 3; j++) {
                    if (pointToLine(x.px[j], x.py[j], x.px[(j + 1) % 3], x.py[(j + 1) % 3], ball.x, ball.y) < ball.r) {
                        mem[cnt] = j;
                        cnt++;
                    }
                }
                if (cnt == 1) {
                    Collideline(x.px[mem[0]], x.py[mem[0]], x.px[(mem[0] + 1) % 3], x.py[(mem[0] + 1) % 3], ball);
                    ball.collidebefore = i;
                    x.collisionnum.getAndDecrement();
                    GameStart.score.getAndAdd(5);
                    if (x.collisionnum.get() == 0) {
                        // GameStart.barriers.remove(i);
                        GameStart.barrierNum.getAndDecrement();
                        ball.collidebefore = 0;
                    }
                    return true;
                }
                if (cnt == 2) {
                    if (mem[0] != 0)
                        Collidepoint(x.px[mem[0] + 1], x.py[mem[0] + 1], ball);
                    else if (mem[1] == 2)
                        Collidepoint(x.px[0], x.py[0], ball);
                    else
                        Collidepoint(x.px[1], x.py[1], ball);
                    ball.collidebefore = i;
                    x.collisionnum.getAndDecrement();
                    GameStart.score.getAndAdd(5);
                    if (x.collisionnum.get() == 0) {
                        // GameStart.barriers.remove(i);
                        GameStart.barrierNum.getAndDecrement();
                        ball.collidebefore = 0;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static int wallCollisionDetect(Ball ball) {
        double x = ball.x;
        double y = ball.y;
        double r = ball.r;
        double leftX = x - r;
        double rightX = x + r;
        double topY = y - r;
        int wallLeftX = GameStart.verLeftWall.x1;
        int wallRightX = GameStart.verRightWall.x1;
        int wallTopY = GameStart.horTopWall.y1;
        if (topY <= wallTopY)
            return -1;
        if (leftX <= wallLeftX)
            return -3;
        if (rightX >= wallRightX)
            return -4;
        return -5;
    }

    public static void handlewallCollision(int condition, Ball ball) {
        switch (condition)
        {
            case -1:
                ball.y += 14;
            case -2:
                ball.setSpeed(ball.vx * GameStart.wallColliLoss, -ball.vy * GameStart.wallColliLoss);
                break;
            case -3:
                ball.x += 14;
                ball.setSpeed(-ball.vx * GameStart.wallColliLoss, ball.vy * GameStart.wallColliLoss);
                break;
            case -4:
                ball.x -= 14;
                ball.setSpeed(-ball.vx * GameStart.wallColliLoss, ball.vy * GameStart.wallColliLoss);
                break;
        }
    }

    public static void hadleCollision(Ball ball) throws Exception// 这个函数将会检测所有碰撞并且更改速度。
    {
        int wallColRes = wallCollisionDetect(ball);
        if (barrierCollision(ball)) {
            crashMusicStart();
            ball.hascollided = true;
            return;
        } else if (wallColRes != -5) {
            ball.hascollided = true;
            if (wallColRes==-3||wallColRes==-4)
                crashMusicStart();
            handlewallCollision(wallColRes, ball);
            return;
        } else
            ball.collidebefore = -5;
    }

    public static void refreshSpeed(Ball ball) // 由于环境影响刷新速度
    {
        if (ball.hascollided == true) {
            switch (GameStart.gameMode)
            {
                case 1:
                    ball.vy += GameStart.gravity * GameStart.ballStep;
                    ball.vy *= GameStart.envOppoLoss;// 加阻尼
                    break;
                case 2:
                    double _vy = ball.vy * Math.cos(0.01) - ball.vx * Math.sin(0.01);
                    double _vx = ball.vx * Math.cos(0.01) + ball.vy * Math.sin(0.01);
                    ball.vy = (_vy + GameStart.gravity * GameStart.ballStep) * GameStart.envOppoLoss;
                    ball.vx = _vx;
                    break;
                case 3:
                case 4:
                    ball.setSpeed(-ball.vx, ball.vy);
                    break;
            }
        }
    }

    public static void crashMusicStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MusicPlay2 crashMusic = new MusicPlay2(WelcomeFrame.class.getResourceAsStream("/testMusic5.wav"));
                try {
                    crashMusic.play();
                } catch (Exception e) {
                    // TODO 自动生成的 catch 块
                    e.printStackTrace();
                }
                // 使用音频播放器播放声音
            }
        }).start();
        return;
    }
}
