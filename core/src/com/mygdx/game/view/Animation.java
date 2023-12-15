package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Animation {
    ArrayList<Texture> data;
    double fps;
    double width, height;
    SpriteBatch batch;
    public Animation(String name, int start, int end, double fps, SpriteBatch batch, double mull){
        this.batch = batch;
        this.fps = fps;
        data = new ArrayList<>(end - start);
        for(int i = start; i < end; i++) {
            data.add(new Texture(name + (i) + ".png"));
        }
        this.width = data.get(0).getWidth() * mull;
        this.height = data.get(0).getHeight() * mull;
    }
    public int draw(int x, int y, int t, boolean inverted){
        batch.draw(data.get((int)(t*fps/60)), x-(float)width/2, y,
                (float) width, (float) height,
                0, 0, data.get((int)(t*fps/60)).getWidth(),
                data.get((int)(t*fps/60)).getHeight(), inverted, false);
        t++;
        t %= (data.size()*60/fps);
        return t;
    }
}
