package com.BaZe.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.BaZe.input.MouseManager;

public class Button {

	private String text;
	private int x, y;
	private FontMetrics fm;
	private Rectangle bounds;
	private boolean onHover;
	private Click click;
	private Font font;
	private Color color;
	
	public Button(String text, int x, int y, Click click, Font font, Color color) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.click = click;
		onHover = false;
		this.font = font;
		this.color = color;
	}
	
	public void updateText(String text) {
		this.text = text;
	}
	
	public void tick() {
		if(bounds != null && bounds.contains(MouseManager.x, MouseManager.y)) {
			onHover = true;
			if(MouseManager.left) {
				click.onClick();
			}
		} else {
			onHover = false;
		}
	}
	
	public void render(Graphics g) {
		g.setFont(font);
		fm = g.getFontMetrics(font);
		
//		System.out.println(fm.stringWidth(text));
		
		if(onHover) {
			Color darkColor = color.darker();
			g.setColor(darkColor);
			g.fillRoundRect(x + fm.stringWidth(text)/2 - 20, y - fm.getHeight()/2 - 3, (int) (fm.stringWidth(text) + 40), fm.getHeight() + 6, 45, 45);
			Text.drawString(g, text, x, y, true, new Color(200, 200, 200), font);
		} else {
			g.setColor(color);
			g.fillRoundRect(x + fm.stringWidth(text)/2 - 20, y - fm.getHeight()/2 - 3, (int) (fm.stringWidth(text) + 40), fm.getHeight() + 6, 45, 45);
			Text.drawString(g, text, x, y, true, new Color(200, 200, 200), font);
		}
		bounds = new Rectangle(x + fm.stringWidth(text)/2, y - fm.getHeight()/2, fm.stringWidth(text), fm.getHeight());
	}
}
