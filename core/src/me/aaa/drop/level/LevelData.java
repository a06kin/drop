package me.aaa.drop.level;

import java.util.Random;


public class LevelData {
	private int[][] grid;
	
	
	public int width;
	public int height;
	
	public float char_x;
	public float char_y;
	public float char_r;
	public float char_a;
	public float char_speed;
	
	public float target_x;
	public float target_y;
	
	public LightBeam[] lightBeams = null;
	
	public boolean isWall(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return true;
		
		return grid[x][y] < 1;
	}
	
	public int getVariation(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return 0;
		return grid[x][y] % 4;
	}
	
	public int getRawType(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return 0;
		return grid[x][y];
	}
	
	public LevelData() {
		width = 10;
		height = 10;
		
		grid = new int[width][height];
		
		Random rand = new Random(); 
		for (int i = 0; i < width; ++i)
			for (int j = 0; j < height; ++j) {
				if (i==0 || j == 0 || i == width - 1 || j == height - 1)
					grid[i][j] = 0;
				else if ( i == width - 3 && j >2 && j != height - 2 )
					grid[i][j] = 0;
				else if ( i == 3 && j > 2 && j != height - 2 )
					grid[i][j] = 0;
				
				else if ( j == height - 3 && i > 2 && i != width - 2 )
					grid[i][j] = 0;
				else
					grid[i][j] = 1 + rand.nextInt(4);
			}
		
		
		char_r = 0.6f;
		char_a = 0;
		char_speed = 4;
		
		char_x = 2f;
		char_y = 2f;
		
		target_x = char_x;
		target_y = char_y;
	}
	
}
