package com.BaZe.assets;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {
	public static final Image metal_wall = loadImage(new File("resources/images/metal_tiles.jpg")).getScaledInstance(60, 60, BufferedImage.SCALE_DEFAULT);;
	public static final Image brick_tile = loadImage(new File("resources/images/BrickTile.png")).getScaledInstance(60, 60, BufferedImage.SCALE_DEFAULT);;
	public static BufferedImage loadImage(File path)
	{
		try {
			return ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
