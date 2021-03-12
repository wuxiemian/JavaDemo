import java.awt.*;

public class Props {

    int x, y;
    private static final int WIDTH = 18;
    private static final int SPEED = 7;
    Client ct = null;
    private int type;
    private int control = 1;

    public Props(int x, int y, int type, Client ct) {
        this.x = x;
        this.y = y;
        this.ct = ct;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();
        if (type == 1) {
            g.setColor(Color.GREEN);
        } else if (type == 2) {
            g.setColor(Color.RED);
        } else if (type == 3) {
            g.setColor(Color.BLACK);
        } else if (type == 4) {
            g.setColor(Color.YELLOW);
        }
        g.fillRect(x, y, WIDTH, WIDTH);
        g.setColor(Color.white);
        g.fillOval(x + 6, y + 6, WIDTH / 3, WIDTH / 3);
        if (control > 0) {
            move();
        }
        control *= -1;
        g.setColor(c);
    }

    public void move() {
        y += SPEED;
        if (y > ct.getGameHeight()) ct.pps.remove(this);
    }

    public Rectangle getRec() {
        return new Rectangle(x, y, WIDTH, WIDTH);
    }

}
