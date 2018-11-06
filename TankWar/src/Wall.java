import java.awt.*;

public class Wall {
	private int x,y;
	public static final int WIDTH=100;
	public static final int HEIGHT=300;
	
	public Wall(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(Color.WHITE);
		g.fillRect(x, y, WIDTH, HEIGHT);
		g.setColor(c);
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public Rectangle getRec(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
}
