package com.mygdx.game.graph;

import com.mygdx.game.GameSettings;
import com.sun.tools.javac.util.Pair;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class GraphSmartEnemy extends GraphCharacter{
    public GraphSmartEnemy(int wasOn, double speed, Graph graph,
                           boolean playable, Integer nextId, int numOfAnimation) {
        super(wasOn, speed, graph, false, nextId, false, numOfAnimation);
    }
    int bfs(){
        int end = graph.player.willBeOn;

        ArrayDeque<Integer> queue = new ArrayDeque<Integer>();
        ArrayList<Boolean> use = new ArrayList<>();
        ArrayList<Integer> parent = new ArrayList<>();
        for(int i = 0; i < graph.graph.size(); i++){
            use.add(false);
            parent.add(-1);
        }
        queue.add(wasOn);
        use.set(queue.getFirst(), true);
        while (queue.size() != 0){
            int now = queue.pollFirst();
            for(int i : graph.graph.get(now)){
                if(use.get(i)){
                    continue;
                }
                use.set(i, true);
                parent.set(i, now);
                queue.addLast(i);
            }
        }
        int ans = end;
        while (parent.get(ans) != wasOn && ans != wasOn){
            //System.out.println(ans);
            ans = parent.get(ans);
            //System.out.println(ans);
        }
        return ans;
    }
    @Override
    void move() {
        if (!moving) {
            int to = bfs();
            if (to != wasOn) {
                setMove(to);
            }
            return;
        }
        pos += (speed / (double) GameSettings.fps);
        if (pos >= 1.) {
            moving = false;
            delete();
            wasOn = willBeOn;
            pos = 0;
            speed *= 1.005;
            graph.characters.get(wasOn).add(this);
            int to = bfs();
            if (to != wasOn) {
                setMove(to);
            }
        }
    }
}
