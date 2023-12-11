package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class FontHelper {

    public static BitmapFont getFont(int size, String fontName, Color color) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = color;
        parameter.size = size;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.borderStraight = true;
        parameter.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();
        bitmapFont.getRegion().getTexture().setFilter(
                Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear
        );
        return bitmapFont;
    }
    public static BitmapFont getFont(int size,
                                     String fontName,
                                     Color color,
                                     int borderWidth,
                                     Color borderColor,
                                     boolean borderStraight) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontName));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = color;
        parameter.size = size;
        parameter.borderColor = borderColor;
        parameter.borderWidth = borderWidth;
        parameter.borderStraight = borderStraight;
        parameter.characters = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяabcdefghijklmnopqrstuvwxyzАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;:,{}\"´`'<>";
        BitmapFont bitmapFont = generator.generateFont(parameter);
        generator.dispose();
        bitmapFont.getRegion().getTexture().setFilter(
                Texture.TextureFilter.Linear,
                Texture.TextureFilter.Linear
        );
        return bitmapFont;
    }

}
