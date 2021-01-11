package com.BaZe.assets;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {
	// Loads buffered image to be used later
	public static final Image metal_wall = loadImage(new File("resources/images/metal_tiles.jpg")).getScaledInstance(60, 60, BufferedImage.SCALE_SMOOTH);
	public static final Image brick_tile = loadImage(new File("resources/images/BrickTile.png")).getScaledInstance(60, 60, BufferedImage.SCALE_SMOOTH);
	public static final Image baze_logo = loadImage(new File("resources/BazeIcon.png")).getScaledInstance(240, 240, BufferedImage.SCALE_SMOOTH);
	
	// Loads font to be used later
//	public static final Font baze_normal = loadFont(new File("resources/font/"));
	
	public static BufferedImage loadImage(File path)
	{
		try {
			return ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Font loadFont(File path, int size){
		try {
			return Font.createFont(Font.TRUETYPE_FONT, path).deriveFont(Font.PLAIN, size);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
