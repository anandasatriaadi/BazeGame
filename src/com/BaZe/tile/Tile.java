package com.BaZe.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.BaZe.main.ID;

public abstract class Tile {
	protected int x, y;
	protected ID id;
	protected int velX, velY;
	protected int side;
	public Boolean isPassed = false;
	public Color color;
	
	public Tile(int x, int y, ID id, int side) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.setSide(side);
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public int getVelX() {
		return velX;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public int getVelY() {
		return velY;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}
}
