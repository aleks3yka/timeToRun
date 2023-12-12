package com.mygdx.game.utils;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.screens.GameScreen;
import com.mygdx.game.view.GraphView;

public class TouchTrackerGame extends InputAdapter {
    GameScreen screen;
    GraphView.Pos touchedDown;

    public TouchTrackerGame(GameScreen screen) {
        this.screen = screen;
        touchedDown = new GraphView.Pos(-1, -1);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (pointer != 0) {
            return super.touchDown(screenX, screenY, pointer, button);
        }
        Vector3 touch = new Vector3(screenX, screenY, 0);
        touch = screen.myGame.orthographicCamera.unproject(touch);
        touchedDown.x = touch.x;
        touchedDown.y = touch.y;
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (pointer != 0) {
            return super.touchDragged(screenX, screenY, pointer);
        }
        Vector3 touch = new Vector3(screenX, screenY, 0);
        touch = screen.myGame.orthographicCamera.unproject(touch);
        double angle = Math.atan2((touch.y - touchedDown.y), (touch.x - touchedDown.x));
        //angle -= Math.PI/2;
        screen.graphView.setAngle(angle);
//        System.out.println("x, y: " + (touch.x - touchedDown.x) + " " +(touch.y - touchedDown.y)
//                + " angle: " + angle/2/Math.PI*360
//        );
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (pointer != 0) {
            return super.touchUp(screenX, screenY, pointer, button);
        }
        Vector3 touch = new Vector3(screenX, screenY, 0);
        touch = screen.myGame.orthographicCamera.unproject(touch);
        double angle = Math.atan2((touch.y - touchedDown.y), (touch.x - touchedDown.x));
        //angle -= Math.PI/2;
//        System.out.println("x, y: " + (touch.x - touchedDown.x) + " " +(touch.y - touchedDown.y)
//                + " angle: " + angle/2/Math.PI*360
//        );
        screen.graphView.setAngle(angle);
        screen.graphView.go();
        return super.touchUp(screenX, screenY, pointer, button);
    }
}
