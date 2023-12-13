package com.mygdx.game.graph;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.mygdx.game.GameSettings;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Graph {
    public ArrayList<ArrayList<Integer>> graph;
    public ArrayList<ArrayList<GraphCharacter>> characters;
    int n, m;
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
    public Graph(int n, int m, double probability){
        Random random = new Random(millis());
        this.n = n;
        this.m = m;
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
        for(int i = 0; i < GameSettings.enemiesCount; i++){
            int v = (int)(random.nextDouble()*this.n*this.m);
            characters.get(v).add(new GraphCharacter(v, GameSettings.enemySpeed * (0.7+0.6*random.nextDouble()), this, false, random));
        }
        int v = (int)(random.nextDouble()*this.n*this.m);
        player = new GraphCharacter(GameSettings.startV, 1/GameSettings.timeOfPlayersTravel, this, true, random);
        characters.get(v).add(player);
    }
    public void update(){
        for(int i = 0; i < characters.size(); i++){
            for(int j = 0; j < characters.get(i).size(); j++){
                characters.get(i).get(j).move();
            }
        }
    }
    public boolean check(){
        return player.collide();
    }
}
