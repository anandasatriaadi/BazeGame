package com.BaZe.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class Text {
	public String content;
	private int x, y;
	private boolean center;
	private Color color;
	private Font font;
	
	public Text(String text, int posX, int posY, boolean center, Color color, Font font) {
		this.content = text;
		this.x = posX;
		this.y = posY;
		this.center = center;
		this.color = color;
		this.font = font;
	}
	
	public void render(Graphics g) {
		int currX = x, currY = y;
		FontMetrics fm = g.getFontMetrics(font);
		int stringWidth = (int) (fm.getStringBounds(content, g).getWidth());
		
		if(center) {
			currX = x - (stringWidth / 2);
			currY = (y - fm.getHeight() / 2) + fm.getAscent();
		}
		g.setColor(color);
		g.drawString(content, currX, currY);
	}
	
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
