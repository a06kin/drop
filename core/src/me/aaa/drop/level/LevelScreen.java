package me.aaa.drop.level;

import me.aaa.drop.DropGame;
import me.aaa.drop.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class LevelScreen extends GameScreen implements Screen {
	private LevelLogic levelLogic;
	private LevelData levelData;
	
	private OrthographicCamera cam;
	
	private SpriteBatch levelBatch;
	private SpriteBatch debugBatch;
	
	private boolean paused = false;
	
	
    private Texture texCharacter;
    
    private Texture[] tiles;
    
    private LevelRenderer levelRenderer;
    private ShapeRenderer shapeRenderer;
    private Sprite character;
    
    
    float screen_width;
    float screen_height;
    float world_width;
    float world_height;
    
    float borderWidth = 0.33333333f;
    
    FrameBuffer shadow;
    TextureRegion tex;
    
    
    int PixelsPerCell = 32; 
    

	public LevelScreen(Screen prevScreen, DropGame game) {
		super(prevScreen, game);
		
		
		
		levelData = new LevelData(); // TODO should pass level number
		levelLogic = new LevelLogic(levelData);
		
		shadow = new FrameBuffer(Format.RGBA4444,levelData.width*PixelsPerCell,levelData.height*PixelsPerCell,false);
		
		levelBatch = new SpriteBatch(); //main rendering
		debugBatch = new SpriteBatch(); //rendering for debugging TODO remove
		
		//textures (TODO probably should be moved to Resources class)
		tiles = new Texture[12];
		tiles[0] = new Texture(Gdx.files.internal("wallConnector.png"));
		tiles[1] = new Texture(Gdx.files.internal("wallConnector.png"));
		tiles[2] = new Texture(Gdx.files.internal("wallConnector.png"));
		tiles[3] = new Texture(Gdx.files.internal("wallConnector.png"));
		tiles[4] = new Texture(Gdx.files.internal("wallConnector.png"));
		tiles[5] = new Texture(Gdx.files.internal("wallConnector.png"));
		tiles[6] = new Texture(Gdx.files.internal("wallConnector.png"));
		tiles[7] = new Texture(Gdx.files.internal("wallConnector.png"));
		tiles[8] = new Texture(Gdx.files.internal("tile5.png"));
		tiles[9] = new Texture(Gdx.files.internal("tile6.png"));
		tiles[10] = new Texture(Gdx.files.internal("tile7.png"));
		tiles[11] = new Texture(Gdx.files.internal("tile8.png"));
		
		levelRenderer = new LevelRenderer(tiles, borderWidth); // wrapper for rendering static scene
		shapeRenderer = new ShapeRenderer(); //renderer of debug stuff like light projections on character
        
		//character related resources (TODO probably should be moved to Resources class)
		texCharacter = new Texture(Gdx.files.internal("character.png"));
        character = new Sprite(texCharacter);
	}
	
	@Override
    public void dispose() {
        levelBatch.dispose();
        debugBatch.dispose();
        texCharacter.dispose();
        for (int i = 0; i < tiles.length; ++i)
        	tiles[i].dispose();
    }
	
	@Override
	public void render(float delta) {
		if (paused) return;
		
		levelLogic.update(delta);
	
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_ONE, GL20.GL_ZERO);
		
        cam = new OrthographicCamera(levelData.width, levelData.height);
        cam.position.set(levelData.width / 2, levelData.height / 2, 0);
		cam.update();
        
		shapeRenderer.setProjectionMatrix(cam.combined);
		
		shadow.begin();
		
		
		//clear the FBO
				Gdx.gl.glClearColor(0f,0f,0f,0.5f);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				
				float f = 10;
				
				shapeRenderer.begin(ShapeType.Filled);
				shapeRenderer.setColor(1, 1, 0.8f, 0.5f);
				
				//shapeRenderer.
				int a = 20;
				for (int i = 0;i<a*2;++i) {
					shapeRenderer.triangle(5.5f, 5.5f, 5.5f + f*(float)Math.sin(i*Math.PI/a), 5.5f + f*(float)Math.cos(i*Math.PI/a), 5.5f + f*(float)Math.sin((i + 1)*Math.PI/a), 5.5f + f*(float)Math.cos((i + 1)*Math.PI/a),
							new Color(1,1,0.8f,0.5f), new Color(0,0,0,0.5f), new Color(0,0,0,0.5f));
				}
				
				
				//shapeRenderer.ellipse(5.5f - f, 5.5f - f, 2*f, 2*f);
				
				
				shapeRenderer.setColor(0, 0, 0, 0.2f);
		        for (int i = 0; i < levelData.width; ++i)
		        	for (int j = 0; j < levelData.height; ++j) {
		        		if (levelData.isWall(i, j)) {
		        			float v1x = i - 5.5f;
		        			float v1y = j - 5.5f;
		        			float v2x = i + 1 - 5.5f;
		        			float v2y = j - 5.5f;
		        			float v3x = i + 1 - 5.5f;
		        			float v3y = j + 1 - 5.5f;
		        			float v4x = i - 5.5f;
		        			float v4y = j + 1 - 5.5f;
		        			
		        			float d1 = (float)Math.sqrt(v1x*v1x + v1y*v1y);
		        			float d2 = (float)Math.sqrt(v2x*v2x + v2y*v2y);
		        			float d3 = (float)Math.sqrt(v3x*v3x + v3y*v3y);
		        			float d4 = (float)Math.sqrt(v4x*v4x + v4y*v4y);
		        			
		        			v1x *= 7/d1; v1y *= 7/d1;
		        			v2x *= 7/d2; v2y *= 7/d2;
		        			v3x *= 7/d3; v3y *= 7/d3;
		        			v4x *= 7/d4; v4y *= 7/d4;
		        			
		        			v1x += 5.5f; v1y += 5.5f;
		        			v2x += 5.5f; v2y += 5.5f;
		        			v3x += 5.5f; v3y += 5.5f;
		        			v4x += 5.5f; v4y += 5.5f;
		        			
		        			shapeRenderer.triangle(i , j, v1x, v1y, v2x, v2y);
		        			shapeRenderer.triangle(i, j, v2x, v2y, (i + 1), j);
		        			
		        			shapeRenderer.triangle(i + 1, j, v2x, v2y, v3x, v3y);
		        			shapeRenderer.triangle(i + 1, j, v3x, v3y, i + 1, j + 1);
		        			
		        			shapeRenderer.triangle(i + 1, j + 1, v3x, v3y, v4x, v4y);
		        			shapeRenderer.triangle(i + 1, j + 1, v4x, v4y, i, j + 1);
		        			
		        			shapeRenderer.triangle(i, j + 1, v4x, v4y, v1x, v1y);
		        			shapeRenderer.triangle(i, j + 1, v1x, v1y, i, j);
		        			
		        			//shapeRenderer.rect(i, j, 1, 1);
		        			
		        		}
		        	}
		        shapeRenderer.end();
		shadow.end();
		
		tex = new TextureRegion(shadow.getColorBufferTexture());
		tex.flip(false, true);
		
		cam = new OrthographicCamera(world_height * screen_width / screen_height, world_height);
        cam.position.set(levelData.width / 2, levelData.height / 2, 0);
		cam.update();
		
        levelBatch.setProjectionMatrix(cam.combined);
        debugBatch.setProjectionMatrix(cam.combined);
        shapeRenderer.setProjectionMatrix(cam.combined);
        
        
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        levelBatch.begin(); //start rendering
        
        levelRenderer.render(levelBatch, levelData); // render static level scene
        
        
        float sx = levelData.char_r;
        float sy = levelData.char_r;
        
        character.setSize(sx * 2, sy * 2);
        character.setOrigin(sx, sy);
        character.setPosition(0, 0);
        character.setRotation(levelData.char_a);
        character.setCenter(levelData.char_x, levelData.char_y);
        character.draw(levelBatch);
        

        
        levelBatch.draw(tex, 0,0,levelData.width,levelData.height);

        
        levelBatch.end(); //stop rendering
        
        Gdx.gl.glDisable(GL20.GL_BLEND);
        
        //DEBUG
        
        if (levelData.lightBeams!=null) {
        	for (LightBeam lBeam: levelData.lightBeams) {
        		if (lBeam.intervals != null) {
        			shapeRenderer.begin(ShapeType.Line);
        			shapeRenderer.setColor(1, 1, 1, 1);
        			for (int i = 0; i < lBeam.intervals.length; i += 2) {
        				float v1_x = lBeam.left_x + lBeam.intervals[i] * (lBeam.right_x - lBeam.left_x);
        				float v1_y = lBeam.left_y + lBeam.intervals[i] * (lBeam.right_y - lBeam.left_y);
        				
        				float v2_x = lBeam.left_x + lBeam.intervals[i + 1] * (lBeam.right_x - lBeam.left_x);
        				float v2_y = lBeam.left_y + lBeam.intervals[i + 1] * (lBeam.right_y - lBeam.left_y);
        				shapeRenderer.triangle(lBeam.src_x, lBeam.src_y, v1_x, v1_y, v2_x, v2_y);
        			}
        			shapeRenderer.end();
        		}
        	}
        }
        /*
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //shapeRenderer.//setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0, 0, 0, 0.5f);
        for (int i = 1; i < levelData.width - 1; ++i)
        	for (int j = 1; j < levelData.height - 1; ++j) {
        		if (levelData.isWall(i, j)) {
        			float v1x = i - 5.5f;
        			float v1y = j - 5.5f;
        			float v2x = i + 1 - 5.5f;
        			float v2y = j - 5.5f;
        			float v3x = i + 1 - 5.5f;
        			float v3y = j + 1 - 5.5f;
        			float v4x = i - 5.5f;
        			float v4y = j + 1 - 5.5f;
        			
        			float d1 = (float)Math.sqrt(v1x*v1x + v1y*v1y);
        			float d2 = (float)Math.sqrt(v2x*v2x + v2y*v2y);
        			float d3 = (float)Math.sqrt(v3x*v3x + v3y*v3y);
        			float d4 = (float)Math.sqrt(v4x*v4x + v4y*v4y);
        			
        			v1x *= 7/d1; v1y *= 7/d1;
        			v2x *= 7/d2; v2y *= 7/d2;
        			v3x *= 7/d3; v3y *= 7/d3;
        			v4x *= 7/d4; v4y *= 7/d4;
        			
        			v1x += 5.5f; v1y += 5.5f;
        			v2x += 5.5f; v2y += 5.5f;
        			v3x += 5.5f; v3y += 5.5f;
        			v4x += 5.5f; v4y += 5.5f;
        			
        			shapeRenderer.triangle(i, j, v1x, v1y, v2x, v2y);
        			shapeRenderer.triangle(i, j, v2x, v2y, i + 1, j);
        			
        			shapeRenderer.triangle(i + 1, j, v2x, v2y, v3x, v3y);
        			shapeRenderer.triangle(i + 1, j, v3x, v3y, i + 1, j + 1);
        			
        			shapeRenderer.triangle(i + 1, j + 1, v3x, v3y, v4x, v4y);
        			shapeRenderer.triangle(i + 1, j + 1, v4x, v4y, i, j + 1);
        			
        			shapeRenderer.triangle(i, j + 1, v4x, v4y, v1x, v1y);
        			shapeRenderer.triangle(i, j + 1, v1x, v1y, i, j);
        			
        			//shapeRenderer.rect(i, j, 1, 1);
        			
        		}
        	}
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);*/
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
        screen_width = width;
        screen_height = height;
        world_width = levelData.width - 2 * (1 - borderWidth);
        world_height = levelData.height - 2 * (1 - borderWidth);
       
        
        cam = new OrthographicCamera(world_height * width / height, world_height);
        cam.position.set(levelData.width / 2, levelData.height / 2, 0);
	}
	
	private void setTarget(float screenX, float screenY) {
		screenY = screen_height - screenY;
		
		levelData.target_x = levelData.width / 2 + (screenX - screen_width / 2f) * world_width / screen_height;
		levelData.target_y = levelData.height / 2 + (screenY - screen_height / 2f) * world_height / screen_height;
		
		levelLogic.shouldUpdatePoisition = true;
		
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
	
	@Override
	public boolean tap(float x, float y, int count, int button) {
		if (count > 1) {
			if (levelData.char_r == 0.4f) levelData.char_r = 0.6f;
			else if (levelData.char_r == 0.6f) levelData.char_r = 0.8f;
			else if (levelData.char_r == 0.8f) levelData.char_r = 0.4f;
		}
		return false;
	}
}
