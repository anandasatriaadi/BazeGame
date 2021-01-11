package com.BaZe.ui;

import java.awt.Color;
import java.awt.Graphics;

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
			System.out.println("Set Width" + n);
		}
	}
}
