package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LabelView extends BaseView{
    BitmapFont font;
    String content;
    public LabelView(int x, int y, String text, boolean centredX, BitmapFont font, SpriteBatch batch){
        super(x, y, batch);
        this.font = font;
        this.content = text;
        GlyphLayout glyph = new GlyphLayout(font, this.content);
        width = (int) glyph.width;
        height = (int) glyph.height;
        if(centredX){
            this.x-=glyph.width/2;
        }
    }
    public void draw(){
        font.draw(batch, content, x, y + height);
    }
}
