import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Map;

public class ImageTool {
	private static Image[] ims = null;
	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Map<String,Image> mps = new HashMap<String,Image>();
	static{
		ims = new Image[]{
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/background.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/hero1.gif")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/enemy0.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/enemy1.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/enemy2.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/bullet.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/bullet1.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/background.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/background.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/background.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/background.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/background.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/background.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/background.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/background.png")),
			tk.getImage(ImageTool.class.getClassLoader().getResource("image/background.png")),
			
		};
		mps.put("background", ims[0]);
		mps.put("myplane", ims[1]);
		mps.put("plane1", ims[2]);
		mps.put("plane2", ims[3]);
		mps.put("boss", ims[4]);
		mps.put("bullet", ims[5]);
		mps.put("bullet1", ims[6]);
	}
	public static Image getImage(String key){
		return mps.get(key);
	}
	
}
