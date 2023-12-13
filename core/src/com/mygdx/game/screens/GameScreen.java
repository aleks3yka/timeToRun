package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.graph.Graph;
import com.mygdx.game.utils.TouchTrackerGame;
import com.mygdx.game.view.BaseView;
import com.mygdx.game.view.GraphView;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    public MyGdxGame myGame;
    ArrayList<BaseView> view;
    Graph graph;
    TouchTrackerGame inputProcessor;
    public GraphView graphView;
    public GameScreen(MyGdxGame myGame){

        this.myGame = myGame;
        inputProcessor = new TouchTrackerGame(this);
        view = new ArrayList<>();
        view.add(graphView);
    }

    @Override
    public void render(float delta) {
        myGame.orthographicCamera.update();
        myGame.batch.setProjectionMatrix(myGame.orthographicCamera.combined);

        ScreenUtils.clear(0,0,0,1);
        myGame.batch.begin();
        graphView.draw();
        myGame.batch.end();
    }
    @Override
    public void show() {
        if(graphView != null){
            graphView.dispose();
        }
        int graphWidth = 900, graphRes = 900;

        graph = new Graph(5, 5, 0.5);
        graphView = new GraphView((GameSettings.width - graphWidth)/2,
                (GameSettings.height - graphWidth)/2,
                graphWidth, graphRes, myGame.batch, graph,
                GameSettings.startV
        );
        graphView.setOnCollideListener(onCollideListener);
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void resize(int width, int height) {
        myGame.viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        graphView.dispose();
    }

    GraphView.OnCollide onCollideListener = new GraphView.OnCollide() {
        @Override
        public void onCollide() {
            //System.out.println("yomayo");
            //graphView.dispose();
            graphView = null;
            graph = null;
            myGame.setScreen(myGame.menuScreen);
        }
    };
}
