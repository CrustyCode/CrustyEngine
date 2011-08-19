package org.crusty.engine.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import org.crusty.engine.FontStore;
import org.crusty.engine.Screen;

public abstract class MessageBox extends GraphicInterfaceObject {

	Color normalColor = Color.YELLOW;
	Color textColor = Color.WHITE;
	Color mouseOverColor = Color.WHITE;
	
	String text = "";
	
	protected Screen screen;
	
	Font font = FontStore.smallFont;
	
	public MessageBox(Screen s, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.screen = s;
	}

	public abstract void logic(long dt);
	
	public void setTextColor(Color c) {
		this.textColor = c;
	}
	
	public void setText(String str) {
		text = str;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(normalColor);
		if (mouseOver) {
			g.setColor(mouseOverColor);
		}
		g.drawRect(getRect().x, getRect().y, getRect().width, getRect().height);
		g.setFont(font);
		g.setColor(textColor);
		g.drawString(text, getRect().x + 10, getRect().y + 15);
	}
	
}
