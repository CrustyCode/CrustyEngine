package org.crusty.engine.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

import org.crusty.engine.Screen;
import org.crusty.math.Vec2;
import org.crusty.util.Line;

/** Root Entities are all drawn on the screen in some way */
public abstract class RootEntity implements Comparable {

	public Vec2 pos;
	public Vec2 vel, acc;
	public double maxvel;
	public int depth;
	protected float alpha;
	private Rectangle bounds = null;
	private Line boundsLine = null;
	public boolean checkCollisions;
	protected Screen parent;
	
	public RootEntity(Screen parent) {
		this.parent = parent;
		pos = new Vec2(0, 0);
		vel = new Vec2(0, 0);
		acc = new Vec2(0, 0);
		maxvel = Double.MAX_VALUE;
		depth = 0;
		alpha = 1f;
//		bounds = new Rectangle();
	}
	
	public void setRect(Rectangle s) {
		this.bounds = s;
	}
	
	public void setLine(Line l) {
		this.boundsLine = l;
	}
	
	public Rectangle getRect() {
		return bounds;
	}
	
	public Line getLine() {
		return boundsLine;
	}
	
	/** Don't call super if there's no movement
	 *  Probably best to call this after making vel/acc/pos changes */
	public void logic(long dt) {
		vel.x += acc.x / 1000;
		vel.y += acc.y / 1000;
		if (this.vel.length() > maxvel) {
			Vec2 newVec = this.vel.normalise();
			this.vel.x = newVec.x * maxvel;
			this.vel.y = newVec.y * maxvel;
		}
		pos.x += vel.x * dt / 1000000;
		pos.y += vel.y * dt / 1000000;
	}
	
	public abstract void collided(RootEntity e);
	
	abstract public void draw(Graphics2D g);
	
	public abstract void mousePressed(MouseEvent e);
	
	public abstract void mouseMoved(MouseEvent e);
	
	public abstract void message(String s, RootEntity entity);
	
	@Override
	public int compareTo(Object o) {
		return this.depth - ((RootEntity) o).depth;
	}
	
}
