package me.aaa.drop;

import com.badlogic.gdx.Game;
import me.aaa.drop.mainMenu.MainMenu;

public class Drop extends Game {

	@Override
	public void create() {
		setScreen(new MainMenu(this));
	}
}
