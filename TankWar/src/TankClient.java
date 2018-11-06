import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame {

	private static final long serialVersionUID = 1L;
	public static final String KeyEvent = null;
	Tank myTank = new Tank(50,50,true,this);
	Wall w=new Wall(400,300);
	Bullet b;
	public static final int GAME_WIDTH=1000,GAME_HEIGHT=800;//»­Ãæ¿í¸ß
	Image offScreenImage = null;
	List<Bullet> bts = new ArrayList<Bullet>();
	List<Explode> eps=new ArrayList<Explode>();
	List<Tank> tks=new ArrayList<Tank>();
	boolean wd=false;
	
	
	public void paint(Graphics g) {
		for(int i=0;i<bts.size();i++){
			Bullet bt = bts.get(i);
			
			for(int j=0;j<tks.size();j++){
				Tank t=tks.get(j);
				bt.hitTank(t);
				
			}
			if(!wd) bt.hitTank(myTank);
			bt.hitWall(w);
			bt.draw(g);
		}
		
		for(int i=0;i<eps.size();i++){
			Explode e=eps.get(i);
			e.draw(g);
			
		}
		for(int i=0;i<tks.size();i++){
			Tank t=tks.get(i);
			t.hitWall(w);
			t.hitTank(tks);
			t.draw(g);
		}
		
		myTank.draw(g);
		myTank.hitWall(w);
		
		w.draw(g);
	}
	
public void update(Graphics g) {
		if(offScreenImage == null){
			offScreenImage = createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
		
	}

	public void launch(){
		setLocation(600, 150);
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setResizable(false);
		setBackground(Color.GREEN);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		addKeyListener(new KeyMonitor());
		setVisible(true);
		new Thread(new PaintThread()).start();
		//new Thread(new RemoveBullet()).start();
	}
	

	public static void main(String[] args) {
		new TankClient().launch();
	}
	
	public void createTank(){
		int x=100;
        int y=600;     
		for(int i=0;i<15;i++){
			tks.add(new Tank(x+i*60,y,false,this));
		}
	}
	
	class PaintThread implements Runnable{
		public void run(){
			while(true){
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class KeyMonitor extends KeyAdapter{

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

		public void keyPressed(KeyEvent e){
			myTank.keyPressed(e);
		}
	}
	public void setWd(){
		if(wd) wd=false;
		else wd=true;
	}
	/*
	class RemoveBullet implements Runnable{
		public void run(){
			while(true){
				for(int i=0;i<bts.size();i++){
					Bullet bt=bts.get(i);
					if(!bt.live) bts.remove(bt);
				}
			}
		}
	}*/

}
