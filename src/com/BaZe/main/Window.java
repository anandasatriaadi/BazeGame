package com.BaZe.main;

import java.awt.Dimension;

import javax.swing.JFrame;

import com.BaZe.states.GameState;
import com.BaZe.states.MenuState;
import com.BaZe.states.State;

public class Window extends JFrame{
	private static final long serialVersionUID = 504538566987843579L;
	
	public Window(int width, int height, Baze game) {
		setTitle("BaZe");
		getContentPane().setPreferredSize(new Dimension(width, height));
		getContentPane().setMaximumSize(new Dimension(width, height));
		getContentPane().setMinimumSize(new Dimension(width, height));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setFocusable(true);

		
		add(game);
		pack();
		setVisible(true);
		game.start();
	}
	
}
