import java.awt.*;
import java.util.Random;

public class BOSS {

    private int x, y, oldX, oldY;
    static Random rd = new Random();
    int step = rd.nextInt(12) + 3;
    int xStep[] = {-6, -4, -2, 0, 2, 4, 6};
    int yStep[] = {-6, -4, -2, 0, 2, 4, 6};
    int aX = rd.nextInt(7);
    int aY = rd.nextInt(7);
    Client ct = null;
    private int blood = 0;
    private boolean live = false;
    bloodBar bb = new bloodBar();
    private Image image_boss = ImageTool.getImage("boss");
    private int WIDTH = 165;
    private int HEIGHT = 246;


    public BOSS(int x, int y, Client ct) {
        this.x = x;
        this.y = y;
        this.ct = ct;
    }

    public void draw(Graphics g) {
        move();
        Color c = g.getColor();
//		g.setColor(Color.RED);
//		g.fillRect(x+WIDTH, y+WIDTH, WIDTH, WIDTH);
//		g.fillRect(x, y+WIDTH, WIDTH, WIDTH);
//		g.fillRect(x+WIDTH, y, WIDTH, WIDTH);
//		g.fillRect(x+WIDTH*2, y+WIDTH, WIDTH, WIDTH);
//		g.fillRect(x+WIDTH, y+WIDTH*2, WIDTH, WIDTH);
        g.drawImage(image_boss, x, y, null);
        bb.draw(g);
        g.setColor(c);
    }

    private void move() {
        oldX = x;
        oldY = y;
        if (step == 0) {
            step = rd.nextInt(24) + 8;
            aX = rd.nextInt(7);
            aY = rd.nextInt(7);
            fire();

        }
        x += xStep[aX];
        y += yStep[aY];
        step--;
        if (x < 10 || x + WIDTH > ct.getGameWidth()) {
            x = oldX;
        }
        if (y < 50 || y + HEIGHT > ct.getGameHeight() - 350) {
            y = oldY;
        }
    }

    private void fire() {
        ct.bts.add(new Bullet(x + image_boss.getWidth(null) / 2, y + image_boss.getHeight(null), 4, ct, true));
        ct.bts.add(new Bullet(x + image_boss.getWidth(null) / 2, y + image_boss.getHeight(null), 2, ct, true));
        ct.bts.add(new Bullet(x + image_boss.getWidth(null) / 2, y + image_boss.getHeight(null), 5, ct, true));
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, image_boss.getWidth(null), image_boss.getHeight(null));
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
        if (live) {
            SoundTool.over();
            SoundTool.bossStart();
            blood = 200 * ct.getBossNum();
            ct.pps.clear();
            ct.ps.clear();
            ct.bts.clear();
        } else {
            SoundTool.bossOver();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SoundTool.start();
        }
    }

    public boolean hitPlane(Plane p) {
        if (this.getRec().intersects(p.getRec())) {
            x = oldX;
            y = oldY;
            return true;
        }
        return false;
    }

    private class bloodBar {
        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.BLACK);
            g.drawString(blood + "", x + WIDTH / 2, y - 60);
            g.drawRect(x - 1, y - 36, WIDTH + 1, 31);
            g.setColor(Color.PINK);
            g.fillRect(x, y - 35, image_boss.getWidth(null) * blood / (200 * ct.getBossNum()), 30);
            g.setColor(c);
        }
    }
}
