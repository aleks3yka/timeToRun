package com.mygdx.game.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.utils.FontHelper;
import com.mygdx.game.GameSettings;
import com.mygdx.game.view.BackgroundView;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.view.LabelView;

public class MenuScreen extends ScreenAdapter {
    MyGdxGame myGame;
    BackgroundView backGround;
    LabelView header;
    BitmapFont headerFont;
    public MenuScreen(MyGdxGame myGame){
        this.myGame = myGame;
        backGround = new BackgroundView(myGame.batch, "menuResources/menuBackground.jpg");
        headerFont = FontHelper.getFont(100, myGame.font, Color.WHITE);
        if(myGame.batch == null) System.out.println("yomayo");
        header = new LabelView(GameSettings.width / 2, GameSettings.height*7/8,
                "You can read this interesting meme", true, headerFont, myGame.batch);
    }

    @Override
    public void render(float delta) {

        myGame.orthographicCamera.update();
        myGame.batch.setProjectionMatrix(myGame.orthographicCamera.combined);

        ScreenUtils.clear(1, 1, 1, 1);
        myGame.batch.begin();
        backGround.draw();
        header.draw();
        myGame.batch.end();
    }
    @Override
    public void dispose(){
        backGround.dispose();
    }
}
