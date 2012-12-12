package org.crusty.engine.entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import org.crusty.engine.CrustyEngine;
import org.crusty.engine.Screen;
import org.crusty.engine.sprite.Sprite;
import org.crusty.math.Vec2;
import org.crusty.math.Vec2int;

public class Entity extends RootEntity {

	protected Vec2 rotatePoint;
	protected Sprite[] sprites;
	protected Sprite currentSprite;
	/** Angle of rotation */
	protected double rotation = 0;
	protected double rotationalVel = 0;
	protected float scale = 1;
	
	/* Sprite */
	
	protected BufferedImage currentImage;
	public Vec2int offset = new Vec2int(0, 0);
	protected boolean animating = false;
	public boolean drawFlippedHori = false;
	public boolean drawFlippedVert = false;
	protected int curImage = 0;
	protected int timeBetweenFrames = 1000;
	protected long lastTime = CrustyEngine.currentTimeMillis();
	
//	public Entity(Sprite sprite) {
//		super();
//		this.sprites = new Sprite[1];
//		this.sprites[0] = sprite;
//		currentSprite = this.sprites[0];
//		rotatePoint = new Vec2(sprite.getWidth()/2, sprite.getHeight()/2);
//		currentImage = this.sprites[0].images[0];
//	}
	
//	protected Screen parent;
	
	/** Reference */
	public Entity(Screen parent, Sprite[] sprites, int xoffset, int yoffset) {
		super(parent);
		this.sprites = sprites;
		currentSprite = this.sprites[0];
		rotatePoint = new Vec2(sprites[0].getWidth()/2, sprites[0].getHeight()/2);
		
		currentImage = this.sprites[0].images[0];
		
		this.offset.x = xoffset;
		this.offset.y = yoffset;
	}
	
	public void setCurImage(int i) {
		curImage = i;
		if (curImage >= currentSprite.images.length) 
			curImage = 0;
		currentImage = currentSprite.images[curImage];
	}
	
	public void setAnimating(boolean b) {
		animating = b;
	}
	
	public void setOffset(Vec2int offset) {
		this.offset.x = offset.x;
		this.offset.y = offset.y;
	}
	
	public void logic(long dt) {
		super.logic(dt);
		
		if (animating) {
//			System.out.println(".");
			if (CrustyEngine.currentTimeMillis() - lastTime > timeBetweenFrames) {
				// Flip frames
				lastTime = CrustyEngine.currentTimeMillis();
				curImage++;
				if (curImage >= currentSprite.images.length) 
					curImage = 0;
				currentImage = currentSprite.images[curImage];
//				System.out.println("Flip: " + curImage + "/" + currentSprite.images.length);
			}
		}
		
		rotation += rotationalVel * dt / 1000000;
	}
	
	public void draw(Graphics2D g) {
		
		AffineTransform save = g.getTransform();
		g.rotate(rotation, pos.x - offset.x + rotatePoint.x, pos.y - offset.y + rotatePoint.y); //  + rotatePoint.x  + rotatePoint.y
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

		int x1, y1, x2, y2;
		
		// Normal
		x1 = 0;
		y1 = 0;
		x2 = currentSprite.getWidth();
		y2 = currentSprite.getHeight();
		
		if (drawFlippedHori) {
			int temp = x1;
			x1 = x2;
			x2 = temp;
		}
		if (drawFlippedVert) {
			int temp = y1;
			y1 = y2;
			y2 = temp;
		}
		
		int scaleBorderx = (int) (currentSprite.getWidth() * (scale - 1)), 
			scaleBordery = (int) (currentSprite.getHeight() * (scale - 1));
		
		g.drawImage(currentImage, 	(int) (pos.x - offset.x) - scaleBorderx, // Destination
									(int) (pos.y - offset.y) - scaleBordery, 
									(int) (pos.x - offset.x + currentSprite.getWidth()) + scaleBorderx, 
									(int) (pos.y - offset.y + currentSprite.getHeight()) + scaleBordery, 
									x1, // Source
									y1,
									x2, 
									y2, 
									null);
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		g.rotate(-rotation , pos.x - offset.x + rotatePoint.x, pos.y - offset.y + rotatePoint.y); //  + rotatePoint.x  + rotatePoint.y
		g.setTransform(save);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void collided(RootEntity e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void message(String s, RootEntity entity) {
		// TODO Auto-generated method stub
		
	}


	
}
