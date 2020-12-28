package com.BaZe.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.BaZe.ui.Button;

public class HUD {
	private static int noMoves = 0;
	private static Button moves = new Button(
			"Moves: ", 15, 35, null, new Font("Comic Sans", Font.PLAIN, 24), new Color(85, 155, 185));
	
	public void tick() {
		moves.updateText("Moves: " + noMoves);
		moves.tick();
	}
	
	public void render(Graphics g) {
		moves.render(g);
	}

	public static int getNoMoves() {
		return noMoves;
	}

	public static void setNoMoves(int noMoves) {
		HUD.noMoves = noMoves;
	}

}
