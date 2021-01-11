package com.BaZe.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Container {
	private int x, y, width, height;
	private boolean isCenter;
	private Color color;
	
	public Container(int x, int y, int width, int height, Color color, boolean isCenter) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.isCenter = isCenter;
	}
	
	public void render(Graphics g) {
		if(g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.setColor(color);
		if(isCenter) {
			g.fillRect(x - (width/2), y, width, height);
		} else {
			g.fillRect(x, y, width, height);
		}
	}
	
	public void setWidth(int n) {
		if(n < 0) {
			this.width = 0;
		} else {
			this.width = n;
		}
	}
}
