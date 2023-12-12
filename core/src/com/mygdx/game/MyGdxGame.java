package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public GameScreen gameScreen;
	public MenuScreen menuScreen;
	public OrthographicCamera orthographicCamera;
	public String font = "MorganChalk-L3aJy.ttf";
	public FitViewport viewport;
	
	@Override
	public void create () {

		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false);
		viewport = new FitViewport(GameSettings.width, GameSettings.height, orthographicCamera);
		batch = new SpriteBatch();
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen();
		setScreen(menuScreen);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
