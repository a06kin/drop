package me.aaa.drop.level;

import java.util.Random;


public class LevelData {
	public int[][] grid;
	public int width;
	public int height;
	
	public float char_x;
	public float char_y;
	public float char_r;
	public float char_a;
	public float char_speed;
	
	public float target_x;
	public float target_y;
	
	public LevelData() {
		width = 8;
		height = 8;
		
		grid = new int[width][height];
		
		Random rand = new Random(); 
		for (int i = 0; i < width; ++i)
			for (int j = 0; j < height; ++j) {
				if (i==0 || j == 0 || i == width - 1 || j == height - 1)
					grid[i][j] = 4;
				else if ( i == width - 3 && j != 1 && j != height - 2 )
					grid[i][j] = 4;
				else if ( i == 2 && j != 1 && j != height - 2 )
					grid[i][j] = 4;
				
				else if ( j == height - 3 && i != 1 && i != width - 2 )
					grid[i][j] = 4;
				else
					grid[i][j] = rand.nextInt(4);
			}
		
		
		char_r = 0.3f;
		char_a = 0;
		char_speed = 2;
		
		char_x = 1.5f;
		char_y = 1.5f;
		
		target_x = char_x;
		target_y = char_y;
	}
	
}
