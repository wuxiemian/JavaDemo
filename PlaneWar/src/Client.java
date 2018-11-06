import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Client extends Panel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int GAME_WIDTH=800,GAME_HEIGHT=800;
	List<Plane> ps = new ArrayList<Plane>();
	Plane myPlane = new Plane(395,750,this);
	List<Bullet> bts = new ArrayList<Bullet>();
	List<Props> pps=new ArrayList<Props>();
	Image offScreenImage = null;
	static Random rd=new Random();
	private Font font=new Font("宋体",Font.PLAIN,30);
	int step=0;
	private int score=0;//分数
	private int damage=0;//损害程度
	private boolean gameOver=false;//是否游戏结束
	private int n=0;
	BOSS boss=new BOSS(350,50,this);
	private int bossNum=0;
	private int control=1;
	
	public void launch(){
		Frame f = new Frame();
		f.setBounds(600, 150, GAME_WIDTH+35, GAME_HEIGHT+60);
		this.setBounds(25, 50, GAME_WIDTH, GAME_HEIGHT);
		f.setBackground(Color.black);
		f.setLayout(null);
		f.add(this);
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		f.addKeyListener(new KeyMonitor());
		f.setResizable(false);
		new Thread(new PlaneThread()).start();
		
	}
	
	public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.white);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
		
	}
	
	public void paint(Graphics g){
		checkGameOver();
		if(control>0)if(!boss.isLive())creatPlane();
		control*=-1;
		creatBOSS();
		if(boss.isLive()) boss.draw(g);
		Color c=g.getColor();
		g.setColor(Color.GRAY);
		g.setFont(font);//设置字体
		g.drawString("分数："+score, 650, 50);
		g.drawString("损害程度："+damage, 600, 150);
		g.drawString("生命："+myPlane.getLiveNum(), 650, 100);
		g.setColor(c);
		for(int i=0;i<bts.size();i++){
			Bullet bt=bts.get(i);
			bt.draw(g);
			for(int j=0;j<ps.size();j++){
				Plane p=ps.get(j);
				if(bt.hitPlane(p)){
					i--;
					break;
				}
			}
			if(bt.hitPlane(myPlane)){
				i--;
				continue;
			}
			if(boss.isLive()&&bt.hitBOSS(boss)){
				i--;
				continue;
			}
		}
		for(int i=0;i<ps.size();i++){
			ps.get(i).draw(g);
		}
		for(int i=0;i<pps.size();i++){
			Props pp=pps.get(i);
			pp.draw(g);
			if(myPlane.hitPorps(pp)){
				i--;
			}
		}
		myPlane.draw(g);
		if(gameOver){
			g.setColor(Color.RED);
			Font font=new Font("宋体",Font.PLAIN,100);
			g.setFont(font);
			g.drawString("游戏结束！", 200, 350);
		}
	}
	
	private void checkGameOver(){
		if(damage==10||myPlane.getBlood()<=0){
			myPlane.setLiveNum(myPlane.getLiveNum()-1);
			if(myPlane.getLiveNum()>0){
				myPlane.setBlood(100);
				damage=0;
			}else{
				gameOver=true;
			}
//			
		}
	}
	
	public void creatPlane(){
		if(step==0){
			step=rd.nextInt(60)+40;
			if(step/10>6){
				ps.add(new Plane1(rd.nextInt(700)+50,30,this));
				ps.add(new Plane1(rd.nextInt(700)+50,30,this));
			}else ps.add(new Plane2(rd.nextInt(700)+50,30,this));
		}
		step--;
	}
	
	public void creatBOSS(){
		n=this.score/200;
		if(this.score>=200&&n>bossNum&&(this.score-200*n)>=0&&!boss.isLive()){
			bossNum++;
			boss.setLive(true);
		}
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getBossNum() {
		return bossNum;
	}

	public static void main(String[] args) {
		new Client().launch();
	}
	
	class PlaneThread implements Runnable{
		public void run(){
			while(true){
				if(!gameOver)repaint();
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	class KeyMonitor extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			myPlane.keyPressed(e);
		}
		public void keyReleased(KeyEvent e){
			myPlane.keyReleased(e);
		}
	}
	
}
