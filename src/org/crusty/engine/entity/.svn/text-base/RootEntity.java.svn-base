package org.crusty.engine.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import org.crusty.math.Vec2;

/** Root Entities are all drawn on the screen in some way */
public abstract class RootEntity implements Comparable {

	public Vec2 pos;
	public Vec2 vel, acc;
	public int depth;
	protected float alpha;
	private Rectangle bounds;
	
	public RootEntity() {
		pos = new Vec2(0, 0);
		vel = new Vec2(0, 0);
		acc = new Vec2(0, 0);
		depth = 0;
		alpha = 1f;
		bounds = new Rectangle();
	}
	
	public void setRect(int x, int y, int width, int height) {
		bounds.x = x;
		bounds.y = y;
		bounds.width = width;
		bounds.height = height;
	}
	
	public Rectangle getRect() {
		return bounds;
	}
	
	/** Don't call super if there's no movement
	 *  Probably best to call this after making vel/acc/pos changes */
	public void logic(long dt) {
		vel.x += acc.x / 1000;
		vel.y += acc.y / 1000;
		pos.x += vel.x * dt / 1000000;
		pos.y += vel.y * dt / 1000000;
	}
	
	abstract public void draw(Graphics2D g);
	
	public abstract void mousePressed(MouseEvent e);
	
	public abstract void mouseMoved(MouseEvent e);
	
	@Override
	public int compareTo(Object o) {
		return this.depth - ((RootEntity) o).depth;
	}
	
}
