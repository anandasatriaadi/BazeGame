package com.BaZe.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Text {
	public static void drawString(Graphics g, String text, int posX, int posY, boolean center, Color color, Font font) {
		int x = posX;
		int y = posY;
		
		FontMetrics fm = g.getFontMetrics(font);
		int stringWidth = (int) (fm.getStringBounds(text, g).getWidth());
		
		if(center) {
			x = x - (stringWidth / 2);
			y = (y - fm.getHeight() / 2) + fm.getAscent();
		}
		g.setColor(color);
		g.drawString(text, x, y);
	}
}
