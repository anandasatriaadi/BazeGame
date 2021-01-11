package com.BaZe.main;

import java.awt.Graphics;
import java.util.LinkedList;

import com.BaZe.tile.Tile;

public class Handler {
	public LinkedList<GameObject> gameObject = new LinkedList<GameObject>();
	public LinkedList<Tile> tile = new LinkedList<Tile>();
	
	public void tick() {
		for(int i = 0; i < tile.size(); i++) {
			Tile tempObject = tile.get(i);
			
			tempObject.tick();
		}
		for(int i = 0; i < gameObject.size(); i++) {
			GameObject tempObject = gameObject.get(i);
			
			tempObject.tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < tile.size(); i++) {
			Tile tempObject = tile.get(i);
			
			tempObject.render(g);
		}
		for(int i = 0; i < gameObject.size(); i++) {
			GameObject tempObject = gameObject.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void addGameObject(GameObject gameObject) {
		this.gameObject.add(gameObject);
	}
	
	public void removeGameObject(GameObject gameObject) {
		this.gameObject.remove(gameObject);
	}
	
	public void addTileObject(Tile tile) {
		this.tile.add(tile);
	}
	
	public void removeTileObject(Tile tile) {
		this.tile.remove(tile);
	}
	
	public void reset() {
		this.tick();
		tile = new LinkedList<Tile>();
		gameObject = new LinkedList<GameObject>();
	}
}
