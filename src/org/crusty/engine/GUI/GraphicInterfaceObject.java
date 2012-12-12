package org.crusty.engine.GUI;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import org.crusty.engine.Screen;
import org.crusty.engine.entity.RootEntity;

public abstract class GraphicInterfaceObject extends RootEntity {
	
	protected boolean mouseOver = false;
	
	public GraphicInterfaceObject(Screen parent, int x, int y, int width, int height) {
		super(parent);
		this.setRect(new Rectangle(x, y, width, height));
	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}
	
	public void logic(long dt) {
		super.logic(dt);
	}
	
//	@Override
//	public void mousePressed(MouseEvent e) {
//
//	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if (this.getRect().contains(e.getX(), e.getY())) {
			mouseOver = true;
		} else {
			mouseOver = false;
		}
	}

}
