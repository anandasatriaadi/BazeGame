package com.BaZe.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import com.BaZe.entity.Ball;
import com.BaZe.main.Baze;
import com.BaZe.main.GameObject;
import com.BaZe.main.Handler;
import com.BaZe.main.ID;
import com.BaZe.main.Window;
import com.BaZe.tile.FloorTile;
import com.BaZe.tile.Tile;
import com.BaZe.tile.WallTile;
import com.BaZe.ui.Button;
import com.BaZe.ui.Click;

public class GameState extends State{

	Font displayFont = new Font("Comic Sans", Font.PLAIN, 24);
	ArrayList<Button> buttons = new ArrayList<Button>();
	
	private Handler handler;
	
	public GameState(Window window, Handler handler) {
		super(window, handler);
		this.handler = handler;
		
		buttons.add(new Button("Back", 100, 50, new Click() {
					@Override
					public void onClick() {
						System.out.println("Clicked");
						State.currentState = Baze.getMenuState();
					}
				}, displayFont, new Color(85, 155, 185), new Color(200, 200, 200)));
		
		int 	ballX = 0,
				ballY = 0;
		boolean foundBall = false;
		int 	totalFloor = 0;
		int 	tileSide = Baze.WIDTH / 16;
		
//		Map: 0 = Floor, 1 = Wall, 9 = Ball
		int[][] map = {
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,9,0,1,0,0,0,1,0,1,1,0,0,0,0,1},
				{1,1,0,1,1,0,0,0,0,1,1,0,1,1,0,1},
				{1,0,0,0,0,0,1,1,0,1,1,0,1,1,0,1},
				{1,1,0,1,0,1,0,0,0,0,0,0,1,1,0,1},
				{1,0,0,0,0,1,1,1,0,1,0,0,0,1,0,1},
				{1,0,1,1,0,1,0,1,0,0,0,1,0,1,0,1},
				{1,0,1,0,0,1,0,0,0,0,1,0,0,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
		};
		
		try {			
			//	Adding gameObjects
			//		Tiles
			for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[0].length; j++) {	
					if(map[i][j] == 1) {
						Tile wallTile = new WallTile(j * tileSide,
								i * tileSide, ID.wallTile, tileSide );
						System.out.println("Added WallTile " + j * tileSide + " " + i * tileSide);;
						handler.addTileObject(wallTile);
					} else {
						Tile floorTile = new FloorTile(j * tileSide,
								i * tileSide, ID.floorTile, tileSide );
						System.out.println("Added FloorTile " + j * tileSide + " " + i * tileSide);
						handler.addTileObject(floorTile);
						totalFloor++;
					}
					
					if(map[i][j] == 9 && !foundBall) {
						ballX = j;
						ballY = i;
						foundBall = true;
					}
				}
			}
			
			GameObject ball = new Ball(ballX * tileSide + 6, ballY * tileSide + 6, tileSide, ID.ball, handler);
			handler.addGameObject(ball);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {
		for(Button button : buttons) {
			button.tick();
		}
	}

	@Override
	public void render(Graphics g) {
		for(int i = 0; i < handler.tile.size(); i++) {
			Tile tempObject = handler.tile.get(i);
			
			tempObject.render(g);
		}
		for(int i = 0; i < handler.gameObject.size(); i++) {
			GameObject tempObject = handler.gameObject.get(i);
			
			tempObject.render(g);
		}
		
		for(Button button : buttons) {
			button.render(g);
		}
	}

}
