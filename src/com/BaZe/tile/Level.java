package com.BaZe.tile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.BaZe.entity.Ball;
import com.BaZe.main.Baze;
import com.BaZe.main.GameObject;
import com.BaZe.main.Handler;
import com.BaZe.main.ID;
import com.BaZe.states.GameState;

public class Level {
	private static int ballX;
	private static int ballY;
	
	public static void levelLoader(Handler handler, int[][] map, String name) {
		GameState.totalFloor = 0;
		map = new int[Baze.ROWS][Baze.COLUMNS];
		
		try {
			File levelFile = new File("resources/level/"+name+".txt");
			Scanner input = new Scanner(levelFile);
			
			for(int i=0; i<Baze.ROWS; i++) {
				for(int j=0; j<Baze.COLUMNS; j++) {
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
			for(int i = 0; i < Baze.ROWS; i++) {
				for(int j = 0; j < Baze.COLUMNS; j++) {	
					if(map[i][j] == 1) {
						Tile wallTile = new WallTile(j * Baze.TILESIDE, i * Baze.TILESIDE, ID.wallTile, Baze.TILESIDE);
						Baze.Logs("Added WallTile " + j * Baze.TILESIDE + " " + i * Baze.TILESIDE);
						handler.addTileObject(wallTile);
					} else {
						Tile floorTile = new FloorTile(j * Baze.TILESIDE, i * Baze.TILESIDE, ID.floorTile, Baze.TILESIDE );
						Baze.Logs("Added FloorTile " + j * Baze.TILESIDE + " " + i * Baze.TILESIDE);
						handler.addTileObject(floorTile);
						GameState.totalFloor += 1;
					}
					
					if(map[i][j] == 9) {
						ballX = j;
						ballY = i;
					}
				}
			}
			
			GameObject ball = new Ball(ballX * Baze.TILESIDE + 6, ballY * Baze.TILESIDE + 6, Baze.TILESIDE, ID.ball, handler);
			handler.addGameObject(ball);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
