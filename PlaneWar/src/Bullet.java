import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Bullet {
	
	private int x,y;
	private int R=10;
	private int SPEED=8;
//	enum Direction {dU,dL,dD,dR,dUL,dLD,dDR,dRU};
	int bType=1;
	private boolean good=false;
	Client ct=null;
	static Random rd=new Random();
	private int control=1;
	
	public Bullet(int x,int y,int bType,Client ct,boolean isBOSS){
		this.x=x+Plane.WIDTH/2-R/2;
		this.bType=bType;
		this.y=y;
		this.ct=ct;
		if(isBOSS){
			R=20;
			SPEED=10;
		}
		if(bType==1) good=true;
	}
	
	public void draw(Graphics g){
		Color c=g.getColor();
		if(good){
			g.setColor(Color.blue);
		}else{
			g.setColor(Color.BLACK);
		}
		g.fillOval(x, y, R, R);
		if(control>0||R>10){
			move();
		}
		control*=-1;
		g.setColor(c);
	}
	
	public void move(){
		switch(bType){
		case 1:y-=SPEED;break;
		case 2:y+=SPEED;break;
		case 3:x-=SPEED;y-=SPEED;break;
		case 4:x-=SPEED;y+=SPEED;break;
		case 5:x+=SPEED;y+=SPEED;break;
		case 6:x+=SPEED;y-=SPEED;break;
		}
		if(x>800||y>800||x<0||y<0){
			ct.bts.remove(this);
		}
	}
	
	public boolean isGood(){
		return good;
	}
	
	public void setGood(boolean good){
		this.good=good;
	}
	
	public Rectangle getRec(){
		return new Rectangle(x,y,R,R);
	}
	
	public boolean hitPlane(Plane p){
		if(p.getRec().intersects(this.getRec())&&p.isGood()!=this.isGood()){
			if(p.isGood()){
				if(R!=20)p.setBlood(p.getBlood()-10);
				else p.setBlood(p.getBlood()-20);
			}
			else{
				int pp=rd.nextInt(19);
				ct.ps.remove(p);
				switch(pp){
				case 0:ct.pps.add(new Props(p.x,p.y,1,ct));break;
				case 1:ct.pps.add(new Props(p.x,p.y,1,ct));break;
				case 2:ct.pps.add(new Props(p.x,p.y,2,ct));break;
				case 3:ct.pps.add(new Props(p.x,p.y,2,ct));break;
				case 4:ct.pps.add(new Props(p.x,p.y,3,ct));break;
				case 5:ct.pps.add(new Props(p.x,p.y,3,ct));break;
				case 6:ct.pps.add(new Props(p.x,p.y,4,ct));break;
				}
				if(p instanceof Plane1){//用instanceof函数判断p属于哪种类型
					ct.setScore(ct.getScore()+5);//类型1一个加10分
				}else if(p instanceof Plane2){
					ct.setScore(ct.getScore()+10);//类型2一个加20分
				}
			}
			ct.bts.remove(this);
			return true;
		}
		return false;
	}
	
	public boolean hitBOSS(BOSS b){
		if(this.isGood()&&b.getRec().intersects(this.getRec())){
			if(b.getBlood()>0){
				b.setBlood(b.getBlood()-10);
			}else{
				b.setLive(false);
				ct.myPlane.setLiveNum(ct.myPlane.getLiveNum()+1);
			}
			ct.bts.remove(this);
			return true;
		}
		return false;
	}
	
}



