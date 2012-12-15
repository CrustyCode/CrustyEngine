package org.crusty.engine.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import org.crusty.engine.CrustyEngine;
import org.crusty.engine.FontStore;
import org.crusty.engine.Screen;
import org.crusty.engine.entity.RootEntity;
import org.crusty.math.Vec2int;

public abstract class Menu extends RootEntity {

	protected String[] options;
	protected Vec2int pos;
	protected int lineSpacing = 50;
	private int lines = 999;
	private int firstLineDisplayed = 0;
	protected int menuPos;
	private Color selectColor, nonSelectColor;
	private long lastMoved;
	private int menuDelay = 150;
	private Font font;
	
	public Menu(Screen parent, Vec2int pos, String[] options) {
		super(parent);
		this.options = options;
		this.pos = pos;
		menuPos = 0;
		selectColor = Color.WHITE;
		nonSelectColor = Color.GREEN;
		lastMoved = System.currentTimeMillis();
		font = FontStore.mainFont;
	}
	
	public void setOptions(String[] options) {
		this.options = options;
	}
	
	/** Set spacing of menu items */
	public void setSpacing(int spacing) {
		this.lineSpacing = spacing;
	}
	
	/** Set position */
	public void setPosition(int x, int y) {
		this.pos.x = x;
		this.pos.y = y;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setFont(font);
		for (int i = 0; i < lines; i++) {
			if (menuPos == i)
				g.setColor(selectColor);
			else
				g.setColor(nonSelectColor);
			if (i + firstLineDisplayed < options.length) {
				g.drawString(options[i], pos.x, pos.y + (i*lineSpacing));
			} else {
				g.drawString("<EMPTY>", pos.x, pos.y + (i*lineSpacing));
			}
		}
		g.drawString(">", pos.x - 18, pos.y + (menuPos*lineSpacing));
	}
	
	public void logic(long dt) {
		if (System.currentTimeMillis() - lastMoved > menuDelay) {
			if (CrustyEngine.keys[KeyEvent.VK_W] || CrustyEngine.keys[KeyEvent.VK_UP]) {
				lastMoved = System.currentTimeMillis();
				menuPos--;
				if (menuPos < 0) {
					menuPos = options.length - 1;
				}
				movedMenuItem();
			}
			if (CrustyEngine.keys[KeyEvent.VK_S] || CrustyEngine.keys[KeyEvent.VK_DOWN]) {
				lastMoved = System.currentTimeMillis();
				menuPos++;
				if (menuPos > options.length - 1) {
					menuPos = 0;
				}
				movedMenuItem();
			}
			if (CrustyEngine.keys[KeyEvent.VK_ENTER] || CrustyEngine.keys[KeyEvent.VK_SPACE]) {
				lastMoved = System.currentTimeMillis();
				activateMenuItem(menuPos);
			}
		}	
	}
	
	/** Sets lastMoved to current time - used for when displaying a menu right 
	 *  after another to stop a keypress interfering with the next menu */
	public void triggerLastMoved() {
		lastMoved = System.currentTimeMillis();
	}
	
	public void setNumOfLines(int lines) {
		this.lines = lines;
	}
	
	public void setColours(Color selectColor, Color nonSelectColor) {
		this.selectColor = selectColor;
		this.nonSelectColor = nonSelectColor;
	}
	
//	public void keyPressed(KeyEvent e) {
//		if (e.equals(KeyEvent.VK_W) || e.equals(KeyEvent.VK_UP)) {
//			menuPos--;
//			if (menuPos < 0) {
//				menuPos = options.length - 1;
//			}
//			movedMenuItem();
//		}
//		if (e.equals(KeyEvent.VK_S) || e.equals(KeyEvent.VK_DOWN)){
//			menuPos++;
//			if (menuPos > options.length - 1) {
//				menuPos = 0;
//			}
//			movedMenuItem();
//		}
//		if (e.equals(KeyEvent.VK_ENTER) || e.equals(KeyEvent.VK_SPACE)) {
//			activateMenuItem(menuPos);
//		}
//	}

//	public void keyReleased(KeyEvent e) {
//		if (e.getKeyCode() < 255) {
//			keys[e.getKeyCode()] = false;
//		}
//	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		int option = 0;
		while (option < options.length) {
			if (e.getY() > pos.y + lineSpacing*option - lineSpacing + 10 &&
					e.getY() < pos.y + lineSpacing*option + 10) {
				menuPos = option;
			}
			option++;
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		int option = 0;
		while (option < options.length) {
			if (e.getY() > pos.y + lineSpacing*option - lineSpacing + 10 &&
					e.getY() < pos.y + lineSpacing*option + 10) {
				activateMenuItem(menuPos);
			}
			option++;
		}
	}
	
	/** Gets called when menu is moved - implement this to play a sound or something */
	public abstract void movedMenuItem();
	
	/** Returns the index of the menu item that gets activated */
	public abstract void activateMenuItem(int index);

}
