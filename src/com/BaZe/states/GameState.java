package com.BaZe.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.BaZe.main.Baze;
import com.BaZe.main.GameObject;
import com.BaZe.main.Handler;
import com.BaZe.main.Window;
import com.BaZe.tile.Level;
import com.BaZe.tile.Tile;
import com.BaZe.ui.Button;
import com.BaZe.ui.Click;
import com.BaZe.ui.Text;

public class GameState extends State{
	ArrayList<Button> buttons = new ArrayList<Button>();
	ArrayList<Text> texts = new ArrayList<Text>();
	
	private Text txt_Level;
	private Text txt_Progress;
	private Text txt_Time;
	
	private Handler handler;
	private static final int MAX_LVL = 7;
	public static int totalFloor;
	private int passedFloor;
	private int currentLevel = 1;
	
	private int[][] map;
	
	public GameState(Window window, Handler handler) {
		super(window, handler);
		this.handler = handler;
		
		buttons.add(new Button("Back", 100, 50, new Click() {
					@Override
					public void onClick() {
						State.currentState = Baze.getMenuState();
					}
				}, Baze.DISPLAY_FONT, new Color(85, 155, 185), new Color(200, 200, 200)));
		
		buttons.add(new Button("Restart", 850, 50, new Click() {
			@Override
			public void onClick() {
				Baze.RestartGame();
				State.currentState = Baze.getGameState();
			}
		}, Baze.DISPLAY_FONT, new Color(85, 155, 185), new Color(200, 200, 200)));
		
		txt_Level = new Text("Level : " + currentLevel, 475, 40, true, new Color(15, 15, 15) , Baze.DISPLAY_FONT);
		txt_Progress = new Text("Progress : ", 475, 65, true, new Color(15, 15, 15) , Baze.DISPLAY_FONT);
		txt_Time = new Text(getDuration(), 475, 90, true, new Color(15, 15, 15) , Baze.DISPLAY_FONT);
		
		Level.levelLoader(handler, map, Integer.toString(this.currentLevel));
	}

	@Override
	public void tick() {
		for(Button button : buttons) {
			button.tick();
		}
		txt_Time.content = getDuration();
	}

	@Override
	public void render(Graphics g) {
		for(Tile tile:handler.tile) {			
			tile.render(g);
		}
		for(GameObject go:handler.gameObject) {			
			go.render(g);
		}
		for(Button button : buttons) {
			button.render(g);
		}
		
		txt_Level.render(g);
		txt_Progress.render(g);
		txt_Time.render(g);
		
		if(passedFloor == totalFloor) {
			handler.reset();	
			this.currentLevel++;
			if(this.currentLevel > MAX_LVL) {
				State.currentState = Baze.gamefinishState;
				Baze.gamefinishState.txt_TimeInfo.content = "Your time : " + getDuration();
			} else {
				passedFloor = 0;
				Level.levelLoader(handler, map, Integer.toString(this.currentLevel));
				txt_Level.content = "Level : "+ this.currentLevel;				
			}
		}
	}

	public int getPassedFloor() {
		return passedFloor;
	}

	public void setPassedFloor(int passedFloor) {
		this.passedFloor = passedFloor;
		Baze.Logs("passedFloor : " + passedFloor + "/" + totalFloor);
		txt_Progress.content = "Progress : " + passedFloor + "/" + totalFloor;
	}
	
	private String getDuration() {
		int difference = (int) (System.currentTimeMillis() - Baze.startTime);
		
		if(difference > 60000) {
			int min = difference / 60 / 1000;
			int sec = (difference / 1000) % 60;
			int mili = difference / 10;
			String minStr, secStr;
			
			if(min < 10) minStr = "0" + Integer.toString(min);
			else minStr = Integer.toString(min);
			
			if(sec < 10) secStr = "0" + Integer.toString(sec);
			else secStr = Integer.toString(sec);
			
			return minStr + ":" + secStr;
		}
		return "" + (difference / 1000);
	}
}
