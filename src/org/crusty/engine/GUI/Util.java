package org.crusty.engine.GUI;

import java.awt.Color;
import java.awt.Graphics2D;

public class Util {

	public static void drawText(String s, int row, Graphics2D g) {
		g.setColor(Color.BLACK);
		int height = 20;
		int width = s.length() * 6;
		int topGap = 50;
		int gap = 2;
		g.fillRect(5, topGap + 5 + (row * (height + gap)), width, height);
		g.setColor(Color.WHITE);
		g.drawRect(5, topGap + 5 + (row * (height + gap)), width, height);
		g.drawString(s, 10, topGap + 5 + (row * (height + gap)) + 15);
	}
	
}
