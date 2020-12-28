package com.BaZe.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.BaZe.entity.Ball;
import com.BaZe.input.KeyInput;
import com.BaZe.tile.FloorTile;
import com.BaZe.tile.Tile;
import com.BaZe.tile.WallTile;

public class Baze extends Canvas implements Runnable{

	private static final long serialVersionUID = 1652027885447519067L;
	
	public static int WIDTH = 960, HEIGHT = WIDTH / 16 * 10;
	public static float speed = (float) Math.ceil(WIDTH/80);
	
	private Thread gameThread;
	private boolean running = false;
	
	private Handler handler;
	private HUD hud;
	
	public static boolean debug = false;
	private static int passedFloor = 0;
	private static int totalFloor = 0;
	
	public Baze() {
		handler = new Handler();
		hud = new HUD();
		this.addKeyListener(new KeyInput(handler));
		
		int tileSide = WIDTH / 16;
		int ballX = 0;
		int ballY = 0;
		boolean foundBall = false;
		
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
		new Window(WIDTH, HEIGHT, "Test Tile", this);
		
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
	
	public synchronized void start() {
		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public synchronized void stop() {
		try {
			gameThread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
//		double amountOfTicks = 60.0;
		double ns = 1000000000 / 60;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1) {
				tick();
				delta--;
			}
			
			if(running) render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames);
				frames = 0;
			};
			
			if(totalFloor == passedFloor) {
				stop();
			}
		}
		
		stop();
	}
	
	private void tick() {
		handler.tick();
		hud.tick();
	}
	
	private void render() {
		Color backgroundColor = new Color(0, 0, 0);
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(backgroundColor);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		hud.render(g);
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max) {
		if(var >= max) {
			return var = max;
		} else if(var <= min) {
			return var = min;
		} else {
			return var;
		}
	}
	
	public static void main(String[] args) {
		new Baze();
	}

	public static int getPassedFloor() {
		return passedFloor;
	}

	public static void setPassedFloor(int passedFloor) {
		Baze.passedFloor = passedFloor;
	}
}
