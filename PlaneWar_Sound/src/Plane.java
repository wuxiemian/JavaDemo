import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

abstract class Plane {
	
	protected int x,y,oldX,oldY;
	protected static final int WIDTH=20,HEIGHT=15;
	protected int SPEED;
    Client ct=null;
    
    Plane(int x,int y,Client ct){
    	this.x=x;
    	this.y=y;
    	this.ct=ct;
    }
    
    public void draw(Graphics g){
    	
    }
    
    protected void move(){
    	
    }
    
    protected void fire(){
    	
    }
    
    abstract public boolean isGood();
    
    abstract public Rectangle getRec();
    
}

class MyPlane extends Plane{
	
    enum Direction {dU,dL,dD,dR,dUL,dLD,dDR,dRU,STOP};
    private boolean bU=false,bL=false,bD=false,bR=false;
    private Direction dir = Direction.STOP;
    protected int SPEED = 5;
    protected int blood=100;
    int superb=0;
    BloodBar bb=new BloodBar();
    private int liveNum=2;

	MyPlane(int x, int y, Client ct) {
		super(x, y, ct);
	}
	
	 public void draw(Graphics g){
	        Color c=g.getColor();
	        g.setColor(Color.blue);
	        g.fillRect(x, y, WIDTH, HEIGHT);
	        g.fillRect(x-WIDTH/2, y+HEIGHT, WIDTH, WIDTH);
	        g.fillRect(x+WIDTH/2, y+HEIGHT, WIDTH, WIDTH);
	        bb.draw(g);
	        moveDirection();
	        move();
	        superb--;
	        g.setColor(c);
	 }

    protected void move(){
        oldX=x;
        oldY=y;
        switch(dir){
            case dU:y-=SPEED;break;
            case dL:x-=SPEED;break;
            case dR:x+=SPEED;break;
            case dD:y+=SPEED;break;
            case dUL:y-=4;x-=4;break;
            case dLD:y+=4;x-=4;break;
            case dDR:y+=4;x+=4;break;
            case dRU:y-=4;x+=4;break;
            default:
                break;
        }
        
        if(x<10||x>ct.getGameWidth()-30){
            x=oldX;
        }
        if(y<0||y>ct.getGameHeight()-35){
            y=oldY;
        }
    }

    void moveDirection(){
        if(bU&&!bL&&!bD&&!bR) dir=Direction.dU;
        else if(!bU&&bL&&!bD&&!bR) dir=Direction.dL;
        else if(!bU&&!bL&&bD&&!bR) dir=Direction.dD;
        else if(!bU&&!bL&&!bD&&bR) dir=Direction.dR;
        else if(bU&&bL&&!bD&&!bR) dir=Direction.dUL;
        else if(!bU&&bL&&bD&&!bR) dir=Direction.dLD;
        else if(!bU&&!bL&&bD&&bR) dir=Direction.dDR;
        else if(bU&&!bL&&!bD&&bR) dir=Direction.dRU;
        else if(!bU&&!bL&&!bD&&!bR) dir=Direction.STOP;

    }

    public void fire(){
        ct.bts.add(new Bullet(x, y,1,ct,false));
        SoundTool.fireSound.stop();
        SoundTool.play(SoundTool.fireSound);
        if(superb>0){
            Bullet bt1=new Bullet(x, y,3,ct,false);
            bt1.setGood(true);
            Bullet bt2=new Bullet(x, y,6,ct,false);
            bt2.setGood(true);
            ct.bts.add(bt1);
            ct.bts.add(bt2);
        }
    }

    public boolean isGood(){
        return true;
    }

    public int getLiveNum() {
        return liveNum;
    }

    public void setLiveNum(int liveNum) {
        this.liveNum = liveNum;
    }
    
    public int getBlood(){
    	return blood;
    }
    
    public void setBlood(int blood){
    	this.blood=blood;
    }
    
    public void setXY(int x,int y){
        this.x=x;
        this.y=y;
    }

    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:bU=true;break;
            case KeyEvent.VK_LEFT:bL=true;break;
            case KeyEvent.VK_DOWN:bD=true;break;
            case KeyEvent.VK_RIGHT:bR=true;break;
            default:break;
        }
    }

    public Rectangle getRec(){
        return new Rectangle(x-WIDTH/2,y,WIDTH*2,WIDTH+HEIGHT);
    }

    public boolean hitPorps(Props p){
        if(this.getRec().intersects(p.getRec())&&this.isGood()){
            if(p.getType()==1){
            	SoundTool.play(SoundTool.ppSound);
                this.blood=100;
            }else if(p.getType()==2){ 
                SoundTool.play(SoundTool.bumbSound);
            	for(int i=0;i<ct.ps.size();i++){
                    Plane plane=ct.ps.get(i);
                    if(plane.getClass()==Plane1.class){
                        ct.setScore(ct.getScore()+10);
                    }else if(plane.getClass()==Plane2.class){
                        ct.setScore(ct.getScore()+20);
                    }
                }
                ct.bts.clear();
                ct.ps.clear();
            }else if(p.getType()==3){
            	SoundTool.play(SoundTool.ppSound);
                this.superb=200;
            }else if(p.getType()==4){
                SoundTool.play(SoundTool.liveSound);
            	this.liveNum++;
            }
            ct.pps.remove(p);
            return true;
        }
        return false;
    }

    public boolean hitBOSS(BOSS b){
        if (this.getRec().intersects(b.getRec())){
            x=oldX;
            y=oldY;
            return true;
        }
        return false;
    }

    public void keyReleased(KeyEvent e){
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:fire();break;
            case KeyEvent.VK_UP:bU=false;break;
            case KeyEvent.VK_LEFT:bL=false;break;
            case KeyEvent.VK_DOWN:bD=false;break;
            case KeyEvent.VK_RIGHT:bR=false;break;
            default:break;
        }
//System.out.println(x+","+y); //
    }

    private class BloodBar{
        public void draw(Graphics g){
            Color c=g.getColor();
            g.setColor(Color.BLACK);
            g.drawRect(x-WIDTH/2, y-12, WIDTH*2, 10);
            g.setColor(Color.RED);
            g.fillRect(x-WIDTH/2, y-12,WIDTH*blood/100*2, 10);
            g.setColor(c);
        }
    }	
}

class Plane1 extends Plane{
	
	static Random rd=new Random();
    private int step=rd.nextInt(12)+3;
    private int xStep[]={-6,-4,-2,0,2,4,6};
    private int a = rd.nextInt(7);
    private int control=1;
    protected int SPEED=4;
	
	Plane1(int x,int y,Client ct){
		super(x, y, ct);
	}
	
	public void draw(Graphics g){
        oldX=x;
        oldY=y;
        Color c=g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.fillRect(x+WIDTH/2, y-HEIGHT, WIDTH, WIDTH);
        g.fillRect(x-WIDTH/2, y-HEIGHT, WIDTH, WIDTH);
        g.setColor(c);
        if(control>0){
            move();
            if(step==0){
                step=rd.nextInt(24)+8;
                a=rd.nextInt(7);
                fire();

            }
            x+=xStep[a];
            step--;
        }
        control*=-1;
        if(x<10||x>ct.getGameWidth()-30){
            x=oldX;
        }
        if(y<0){
            y=oldY;
        }
        if(y>ct.getGameHeight()-35){
            ct.setDamage(ct.getDamage()+1);
            ct.ps.remove(this);
        }
    }

    public void move(){

        y+=SPEED-2;
    }

    public void fire(){
        ct.bts.add(new Bullet(x, y,2,ct,false));
    }

    public Rectangle getRec(){
        return new Rectangle(x-WIDTH/2,y-WIDTH-HEIGHT,WIDTH*2,WIDTH+HEIGHT);
    }

    public boolean isGood(){
        return false;
    }
	
}

class Plane2 extends Plane{
	
	static Random rd=new Random();
    private int step=rd.nextInt(12)+3;
    private int xStep[]={-6,-4,-2,0,2,4,6};
    private int a = rd.nextInt(7);
    private int control=1;
    protected int SPEED=4;
	
	Plane2(int x,int y,Client ct){
		super(x,y,ct);
	}
	
	public void draw(Graphics g){
        oldX=x;
        oldY=y;
        Color c=g.getColor();
        g.setColor(Color.red);
        g.fillOval(x, y, WIDTH*2, HEIGHT*2);
        g.setColor(c);
        if(control>0){
            move();
            if(step==0){
                step=rd.nextInt(24)+8;
                a=rd.nextInt(7);
                fire();

            }
            x+=xStep[a];
            step--;
        }
        control*=-1;
        if(x<10||x>ct.getGameWidth()-30){
            x=oldX;
        }
        if(y<0){
            y=oldY;
        }
        if(y>ct.getGameHeight()-35){
            ct.setDamage(ct.getDamage()+1);
            ct.ps.remove(this);
        }
    }

    public void move(){
        y+=SPEED-2;
    }

    public void fire(){
        ct.bts.add(new Bullet(x, y,4,ct,false));
        ct.bts.add(new Bullet(x, y,2,ct,false));
        ct.bts.add(new Bullet(x, y,5,ct,false));
    }

    public Rectangle getRec(){
        return new Rectangle(x,y,WIDTH*2,WIDTH*2);
    }

    public boolean isGood(){
        return false;
    }
	
}


