package com.mygdx.game.utils;

import com.mygdx.game.GameSettings;

public enum Difficulty {
    EASY(0, GameSettings.ENEMY_SPEED_EASY, GameSettings.ENEMY_COUNT_EASY,
            GameSettings.N_VERTICES_EASY, GameSettings.M_VERTICES_EASY,
            GameSettings.COIN_COUNT_EASY),
    MEDIUM(1, GameSettings.ENEMY_SPEED_MEDIUM, GameSettings.ENEMY_COUNT_MEDIUM,
            GameSettings.N_VERTICES_MEDIUM, GameSettings.M_VERTICES_MEDIUM,
            GameSettings.COIN_COUNT_MEDIUM),
    HARD(2, GameSettings.ENEMY_SPEED_HARD, GameSettings.ENEMY_COUNT_HARD,
            GameSettings.N_VERTICES_HARD, GameSettings.M_VERTICES_HARD,
            GameSettings.COIN_COUNT_HARD);
    public double enemySpeed;
    public int id;
    public int nVer, mVer;
    public int coinCount;
    public int dumbEnemyCount;

    Difficulty(int id, double enemySpeed, int dumbEnemyCount, int nVer, int mVer, int coinCount){
        this.id = id;
        this.enemySpeed = enemySpeed;
        this.dumbEnemyCount = dumbEnemyCount;
        this.nVer = nVer;
        this.mVer = mVer;
        this.coinCount = coinCount;
    }
    public static Difficulty getDifficultyById(int id){
        switch (id){
            case 0:{
                return Difficulty.EASY;
            }
            case 2:{
                return Difficulty.HARD;
            } default:{
                return Difficulty.MEDIUM;
            }
        }
    }
}
