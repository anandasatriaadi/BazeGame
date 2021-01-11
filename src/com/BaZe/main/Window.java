package com.BaZe.main;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Window extends JFrame{
	private static final long serialVersionUID = 504538566987843579L;
	
	public Window(int width, int height, Baze game) {
		setTitle("BaZe");
		getContentPane().setPreferredSize(new Dimension(width, height));
		getContentPane().setMaximumSize(new Dimension(width, height));
		getContentPane().setMinimumSize(new Dimension(width, height));
		setIconImage(new ImageIcon("resources/BazeIcon.png").getImage());
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setFocusable(true);

		
		add(game);
		pack();
		setVisible(true);
		game.start();
	}
	
}
