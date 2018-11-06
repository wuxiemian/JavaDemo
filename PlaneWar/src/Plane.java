import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

class Plane {

    int x,y,oldX,oldY;
    //	private boolean good;
    public static final int WIDTH=20,HEIGHT=15;
    enum Direction {dU,dL,dD,dR,dUL,dLD,dDR,dRU,STOP};
    private boolean bU=false,bL=false,bD=false,bR=false;
    private Direction dir = Direction.STOP;
    public static final int SPEED = 5;
    Client ct=null;
    static Random rd=new Random();
    int step=rd.nextInt(12)+3;
    int xStep[]={-6,-4,-2,0,2,4,6};
    int a = rd.nextInt(7);
    private int blood=100;
    int superb=0;
    bloodBar bb=new bloodBar();
    int control=1;
    private int liveNum=2;//生命

    Plane(int x,int y,Client ct){
        this.x = x;
        this.y = y;
        this.ct = ct;
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

    void move(){
        oldX=x;
        oldY=y;
        switch(dir){
            case dU:y-=SPEED;break;
            case dL:x-=SPEED;break;
            case dR:x+=SPEED;break;
            case dD:y+=SPEED;break;
            case dUL:y-=SPEED;x-=SPEED;break;
            case dLD:y+=SPEED;x-=SPEED;break;
            case dDR:y+=SPEED;x+=SPEED;break;
            case dRU:y-=SPEED;x+=SPEED;break;
            default:
                break;
        }
        if(x<10||x>770){
            x=oldX;
        }
        if(y<0||y>715){
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
        if(superb>0){
            Bullet bt1=new Bullet(x, y,3,ct,false);
            bt1.setGood(true);
            Bullet bt2=new Bullet(x, y,6,ct,false);
            bt2.setGood(true);
            ct.bts.add(bt1);
            ct.bts.add(bt2);
        }
//System.out.println("11");
    }

    public void setBlood(int a){
        blood=a;
    }
    public int getBlood(){
        return blood;
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
                this.blood=100;
            }else if(p.getType()==2){
                for(int i=0;i<ct.ps.size();i++){
                    Plane plane=ct.ps.get(i);
                    if(plane.getClass()==Plane1.class){//用getclass函数判断p属于哪种类型
                        ct.setScore(ct.getScore()+10);//类型1一个加10分
                    }else if(plane.getClass()==Plane2.class){
                        ct.setScore(ct.getScore()+20);//类型2一个加20分
                    }
                }
                ct.bts.clear();
                ct.ps.clear();
            }else if(p.getType()==3){
                this.superb=200;
            }else if(p.getType()==4){
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
//System.out.println(x+","+y); //测位置
    }

    private class bloodBar{
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

    public static final int SPEED = 4;

    Plane1(int x, int y, Client ct) {
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
        if(x<10||x>770){
            x=oldX;
        }
        if(y<0){
            y=oldY;
        }
        if(y>750){
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

    public static final int SPEED = 4;

    Plane2(int x, int y, Client ct) {
        super(x, y, ct);
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
        if(x<10||x>770){
            x=oldX;
        }
        if(y<0){
            y=oldY;
        }
        if(y>750){
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
