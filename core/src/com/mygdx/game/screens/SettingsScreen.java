package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.utils.MemoryHelper;
import com.mygdx.game.utils.TouchTrackerSettings;
import com.mygdx.game.view.BaseView;
import com.mygdx.game.view.LabelView;
import com.mygdx.game.view.SliderView;
import com.mygdx.game.view.SwitcherView;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {
    public MyGdxGame myGame;
    public ArrayList<BaseView> view;
    public SliderView sliderView;
    public SettingsScreen(MyGdxGame myGame){
        this.myGame = myGame;

    }

    @Override
    public void render(float delta) {
        myGame.orthographicCamera.update();
        myGame.batch.setProjectionMatrix(myGame.orthographicCamera.combined);
        ScreenUtils.clear(0, 0, 0, 0);
        myGame.batch.begin();
        for(int i = 0; i < view.size(); i++){
            myGame.batch.setColor(Color.WHITE);
            view.get(i).draw();
        }
        myGame.batch.end();
    }
    @Override
    public void resize(int width, int height) {
        myGame.viewport.update(width, height, true);
    }

    @Override
    public void show() {
        view = new ArrayList<>();
        view.add(new LabelView(200, 500, "Difficulty:", false,
                myGame.normalFont, myGame.batch));
        view.add(new SwitcherView(500, 500, MemoryHelper.loadDifficultyLevel().id,
                myGame.accentFont, myGame.batch));
        view.add(new LabelView(200, 400, "Volume:", false,
                myGame.normalFont, myGame.batch));
        view.add(new LabelView(100, 700, "Back",
                false, myGame.normalFont, myGame.batch));
        view.get(view.size()-1).setClickListener(new BaseView.onClickListener(){
            @Override
            public void onClick() {
                myGame.setScreen(myGame.menuScreen);
            }
        });
        sliderView = new SliderView(400, 400, 500, 50, myGame.batch);
        sliderView.pointerImage.x = (int)(MemoryHelper.loadVolume()
                * (sliderView.width-sliderView.pointerImage.width)
                + sliderView.x);
        view.add(sliderView);
        Gdx.input.setInputProcessor(new TouchTrackerSettings(this));
    }
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}
