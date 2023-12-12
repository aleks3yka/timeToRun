package com.mygdx.game.screens;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.graph.Graph;
import com.mygdx.game.utils.FontHelper;
import com.mygdx.game.GameSettings;
import com.mygdx.game.view.BackgroundView;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.BaseView;
import com.mygdx.game.view.GraphView;
import com.mygdx.game.view.LabelView;

import java.util.ArrayList;

public class MenuScreen extends ScreenAdapter {
    MyGdxGame myGame;
    BackgroundView backGround;
    LabelView header;
    BitmapFont headerFont;
    GraphView graphView;
    Graph graph;
    ArrayList<BaseView> view;
    long lastMove;
    int v;
    double fps = 60;
    long start;
    public MenuScreen(MyGdxGame myGame){
        this.myGame = myGame;
        view = new ArrayList<>();
        view.add(new BackgroundView(myGame.batch, "menuResources/menuBackground.jpg"));
        headerFont = FontHelper.getFont(100, myGame.font, Color.BLACK,
                0, Color.BLACK, true);
        view.add(new LabelView(GameSettings.width / 2, GameSettings.height*7/8,
                "You can read this interesting meme", true, headerFont, myGame.batch));
        graph = new Graph(5, 5, 0.4);
        int graphWidth = 900, graphRes = 100;
        graphView = new GraphView((GameSettings.width - graphWidth)/2,
                (GameSettings.height - graphWidth)/2,
                graphWidth, graphRes, myGame.batch, graph,
                0
        );
        v = 0;
        view.add(graphView);
    }

    @Override
    public void show() {
        lastMove = millis();
        start = millis();
    }

    @Override
    public void render(float delta) {

        myGame.orthographicCamera.update();
        myGame.batch.setProjectionMatrix(myGame.orthographicCamera.combined);

        handleInput();
        ScreenUtils.clear(1, 1, 1, 1);
        myGame.batch.begin();
        for(BaseView i : view){
            i.draw();
        }
        myGame.batch.end();
        fps = (7./8*fps + 1000./ (millis() - start) /8);
        //System.out.println(fps);
        start = millis();
    }

    @Override
    public void resize(int width, int height) {
        myGame.viewport.update(width, height, true);
    }

    void handleInput(){
        if(millis() - lastMove >= 2000){
            v = graph.graph.get(v).get((int)(Math.random() * graph.graph.get(v).size()));
            graphView.setv(v);
            lastMove = millis();
        }
    }
    @Override
    public void dispose(){
        backGround.dispose();
    }
}
