package me.aaa.drop.level;

import me.aaa.drop.DropGame;
import me.aaa.drop.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class LevelScreen extends GameScreen implements Screen {
	private LevelLogic gameLogic;
	private boolean paused = false;
	
	public LevelScreen(Screen prevScreen, DropGame game) {
		super(prevScreen, game);
		gameLogic = new LevelLogic(new LevelData());
	}
	
	@Override
	public void render(float delta) {
		if (!paused) gameLogic.update(delta);
		Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// TODO Auto-generated method stub
	}
}
