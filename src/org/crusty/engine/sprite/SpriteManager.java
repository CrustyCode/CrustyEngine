package org.crusty.engine.sprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.crusty.engine.ImageManager;

/** Add sprites with addSprite(String str, Sprite s)<br>
 	Get sprites with getSprite(String s) */
public class SpriteManager {

	static HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	String spritesFile = "sprites.crusty";
	
	public SpriteManager() {
		
	}
	
	/**
	 * Load saved sprite data made with Sprite Maker
	 */
	public void loadSprites() {
		try {
			FileInputStream fis = new FileInputStream(new File(spritesFile));
			ObjectInputStream ois = new ObjectInputStream(fis);

			ois.close();
			fis.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addFlipSprite(String str, String[] strImages) {
		BufferedImage[] images = new BufferedImage[strImages.length];
		for (int i = 0; i < strImages.length; i++) 
			images[i] = ImageManager.flipImage(loadImage(strImages[i]));
		Sprite s = new Sprite(images, strImages);
		sprites.put(str, s);
	}
	
	public static void addSprite(String str, String[] strImages) {
		BufferedImage[] images = new BufferedImage[strImages.length];
		for (int i = 0; i < strImages.length; i++) 
			images[i] = loadImage(strImages[i]);
		Sprite s = new Sprite(images, strImages);
		System.out.println("Sprite added: " + str + " (" + images.length + ")");
		sprites.put(str, s);
	}
	
	/** Add a sprite with single image */
	public static void addSprite(String str) {
		Sprite s = new Sprite(new BufferedImage[] { loadImage(str) }, new String[] { str });
		sprites.put(str, s);
	}
	
	public static void reloadSprite(String str) {
		Sprite s = sprites.get(str);
		
		for (int i = 0; i < s.images.length; i++) {
			s.images[i] = loadImage(s.str[i]);
		}
		
//		sprites.remove(sprites.get(str));
//		addSprite(str);
	}
	
    /**
     * Add a sprite using a BufferedImage
     * @param img
     * @param str
     */
    public static void addSprite(BufferedImage img, String str) {
            Sprite s = new Sprite(new BufferedImage[] { img }, new String[] { str });
            sprites.put(str, s);
    }
	
    public static BufferedImage dupe(BufferedImage image) {
    	BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.drawImage(image, 0, 0, null);
		return bi;
    }
    
	private static BufferedImage loadImage(String str) {
//		BufferedImage image = null;
		System.out.println("Loading: " + str);
		URL url = Thread.currentThread().getContextClassLoader().getResource(str);
//		System.out.println("Loading: " + url.toString());
		BufferedImage img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.drawImage(img, 0, 0, null);
//		g.setColor(Color.WHITE);
//		g.drawLine(0, 0, 5, 5);
		
//		BufferedImage bi = null;
//		try {
//			bi = ImageIO.read(url);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		
//		Graphics2D g2 = bi.createGraphics();
//		image = g2.getDeviceConfiguration().createCompatibleImage(
//				bi.getWidth(), 
//				bi.getHeight(), 
//				Transparency.TRANSLUCENT);
//		Graphics2D g3 = image.createGraphics();
		
		bi = ImageManager.makeColorTransparent(bi, Color.RED);
		return bi;
	}
	
	/** Return sprite of given string */
	public static Sprite getSprite(String str) {
		Sprite s = sprites.get(str);
		if (s != null)
			return s;
		else {
			System.out.println("Can't find: " + str);
			System.exit(-1);
			return null;
		}
	}
	
}
