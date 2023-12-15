package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.SettingsScreen;
import com.mygdx.game.utils.FontHelper;
import com.mygdx.game.utils.SoundsHelper;
import com.mygdx.game.view.Animation;

import java.util.ArrayList;

public class MyGdxGame extends Game {
	public SpriteBatch batch;
	public GameScreen gameScreen;
	public MenuScreen menuScreen;
	public GameOverScreen gameOverScreen;
	public OrthographicCamera orthographicCamera;
	public String font = "75blitzel.ttf";
	public BitmapFont veryBigFont;
	public BitmapFont bigFont;
	public BitmapFont normalFont;
	public BitmapFont accentFont;
	public FitViewport viewport;
	public Texture roadTexture;
	public Animation playerIdling;
	public Animation playerMoving;
	public Animation coin;
	public ArrayList<Animation> enemyMoving;
	public SettingsScreen settingsScreen;
	public Texture boulderTexture;
	
	@Override
	public void create () {
		normalFont = FontHelper.getFont(50, font, Color.WHITE);
		bigFont = FontHelper.getFont(100, font, Color.WHITE);
		veryBigFont = FontHelper.getFont(200, font, Color.WHITE);
		batch = new SpriteBatch();
		roadTexture = new Texture("graphResources/road.png");
		playerMoving = new Animation("graphResources/PlayerMoving/GG(walk)", 1, 11,
				60, batch, 4);
		playerIdling = new Animation("graphResources/PlayerIdling/GG(static)", 1, 8,
				1, batch, 4);
		boulderTexture = new Texture("graphResources/boulder.png");
		coin = new Animation("graphResources/Coin/coin", 1, 7, 4, batch, 2);
		enemyMoving = new ArrayList<>();
		enemyMoving.add(new Animation("graphResources/enemyMoving/EnemyOne(walk)",
				1, 25, 5, batch, 4));
		enemyMoving.add(new Animation("graphResources/enemyMoving1/Кенгуренок",
				1, 5, 5, batch, 4));
		orthographicCamera = new OrthographicCamera();
		orthographicCamera.setToOrtho(false);
		viewport = new FitViewport(GameSettings.width, GameSettings.height, orthographicCamera);
		menuScreen = new MenuScreen(this);
		gameScreen = new GameScreen(this);
		settingsScreen = new SettingsScreen(this);
		gameOverScreen = new GameOverScreen(this);
		normalFont = FontHelper.getFont(50, font, Color.WHITE);
		bigFont = FontHelper.getFont(100, font, Color.WHITE);
		accentFont = FontHelper.getFont(50, font, Color.LIGHT_GRAY);
		setScreen(menuScreen);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
