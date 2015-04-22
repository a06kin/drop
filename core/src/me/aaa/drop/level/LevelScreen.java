package me.aaa.drop.level;

import me.aaa.drop.DropGame;
import me.aaa.drop.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LevelScreen extends GameScreen implements Screen {
	private LevelLogic levelLogic;
	private boolean paused = false;
	
	private SpriteBatch batch;
    private Texture texCharacter;
    private Texture[] texTiles;
    private Texture[] texWall;
    
    private Sprite character;
    
    private int w;
    private int h;
    /*private float x;
    private float y;
    
    private float vx;
    private float vy;*/
    
    LevelData levelData;
    
    float off_x;
    float off_y;
    float scale_x;
    float scale_y;
    
    float borderWidth = 0.33333333f;
    
	
	public LevelScreen(Screen prevScreen, DropGame game) {
		super(prevScreen, game);
		levelData = new LevelData();
		levelLogic = new LevelLogic(levelData);
		
		batch = new SpriteBatch();
		
        texCharacter = new Texture(Gdx.files.internal("character.png"));
        texTiles = new Texture[4];
        texTiles[0] = new Texture(Gdx.files.internal("tile5.png"));
        texTiles[1] = new Texture(Gdx.files.internal("tile6.png"));
        texTiles[2] = new Texture(Gdx.files.internal("tile7.png"));
        texTiles[3] = new Texture(Gdx.files.internal("tile8.png"));
        
        texWall = new Texture[5];
        
        texWall[0] = new Texture(Gdx.files.internal("wallConnector.png")); //E
        texWall[1] = new Texture(Gdx.files.internal("wallConnector.png")); //N
        texWall[2] = new Texture(Gdx.files.internal("wallConnector.png")); //W
        texWall[3] = new Texture(Gdx.files.internal("wallConnector.png")); //S
        texWall[4] = new Texture(Gdx.files.internal("wallConnector.png")); //Connector
        
        character = new Sprite(texCharacter);
	}
	
	@Override
    public void dispose() {
        batch.dispose();
        texCharacter.dispose();
        for (int i = 0; i < 4; ++i) {
        	texTiles[i].dispose();
        	texWall[i].dispose();
        	
        }
        texWall[4].dispose();
    }
	
	@Override
	public void render(float delta) {
		if (!paused) levelLogic.update(Math.min(delta,0.1f));
		
		float sx = levelData.char_r * scale_x;
		float sy = levelData.char_r * scale_y;
		
		character.setSize(sx * 2, sy * 2);
		character.setOrigin(sx, sy);
        character.setPosition(0, 0);
        character.setRotation(levelData.char_a);
		character.setCenter(off_x + levelData.char_x * scale_x, off_y + levelData.char_y * scale_y);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        
        batch.begin();
        
        for (int i = 0; i < levelData.width; ++i)
        	for (int j = 0; j < levelData.height; ++j) {
        		int type = levelData.grid[i][j] / 4;
        		int variation = levelData.grid[i][j] % 4;
        		if (type == 0)
        			batch.draw(texTiles[variation], off_x + i * scale_x, off_y + j * scale_y, scale_x, scale_y);
        	
        		if (type == 1) {
        			
        			
        			if (i < levelData.width - 1 && levelData.grid[i + 1][j] / 4 == 0)
        				batch.draw(texWall[0], off_x + (i  + 1 - borderWidth) * scale_x, off_y + (j + borderWidth) * scale_y, borderWidth * scale_x, ( 1 - 2 * borderWidth) * scale_y);
        			
        			if (j > 0 && levelData.grid[i][j - 1] / 4 == 0)
        				batch.draw(texWall[1], off_x + (i + borderWidth) * scale_x, off_y + j * scale_y, (1 - borderWidth * 2) * scale_x, borderWidth * scale_y);
        			
        			if (i > 0 && levelData.grid[i - 1][j] / 4 == 0)
        				batch.draw(texWall[2], off_x + i * scale_x, off_y + (j + borderWidth)  * scale_y, borderWidth * scale_x, (1 - 2 * borderWidth) * scale_y);
        			
        			if (j < levelData.height - 1 && levelData.grid[i][j + 1] / 4 == 0)
        				batch.draw(texWall[3], off_x + (i + borderWidth) * scale_x, off_y + (j + 1 - borderWidth) * scale_y, (1 - 2* borderWidth) * scale_x, borderWidth * scale_y);
        			
        			if (i > 0 && j > 0)
        				if (levelData.grid[i - 1][j] / 4 == 0 || levelData.grid[i][j - 1] / 4 == 0 || levelData.grid[i - 1][j - 1] / 4 == 0)
        					batch.draw(texWall[4], off_x + i * scale_x, off_y + j * scale_y, borderWidth * scale_x, borderWidth * scale_y);
        			
        			if (i < levelData.width - 1 && j > 0)
        				if (levelData.grid[i + 1][j] / 4 == 0 || levelData.grid[i][j - 1] / 4 == 0 || levelData.grid[i + 1][j - 1] / 4 == 0)
        					batch.draw(texWall[4], off_x + (i + 1 - borderWidth) * scale_x, off_y + j * scale_y, borderWidth * scale_x, borderWidth * scale_y);
        		
        			if (i > 0 && j < levelData.height - 1)
        				if (levelData.grid[i - 1][j] / 4 == 0 || levelData.grid[i][j + 1] / 4 == 0 || levelData.grid[i - 1][j + 1] / 4 == 0)
        					batch.draw(texWall[4], off_x + i * scale_x, off_y + (j + 1 - borderWidth) * scale_y, borderWidth * scale_x, borderWidth * scale_y);
        			
        			if (i < levelData.width - 1 && j < levelData.height - 1)
        				if (levelData.grid[i + 1][j] / 4 == 0 || levelData.grid[i][j + 1] / 4 == 0 || levelData.grid[i + 1][j + 1] / 4 == 0)
        					batch.draw(texWall[4], off_x + (i + 1 - borderWidth) * scale_x, off_y + (j + 1 - borderWidth) * scale_y, borderWidth * scale_x, borderWidth * scale_y);
        		}
        	}
        
        //batch.draw(texture2, (w -h) / 2f, 0, h, h);
        
        
        
        character.draw(batch);
        //batch.draw(texture1, x - h / 16f, y - h / 16f, h / 8f, h / 8f);
     
        batch.end();
        
        
	}
	
	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		paused = false;
	}
	
	@Override
	public void resize(int width, int height) {
		w = width;
        h = height;
        
        scale_x = w / (levelData.width - 2 * (1 - borderWidth));
        scale_y = h / (levelData.height - 2 * (1 - borderWidth));
        
        if (scale_x > scale_y)
        	scale_x = scale_y;
        else
        	scale_y = scale_x;
        
        
        off_x = (w - scale_x * levelData.width) / 2;
        off_y = (h - scale_y * levelData.height) / 2;
	}
	
	private void setTarget(float screenX, float screenY) {
		screenY = h - screenY;
		
		levelData.target_x = (screenX - off_x ) / scale_x;
		levelData.target_y = (screenY - off_y ) / scale_y;
		
		levelLogic.shouldUpdatePoisition = true;
		
		//levelData.target_x = 
		
	}
	
	@Override
	public boolean touchDown(float screenX, float screenY, int pointer, int button) {
		setTarget(screenX, screenY);
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		setTarget(screenX, screenY);
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		levelLogic.shouldUpdatePoisition = false;
		return false;
	}
}
