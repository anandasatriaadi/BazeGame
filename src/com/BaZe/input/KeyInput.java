package com.BaZe.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import com.BaZe.main.Baze;
import com.BaZe.main.GameObject;
import com.BaZe.main.HUD;
import com.BaZe.main.Handler;
import com.BaZe.main.ID;

public class KeyInput extends KeyAdapter{
	private Handler handler;
	
	public KeyInput(Handler handler) {
		this.handler = handler;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		for(int i = 0; i < handler.gameObject.size(); i++) {
			GameObject tempObject = handler.gameObject.get(i);
			
			if(tempObject.getId() == ID.ball && tempObject.getVelX() == 0 &&  tempObject.getVelY() == 0) {
				if((key == KeyEvent.VK_W && key != KeyEvent.VK_S) ||
						(key == KeyEvent.VK_UP && key != KeyEvent.VK_DOWN)) tempObject.setVelY(-Baze.speed);
				if(key == KeyEvent.VK_S && key != KeyEvent.VK_W ||
						(key == KeyEvent.VK_DOWN && key != KeyEvent.VK_UP)) tempObject.setVelY(Baze.speed);
				if(key == KeyEvent.VK_A && key != KeyEvent.VK_D ||
						(key == KeyEvent.VK_LEFT && key != KeyEvent.VK_RIGHT)) tempObject.setVelX(-Baze.speed);
				if(key == KeyEvent.VK_D && key != KeyEvent.VK_A ||
						(key == KeyEvent.VK_RIGHT && key != KeyEvent.VK_LEFT)) tempObject.setVelX(Baze.speed);
				HUD.setNoMoves(HUD.getNoMoves() + 1);
			}
		}
	}
}
