package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LabelView extends BaseView{
    BitmapFont font;
    String content;
    boolean centredX;
    public LabelView(int x, int y, String text, boolean centredX, BitmapFont font, SpriteBatch batch){
        super(x, y, batch);
        this.font = font;
        this.content = text;
        this.centredX = centredX;
        GlyphLayout glyph = new GlyphLayout(font, this.content);
        width = (int) glyph.width;
        height = (int) glyph.height;

    }
    public void setMessage(String message){
        this.content = message;
        GlyphLayout glyph = new GlyphLayout(font, this.content);
        width = (int) glyph.width;
        height = (int) glyph.height;
    }
    public void draw(){
        if(centredX){
            font.draw(batch, content, x-width/2, y + height);
        } else {
            font.draw(batch, content, x, y + height);
        }
    }
}
