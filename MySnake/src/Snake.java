import java.awt.*;
import java.awt.event.KeyEvent;

public class Snake {
    public int getSize() {
        return size;
    }

    private Node head = null;
    private Node tail = null;
    private int size = 0;
    private boolean gameOver = false;
    //	Node n1=new Node(10,20,true,Dir.L);
//	Node n2=new Node(10,20,true,Dir.L);
    private Font font = new Font("ËÎÌו", Font.PLAIN, 100);
    Yard yd;

    public Snake(Yard yd) {
        head = new Node(10, 20, true, Dir.L);
        tail = new Node(10, 21, true, Dir.L);
        head.next = tail;
        tail.prev = head;
        this.yd = yd;
    }

    public void draw(Graphics g) {
        Color c = g.getColor();


        if (!gameOver) move();
        g.setColor(Color.GREEN);
        for (Node n = head; n != null; n = n.next) {
            n.draw(g);
        }
        g.setFont(font);
        g.setColor(Color.BLACK);
        if (gameOver) g.drawString("GameOver", 400, 300);
        g.setColor(c);

    }

//	private void addToTail(){
//		Node n=null;
//		switch(tail.dir){
//		case U:
//			n=new Node(tail.row+1,tail.col,false,tail.dir);
//			break;
//		case L:
//			n=new Node(tail.row,tail.col+1,false,tail.dir);
//			break;
//		case D:
//			n=new Node(tail.row-1,tail.col,false,tail.dir);
//			break;
//		case R:
//			n=new Node(tail.row,tail.col-1,false,tail.dir);
//			break;
//		}
//		tail.next=n;
//		n.prev=tail;
//		tail=n;
//		size++;
//	}

    private void addToHead() {
        Node n = null;
        switch (head.dir) {
            case L:
                n = new Node(head.row, head.col - 1, true, head.dir);
                break;
            case U:
                n = new Node(head.row - 1, head.col, true, head.dir);
                break;
            case R:
                n = new Node(head.row, head.col + 1, true, head.dir);
                break;
            case D:
                n = new Node(head.row + 1, head.col, true, head.dir);
                break;
        }
        head.isHead = false;
        n.next = head;
        head.prev = n;
        head = n;
        size++;
    }

    private void isGameOver() {
        if (head.col < 0 || head.row < 0 || head.col > Yard.COL - 1 || head.row > Yard.ROW - 1) {
            gameOver = true;
        }
        for (Node n = head.next; n != null; n = n.next) {
            if (head.col == n.col && head.row == n.row) gameOver = true;
        }
    }


    private void move() {
        addToHead();
        deleteTail();
        isGameOver();
    }

    private void deleteTail() {
        if (size == 0) return;
        tail = tail.prev;
        tail.next = null;
        size--;
    }

    public void eatEgg(Egg e) {
        if (e.getRect().intersects(this.getRect())) {
            addToHead();
            e.reAppear();
        }
    }

    public Rectangle getRect() {
        return new Rectangle(head.col * Yard.SIZE, head.row * Yard.SIZE, Yard.SIZE, Yard.SIZE);
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                yd.s = new Snake(yd);
                gameOver = false;
                yd.e = new Egg();
                break;
//		case KeyEvent.VK_F:this.setLive(true);break;
//		case KeyEvent.VK_W:tc.setWd();break;
//		case KeyEvent.VK_A:tc.createTank();break;
            case KeyEvent.VK_UP:
                if (head.dir == Dir.D) return;
                head.dir = Dir.U;
                break;
            case KeyEvent.VK_LEFT:
                if (head.dir == Dir.R) return;
                head.dir = Dir.L;
                break;
            case KeyEvent.VK_DOWN:
                if (head.dir == Dir.U) return;
                head.dir = Dir.D;
                break;
            case KeyEvent.VK_RIGHT:
                if (head.dir == Dir.L) return;
                head.dir = Dir.R;
                break;
            default:
                break;
        }
    }

    private class Node {
        int row, col;
        Dir dir = Dir.L;
        Node next = null;
        Node prev = null;
        private boolean isHead;

        Node(int row, int col, boolean isHead, Dir dir) {
            this.row = row;
            this.col = col;
            this.dir = dir;
            this.isHead = isHead;
        }

        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.BLACK);
//			g.drawRect(col*Yard.SIZE, row*Yard.SIZE, Yard.SIZE, Yard.SIZE);
            g.setColor(c);
            if (isHead) g.fillOval(col * Yard.SIZE, row * Yard.SIZE, Yard.SIZE, Yard.SIZE);
            else g.fillRect(col * Yard.SIZE, row * Yard.SIZE, Yard.SIZE, Yard.SIZE);
        }
    }
}
