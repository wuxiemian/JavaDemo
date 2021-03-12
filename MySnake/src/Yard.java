import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Panel {

    private static final long serialVersionUID = 1L;
    public static final int SIZE = 30;//格的大小
    public static final int ROW = 20;//行
    public static final int COL = 40;//列
    private Font font = new Font("宋体", Font.PLAIN, 30);
    Image offScreenImage = null;//双缓冲

    Snake s = new Snake(this);
    Egg e = new Egg();

    public void lunachFrame() {
        Frame f = new Frame("贪吃蛇");

        f.setLocation(200, 100);
        f.setBackground(Color.BLACK);
        f.setSize(SIZE * COL + 40, SIZE * ROW + 70);

        f.setLayout(null);
        setBackground(Color.WHITE);
        setBounds(20, 50, SIZE * COL, SIZE * ROW);
        f.addKeyListener(new KeyMonitor());
        f.add(this);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setVisible(true);
        new Thread(new PaintThread()).start();
    }

    public void update(Graphics g) {

        if (offScreenImage == null) {
            offScreenImage = this.createImage(COL * SIZE, ROW * SIZE);
        }
        Graphics a = offScreenImage.getGraphics();
        Color c = a.getColor();
        a.setColor(Color.WHITE);
        a.fillRect(0, 0, COL * SIZE, ROW * SIZE);
        a.setColor(c);
        paint(a);
        g.drawImage(offScreenImage, 0, 0, null);
    }


    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        for (int i = 1; i < ROW; i++) {
            g.drawLine(0, SIZE * i, SIZE * COL, SIZE * i);
        }
        for (int i = 1; i < COL; i++) {
            g.drawLine(SIZE * i, 0, SIZE * i, SIZE * ROW);
        }
        g.setFont(font);
        g.drawString("分数:" + s.getSize() * 10, SIZE * COL - 140, 40);
        g.setColor(c);
        s.eatEgg(e);
        e.darw(g);
        s.draw(g);
    }


    public static void main(String[] args) {
        new Yard().lunachFrame();
    }

    private class KeyMonitor extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            s.keyPressed(e);
        }
    }

    private class PaintThread implements Runnable {
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(170);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
