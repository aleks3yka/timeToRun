package com.mygdx.game.graph;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        Graph graph = new Graph(3, 3, 0.5);
        for(int i = 0; i < graph.graph.size(); i++){
            System.out.print(i+1 + ": ");
            for (Integer j : graph.graph.get(i)){
                System.out.print((j+1) + " ");
            }
            System.out.println();
        }
    }
}
