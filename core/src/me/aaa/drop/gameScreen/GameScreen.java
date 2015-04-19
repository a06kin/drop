package me.aaa.drop.gameScreen;

import me.aaa.drop.DropGame;

import com.badlogic.gdx.Screen;

public class GameScreen implements Screen {
	private GameLogic gameLogic;
	private boolean paused = false;
	
	public GameScreen(DropGame game) {
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		if (!paused) gameLogic.update(delta);
			
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		paused = true;
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		paused = false;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
