package com.BaZe.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.BaZe.main.ID;

public class WallTile extends Tile{

	public WallTile(int x, int y, ID id, int side) {
		super(x, y, id, side);
		this.color = new Color(155, 155, 155);
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {		
		g.setColor(color);
		g.fillRect(x, y, side, side);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, side, side);
	}

}
