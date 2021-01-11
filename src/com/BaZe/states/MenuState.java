package com.BaZe.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import com.BaZe.entity.Ball;
import com.BaZe.main.Baze;
import com.BaZe.main.GameObject;
import com.BaZe.main.Handler;
import com.BaZe.main.Window;
import com.BaZe.tile.Level;
import com.BaZe.tile.Tile;
import com.BaZe.ui.Button;
import com.BaZe.ui.Click;

public class MenuState extends State{
	private ArrayList<Button> buttons;
	
	private Handler handler;
	private int[][] map;
	private Ball ball = null;
	private long lastTurn;
	
	public MenuState(Window window, Handler handler) {
		super(window, handler);
		this.handler = handler;
		
		buttons = new ArrayList<Button>();
		buttons.add(new Button("Play", Baze.WIDTH/2, Baze.HEIGHT/2, 100, 30, new Click() {

			@Override
			public void onClick() {
				Baze.startTime = System.currentTimeMillis();
				Baze.RestartGame();
				State.currentState = Baze.getGameState();
			}
		}, Baze.DISPLAY_FONT, new Color(185, 125, 65), new Color(215, 215, 215)));
		
		Level.levelLoader(handler, map, "mainmenu");
		
		for(GameObject go:handler.gameObject) {
			if(go instanceof Ball) {
				this.ball = (Ball) go;
				this.ball.setColor(new Color(155,0,0));
			}
		}
	}
	

	@Override
	public void tick() {
		for(Button button : buttons) {
			button.tick();
		}
		if(System.currentTimeMillis() - lastTurn > 100) {
			if(ball.getVelX() == 0 && ball.getVelY() == 0) {
				Random rand = new Random();
				((Ball)ball).move(rand.nextInt() % 4);
			}
			lastTurn = System.currentTimeMillis();
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
