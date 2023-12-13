package com.mygdx.game.graph;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.mygdx.game.GameSettings;

import java.util.Random;

public class GraphCharacter {
    final double speed;
    final double eps = 0.000001;
    public double pos;
    public int wasOn;
    public int willBeOn;
    Graph graph;
    public boolean playable;
    boolean moving;
    public final int id;

    public GraphCharacter(int wasOn, double speed, Graph graph, boolean playable, Random random) {
        this.wasOn = wasOn;
        this.graph = graph;
        this.speed = speed;
        this.playable = playable;
        this.id = (int) (random.nextDouble() * 0x80000000);
        if (!playable) {
            setMove(graph.graph.get(wasOn)
                    .get((int) (Math.random() * graph.graph.get(wasOn).size())));
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
                setMove(graph.graph.get(wasOn)
                        .get((int) (Math.random() * graph.graph.get(wasOn).size())));
            }
        }
        ;
    }

    public void setMove(int v) {
        if (moving) {
            return;
        }
        willBeOn = v;
        pos = 0;
        moving = true;
    }

    boolean collide() {
        if (!playable) {
            return false;
        }
        if (moving) {
            for (int j = 0; j < graph.characters.get(willBeOn).size(); j++) {
                if (graph.characters.get(willBeOn).get(j).willBeOn == wasOn
                        && pos >= 1 - graph.characters.get(willBeOn).get(j).pos) {
                    return true;
                }
            }
            for(int j = 0; j < graph.characters.get(wasOn).size(); j++){
                if (!graph.characters.get(wasOn).get(j).playable
                        && graph.characters.get(wasOn).get(j).willBeOn == willBeOn
                        && eps > graph.characters.get(wasOn).get(j).pos - pos) {
                    return true;
                }
            }
        } else {
            for (int i = 0; i < graph.characters.get(wasOn).size(); i++) {
                if (graph.characters.get(wasOn).get(i).playable) {
                    continue;
                }
                if (graph.characters.get(wasOn).get(i).pos == 0) {
                    return true;
                }
            }
        }

        return false;
    }

}
