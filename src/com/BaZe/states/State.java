package com.BaZe.states;

import java.awt.Graphics;

import com.BaZe.main.Handler;
import com.BaZe.main.Window;

public abstract class State {
	public static State currentState = null;
	protected Window window;
	
	public State(Window window, Handler handler) {
		this.window = window;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
}
