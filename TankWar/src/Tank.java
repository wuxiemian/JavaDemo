import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.util.*;

public class Tank {
	private int x,y;
	private int oldX,oldY;
	public static int SPEED=8;
	public static final int WIDTH=50;
	public static final int HEIGHT=50;
	private boolean bU=false,bL=false,bD=false,bR=false;
	enum Direction {dU,dL,dD,dR,dUL,dLD,dDR,dRU,STOP}; 
	private Direction dir = Direction.STOP;
	TankClient tc;
	private Direction ptDir = Direction.dR;
	private boolean live = true;
	private boolean good;
	private static Random rd=new Random();
	private int step=rd.nextInt(12)+3;
	private int bStep=0;
	private int blood=100;
	BloodBar bb=new BloodBar();
	
	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
		
	}
	public Tank(int x, int y,boolean good,TankClient tc) {
		this(x,y);
		this.good=good;
		this.tc=tc;
		
	}
	
	public void keyPressed(KeyEvent e){
		switch (e.getKeyCode()) {
		//case KeyEvent.VK_SPACE:fire();break;
		case KeyEvent.VK_F:this.setLive(true);break;
		case KeyEvent.VK_W:tc.setWd();break;
		case KeyEvent.VK_A:tc.createTank();break;
		case KeyEvent.VK_UP:bU=true;break;
		case KeyEvent.VK_LEFT:bL=true;break;
		case KeyEvent.VK_DOWN:bD=true;break;
		case KeyEvent.VK_RIGHT:bR=true;break;
		default:break;
		}
	}
	public void draw(Graphics g){
		if(!live) {
			tc.tks.remove(this);
			return;
		}
		
		Color c=g.getColor();
		g.drawString(tc.bts.size()+"", 10, 50);
		if(good) g.setColor(Color.RED);
		else g.setColor(Color.blue);
		g.fillOval(x,y,WIDTH,HEIGHT);
		g.setColor(c);
		
		if(good) bb.draw(g);
		
		switch(ptDir) {
		case dL:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT/2);
			break;
		case dUL:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y);
			break;
		case dU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y);
			break;
		case dRU:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y);
			break;
		case dR:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT/2);
			break;
		case dDR:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH, y + Tank.HEIGHT);
			break;
		case dD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x + Tank.WIDTH/2, y + Tank.HEIGHT);
			break;
		case dLD:
			g.drawLine(x + Tank.WIDTH/2, y + Tank.HEIGHT/2, x, y + Tank.HEIGHT);
			break;
		default:break;
		}
			move();
		
	}
	
	
	
	void move(){
		oldX=x;
		oldY=y;
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
		if(good) moveDirection();
	if(x < 0) x = 0;
	if(y < 40) y = 40;
	if(x + Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
	if(y + Tank.HEIGHT > TankClient.GAME_HEIGHT) y = TankClient.GAME_HEIGHT - Tank.HEIGHT;
	
	if(!good){
		Direction[] dirs = Direction.values();
		if(step==0){
			step=rd.nextInt(12)+3;
			int xb=rd.nextInt(dirs.length-1);
			dir=dirs[xb];
			ptDir=dirs[xb];
			bStep++;
			if(bStep==3){
				fire();
				bStep=0;
			}
		}
		step--;
		//
	}
	}
	
	void moveDirection(){
		if(bU&&!bL&&!bD&&!bR) ptDir=dir=Direction.dU;
		else if(!bU&&bL&&!bD&&!bR) ptDir=dir=Direction.dL;
		else if(!bU&&!bL&&bD&&!bR) ptDir=dir=Direction.dD;
		else if(!bU&&!bL&&!bD&&bR) ptDir=dir=Direction.dR;
		else if(bU&&bL&&!bD&&!bR) ptDir=dir=Direction.dUL;
		else if(!bU&&bL&&bD&&!bR) ptDir=dir=Direction.dLD;
		else if(!bU&&!bL&&bD&&bR) ptDir=dir=Direction.dDR;
		else if(bU&&!bL&&!bD&&bR) ptDir=dir=Direction.dRU;
		else if(!bU&&!bL&&!bD&&!bR) dir=Direction.STOP;
		
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:fire();break;
		case KeyEvent.VK_UP:bU=false;break;
		case KeyEvent.VK_LEFT:bL=false;break;
		case KeyEvent.VK_DOWN:bD=false;break;
		case KeyEvent.VK_RIGHT:bR=false;break;
		default:break;
		}
		
	}
	
	
	public void fire(){
		if(!tc.wd||!good){
			Bullet bt=new Bullet(x,y,good,ptDir,tc);
			tc.bts.add(bt);
		}else{
			Bullet bt1=new Bullet(x,y,good,Direction.dU,tc);
			Bullet bt2=new Bullet(x,y,good,Direction.dL,tc);
			Bullet bt3=new Bullet(x,y,good,Direction.dD,tc);
			Bullet bt4=new Bullet(x,y,good,Direction.dR,tc);
			Bullet bt5=new Bullet(x,y,good,Direction.dUL,tc);
			Bullet bt6=new Bullet(x,y,good,Direction.dLD,tc);
			Bullet bt7=new Bullet(x,y,good,Direction.dDR,tc);
			Bullet bt8=new Bullet(x,y,good,Direction.dRU,tc);
			tc.bts.add(bt1);
			tc.bts.add(bt2);
			tc.bts.add(bt3);
			tc.bts.add(bt4);
			tc.bts.add(bt5);
			tc.bts.add(bt6);
			tc.bts.add(bt7);
			tc.bts.add(bt8);
		}
	}
	
	public Rectangle getRec(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	public void setLive(boolean live){
		this.live=live;
		this.blood=100;
	}
	public boolean isGood(){
		return good;
	}
	public boolean isLive(){
		return live;
	}
	public void hitWall(Wall w){
		if(this.getRec().intersects(w.getRec())){
			x=oldX;
			y=oldY;
		}
	}
	public void hitTank(List<Tank> t){
		for(int i=0;i<t.size();i++){
			Tank tk=t.get(i);
			if(tk!=this)
			if(this.getRec().intersects(tk.getRec())){
				x=oldX;
				y=oldY;
			}
		}
		
	}
	public int getBlood(){
		return blood;
	}
	public void setBlood(int blood){
		this.blood=blood;
	}
	private class BloodBar{
		public void draw(Graphics g){
			Color c=g.getColor();
			g.setColor(Color.RED);
			g.drawRect(x, y-20, WIDTH, 20);
			g.fillRect(x, y-20, (WIDTH*blood/100), 20);
			g.setColor(c);
		}
	}
}












