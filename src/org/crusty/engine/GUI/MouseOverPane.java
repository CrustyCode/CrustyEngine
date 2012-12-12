package org.crusty.engine.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import org.crusty.engine.CrustyEngine;
import org.crusty.engine.FontStore;
import org.crusty.engine.Screen;
import org.crusty.engine.entity.RootEntity;

public class MouseOverPane extends GraphicInterfaceObject {

	Color normalColor = Color.YELLOW;
	Color textColor = Color.WHITE;
	Color mouseOverColor = Color.WHITE;
	Color bgColor = Color.BLACK;
	
	String[] text = null;
	
//	protected Screen screen;
	
	Font font = FontStore.smallFont;
	
	int verticalSpacing = 20;
	
	public MouseOverPane(Screen parent, String[] text) {
		super(parent, 0, 0, 0, 0);
//		this.screen = s;
		this.text = text;
		depth = 999; // Infront
		
		// Set width and height depending on inputted text
		// Hacky
		Graphics g = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage bi = gc.createCompatibleImage(1, 1);
		
		g = bi.getGraphics();
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics();
		
		// get max width
		int maxWidth = 0;
		for (int i = 0; i < text.length; i++) {
			if (fm.stringWidth(text[i]) > maxWidth) {
				maxWidth = fm.stringWidth(text[i]);
			}
		}
		
		this.getRect().width = maxWidth + 15;
		this.getRect().height = verticalSpacing * text.length + 3;
		
	}
	
	public void logic(long dt) {
		super.logic(dt);
	}
	
	public void setTextColor(Color c) {
		this.textColor = c;
	}
	
	public void setText(String[] str) {
		text = str;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(bgColor);
		g.fillRect(getRect().x, getRect().y, getRect().width, getRect().height);
		g.setColor(normalColor);
		if (mouseOver) {
			g.setColor(mouseOverColor);
		}
		g.drawRect(getRect().x, getRect().y, getRect().width, getRect().height);
		g.setFont(font);
		g.setColor(textColor);
		for (int i = 0; i < text.length; i++) {
			g.drawString(text[i], getRect().x + 10, getRect().y + 15 + (i*verticalSpacing));
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if (e.getX() + this.getRect().width < CrustyEngine.width) {
			this.getRect().x = e.getX();
		} else {
			this.getRect().x = e.getX() - this.getRect().width;
		}
		if (e.getY() + this.getRect().height < CrustyEngine.height) {
			this.getRect().y = e.getY();
		} else {
			this.getRect().y = e.getY() - this.getRect().height;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
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
