import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	int x,y;
	boolean live=true;
	int[] r={5,9,13,15,19,30,50,60,40,30,14,5};
	int step=0;
	TankClient tc;
	
	public Explode(int x,int y,TankClient tc){
		this.x=x;
		this.y=y;
		this.tc=tc;
	}
	public void draw(Graphics g){
		if(!live) {
			tc.eps.remove(this);
			return;
		}
		if(step==r.length){
			step=0;
			live=false;
			return;
		}
//		if(step==r.length){
//			live=false
//		}
		Color c=g.getColor();
		g.setColor(Color.YELLOW);
		
		g.fillOval(x-r[step]/2, y-r[step]/2, r[step],r[step]);
		step++;
		g.setColor(c);
	}
}
