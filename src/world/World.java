package world;

import javafx.geometry.Point2D;
import pandas.Panda;

public class World {
	
	public int[][] tiles= {
			{0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
			{0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
			{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
			{1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
			{0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0},
			{0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}	
	};
	
	public int width = tiles[0].length;
	public int height = tiles.length;
	
	public int getTile(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return -1;
		return tiles[y][x];
	}
	public void setTile(int x, int y, int value) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return;
		
		else tiles[y][x] = value;
	}
	
	
}
