package org.crusty.engine.sprite;

import java.awt.image.BufferedImage;

/** All images in sprite should be the same size */
public class Sprite {
	
	/** Images in the sprite. May be 1, may be many for animation */
	public BufferedImage[] images;
	public String[] str;
	
	int width, height;
	
	/** Set by reference */
	public Sprite(BufferedImage[] images, String[] str) {
		this.str = str;
		this.images = images;
		width = this.images[0].getWidth();
		height = this.images[0].getHeight();
//		System.out.println("Sprite: " + width + "*" + height + " (" + this.images.length + ")");
	}
	
	public BufferedImage[] getImages() {
		return images;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
//	public void draw(Graphics2D g, double x, double y) {
////		System.out.println("Draw: " + currentImage.getHeight() + " x: " + x + " y: " + y);
////		g.drawImage(currentImage,
////		null,
////		(int) x - offset.x, 
////		(int) y - offset.y);
//		
//
//	}
	
}
