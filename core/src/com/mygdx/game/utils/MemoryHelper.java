package com.mygdx.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.GameSettings;

public class MemoryHelper {

    private static final Preferences prefs = Gdx.app.getPreferences("User saves");
    private static int score;

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        MemoryHelper.score = score;
    }

    public static void saveDifficultyLevel(Difficulty difficulty) {
        if (difficulty == null) return;
        // System.out.println("save difficulty level: " + difficulty.difficultyIdx);
        prefs.putInteger("difficultyLevel1", difficulty.id).flush();
    }

    public static Difficulty loadDifficultyLevel() {
        if (prefs.contains("difficultyLevel1")) {
            int idx = prefs.getInteger("difficultyLevel1");
            if (idx == 0) return Difficulty.EASY;
            else if (idx == 1) return Difficulty.MEDIUM;
            else if (idx == 2) return Difficulty.HARD;
        }
        saveDifficultyLevel(GameSettings.DEFAULT_DIFFICULTY_LEVEL);
        return GameSettings.DEFAULT_DIFFICULTY_LEVEL;
    }

    public static float loadVolume() {
        float ans;
        if (prefs.contains("volume")) {
            ans = prefs.getFloat("volume");
        } else {
            ans = 1;
            saveVolume(ans);
        }
        return ans;
    }

    public static void saveVolume(float ans) {
        prefs.putFloat("volume", ans).flush();
    }

    public static void saveBestScore(int bestScore) {
        int diff = loadDifficultyLevel().id;
        prefs.putInteger("bestScore" + diff, bestScore).flush();
    }

    public static int loadBestScore() {
        int diff = loadDifficultyLevel().id;
        int ans;
        if (prefs.contains("bestScore" + diff)) {
            ans = prefs.getInteger("bestScore"+diff);
        }else {
            ans = 0;
            saveBestScore(ans);
        }
        return ans;
    }
}
