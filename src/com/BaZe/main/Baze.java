package com.BaZe.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import com.BaZe.input.KeyInput;
import com.BaZe.input.MouseInput;
import com.BaZe.states.GameState;
import com.BaZe.states.MenuState;
import com.BaZe.states.State;

public class Baze extends Canvas implements Runnable{

	private static final long serialVersionUID = 1652027885447519067L;
	
	public static final int WIDTH = 960, HEIGHT = WIDTH / 16 * 10;
	public static float speed = (float) Math.ceil(WIDTH/80);
	
	private Window window;
	private Thread gameThread;
	private boolean running = false;
	
	private Handler handler;
	private HUD hud;
	
	private static MenuState menuState;
	private static GameState gameState;
	
	private MouseInput mouseInput;
	
	public static boolean debug = true;
	private static int passedFloor = 0;
	private static int totalFloor = 0;
	
	public Baze() {
		handler = new Handler();
		hud = new HUD();
		mouseInput = new MouseInput();
		this.addKeyListener(new KeyInput(handler));
		this.addMouseMotionListener(mouseInput);
		this.addMouseListener(mouseInput);

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
//		double amountOfTicks = 60.0;
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
				System.out.println("FPS: " + frames);
				frames = 0;
			};
			
		}
		
		stop();
	}
	
	private void tick() {
		handler.tick();
		hud.tick();
		if(State.currentState != null) {
//			System.out.println(State.currentState);
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
		
//		handler.render(g);
//		
		if(State.currentState != null) {
//			System.out.println(State.currentState);
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

	public static int getPassedFloor() {
		return passedFloor;
	}

	public static void setPassedFloor(int passedFloor) {
		Baze.passedFloor = passedFloor;
	}
	
	public void init() {
		gameState = new GameState(window, handler);
		menuState = new MenuState(window, handler);
		State.currentState = gameState;
	}

	public static State getMenuState() {
		return menuState;
	}

	public static State getGameState() {
		return gameState;
	}
}
