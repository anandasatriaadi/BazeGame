package com.BaZe.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.BaZe.assets.Assets;
import com.BaZe.input.KeyInput;
import com.BaZe.input.MouseInput;
import com.BaZe.states.GameFinishState;
import com.BaZe.states.GameState;
import com.BaZe.states.MenuState;
import com.BaZe.states.State;
import com.BaZe.tile.WallTile;

public class Baze extends Canvas implements Runnable{

	private static final long serialVersionUID = 1652027885447519067L;
	public static final int WIDTH = 60*16, HEIGHT = WIDTH / 16 * 12;
	public static final int ROWS = 12;
	public static final int COLUMNS = 16;
	public static final int TILESIDE = Baze.WIDTH/COLUMNS;
	public static float speed = (float) Math.ceil(WIDTH/40);
	public static final Font DISPLAY_FONT = new Font("Comic Sans", Font.BOLD, 24);
	private static final DateTimeFormatter TIMEFORMATER = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
	public static long startTime;
	
	private static Window window;
	private Thread gameThread;
	private boolean running = false;
	
	private static Handler gameHandler;
	private Handler menuHandler;
	private Handler gamefinishHandler;

	private HUD hud;
	
	private static MenuState menuState;
	private static GameState gameState;
	private static GameFinishState gamefinishState;
	
	private MouseInput mouseInput;
	
	public static boolean debug = false;
	
	public Baze() {
		gameHandler = new Handler();
		menuHandler = new Handler();
		gamefinishHandler = new Handler();
		hud = new HUD();
		
		mouseInput = new MouseInput();
		this.addKeyListener(new KeyInput(gameHandler));
		this.addMouseMotionListener(mouseInput);
		this.addMouseListener(mouseInput);
		PlaySound.playSound("MainMenuSong.wav", 60, true);

		window = new Window(WIDTH, HEIGHT, this);;
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
		double ns = 1000000000 / 60;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		
		init();
		
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
				Logs("FPS: " + frames);
				frames = 0;
			};
			
		}
		
		stop();
	}
	
	private void tick() {
		if(State.currentState != null) {
			if(State.currentState == gameState) {
				gameHandler.tick();				
			}else if(State.currentState == menuState) {
				menuHandler.tick();				
			}else {
				gamefinishHandler.tick();
			}
			State.currentState.tick();
		}
	}
	
	private void render() {
		Color backgroundColor = new Color(185, 185, 185);
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(backgroundColor);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// Renders the current state
		if(State.currentState != null) {
			State.currentState.render(g);
		}
		
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
	
	public void init() {
		gameState = new GameState(window, gameHandler);
		menuState = new MenuState(window, menuHandler);
		gamefinishState = new GameFinishState(window, gamefinishHandler);
		State.currentState = menuState;
	}
	
	public static void RestartGame() {
		GameState.CURRENT_WALL = Assets.brick_tile;
		startTime = System.currentTimeMillis();
		gameHandler.reset();
		gameState = new GameState(window, gameHandler);
	}

	public static State getMenuState() {
		return menuState;
	}

	public static State getGameState() {
		return gameState;
	}
	
	public static GameFinishState getGamefinishState() {
		return gamefinishState;
	}

	public static void Logs(String msg) {
		System.out.println(TIMEFORMATER.format(LocalDateTime.now()) + " : " + msg);		
	}
	
	public static void updatePassedFloor(int value) {
		if(State.currentState == gameState) {			
			gameState.setPassedFloor(gameState.getPassedFloor() + value);
		}
	}
}
