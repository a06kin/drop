package me.aaa.drop.mainMenu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import me.aaa.drop.DropGame;
import me.aaa.drop.GameScreen;
import me.aaa.drop.level.LevelScreen;

public class MainMenu extends GameScreen implements Screen {


    public MainMenu(DropGame game) {
    	super(game);
    }
    
    @Override
	public void render(float delta) {
    	Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
    
    @Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		game.setScreen(new LevelScreen(this,game));
		return false;
	}
}
