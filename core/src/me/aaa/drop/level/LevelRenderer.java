package me.aaa.drop.level;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LevelRenderer {
	
	private Texture[] tiles;
	private float borderWidth;
	
	
	float off_x = 0;//deprecated
	float off_y = 0;//deprecated
	float scale_x = 1;//deprecated
	float scale_y = 1;//deprecated
	
	public LevelRenderer(Texture[] _tiles, float _borderWidth) {
		if (_tiles == null || _borderWidth < 0) throw new IllegalArgumentException();
		
		tiles = _tiles;
		borderWidth = _borderWidth;
	}
	
	void render(SpriteBatch levelBatch, LevelData levelData) {
		for (int i = 0; i < levelData.width; ++i)
        	for (int j = 0; j < levelData.height; ++j) {
        		if (levelData.isWall(i, j)) {
        			
        			
        			if (!levelData.isWall(i + 1, j))
        				levelBatch.draw(tiles[0], off_x + (i  + 1 - borderWidth) * scale_x, off_y + (j + borderWidth) * scale_y, borderWidth * scale_x, ( 1 - 2 * borderWidth) * scale_y);
        			
        			if (!levelData.isWall(i, j - 1))
        				levelBatch.draw(tiles[1], off_x + (i + borderWidth) * scale_x, off_y + j * scale_y, (1 - borderWidth * 2) * scale_x, borderWidth * scale_y);
        			
        			if (!levelData.isWall(i - 1, j))
        				levelBatch.draw(tiles[2], off_x + i * scale_x, off_y + (j + borderWidth)  * scale_y, borderWidth * scale_x, (1 - 2 * borderWidth) * scale_y);
        			
        			if (!levelData.isWall(i, j + 1))
        				levelBatch.draw(tiles[3], off_x + (i + borderWidth) * scale_x, off_y + (j + 1 - borderWidth) * scale_y, (1 - 2* borderWidth) * scale_x, borderWidth * scale_y);
        			
        			if (!levelData.isWall(i - 1, j) || !levelData.isWall(i, j - 1) || !levelData.isWall(i - 1, j - 1))
        				levelBatch.draw(tiles[4], off_x + i * scale_x, off_y + j * scale_y, borderWidth * scale_x, borderWidth * scale_y);
        			
    				if (!levelData.isWall(i + 1, j) || !levelData.isWall(i, j - 1) || !levelData.isWall(i + 1, j - 1))
    					levelBatch.draw(tiles[4], off_x + (i + 1 - borderWidth) * scale_x, off_y + j * scale_y, borderWidth * scale_x, borderWidth * scale_y);
        		
    				if (!levelData.isWall(i - 1, j) || !levelData.isWall(i, j + 1) || !levelData.isWall(i - 1, j + 1))
    					levelBatch.draw(tiles[4], off_x + i * scale_x, off_y + (j + 1 - borderWidth) * scale_y, borderWidth * scale_x, borderWidth * scale_y);
        			
        			
    				if (!levelData.isWall(i + 1, j) || !levelData.isWall(i, j + 1) || !levelData.isWall(i + 1, j + 1))
    					levelBatch.draw(tiles[4], off_x + (i + 1 - borderWidth) * scale_x, off_y + (j + 1 - borderWidth) * scale_y, borderWidth * scale_x, borderWidth * scale_y);
        		} else {
        			levelBatch.draw(tiles[8 + (levelData.getRawType(i,j) - 1)], off_x + i * scale_x, off_y + j * scale_y, scale_x, scale_y);
        		}
        	}
        
	}
}
