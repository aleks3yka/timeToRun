package com.mygdx.game.view;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BaseView extends InputAdapter {
    public int x;
    int y;
    public int width;
    int height;
    SpriteBatch batch;
    onClickListener onClickListener;
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
        if(xt >= x-20 && xt <= x+20 + width && yt >= y-20 && yt <= y+20 + height){
            if(onClickListener != null) onClickListener.onClick();
            return true;
        }
        return false;
    }
    public void setClickListener(onClickListener onClickListener){
        this.onClickListener = onClickListener;
    }
    public interface onClickListener {
        void onClick();
    }
}
