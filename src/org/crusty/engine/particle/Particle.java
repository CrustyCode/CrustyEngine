package org.crusty.engine.particle;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import org.crusty.engine.sprite.Sprite;
import org.crusty.math.Vec2;
import org.crusty.math.Vec2int;

public class Particle {

	Vec2 pos, vel, acc;
	Sprite sprite;
	float alpha;
	AlphaComposite ac;
	float alphaDecayRate = 1;
	public Vec2int offset;
	float scale = 1;
	double rotation = 0, rotationVel = 0;
	Vec2int rotatePoint = new Vec2int(0, 0);
	
	public Particle(Sprite sprite, Vec2 pos, Vec2 vel, Vec2 acc, Vec2int offset, float scale, double rotationVel) {
		this.pos = new Vec2(pos.x, pos.y);
		this.vel = new Vec2(vel.x, vel.y);
		this.acc = new Vec2(acc.x, acc.y);
		rotatePoint.x = offset.x;
		rotatePoint.y = offset.y;
		this.scale = scale;
		this.offset = new Vec2int(offset.x, offset.y);
		this.sprite = sprite;
		alpha = 1f;
		Random r = new Random();
		rotation = r.nextDouble() * Math.PI*2;
		this.rotationVel = rotationVel;
	}
	
	public void logic(double dt) {
		vel.x += acc.x / 1000;
		vel.y += acc.y / 1000;
		pos.x += vel.x * dt / 1000000;
		pos.y += vel.y * dt / 1000000;
		if (alpha > 0)
			alpha -= (dt / (double) 1000000000) * alphaDecayRate;
		if (alpha < 0)
			alpha = 0;
		rotation += rotationVel * dt / 1000000;
//		System.out.println(".");
	}
	
	public void draw(Graphics2D g) {
		
		AffineTransform save = g.getTransform();
		g.rotate(rotation, pos.x - offset.x + rotatePoint.x, pos.y - offset.y + rotatePoint.y);
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
		g.setComposite(ac);
//		g.drawImage(sprite.images[0], 
//					(int) pos.x - offset.x,
//					(int) pos.y - offset.y,
//					null);
		int x1 = 0;
		int y1 = 0;
		int x2 = sprite.images[0].getWidth();
		int y2 = sprite.images[0].getHeight();
		
		int scaleBorderx = (int) (sprite.images[0].getWidth() * (scale - 1)), 
			scaleBordery = (int) (sprite.images[0].getHeight() * (scale - 1));
		
		g.drawImage(sprite.images[0], 	(int) (pos.x - offset.x) - scaleBorderx, // Destination
									(int) (pos.y - offset.y) - scaleBordery, 
									(int) (pos.x - offset.x + sprite.images[0].getWidth()) + scaleBorderx, 
									(int) (pos.y - offset.y + sprite.images[0].getHeight()) + scaleBordery, 
									x1, // Source
									y1,
									x2, 
									y2, 
									null);
//		sprite.draw(g, pos.x, pos.y);
		
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f);
		g.setComposite(ac);
		g.rotate(-rotation , pos.x - offset.x + rotatePoint.x, pos.y - offset.y + rotatePoint.y); //  + rotatePoint.x  + rotatePoint.y
		g.setTransform(save);
//		System.out.println(",");
	}
	
}
