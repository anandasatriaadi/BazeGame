package com.BaZe.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.BaZe.assets.Assets;
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
		g.drawImage(Assets.brick_tile, x, y, null);
//		g.setColor(Color.BLACK);
//		g.fillRect(x, y, side, side);
//		g.setColor(color);
//		g.fillRect(x+1, y+1, side-1, side-1);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, side, side);
	}

}
