package com.mygdx.game.screens;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.FontHelper;
import com.mygdx.game.utils.SoundsHelper;
import com.mygdx.game.utils.TouchTrackerMenu;
import com.mygdx.game.view.BackgroundView;
import com.mygdx.game.view.BaseView;
import com.mygdx.game.view.LabelView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SplashScreen extends ScreenAdapter {
    public MyGdxGame myGame;
    VideoPlayer player;
    long timer = -1;

    public SplashScreen(final MyGdxGame myGame) {
        this.myGame = myGame;
        player = VideoPlayerCreator.createVideoPlayer();
        FileHandle file = Gdx.files.internal("LOGO.webm");
        try {
            player.play(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void show() {
        ScreenUtils.clear(0, 0, 0, 0);
    }

    @Override
    public void render(float delta) {
        if(player.isBuffered()){
            if(timer == -1){
                timer = millis();
            }
            myGame.orthographicCamera.update();
            myGame.batch.setProjectionMatrix(myGame.orthographicCamera.combined);
            player.update();
            ScreenUtils.clear(0, 0, 0, 0);
            myGame.batch.begin();
            myGame.batch.draw(player.getTexture(), 0, 0, GameSettings.width, GameSettings.height);
            myGame.batch.end();
        }
        if(timer != -1 && millis()-timer >= 6000){
            myGame.setScreen(myGame.menuScreen);
        }
    }

    @Override
    public void resize(int width, int height) {
        myGame.viewport.update(width, height, true);
    }


    @Override
    public void dispose() {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
