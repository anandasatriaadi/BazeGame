package com.BaZe.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

public class GameState extends State{

	Font displayFont = new Font("Comic Sans", Font.PLAIN, 24);
	ArrayList<Button> buttons = new ArrayList<Button>();
	private Handler handler;
	
	private static final int ROWS = 12;
	private static final int COLUMNS = 16;
	private static final int TILESIDE = Baze.WIDTH/COLUMNS;
	private int totalFloor;
	private int passedFloor;
	private int currentLevel = 1;
	
	private int[][] map;
	private int ballX;
	private int ballY;
	
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
		
		buttons.add(new Button("Next", 850, 50, new Click() {
			@Override
			public void onClick() {
				System.out.println("Clicked");
				State.currentState = Baze.getMenuState();
			}
		}, displayFont, new Color(85, 155, 185), new Color(200, 200, 200)));
		
		this.loadLevel(currentLevel);
	}
	
	private void loadLevel(int level) {
		totalFloor = 0;
		passedFloor = 0;
		map = new int[ROWS][COLUMNS];
		
		try {
			File levelFile = new File("resources/level/"+level+".txt");
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
						totalFloor++;
					}
					
					if(map[i][j] == 9) {
						ballX = j;
						ballY = i;
					}
				}
			}
			
			GameObject ball = new Ball(ballX * TILESIDE + 6, ballY * TILESIDE + 6, TILESIDE, ID.ball, handler);
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

	public int getPassedFloor() {
		return passedFloor;
	}

	public void setPassedFloor(int passedFloor) {
		this.passedFloor = passedFloor;
		Baze.Logs("passedFloor : " + passedFloor + "/" + totalFloor);
		if(passedFloor == totalFloor) {
			handler.reset();
			this.currentLevel++;
			loadLevel(this.currentLevel);
		}
	}
}
