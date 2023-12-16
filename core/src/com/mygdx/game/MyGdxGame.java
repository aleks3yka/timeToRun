package com.mygdx.game;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.mygdx.game.screens.GameOverScreen;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.SettingsScreen;
import com.mygdx.game.screens.SplashScreen;
import com.mygdx.game.utils.FontHelper;
import com.mygdx.game.utils.SoundsHelper;
import com.mygdx.game.view.Animation;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.FileHandler;

public class MyGdxGame extends Game {
    public SpriteBatch batch;
    public GameScreen gameScreen;
    public MenuScreen menuScreen;
    public SplashScreen splashScreen;
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
    //VideoPlayer player;
    long timer = -1;

    @Override
    public void create() {
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
        splashScreen = new SplashScreen(this);
        normalFont = FontHelper.getFont(50, font, Color.WHITE);
        bigFont = FontHelper.getFont(100, font, Color.WHITE);
        accentFont = FontHelper.getFont(50, font, Color.LIGHT_GRAY);
        setScreen(splashScreen);
//        player = VideoPlayerCreator.createVideoPlayer();
//        FileHandle file = Gdx.files.internal("LOGO.webm");
//        try {
//            player.play(file);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        super.render();
//        if (player.isBuffered()) {
//            if(timer == -1){
//                timer = millis();
//            }
//            ScreenUtils.clear(Color.BLACK);
//            orthographicCamera.update();
//            batch.setProjectionMatrix(orthographicCamera.combined);
//            player.update();
//            batch.begin();
//            batch.draw(player.getTexture(), 0, 0, GameSettings.width, GameSettings.height);
//            batch.end();
//            super.render();
//        }
//        if(timer != -1 && millis() - timer >= 5600){
//            setScreen(menuScreen);
//        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        //player.dispose();
    }
}
