package com.BaZe.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import com.BaZe.assets.Assets;
import com.BaZe.entity.Ball;
import com.BaZe.main.Baze;
import com.BaZe.main.GameObject;
import com.BaZe.main.Handler;
import com.BaZe.main.Window;
import com.BaZe.tile.Level;
import com.BaZe.tile.Tile;
import com.BaZe.ui.Button;
import com.BaZe.ui.Click;
import com.BaZe.ui.Text;

public class MenuState extends State{
	private ArrayList<Button> buttons = new ArrayList<Button>();
	private Text title;
	private Handler handler;
	private Ball ball = null;
	private Button btn_play;
	private Random rand = new Random();
	
	private int[][] map;
	private long lastTurn;
	
	public MenuState(Window window, Handler handler) {
		super(window, handler);
		this.handler = handler;
		btn_play = new Button("Play", Baze.WIDTH/2, Baze.HEIGHT/2 + 200, 100, 30, new Click() {
			@Override
			public void onClick() {
				if(btn_play.getText() == "Play") {
					Baze.startTime = System.currentTimeMillis();
					Baze.RestartGame();					
				}
				State.currentState = Baze.getGameState();
			}
		}, Baze.DISPLAY_FONT, new Color(0, 169, 126), new Color(215, 190, 1));
		
		buttons.add(btn_play);
		
		Level.levelLoader(handler, map, "mainmenu");
		
		for(GameObject go:handler.gameObject) {
			if(go instanceof Ball) {
				this.ball = (Ball) go;
				this.ball.setColor(new Color(215, 190, 1));
			}
		}
	}
	

	@Override
	public void tick() {
		if(GameState.CURRENT_LVL == 1) {
			btn_play.updateText("Play");
		}else {
			btn_play.updateText("Continue");			
		}
		
		for(Button button : buttons) {
			button.tick();
		}
		if(System.currentTimeMillis() - lastTurn > 100) {
			if(ball.getVelX() == 0 && ball.getVelY() == 0) {
				ball.move(rand.nextInt(8) % 4);
			}
			lastTurn = System.currentTimeMillis();
		}
	}

	@Override
	public void render(Graphics g) {
		if(g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		
		handler.render(g);
		
		g.setColor(new Color(0, 169, 126, 45));
		g.fillRect(0, 0, Baze.WIDTH, Baze.HEIGHT);
		g.drawImage(Assets.baze_logo, Baze.WIDTH/2-120, 75, null);
		
		for (Button button : buttons) {
			button.render(g);
		}
	}

}
