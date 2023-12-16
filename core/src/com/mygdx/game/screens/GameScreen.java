package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.GameSettings;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.graph.Graph;
import com.mygdx.game.graph.GraphCharacter;
import com.mygdx.game.utils.MemoryHelper;
import com.mygdx.game.utils.SoundsHelper;
import com.mygdx.game.utils.TouchTrackerGame;
import com.mygdx.game.view.BaseView;
import com.mygdx.game.view.GraphView;
import com.mygdx.game.view.LabelView;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    public MyGdxGame myGame;
    ArrayList<BaseView> view;
    Graph graph;
    TouchTrackerGame inputProcessor;
    public GraphView graphView;
    int coin;

    public GameScreen(MyGdxGame myGame){
        inputProcessor = new TouchTrackerGame(this);
        this.myGame = myGame;

    }

    @Override
    public void render(float delta) {
        myGame.orthographicCamera.update();
        myGame.batch.setProjectionMatrix(myGame.orthographicCamera.combined);

        ScreenUtils.clear(0,0,0,1);
        myGame.batch.begin();
        for(int i = 0; i < view.size(); i++){
            myGame.batch.setColor(1,1,1,1);
            view.get(i).draw();
        }
        myGame.batch.end();

    }
    @Override
    public void show() {
        SoundsHelper.stopBackMenuSound();
        SoundsHelper.playBackGameSound();
        SoundsHelper.setVolume(MemoryHelper.loadVolume());
        if(graphView != null){
            graphView.dispose();
        }
        coin = 0;
        int graphWidth = 900, graphRes = 900;

        graph = new Graph(0.5);
        graphView = new GraphView((GameSettings.width - graphWidth)/2,
                (GameSettings.height - graphWidth)/2,
                graphWidth, graphRes, myGame.batch, graph,
                GameSettings.startV, myGame
        );

        view = new ArrayList<>();
        view.add(graphView);
        view.add(new LabelView(1405, 850, "000000",
                false, myGame.normalFont, myGame.batch));
        final int bestScore = MemoryHelper.loadBestScore();
        view.add(new LabelView(10, 850, bestScore/100000%10 + "" + bestScore/10000%10
                + "" + bestScore/1000%10
                + "" + bestScore/100%10
                + "" + bestScore/10%10
                + "" + bestScore%10, false, myGame.normalFont, myGame.batch));
        graphView.setOnCollideListener(onCollideListener);
        graphView.setOnCoin(new GraphCharacter.OnCoinCollectionListener(){

            @Override
            public void onCoinCollection() {
                SoundsHelper.coinCollected();
                coin++;
                MemoryHelper.setScore(coin);
                String message = coin/100000%10 + "" + coin/10000%10
                       + "" + coin/1000%10
                        + "" + coin/100%10
                        + "" + coin/10%10
                        + "" + coin%10;
                LabelView label = (LabelView)view.get(1);
                label.setMessage(message);
                int bestScore = MemoryHelper.loadBestScore();
                if(coin > bestScore){
                    bestScore = coin;
                    MemoryHelper.saveBestScore(coin);
                    label = (LabelView) view.get(2);
                    label.setMessage(message);
                }
            }
        });
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
            myGame.setScreen(myGame.gameOverScreen);
        }
    };
}
