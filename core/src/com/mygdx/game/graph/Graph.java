package com.mygdx.game.graph;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.mygdx.game.GameSettings;
import com.mygdx.game.utils.Difficulty;
import com.mygdx.game.utils.MemoryHelper;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Graph {
    public ArrayList<ArrayList<Integer>> graph;
    public ArrayList<ArrayList<GraphCharacter>> characters;
    int n, m;
    int coins = 0;
    public GraphCharacter player;
    int repairPos(int v){
        if(v < 0){
            v += n * m;
        }else if(v >= n * m){
            v -= n * m;
        }
        return v;
    }
    ArrayList<Integer> disjointSet;
    ArrayList<Integer> rank;
    int root(int v){
        if(disjointSet.get(v).equals(v)){
            return v;
        }else{
            //System.out.println(v + ", " + disjointSet.get(v));
            disjointSet.set(v, root(disjointSet.get(v)));
            return disjointSet.get(v);
        }
    }
    void connect(int u, int v){
        if(rank.get(root(u)) < rank.get(root(v))){
            disjointSet.set(root(u), root(v));
        }else if(Objects.equals(rank.get(root(u)), rank.get(root(v)))){
            disjointSet.set(root(u), root(v));
            rank.set(v, rank.get(v) + 1);
        }else{
            disjointSet.set(root(v), root(u));
        }
    }
    public boolean spawnCoin(){
        ArrayList<Boolean> use = new ArrayList<>(graph.size());
        for(int i = 0; i < graph.size(); i++){
            use.add(true);
        }
        for(int i = 0; i < graph.size(); i++){
            for(int j = 0; j < characters.get(i).size(); j++){
                if(characters.get(i).get(j).coin || characters.get(i).get(j).playable){
                    use.set(i, false);
                }
            }
        }
        for(int i = 0; i < graph.get(player.wasOn).size(); i++){
            use.set(graph.get(player.wasOn).get(i), false);
            for(int j = 0; j < graph.get(graph.get(player.wasOn).get(i)).size(); j++){
                use.set(graph.get(graph.get(player.wasOn).get(i)).get(j), false);
            }
        }
        if(player.moving){
            for(int i = 0; i < graph.get(player.willBeOn).size(); i++){
                use.set(graph.get(player.willBeOn).get(i), false);
                for(int j = 0; j < graph.get(graph.get(player.willBeOn).get(i)).size(); j++){
                    use.set(graph.get(graph.get(player.willBeOn).get(i)).get(j), false);
                }
            }
        }
        boolean flag = false;
        for(int i = 0; i < use.size(); i++){
            flag |= use.get(i);
        }
        if(!flag){
            return false;
        }
        int place = (int)(Math.random()*use.size());
        while (!use.get(place)){
            place++;
            place %= use.size();
        }
        coins++;
        characters.get(place).add(new GraphCharacter(place, 0, this,
                false, -1, true, 0));
        return true;
    }
    public Graph(double probability){
        Difficulty difficulty = MemoryHelper.loadDifficultyLevel();
        Random random = new Random(millis());
        this.n = difficulty.nVer;
        this.m = difficulty.mVer;
        graph = new ArrayList<>(this.n * this.m);
        rank = new ArrayList<>(this.n * this.m);
        disjointSet = new ArrayList<>(this.n * this.m);
        int[] neighbours = {-m, -m+1, -1};
        for(int i = 0; i < this.n * this.m; i++) {
            rank.add(1);
            disjointSet.add(i);
            graph.add(new ArrayList<Integer>());
        }
        for(int i = 0; i < this.n * this.m; i++){
            for (int neighbour : neighbours) {
                if (random.nextDouble() < probability) {
                    graph.get(i).add(repairPos(i + neighbour));
                    graph.get(repairPos(i + neighbour)).add(i);
                    connect(i, repairPos(i+neighbour));
                }
            }
        }
        int lastComponent = root(0);
        for(int i = 1; i < this.n * this.m; i++){
            int rootI = root(i);
            if(rootI != lastComponent){
                disjointSet.set(lastComponent, rootI);
                graph.get(rootI).add(lastComponent);
                graph.get(lastComponent).add(rootI);
                lastComponent = rootI;
            }
        }

        characters = new ArrayList<>();
        for(int i = 0; i < this.n * this.m; i++){
            characters.add(new ArrayList<GraphCharacter>());
        }
        Integer nextId = 0;
        for(int i = 0; i < difficulty.dumbEnemyCount; i++){
            int v = (int)(random.nextDouble()*(this.n*this.m-1))+1;
            characters.get(v).add(new GraphCharacter(v,
                    difficulty.enemySpeed * (0.7+0.6*random.nextDouble()),
                    this, false, nextId++,
                    false, (int)(Math.random()*GameSettings.enemyAnimationCount)));
        }
        player = new GraphCharacter(GameSettings.startV, 1/GameSettings.timeOfPlayersTravel,
                this, true, nextId++, false, 0);
        characters.get(GameSettings.startV).add(player);
        int v = (int)(random.nextDouble()*(this.n*this.m-1))+1;
        characters.get(v).add(new GraphSmartEnemy(v,
                difficulty.enemySpeed,
                this, false, nextId++, (int)(Math.random()*GameSettings.enemyAnimationCount)));
        for(int i = 0; i < difficulty.coinCount; i++){
            spawnCoin();
        }
        coins = difficulty.coinCount;
    }
    public void update(){
        for(int i = 0; i < characters.size(); i++){
            for(int j = 0; j < characters.get(i).size(); j++){
                characters.get(i).get(j).move();
            }
        }
        if(coins < MemoryHelper.loadDifficultyLevel().coinCount){
            spawnCoin();
        }
    }
    public void setOnCoin(GraphCharacter.OnCoinCollectionListener onCoin){
        player.setOnCoin(onCoin);
    }
    public boolean check(){
        return player.collide();
    }
}
