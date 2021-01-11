package com.BaZe.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Random;

import com.BaZe.main.Baze;
import com.BaZe.main.GameObject;
import com.BaZe.main.Handler;
import com.BaZe.main.ID;
import com.BaZe.main.PlaySound;
import com.BaZe.states.State;
import com.BaZe.tile.Tile;

public class Ball extends GameObject{
	public static final int MOVE_LEFT = 0;
	public static final int MOVE_RIGHT = 1;
	public static final int MOVE_DOWN = 2;
	public static final int MOVE_UP = 3;

	int width = Baze.WIDTH - (Baze.WIDTH / 16);
	int height = Baze.HEIGHT - (Baze.WIDTH / 16);

	Random rand = new Random();
	
	private Color defaultColor = new Color(randInt(155, 255), randInt(155, 255), randInt(155, 255));
	private Color darkerColor = defaultColor.darker();
	private Color ballColor;
	
	Handler handler;

	public Ball(int x, int y,  int side, ID id, Handler handler) {
		super(x, y, id, side);
		this.handler = handler;
		this.ballColor = defaultColor;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void tick() {
		if(velX == 0 && velY == 0) {
			ballColor = defaultColor;
		} else {
			ballColor = darkerColor;
		}
		
		x += velX;
		y += velY;
		
		x = Baze.clamp(x, 0, width);
		y = Baze.clamp(y, 0, height);
		
		collide();	
	}
	
	public void move(int Movement) {
		switch(Movement) {
			case MOVE_LEFT:
				this.velX = Baze.speed*-1;
				break;
			case MOVE_RIGHT:
				this.velX = Baze.speed;
				break;
			case MOVE_UP:
				this.velY = Baze.speed*-1;
				break;
			case MOVE_DOWN:
				this.velY = Baze.speed;
				break;
		}
	}
	
	private void collide() {
		for (int i = 0; i < handler.tile.size(); i++) {
			Tile tempObject = handler.tile.get(i);
			
			if(tempObject.getId() == ID.wallTile) {
				if(getBounds().intersects(tempObject.getBounds())) {
					if(State.currentState == Baze.getGameState()) {
						PlaySound.playSound("metal_impact.wav", 70, false);					
					}

					// If the ball moves in the horizontal pane
					if(getVelX() != 0) {
						if(getVelX() > 0) {	
							int wall = tempObject.getX() - tempObject.getSide();
							setX(wall + 6);
						} else {
							int wall = tempObject.getX() + tempObject.getSide();
							setX(wall + 6);
						}
						setVelX(0);
					}
					
					// If the ball moves in the vertical pane
					if(getVelY() != 0) {
						if(getVelY() > 0) {	
							int wall = tempObject.getY() - tempObject.getSide();
							setY(wall + 6);
						} else {
							int wall = tempObject.getY() + tempObject.getSide();
							setY(wall + 6);
						}
						setVelY(0);
					}
				}
			}
			
			if(tempObject.getId() == ID.floorTile) {
				if(getBounds().intersects(tempObject.getBounds()) && !tempObject.isPassed) {
					tempObject.color = defaultColor.darker().darker().darker();
					tempObject.isPassed = true;
					Baze.updatePassedFloor(1);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if(g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		
		g.setColor(ballColor);
		g.fillOval((int)x, (int)y, side-12, side-12);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, side-12, side-12);
	}

	public void setColor(Color c) {
		this.defaultColor = c;
		this.darkerColor = c.darker();
	}
	
	private int randInt(int min, int max) {
		return rand.nextInt((max - min) + 1) + min;
	}
}
