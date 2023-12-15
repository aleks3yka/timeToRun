package com.mygdx.game.graph;

import static com.badlogic.gdx.utils.TimeUtils.millis;
import static com.badlogic.gdx.utils.TimeUtils.timeSinceMillis;

import com.mygdx.game.GameSettings;
import com.mygdx.game.view.Animation;

import java.util.Random;

public class GraphCharacter {
    double speed;
    double eps = 0.005;
    public double pos;
    public int wasOn;
    public int willBeOn;
    Graph graph;
    public boolean playable;
    public boolean moving;
    public final int id;
    public boolean coin;
    public int numOfAnimation;
    public int animationFrames;
    OnCoinCollectionListener onCoin;


    public GraphCharacter(int wasOn, double speed, Graph graph,
                          boolean playable, Integer nextId, boolean coin, int numOfAnimation) {
        this.wasOn = wasOn;
        this.graph = graph;
        this.speed = speed;
        this.playable = playable;
        this.id = (int) (nextId);
        this.coin = coin;
        this.pos = 0;
        this.willBeOn = wasOn;
        this.animationFrames = 0;
        this.numOfAnimation = numOfAnimation;
        if (!playable && !this.coin) {
            setMove(graph.graph.get(wasOn)
                    .get((int) (Math.random() * graph.graph.get(wasOn).size())));
        }else{
            eps = speed/30;
        }
    }

    void delete() {
        for (int i = 0; i < graph.characters.get(wasOn).size(); i++) {
            if (graph.characters.get(wasOn).get(i).id == id) {
                graph.characters.get(wasOn).remove(i);
                return;
            }
        }
    }

    void move() {
        if (!moving) {
            return;
        }
        pos += (speed / (double) GameSettings.fps);
        if (pos >= 1.) {
            moving = false;
            delete();
            wasOn = willBeOn;
            graph.characters.get(wasOn).add(this);
            if (!playable) {
                speed *= 1.005;
                setMove(graph.graph.get(wasOn)
                        .get((int) (Math.random() * graph.graph.get(wasOn).size())));
            }else {
                animationFrames = 0;
            }
        }
    }

    public void setMove(int v) {
        if (moving) {
            return;
        }
        willBeOn = v;
        pos = 0;
        moving = true;
        animationFrames = 0;
    }

    public void setOnCoin(OnCoinCollectionListener onCoin) {
        this.onCoin = onCoin;
    }

    boolean collide() {
        if (!playable) {
            return false;
        }
        if (moving) {
            for (int j = 0; j < graph.characters.get(willBeOn).size(); j++) {
                if (graph.characters.get(willBeOn).get(j).willBeOn == wasOn
                        && pos >= 1 - graph.characters.get(willBeOn).get(j).pos
                        && !graph.characters.get(willBeOn).get(j).coin) {
                    return true;
                }
            }
            for(int j = 0; j < graph.characters.get(wasOn).size(); j++){
                if (!graph.characters.get(wasOn).get(j).playable
                        && !graph.characters.get(wasOn).get(j).coin
                        && graph.characters.get(wasOn).get(j).willBeOn == willBeOn
                        && eps > Math.abs(graph.characters.get(wasOn).get(j).pos - pos)) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < graph.characters.get(wasOn).size(); i++) {
                if (graph.characters.get(wasOn).get(i).playable) {
                    continue;
                }else if(graph.characters.get(wasOn).get(i).coin && onCoin != null){
                    graph.characters.get(wasOn).remove(i);
                    graph.coins--;
                    onCoin.onCoinCollection();
                    i--;
                }else if (graph.characters.get(wasOn).get(i).pos <= eps) {
                    return true;
                }
            }

        }

        return false;
    }
    public interface OnCoinCollectionListener{
        public void onCoinCollection();
    }

}
