package com.BaZe.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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

public class MenuState extends State{
	private ArrayList<Button> buttons;
	
	private Handler handler;
	private static final int ROWS = 12;
	private static final int COLUMNS = 16;
	private static final int TILESIDE = Baze.WIDTH/COLUMNS;
	private int[][] map;
	private int ballX;
	private int ballY;
	
	private GameObject ball;
	
	public MenuState(Window window, Handler handler) {
		super(window, handler);
		this.handler = handler;
		
		buttons = new ArrayList<Button>();
		buttons.add(new Button("Play", Baze.WIDTH/2, Baze.HEIGHT/2, new Click() {

			@Override
			public void onClick() {
				Baze.RestartGame();
				State.currentState = Baze.getGameState();
			}
		}, Baze.DISPLAY_FONT, new Color(185, 185, 185), new Color(45, 45, 45)));
		
		
		this.loadLevel("mainmenu");
	}
	
	private void loadLevel(String name) {
		map = new int[ROWS][COLUMNS];
		
		try {
			File levelFile = new File("resources/level/"+name+".txt");
			Scanner input = new Scanner(levelFile);
			
			for(int i=0; i<ROWS; i++) {
				for(int j=0; j<COLUMNS; j++) {
					if(input.hasNextInt())
			        {
			            map[i][j] = input.nextInt();
			        }
				}
			}
			input.close();
		}catch (FileNotFoundException e) {
		      System.out.println("Error file level not found");
		      e.printStackTrace();
		}
		
		// Trying to add gameObjects
		try {
			// Add Tiles
			for(int i = 0; i < ROWS; i++) {
				for(int j = 0; j < COLUMNS; j++) {	
					if(map[i][j] == 1) {
						Tile wallTile = new WallTile(j * TILESIDE, i * TILESIDE, ID.wallTile, TILESIDE);
						Baze.Logs("Added WallTile " + j * TILESIDE + " " + i * TILESIDE);
						handler.addTileObject(wallTile);
					} else {
						Tile floorTile = new FloorTile(j * TILESIDE, i * TILESIDE, ID.floorTile, TILESIDE );
						Baze.Logs("Added FloorTile " + j * TILESIDE + " " + i * TILESIDE);
						handler.addTileObject(floorTile);
					}
					
					if(map[i][j] == 9) {
						ballX = j;
						ballY = i;
					}
				}
			}
			
			ball = new Ball(ballX * TILESIDE + 6, ballY * TILESIDE + 6, TILESIDE, ID.ball, handler);
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
		if(ball.getVelX() == 0 &&  ball.getVelY() == 0) {
			Random rand = new Random();
			((Ball)ball).move(rand.nextInt() % 4);
		}
	}

	@Override
	public void render(Graphics g) {
		for(Tile tile : handler.tile) {
			tile.render(g);
		}
		for(GameObject go : handler.gameObject) {
			go.render(g);
		}
		
		for (Button button : buttons) {
			button.render(g);
		}
		if(Baze.debug) {
			g.setColor(Color.red);
			g.drawLine(0, Baze.HEIGHT/2,Baze.WIDTH, Baze.HEIGHT/2);
			g.drawLine(0, Baze.HEIGHT/3,Baze.WIDTH, Baze.HEIGHT/3);
			g.drawLine(0, Baze.HEIGHT/3*2,Baze.WIDTH, Baze.HEIGHT/3*2);
			g.drawLine(Baze.WIDTH/2, 0,Baze.WIDTH/2, Baze.HEIGHT);
			g.drawLine(Baze.WIDTH/3, 0,Baze.WIDTH/3, Baze.HEIGHT);
			g.drawLine(Baze.WIDTH/3*2, 0,Baze.WIDTH/3*2, Baze.HEIGHT);
		}
	}

}
