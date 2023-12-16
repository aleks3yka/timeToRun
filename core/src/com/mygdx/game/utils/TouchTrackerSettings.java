package com.mygdx.game.utils;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.screens.MenuScreen;
import com.mygdx.game.screens.SettingsScreen;

public class TouchTrackerSettings extends InputAdapter {
    public SettingsScreen screen;
    public TouchTrackerSettings(SettingsScreen screen){
        this.screen = screen;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer != 0){
            return super.touchDown(screenX, screenY, pointer, button);
        }

        Vector3 vector = new Vector3(screenX, screenY, 0);
        vector = screen.myGame.viewport.unproject(vector);
        System.out.println(vector.x + " " + vector.y);

        for(int i = 0; i < screen.view.size(); i++){
            screen.view.get(i).isHit((int)vector.x, (int)vector.y);
        }

        if (screen.sliderView.isHit((int) vector.x, (int) vector.y)) {
            screen.sliderView.isDragging = true;
            int newX = (int) (vector.x - screen.sliderView.pointerImage.width / 2.);
            newX = (int) Math.min(screen.sliderView.x + screen.sliderView.stickImage.width
                    - screen.sliderView.pointerImage.width, newX);
            newX = (int) Math.max(screen.sliderView.x, newX);
            screen.sliderView.pointerImage.x = newX;
            SoundsHelper.setVolume(screen.sliderView.getValue());
            MemoryHelper.saveVolume(screen.sliderView.getValue());
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(pointer != 0){
            return super.touchDragged(screenX, screenY, pointer);
        }
        Vector3 vector = new Vector3(screenX, screenY, 0);
        vector = screen.myGame.viewport.unproject(vector);
        if (screen.sliderView.isDragging) {
            int newX = (int) (vector.x - screen.sliderView.pointerImage.width / 2.);
            newX = (int) Math.min(screen.sliderView.x + screen.sliderView.stickImage.width
                    - screen.sliderView.pointerImage.width, newX);
            newX = (int) Math.max(screen.sliderView.x, newX);
            screen.sliderView.pointerImage.x = newX;
            SoundsHelper.setVolume(screen.sliderView.getValue());
            MemoryHelper.saveVolume(screen.sliderView.getValue());
        }
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer != 0){
            return super.touchUp(screenX, screenY, pointer, button);
        }
        Vector3 vector = new Vector3(screenX, screenY, 0);
        vector = screen.myGame.viewport.unproject(vector);
        if (screen.sliderView.isDragging) {
            int newX = (int) (vector.x - screen.sliderView.pointerImage.width / 2.);
            newX = (int) Math.min(screen.sliderView.x + screen.sliderView.stickImage.width
                    - screen.sliderView.pointerImage.width, newX);
            newX = (int) Math.max(screen.sliderView.x, newX);
            screen.sliderView.pointerImage.x = newX;
            screen.sliderView.isDragging = false;
            SoundsHelper.setVolume(screen.sliderView.getValue());
            MemoryHelper.saveVolume(screen.sliderView.getValue());
        }
        return super.touchUp(screenX, screenY, pointer, button);
    }
}
