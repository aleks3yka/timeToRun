package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public class SoundsHelper {
    static Music backSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Chase Pulse Faster.mp3"));

    /*static Sound[] TimetoRun = {
            Gdx.audio.newSound(Gdx.files.internal("sounds/.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/.mp3"))
    };*/

    public static void playBackSound() {
        backSound.play();
        backSound.setLooping(true);
    }

    public static void stopPlaying() {
        backSound.stop();
    }

    /*public static void playTimeToRun() {
        TimetoRun[MathUtils.random(0, TimetoRun.length - 1)].play();
    }*/
}
