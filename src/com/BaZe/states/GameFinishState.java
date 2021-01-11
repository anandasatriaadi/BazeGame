package com.BaZe.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.BaZe.entity.Ball;
import com.BaZe.main.Baze;
import com.BaZe.main.Handler;
import com.BaZe.main.ID;
import com.BaZe.main.Window;
import com.BaZe.tile.FloorTile;
import com.BaZe.tile.Tile;
import com.BaZe.tile.WallTile;
import com.BaZe.ui.Button;
import com.BaZe.ui.Click;
import com.BaZe.ui.Text;

public class GameFinishState extends State {
	private ArrayList<Button> buttons;
	private Handler handler;
	private static final int ROWS = 12;
	private static final int COLUMNS = 16;
	private static final int TILESIDE = Baze.WIDTH/COLUMNS;
	private int[][] map;
	private String totalTime;
	
	private Text txt_Congratulations;
	public Text txt_TimeInfo;
	
	public GameFinishState(Window window, Handler handler) {
		super(window, handler);
		this.handler = handler;
		this.buttons = new ArrayList<Button>();
		
		buttons.add(new Button("Play Again", Baze.WIDTH/2, Baze.HEIGHT/2 + 30, new Click() {

			@Override
			public void onClick() {
				Baze.RestartGame();
				State.currentState = Baze.getGameState();
			}
		}, Baze.DISPLAY_FONT, new Color(185, 185, 185), new Color(45, 45, 45)));
		
		txt_Congratulations = new Text("Congratulations!", Baze.WIDTH/2, Baze.HEIGHT/2 - 40, true, new Color(200, 200, 200) , Baze.DISPLAY_FONT);
		txt_TimeInfo = new Text("Your Time : " + totalTime, Baze.WIDTH/2, Baze.HEIGHT/2 - 10, true, new Color(200, 200, 200) , Baze.DISPLAY_FONT);

		this.loadLevel("gamefinish");
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
				}
			}
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
		if(g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		
		handler.render(g);
		
		for (Button button : buttons) {
			button.render(g);
		}
		txt_Congratulations.render(g);
		txt_TimeInfo.render(g);
	}

}
