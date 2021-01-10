package com.BaZe.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import com.BaZe.main.Baze;
import com.BaZe.main.Handler;
import com.BaZe.main.Window;
import com.BaZe.ui.Button;
import com.BaZe.ui.Click;

public class MenuState extends State{
	
	Font displayFont = new Font("Comic Sans", Font.BOLD, 24);

	ArrayList<Button> buttons = new ArrayList<Button>();
	
	public MenuState(Window window, Handler handler) {
		super(window, handler);
		
		buttons.add(new Button("Play", Baze.WIDTH/2, Baze.HEIGHT/2, new Click() {

			@Override
			public void onClick() {
				State.currentState = Baze.getGameState();
			}
		}, displayFont, new Color(185, 185, 185), new Color(45, 45, 45)));
	}

	@Override
	public void tick() {
		for(Button button : buttons) {
			button.tick();
		}
	}

	@Override
	public void render(Graphics g) {
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
