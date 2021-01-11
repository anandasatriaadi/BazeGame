package com.BaZe.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.BaZe.main.ID;

public class FloorTile extends Tile{

	public int image;
	public Boolean isPassed = false;
	
	public FloorTile(int x, int y, ID id, int side) {
		super(x, y, id, side);
		this.color = new Color(45, 45, 45);
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
