package Game2;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * 测试声音播放
 */
class MusicPlay extends Thread
{
    String fileName;

    public MusicPlay(String fileName) {
        this.fileName = fileName;
       // System.out.println(fileName);
    }
    @Override
    public void run() {
        // TODO 自动生成的方法存根
        super.run();
        InputStream in = null;
        try {
            in = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        // 创建音频流对象
        AudioStream audioStream = null;
        try {
            audioStream = new AudioStream(in);
        } catch (IOException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        AudioPlayer.player.start(audioStream);
        // 停止声音播放
        AudioPlayer.player.stop(audioStream);
    }
    
    void play() throws Exception {
        InputStream in = new FileInputStream(fileName);
        // 创建音频流对象
        final AudioStream audioStream = new AudioStream(in);
        System.out.println("thread start before "+ audioStream);
        new Thread(new Runnable() {         
            @Override
            public void run() {
                // 使用音频播放器播放声音
                AudioPlayer.player.start(audioStream);              
            }
        }).start();

        AudioPlayer.player.stop(audioStream);
    }
    
    void playCircle() throws Exception {
        while (true)
        {
            InputStream in = new FileInputStream(fileName);
            // 创建音频流对象
            final AudioStream audioStream = new AudioStream(in);
            new Thread(new Runnable() {         
                @Override
                public void run() {
                    // 使用音频播放器播放声音
                    AudioPlayer.player.start(audioStream);
                }
            }).start();
            Thread.sleep(93000);
        }
    }
}