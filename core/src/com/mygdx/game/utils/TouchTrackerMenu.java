package com.mygdx.game.utils;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.screens.MenuScreen;

import java.util.Vector;

public class TouchTrackerMenu extends InputAdapter {
    MenuScreen screen;
    public TouchTrackerMenu(MenuScreen screen){
        this.screen = screen;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer != 0){
            return false;
        }
        Vector3 touch = new Vector3(screenX, screenY, 0);
        touch = screen.myGame.viewport.unproject(touch);
        for(int i = 0; i < screen.view.size(); i++){
            screen.view.get(i).isHit((int)touch.x, (int)touch.y);
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
