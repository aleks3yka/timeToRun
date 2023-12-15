package com.mygdx.game.screens;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.utils.FontHelper;
import com.mygdx.game.GameSettings;
import com.mygdx.game.utils.SoundsHelper;
import com.mygdx.game.utils.TouchTrackerMenu;
import com.mygdx.game.view.BackgroundView;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.BaseView;
import com.mygdx.game.view.LabelView;

import java.util.ArrayList;

public class MenuScreen extends ScreenAdapter {
    public MyGdxGame myGame;
    BackgroundView backGround;
    LabelView header;
    BitmapFont headerFont;
    //GraphView graphView;
    //Graph graph;
    public ArrayList<BaseView> view;
    long lastMove;
    int v;
    double fps = 60;
    long start;

    public MenuScreen(final MyGdxGame myGame) {
        this.myGame = myGame;
        view = new ArrayList<>();
        //view.add(new BackgroundView(myGame.batch, "menuResources/menuBackground.jpg"));
        headerFont = FontHelper.getFont(100, myGame.font, Color.BLACK,
                0, Color.BLACK, true);
        view.add(new LabelView(GameSettings.width / 2, GameSettings.height * 7 / 8,
                "Time to run", true, myGame.bigFont, myGame.batch));
        view.add(new LabelView(200, 500, "Start", false,
                myGame.normalFont, myGame.batch));
        view.get(view.size() - 1).setClickListener(new BaseView.onClickListener() {
            @Override
            public void onClick() {
                myGame.setScreen(myGame.gameScreen);
            }
        });
        view.add(new LabelView(200, 400, "Settings", false,
                myGame.normalFont, myGame.batch));
        view.get(view.size()-1).setClickListener(new BaseView.onClickListener() {
            @Override
            public void onClick() {
                myGame.setScreen(myGame.settingsScreen);
            }
        });
        view.add(new LabelView(200, 300, "Exit", false,
                myGame.normalFont, myGame.batch));
        view.get(view.size()-1).setClickListener(new BaseView.onClickListener() {
            @Override
            public void onClick() {
                Gdx.app.exit();
            }
        });
        //graph = new Graph(5, 5, 0.4);
//        int graphWidth = 900, graphRes = 100;
//        graphView = new GraphView((GameSettings.width - graphWidth)/2,
//                (GameSettings.height - graphWidth)/2,
//                graphWidth, graphRes, myGame.batch, graph,
//                0
//        );
//        v = 0;
//        view.add(graphView);
    }

    @Override
    public void show() {
        SoundsHelper.playBackMenuSound();
        Gdx.input.setInputProcessor(new TouchTrackerMenu(this));
    }

    @Override
    public void render(float delta) {

        myGame.orthographicCamera.update();
        myGame.batch.setProjectionMatrix(myGame.orthographicCamera.combined);

        ScreenUtils.clear(0, 0, 0, 0);
        myGame.batch.begin();
        for (BaseView i : view) {
            i.draw();
        }
        myGame.batch.end();
        fps = (7. / 8 * fps + 1000. / (millis() - start) / 8);
        //System.out.println(fps);
        start = millis();
    }

    @Override
    public void resize(int width, int height) {
        myGame.viewport.update(width, height, true);
    }


    @Override
    public void dispose() {
        backGround.dispose();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
