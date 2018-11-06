import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundTool {
	public static Clip backSound=null;
	public static AudioInputStream backSoundLoad=null;
	public static Clip bossDieSound=null;
	public static AudioInputStream bossDieSoundLoad=null;
	public static Clip bossSound=null;
	public static AudioInputStream bossSoundLoad=null;
	public static Clip dieSound=null;
	public static AudioInputStream dieSoundLoad=null;
	public static Clip fireSound=null;
	public static AudioInputStream fireSoundLoad=null;
	public static Clip ppSound=null;
	public static AudioInputStream ppSoundLoad=null;
	public static Clip bumbSound=null;
	public static AudioInputStream bumbSoundLoad=null;
	public static Clip liveSound=null;
	public static AudioInputStream liveSoundLoad=null;
	public static Clip gameOver=null;
	public static AudioInputStream gameOverLoad=null;
	static{
		try{
			backSoundLoad=AudioSystem.getAudioInputStream(SoundTool.class.getClassLoader().getResource("Sound/backSound.wav"));
			backSound=AudioSystem.getClip();
			backSound.open(backSoundLoad);
			backSound.start();
			backSound.stop();
			backSound.setMicrosecondPosition(0);
			
			bossDieSoundLoad=AudioSystem.getAudioInputStream(SoundTool.class.getClassLoader().getResource("Sound/bossDieSound.wav"));
			bossDieSound=AudioSystem.getClip();
			bossDieSound.open(bossDieSoundLoad);
			bossDieSound.start();
			bossDieSound.stop();
			bossDieSound.setMicrosecondPosition(0);
			
			bossSoundLoad=AudioSystem.getAudioInputStream(SoundTool.class.getClassLoader().getResource("Sound/bossSound.wav"));
			bossSound=AudioSystem.getClip();
			bossSound.open(bossSoundLoad);
			bossSound.start();
			bossSound.stop();
			bossSound.setMicrosecondPosition(0);
			
			dieSoundLoad=AudioSystem.getAudioInputStream(SoundTool.class.getClassLoader().getResource("Sound/dieSound.wav"));
			dieSound=AudioSystem.getClip();
			dieSound.open(dieSoundLoad);
			dieSound.start();
			dieSound.stop();
			dieSound.setMicrosecondPosition(0);
			
			fireSoundLoad=AudioSystem.getAudioInputStream(SoundTool.class.getClassLoader().getResource("Sound/fireSound.wav"));
			fireSound=AudioSystem.getClip();
			fireSound.open(fireSoundLoad);
			fireSound.start();
			fireSound.stop();
			fireSound.setMicrosecondPosition(0);
			
			ppSoundLoad=AudioSystem.getAudioInputStream(SoundTool.class.getClassLoader().getResource("Sound/ppSound.wav"));
			ppSound=AudioSystem.getClip();
			ppSound.open(ppSoundLoad);
			ppSound.start();
			ppSound.stop();
			ppSound.setMicrosecondPosition(0);
			
			bumbSoundLoad=AudioSystem.getAudioInputStream(SoundTool.class.getClassLoader().getResource("Sound/bumbSound.wav"));
			bumbSound=AudioSystem.getClip();
			bumbSound.open(bumbSoundLoad);
			bumbSound.start();
			bumbSound.stop();
			bumbSound.setMicrosecondPosition(0);
			
			liveSoundLoad=AudioSystem.getAudioInputStream(SoundTool.class.getClassLoader().getResource("Sound/liveSound.wav"));
			liveSound=AudioSystem.getClip();
			liveSound.open(liveSoundLoad);
			liveSound.start();
			liveSound.stop();
			liveSound.setMicrosecondPosition(0);
			
			gameOverLoad=AudioSystem.getAudioInputStream(SoundTool.class.getClassLoader().getResource("Sound/gameOver.wav"));
			gameOver=AudioSystem.getClip();
			gameOver.open(gameOverLoad);
			gameOver.start();
			gameOver.stop();
			gameOver.setMicrosecondPosition(0);
		}catch(Exception e){
//			System.out.println("111");
			e.printStackTrace();
		}
	}
	
	public static void start(){
		backSound.loop(Integer.MAX_VALUE);
	}
	
	public static void bossStart(){
		bossSound.loop(Integer.MAX_VALUE);
	}
	
	public static void bossOver(){
		bossSound.stop();
		bossSound.setMicrosecondPosition(0);
	}
	
	public static void over(){
		backSound.stop();
		backSound.setMicrosecondPosition(0);
	}
	
	public static void play(Clip clip){
		clip.setMicrosecondPosition(0);
		clip.start();
	}
}
