package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundsHelper {
    static Music backGameSound = Gdx.audio.newMusic(Gdx.files.internal(
            "sounds/y2mate_is_The_binding_of_Isaac_OST_Basement_theme_izHxUkF4ZAQ_192k.mp3"
    ));
    static Music backMenuSound = Gdx.audio.newMusic(Gdx.files.internal("sounds/Noise.mp3"));
    static Sound loose = Gdx.audio.newSound(Gdx.files.internal("sounds/mixkit-8-bit-lose-2031.wav"));
    static Sound getCoin = Gdx.audio.newSound(
            Gdx.files.internal("sounds/upali-dengi-na-igrovoy-schet.mp3")
    );
    /*static Sound[] TimetoRun = {
            Gdx.audio.newSound(Gdx.files.internal("sounds/.mp3")),
            Gdx.audio.newSound(Gdx.files.internal("sounds/.mp3"))
    };*/

    public static void playBackGameSound() {
        if (backGameSound.isPlaying()) {
            return;
        }
        backGameSound.setVolume(MemoryHelper.loadVolume());
        backGameSound.play();
        backGameSound.setLooping(true);
    }

    public static void stopBackGameSound() {
        if (backGameSound.isPlaying()) {
            backGameSound.stop();
        }
    }

    public static void playBackMenuSound() {
        if (backMenuSound.isPlaying()) {
            return;
        }
        backMenuSound.setVolume(MemoryHelper.loadVolume());
        backMenuSound.play();
        backMenuSound.setLooping(true);
    }

    public static void stopBackMenuSound() {
        if (backMenuSound.isPlaying()) {
            backMenuSound.stop();
        }
    }

    public static void coinCollected() {
        long id = getCoin.play();
        getCoin.setVolume(id, MemoryHelper.loadVolume()*0.3f);
    }

    public static void died() {
        long id = loose.play();
        backGameSound.pause();
        loose.setVolume(id, MemoryHelper.loadVolume());
    }

    public static void setVolume(float a) {
        backGameSound.setVolume(a);
        backMenuSound.setVolume(a);
    }

    public static void stopPlaying() {
        backGameSound.stop();
    }

    /*public static void playTimeToRun() {
        TimetoRun[MathUtils.random(0, TimetoRun.length - 1)].play();
    }*/
}
