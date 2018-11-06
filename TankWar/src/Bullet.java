import java.awt.*;

public class Bullet {
	private int x,y;
	Tank.Direction dir;
	private TankClient tc;
	public static final int SPEED=12;
	public static final int BULLET_WIDTH=20;
	public static final int BULLET_HEIGHT=10;
	private boolean live=true;
	private boolean good;
	
	public Bullet(int x, int y,boolean good, Tank.Direction dir,TankClient tc) {
		this.x = x+Tank.WIDTH/2-BULLET_WIDTH/2;
		this.y = y+Tank.HEIGHT/2-BULLET_HEIGHT/2;
		this.good=good;
		this.dir = dir;
		this.tc=tc;
	}
	
	public void draw(Graphics g){
		if(!live){
//			tc.bts.remove(this);
			return;
		}
		Color c=g.getColor();
		if(!good) g.setColor(Color.BLUE);
		else g.setColor(Color.GRAY);
		g.fillOval(x,y,BULLET_WIDTH,BULLET_HEIGHT);
		g.setColor(c);
		move();
	}

	private void move() {
		switch(dir){
		case dU:
			y-=SPEED;
			break;
		case dL:
			x-=SPEED;
			break;
		case dD:
			y+=SPEED;
			break;
		case dR:
			x+=SPEED;
			break;
		case dUL:
			y-=SPEED;
			x-=SPEED;
			break;
		case dLD:
			y+=SPEED;
			x-=SPEED;
			break;
		case dDR:
			y+=SPEED;
			x+=SPEED;
			break;
		case dRU:
			y-=SPEED;
			x+=SPEED;
			break;
		default:break;
	}
	if(x<0 || y<0 || x>TankClient.GAME_WIDTH || y>TankClient.GAME_HEIGHT){
		tc.bts.remove(this);
	}
	}
	
	public Rectangle getRec(){
		return new Rectangle(x,y,BULLET_WIDTH,BULLET_HEIGHT);
	}
//	public void setLive(boolean live){
//		this.live=live;
//	}
//	public boolean isLive(){
//		return live;
//	}
	public void hitTank(Tank t){
		if(this.getRec().intersects(t.getRec())&&t.isLive()&&this.good!=t.isGood()){
//			this.setLive(false);
			if(t.isGood()){
				t.setBlood(t.getBlood()-20);
				if(t.getBlood()<=0) t.setLive(false);
			}else t.setLive(false);
			
			tc.eps.add(new Explode(x+BULLET_WIDTH/2,y+BULLET_HEIGHT/2,tc));
			tc.bts.remove(this);
		}
	}
	
	public void hitWall(Wall w){
		if(this.getRec().intersects(w.getRec())){
			tc.bts.remove(this);
		}
	}
}







