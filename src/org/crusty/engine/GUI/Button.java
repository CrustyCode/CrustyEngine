package org.crusty.engine.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import org.crusty.engine.FontStore;
import org.crusty.engine.Screen;

public abstract class Button extends GraphicInterfaceObject {
	
	Color normalColor = Color.YELLOW;
	Color mouseOverColor = Color.WHITE;
	
	String text = "";
	
	protected Screen screen;
	
	Font font = FontStore.smallFont;
	
	MouseOverPane mouseOverPane = null;
	private boolean displayingInfoPane = false;
	
	public Button(Screen s, int x, int y, int width, int height, MouseOverPane ip) {
		super(x, y, width, height);
		this.screen = s;
		setInfoPane(ip);
	}

	public void logic(long dt) {
		super.logic(dt);
		if (mouseOverPane != null)
			if (mouseOver) {
				if (!displayingInfoPane) {
					screen.addEntity(mouseOverPane);
					displayingInfoPane = true;
				}
			} else {
				if (displayingInfoPane) {
					displayingInfoPane = false;
					screen.getEntities().remove(mouseOverPane);
				}
			}
	}
	
	public void setText(String str) {
		text = str;
	}
	
	public void setInfoPane(MouseOverPane mouseOverPane) {
		this.mouseOverPane = mouseOverPane;
	}
	
	@Override
	public abstract void mousePressed(MouseEvent e);
	
	public void mouseMoved(MouseEvent e) {
		super.mouseMoved(e);
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(normalColor);
		if (mouseOver) {
			g.setColor(mouseOverColor);
		}
		g.drawRect(getRect().x, getRect().y, getRect().width, getRect().height);
		g.setFont(font);
		g.drawString(text, getRect().x + 10, getRect().y + 15);
		
	}
	
}
