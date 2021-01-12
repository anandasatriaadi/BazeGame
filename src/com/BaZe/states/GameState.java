package com.BaZe.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.util.ArrayList;

import com.BaZe.assets.Assets;
import com.BaZe.main.Baze;
import com.BaZe.main.GameObject;
import com.BaZe.main.Handler;
import com.BaZe.main.PlaySound;
import com.BaZe.main.Window;
import com.BaZe.tile.Level;
import com.BaZe.tile.Tile;
import com.BaZe.ui.Button;
import com.BaZe.ui.Click;
import com.BaZe.ui.Container;
import com.BaZe.ui.Text;

public class GameState extends State{
	ArrayList<Button> buttons = new ArrayList<Button>();
	
	private Text txt_Level;
	private Text txt_Progress;
	private Text txt_Time;
	
	private Container bgContainer;
	private Container fgContainer;
	
	private Handler handler;
	private static final int MAX_LVL = 9;
	public static int totalFloor;
	public static Image CURRENT_WALL = Assets.brick_tile;
	private int passedFloor;
	public static int CURRENT_LVL = 1;
	
	private int[][] map;
	
	public GameState(Window window, Handler handler) {
		super(window, handler);
		this.handler = handler;
		
		initButton();
		initTitle();
		
		this.CURRENT_LVL = 1;
		
		Level.levelLoader(handler, map, Integer.toString(this.CURRENT_LVL));
	}

	@Override
	public void tick() {
		for(Button button : buttons) {
			button.tick();
		}
		txt_Time.content = getDuration();
		fgContainer.setWidth((int)Math.ceil((passedFloor/(float)totalFloor*230)));
	}

	@Override
	public void render(Graphics g) {
		if(g instanceof Graphics2D) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		
		handler.render(g);
		
		for(Button button : buttons) {
			button.render(g);
		}
		
		bgContainer.render(g);
		fgContainer.render(g);
		
		txt_Level.render(g);
		txt_Progress.render(g);
		txt_Time.render(g);
		
		if(passedFloor == totalFloor) {
			PlaySound.playSound("level_complete.wav", 70, false);
			handler.reset();
			this.CURRENT_LVL++;
			if(this.CURRENT_LVL >= 5) {
				Baze.Logs("Level >= 5");
				CURRENT_WALL = Assets.metal_wall;
			}
			if(this.CURRENT_LVL > MAX_LVL) {
				State.currentState = Baze.getGamefinishState();
				Baze.getGamefinishState().txt_TimeInfo.content = "Your time : " + getDuration();
			} else {
				passedFloor = 0;
				Level.levelLoader(handler, map, Integer.toString(this.CURRENT_LVL));
				txt_Level.content = "Level : "+ this.CURRENT_LVL;				
			}
		}
	}
	
	private void initButton() {
		// Add buttons
		buttons.add(new Button("Back", 100, 50, new Click() {
					@Override
					public void onClick() {
						State.currentState = Baze.getMenuState();
					}
				}, Baze.DISPLAY_FONT, new Color(185, 25, 0), new Color(255, 255, 255)));
		
		buttons.add(new Button("Restart", 850, 50, new Click() {
			@Override
			public void onClick() {
				Baze.RestartGame();
				State.currentState = Baze.getGameState();
			}
		}, Baze.DISPLAY_FONT, new Color(185, 25, 0), new Color(255, 255, 255)));
				
	}
	
	private void initTitle() {
		//Add containers
		bgContainer = new Container(470, 20, 230, 90, new Color(65, 65, 65), true);
		fgContainer = new Container(470 - (230/2), 105, 0, 5, new Color(85, 215, 85), false);
		
		//Add texts
		txt_Level = new Text("Level : " + CURRENT_LVL, 475, 40, true, new Color(215, 215, 215) , Baze.DISPLAY_FONT);
		txt_Progress = new Text("Progress : ", 475, 65, true, new Color(215, 215, 215) , Baze.DISPLAY_FONT);
		txt_Time = new Text(getDuration(), 475, 90, true, new Color(215, 215, 215) , Baze.DISPLAY_FONT);
		
	}
	

	private String getDuration() {
		int difference = (int) (System.currentTimeMillis() - Baze.startTime);
		
			int min = difference / 60 / 1000 % 60;
			int sec = (difference / 1000) % 60;
			int mili = difference / 10 % 100;
			String minStr, secStr, miliStr;
			
			if(min < 10) minStr = "0" + Integer.toString(min);
			else minStr = Integer.toString(min);
			
			if(sec < 10) secStr = "0" + Integer.toString(sec);
			else secStr = Integer.toString(sec);
			
			if(mili < 10) miliStr = "0" + Integer.toString(mili);
			else miliStr = Integer.toString(mili);
			
			return minStr + ":" + secStr + "." + miliStr;
	}

	public int getPassedFloor() {
		return passedFloor;
	}

	public void setPassedFloor(int passedFloor) {
		this.passedFloor = passedFloor;
		Baze.Logs("passedFloor : " + passedFloor + "/" + totalFloor);
		txt_Progress.content = "Progress : " + passedFloor + "/" + totalFloor;
	}
	
}
