import java.awt.*;
import java.util.Random;

public class Egg {
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	private int row,col;
	private int w=Yard.SIZE/2;
	private int h=Yard.SIZE/2;
	Random r=new Random();
	Color c1 = Color.YELLOW;
	
	public Egg(){
		row=r.nextInt(Yard.ROW);
		col=r.nextInt(Yard.COL);
	}
	
	public void reAppear(){
		row=r.nextInt(Yard.ROW);
		col=r.nextInt(Yard.COL);
	}
	
	public Rectangle getRect(){
		return new Rectangle(col*Yard.SIZE+Yard.SIZE/4,row*Yard.SIZE+Yard.SIZE/4, w, h);
	}
	
	public void darw(Graphics g){
		Color c=g.getColor();
		g.setColor(c1);
		g.fillOval(col*Yard.SIZE+Yard.SIZE/4,row*Yard.SIZE+Yard.SIZE/4, w, h);
		if(c1==Color.YELLOW) c1=Color.RED;
		else c1=Color.YELLOW;
		g.setColor(c);
	}
}
