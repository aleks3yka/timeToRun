package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.GameSettings;

public class BackgroundView extends BaseView{
    Texture img;
    public BackgroundView(SpriteBatch batch, String img){
        super(0, 0, GameSettings.width, GameSettings.height, batch);
        this.img = new Texture(img);
    }
    public void draw(){
        batch.draw(img, 0, 0, GameSettings.width, GameSettings.height);
    }
    public void dispose(){
        img.dispose();
    }
}
