package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BaseView {
    int x;
    int y;
    int width;
    int height;
    SpriteBatch batch;
    ClickListener clickListener;
    BaseView(int x, int y, int width, int height, SpriteBatch batch){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.batch = batch;
    }
    BaseView(int x, int y, SpriteBatch batch){
        this.x = x;
        this.y = y;
        this.batch = batch;
    }

    public void draw() {};

    public boolean isHit(int xt, int yt){
        if(xt >= x && xt <= x + width && yt >= y && yt <= y + height){
            if(clickListener != null)clickListener.onClick();
            return true;
        }
        return false;
    }
    public void serClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }
    interface ClickListener{
        void onClick();
    }
}
