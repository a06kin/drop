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
	private LevelLogic gameLogic;
	private boolean paused = false;
	
	private SpriteBatch batch;
    private Texture texture1;
    private Texture texture2;
    
    private Sprite character;
    
    private int w;
    private int h;
    private float x;
    private float y;
    
    private float vx;
    private float vy;
    
	
	public LevelScreen(Screen prevScreen, DropGame game) {
		super(prevScreen, game);
		gameLogic = new LevelLogic(new LevelData());
		
		batch = new SpriteBatch();
        texture1 = new Texture(Gdx.files.internal("character.png"));
        texture2 = new Texture(Gdx.files.internal("tiles.jpg"));
        
        character = new Sprite(texture1);
        
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        
        character.setSize(h/8f, h/8f);
        
        x = w / 2f;
        y = h / 2f;
        
        vx = x;
        vy = y;
        
        
	}
	
	@Override
    public void dispose() {
        batch.dispose();
        texture1.dispose();
        texture2.dispose();
    }
	
	@Override
	public void render(float delta) {
		if (!paused) gameLogic.update(delta);
		float dx = vx - x;
		float dy = vy - y;
		
		float d = (float)Math.sqrt(dx*dx+dy*dy);
		
		if (d < 200*delta) {
			x = vx;
			y = vy;
		} else {
			x += dx*200*delta/d;
			y += dy*200*delta/d;
			
			character.setOrigin(h/16f, h/16f);
	        character.setPosition(0,0);
	        
			float angle = (float)(Math.atan2(dy, dx) *180 / Math.PI);
			character.setRotation(angle);
		}
		
		x = Math.max(Math.min(x, (w -h) / 2f +h-h/16f), (w -h) / 2f +h/16f);
		y = Math.max(Math.min(y, h-h/16f), h/16f);
		
		
		
		character.setCenter(x, y);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        
        
        batch.begin();
        batch.draw(texture2, (w -h) / 2f, 0, h, h);
        
        character.draw(batch);
        //batch.draw(texture1, x - h / 16f, y - h / 16f, h / 8f, h / 8f);
     
        batch.end();
        
        
	}
	
	@Override
	public boolean touchDown(float screenX, float screenY, int pointer, int button) {
		vx = screenX;
		vy = h - screenY;
		return false;
	}
	
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		vx = screenX;
		vy = h - screenY;

		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		vx = x;
		vy = y;
		return false;
	}
}
