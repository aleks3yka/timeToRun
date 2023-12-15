package com.mygdx.game.screens;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.FontHelper;
import com.mygdx.game.utils.MemoryHelper;
import com.mygdx.game.utils.TouchTrackerMenu;
import com.mygdx.game.view.BackgroundView;
import com.mygdx.game.view.BaseView;
import com.mygdx.game.view.LabelView;

import java.util.ArrayList;
import java.util.Vector;

public class GameOverScreen extends ScreenAdapter{

    public MyGdxGame myGame;
    public ArrayList<BaseView> view;

    public GameOverScreen(MyGdxGame myGame) {
        this.myGame = myGame;

    }

    @Override
    public void show() {
        view = new ArrayList<>();
        view.add(new LabelView(GameSettings.width / 2, 450,
                "GAME OVER", true, myGame.veryBigFont, myGame.batch));
        view.add(new LabelView(200, 300,
                "Score: ", false, myGame.normalFont, myGame.batch));
        view.add(new LabelView(200, 200,
                "Best score: ", false, myGame.normalFont, myGame.batch));
        int score = MemoryHelper.getScore();
        String message = score / 100000 % 10 + "" + score / 10000 % 10
                + "" + score / 1000 % 10
                + "" + score / 100 % 10
                + "" + score / 10 % 10
                + "" + score % 10;
        view.add(new LabelView(363, 300, message, false,
                myGame.normalFont, myGame.batch));
        score = MemoryHelper.loadBestScore();
        message = score / 100000 % 10 + "" + score / 10000 % 10
                + "" + score / 1000 % 10
                + "" + score / 100 % 10
                + "" + score / 10 % 10
                + "" + score % 10;
        view.add(new LabelView(480, 200, message, false,
                myGame.normalFont, myGame.batch));
        view.add(new LabelView(100, 700, "Back",
                false, myGame.normalFont, myGame.batch));
        view.get(view.size() - 1).setClickListener(new BaseView.onClickListener() {
            @Override
            public void onClick() {
                myGame.setScreen(myGame.menuScreen);
            }
        });
    }

    @Override
    public void render(float delta) {
        handleInput();
        myGame.orthographicCamera.update();
        myGame.batch.setProjectionMatrix(myGame.orthographicCamera.combined);

        ScreenUtils.clear(0, 0, 0, 0);
        myGame.batch.begin();
        for (BaseView i : view) {
            i.draw();
        }
        myGame.batch.end();
    }
    void handleInput(){
        if(Gdx.input.justTouched()){
            Vector3 touch = new Vector3(Gdx.input.getX(0),
                    Gdx.input.getY(0), 0);
            touch = myGame.orthographicCamera.unproject(touch);
            for(int i = 0; i < view.size(); i++){
                view.get(i).isHit((int) touch.x, (int) touch.y);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        myGame.viewport.update(width, height, true);
    }


    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

}
