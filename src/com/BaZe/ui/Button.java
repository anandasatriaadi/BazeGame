package com.BaZe.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import com.BaZe.input.MouseInput;
import com.BaZe.main.Baze;
import com.BaZe.main.PlaySound;

public class Button {
	private int x, y, marginHeight, marginWidth;
	private FontMetrics fm;
	private Rectangle bounds;
	private boolean onHover;
	private Click click;
	private Font font;
	private Color bgColor, darkerBGColor;
	
	private Text text;
	
	public Button(String text, int x, int y, Click click, Font font, Color bgColor, Color fontColor) {
		this.x = x;
		this.y = y;
		this.text = new Text(text, x, y, true, fontColor, font);
		this.font = font;
		this.click = click;
		this.bgColor = bgColor;
		this.darkerBGColor = bgColor.darker();
		this.marginHeight = 6;
		this.marginWidth = 40;
		onHover = false;
	}
	
	public Button(String text, int x, int y, int marginWidth, int marginHeight, Click click, Font font, Color bgColor, Color fontColor) {
		this.x = x;
		this.y = y;
		this.text = new Text(text, x, y, true, fontColor, font);
		this.font = font;
		this.click = click;
		this.bgColor = bgColor;
		this.darkerBGColor = bgColor.darker();
		this.marginHeight = marginHeight;
		this.marginWidth = marginWidth;
		onHover = false;
	}
	
	public void updateText(String text) {
		this.text.content = text;
	}
	
	public void tick() {
		if(bounds != null && bounds.contains(MouseInput.x, MouseInput.y)) {
			onHover = true;
			if(MouseInput.left) {
				click.onClick();
				PlaySound.playSound("button_click.wav", 100, false);
			}
		} else {
			onHover = false;
		}
	}
	
	public void render(Graphics g) {
		if(g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		
		g.setFont(font);
		fm = g.getFontMetrics(font);
		
		int stringWidth = (int) (fm.getStringBounds(text.content, g).getWidth());
		int stringHeight = (int) (fm.getStringBounds(text.content, g).getHeight());
		
		if(onHover) {
//			Baze.Logs(this + " mouse in");
			g.setColor(darkerBGColor);
			g.fillRoundRect(x - (stringWidth/2 + marginWidth/2), y - (stringHeight/2 + marginHeight/2), stringWidth + marginWidth, stringHeight + marginHeight, 50, 50);
		} else {
			g.setColor(bgColor);
			g.fillRoundRect(x - (stringWidth/2 + marginWidth/2), y - (stringHeight/2 + marginHeight/2), stringWidth + marginWidth, stringHeight + marginHeight, 50, 50);
		}
		text.render(g);
		bounds = new Rectangle(x - (stringWidth/2 + marginWidth/2), y - (stringHeight/2 + marginHeight/2), stringWidth + marginWidth, stringHeight + marginHeight);
	}
}
