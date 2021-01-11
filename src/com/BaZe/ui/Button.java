package com.BaZe.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.BaZe.input.MouseInput;
import com.BaZe.main.Baze;
import com.BaZe.main.PlaySound;

public class Button {
	private int x, y;
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
		g.setFont(font);
		fm = g.getFontMetrics(font);
		
		int stringWidth = (int) (fm.getStringBounds(text.content, g).getWidth());
		int stringHeight = (int) (fm.getStringBounds(text.content, g).getHeight());
		
		if(onHover) {
			Baze.Logs(this + " mouse in");
			g.setColor(darkerBGColor);
			g.fillRoundRect(x - (stringWidth/2 + 20), y - (stringHeight/2 + 3), stringWidth + 40, stringHeight + 6, 50, 50);
		} else {
			g.setColor(bgColor);
			g.fillRoundRect(x - (stringWidth/2 + 20), y - (stringHeight/2 + 3), stringWidth + 40, stringHeight + 6, 50, 50);
		}
		text.render(g);
		bounds = new Rectangle(x - (stringWidth/2 + 20), y - (stringHeight/2 + 3), stringWidth + 40, stringHeight + 6);
	}
}
