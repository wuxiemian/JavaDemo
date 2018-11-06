import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class BOSS {
	
	private static final int WIDTH=50;
	private int x,y,oldX,oldY;
	static Random rd=new Random();
	int step=rd.nextInt(12)+3;
	int xStep[]={-6,-4,-2,0,2,4,6};
	int yStep[]={-6,-4,-2,0,2,4,6};
	int aX = rd.nextInt(7);
	int aY = rd.nextInt(7);
	Client ct=null;
	private int blood=0;
	private boolean live=false; 
	bloodBar bb=new bloodBar();
	
	
	public BOSS(int x,int y,Client ct){
		this.x=x;
		this.y=y;
		this.ct=ct;
	}
	
	public void draw(Graphics g){
		move();
		Color c=g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x+WIDTH, y+WIDTH, WIDTH, WIDTH);
		g.fillRect(x, y+WIDTH, WIDTH, WIDTH);
		g.fillRect(x+WIDTH, y, WIDTH, WIDTH);
		g.fillRect(x+WIDTH*2, y+WIDTH, WIDTH, WIDTH);
		g.fillRect(x+WIDTH, y+WIDTH*2, WIDTH, WIDTH);
		bb.draw(g);
		g.setColor(c);
	}
	
	private void move(){
		oldX=x;
		oldY=y;
		if(step==0){
			step=rd.nextInt(24)+8;
			aX=rd.nextInt(7);
			aY=rd.nextInt(7);
			fire();
			
		}
		x+=xStep[aX];
		y+=yStep[aY];
		step--;
		if(x<10||x+WIDTH*3>ct.getGameWidth()){
			x=oldX;
		}
		if(y<50||y+WIDTH*3>ct.getGameHeight()-300){
			y=oldY;
		}
	}
	
	private void fire(){
		ct.bts.add(new Bullet(x+WIDTH*3/2, y+WIDTH*3/2,4,ct,true));
		ct.bts.add(new Bullet(x+WIDTH*3/2, y+WIDTH*3/2,2,ct,true));
		ct.bts.add(new Bullet(x+WIDTH*3/2, y+WIDTH*3/2,5,ct,true));
	}
	
	public Rectangle getRec(){
		return new Rectangle(x,y,WIDTH*3,WIDTH*3);
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
		if(live){
			SoundTool.over();
			SoundTool.bossStart();
			blood=200*ct.getBossNum();
			ct.pps.clear();
			ct.ps.clear();
			ct.bts.clear();
		}else{
			SoundTool.bossOver();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			SoundTool.start();
		}
	}
	public boolean hitPlane(Plane p){
	    if(this.getRec().intersects(p.getRec())){
	        x=oldX;
	        y=oldY;
	        return true;
        }
        return false;
    }

	private class bloodBar{
		public void draw(Graphics g){
			Color c=g.getColor();
			g.setColor(Color.BLACK);
			g.drawString(blood+"",x+WIDTH, y-60);
			g.drawRect(x, y-35, WIDTH*3, 30);
			g.setColor(Color.PINK);
			g.fillRect(x, y-35,WIDTH*blood/(200*ct.getBossNum())*3, 30);
			g.setColor(c);
		}
	}
}
