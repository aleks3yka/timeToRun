package com.mygdx.game.view;

import static com.badlogic.gdx.utils.TimeUtils.millis;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.GameSettings;
import com.mygdx.game.graph.Graph;
import com.mygdx.game.graph.GraphCharacter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;

public class GraphView extends BaseView implements Disposable {
    Comparator<Edge> comparator = new Comparator<Edge>() {
        @Override
        public int compare(Edge t1, Edge t2) {
            if (t1.a < t2.a || (t1.a == t2.a && t1.b < t2.b)) {
                return -1;
            } else if (t1.a == t2.a && t1.b == t2.b) {
                return 0;
            } else {
                return -1;
            }
        }
    };

    public static class Pos implements Cloneable {
        public double x;
        public double y;
        public double angle;
        public double radius;

        public Pos(double x, double y) {
            this.x = x;
            this.y = y;
        }

        Pos mul(double a) {
            Pos ans = new Pos(x * a, y * a);
            return ans;
        }

        Pos add(Pos b) {
            return new Pos(x + b.x, y + b.y);
        }

        @Override
        protected Object clone() {
            return new Pos(x, y);
        }
    }

    class Edge implements Cloneable {
        int a, b;

        Edge(int a, int b) {
            if (a > b) {
                this.b = a;
                this.a = b;
                return;
            }
            this.a = a;
            this.b = b;
        }

        @Override
        protected Object clone() {
            return new Edge(this.a, this.b);
        }
    }
    Graph graph;
    Pixmap canvas;
    int v;
    boolean updating = false;
    double updateTime = GameSettings.timeOfPlayersTravel;
    long timeUpdateStart;
    ArrayList<Pos> prevState;
    TreeSet<Edge> prevEdgeArrayWhite;
    TreeSet<Edge> prevEdgeArrayGrey;
    ArrayList<Pos> newState;
    TreeSet<Edge> newEdgeArrayWhite;
    TreeSet<Edge> newEdgeArrayGrey;
    int res;

    Color colorEnemy = Color.RED;
    Color colorPlayer = Color.CHARTREUSE;

    Color root = Color.WHITE;
    Color neighbour = Color.WHITE;
    Color edgeWhite = Color.WHITE;
    Color edgeGrey = Color.GRAY;
    Color background = Color.BLACK;
    Color highLighted = Color.VIOLET;
    Texture texture;
    double angle;
    int chosen = -1;
    ArrayList<Integer> choosing;
    OnCollide onCollideListener;
    //    double rotationSpeed1 = 0.1;
//    double rotationSpeed2 = -0.07;
//    double theta1;
//    double theta2;
    public void setOnCollideListener(OnCollide onCollideListener) {
        this.onCollideListener = onCollideListener;
    }


    public double funk(double x) {
        return (-Math.cos(x * Math.PI) * Math.PI) / Math.PI / 2 + 0.5;
    }

    void getNewGraph() {
        TreeSet<Integer> neighbours = new TreeSet<>();
        newEdgeArrayWhite.clear();
        newState.clear();
        newEdgeArrayGrey.clear();
        choosing.clear();
        for (int i = 0; i < graph.graph.size(); i++) {
            newState.add(new Pos(-1, -1));
        }
        for (Integer i : graph.graph.get(v)) {
            newEdgeArrayWhite.add(new Edge(i, v));
            neighbours.add(i);
        }
        TreeSet<Integer> neighboursSecond = new TreeSet<>();
        for (Integer i : neighbours) {
            for (Integer j : graph.graph.get(i)) {
                if (!neighbours.contains(j) && v != j) {
                    neighboursSecond.add(j);
                } else if (neighbours.contains(j)) {
                    newEdgeArrayGrey.add(new Edge(i, j));
                }
            }

        }
        int j = 0;
        newState.set(v, new Pos((double) res / 2, (double) res / 2));
        for (Integer neighbour : neighbours) {
            choosing.add(neighbour);
            newState.set(neighbour, new Pos
                    (
                            (int) (
                                    (double) res / 2 + Math.cos(
                                            (
                                                    (double) j / neighbours.size() + 0.25
                                            ) * Math.PI * 2
                                    ) * 0.2 * res
                            ),
                            (int) (
                                    (double) res / 2 + Math.sin(
                                            (
                                                    (double) j / neighbours.size() + 0.25
                                            ) * Math.PI * 2
                                    ) * 0.2 * res
                            )
                    )
            );
            newState.get(neighbour).angle = (double) j / neighbours.size() + 0.25;
            newState.get(neighbour).radius = 0.2 * res;
            j++;
        }
        j = 0;
        for (Integer neighbourSecond : neighboursSecond) {
            newState.set(neighbourSecond, new Pos
                    (
                            (int) ((double) res / 2 + Math.cos(
                                    (
                                            (double) j / neighboursSecond.size() + 0.25
                                    ) * Math.PI * 2) * 0.4 * res
                            ),
                            (int) ((double) res / 2 + Math.sin(
                                    (
                                            (double) j / neighboursSecond.size() + 0.25
                                    ) * Math.PI * 2) * 0.4 * res
                            )
                    )
            );
            newState.get(neighbourSecond).angle = (double) j / neighbours.size() + 0.25;
            newState.get(neighbourSecond).radius = 0.4 * res;
            j++;
            for (Integer neighbourThird : graph.graph.get(neighbourSecond)) {
                if (neighbours.contains(neighbourThird)) {
                    newEdgeArrayWhite.add(new Edge(neighbourThird, neighbourSecond));
                } else if (neighboursSecond.contains(neighbourThird)) {
                    newEdgeArrayGrey.add(new Edge(neighbourSecond, neighbourThird));
                }
            }
        }
    }

    void fromAngleToChosen() {
        chosen = (int) (
                (((-angle) + Math.PI * 3 / 2 + Math.PI / choosing.size())%(Math.PI * 2))
                        / (Math.PI * 2 / choosing.size())
        );
    }

    public void setAngle(double angle) {
        if (updating) {
            return;
        }
        this.angle = angle;
        fromAngleToChosen();
        //System.out.println(angle + " " + chosen);
    }

    public GraphView(int x, int y, int width, int res, SpriteBatch batch, Graph graph, int v) {
        super(x, y, width, width, batch);
        this.graph = graph;
        this.v = v;
        this.res = res;
        canvas = new Pixmap(this.res, this.res, Pixmap.Format.RGBA8888);
        timeUpdateStart = -1000000;
        prevState = new ArrayList<>();
        prevEdgeArrayWhite = new TreeSet<>(comparator);
        prevEdgeArrayGrey = new TreeSet<>(comparator);
        newState = new ArrayList<>();
        newEdgeArrayWhite = new TreeSet<>(comparator);
        newEdgeArrayGrey = new TreeSet<>(comparator);
//        theta1 = 0;
//        theta2 = 0;
        for (int i = 0; i < graph.graph.size(); i++) {
            prevState.add(new Pos(-1, -1));
            newState.add(new Pos(-1, -1));
        }
        choosing = new ArrayList<>();
        getNewGraph();
        update();
    }

    void update() {
        graph.update();
        if(graph.check()){
            if(onCollideListener != null) onCollideListener.onCollide();
        }
        canvas.setColor(background);
        canvas.fill();
        if (((double) millis() - timeUpdateStart) / 1000 >= updateTime) {
            prevState = (ArrayList<Pos>) newState.clone();
            prevEdgeArrayWhite = (TreeSet<Edge>) newEdgeArrayWhite.clone();
            prevEdgeArrayGrey = (TreeSet<Edge>) newEdgeArrayGrey.clone();
            updating = false;
        }
        double t = Math.min(1.0, ((double) millis() - timeUpdateStart) / 1000 / updateTime);
        t = Math.max(0., t);
        double tFunk = funk(t);
        //System.out.println(tFunk);
        ArrayList<Pos> State = new ArrayList<>(newState.size());
        for (int i = 0; i < newState.size(); i++) {
            //State.add(newState.get(i));
            if (prevState.get(i).x != -1. && newState.get(i).x != -1.) {
                State.add
                        (
                                newState.get(i).mul(tFunk)
                                        .add(prevState.get(i).mul(1-tFunk))
                        );
            } else if (prevState.get(i).x != -1.) {
                State.add((Pos) prevState.get(i).clone());
            } else if (newState.get(i).x != -1.) {
                State.add((Pos) newState.get(i).clone());
            } else {
                State.add(new Pos(-1, -1));
            }
        }
        for (Edge a : newEdgeArrayGrey) {
            Pos v = State.get(a.a), u = State.get(a.b);
            if (prevEdgeArrayGrey.contains(a)) {
                canvas.setColor(edgeGrey);
            } else if (prevEdgeArrayWhite.contains(a)) {
                canvas.setColor((float) (edgeGrey.r * t + edgeWhite.r * (1 - t)),
                        (float) (edgeGrey.g * t + edgeWhite.g * (1 - t)),
                        (float) (edgeGrey.b * t + edgeWhite.b * (1 - t)),
                        (float) (edgeGrey.a * t + edgeWhite.a * (1 - t))
                );
            } else {
                canvas.setColor(edgeGrey.r, edgeGrey.g, edgeGrey.b, (float) (edgeGrey.a * t));
            }
            canvas.drawLine((int) v.x, (int) v.y, (int) u.x, (int) u.y);
        }
        for (Edge a : prevEdgeArrayGrey) {
            Pos v = State.get(a.a), u = State.get(a.b);
            if (!newEdgeArrayGrey.contains(a) && !newEdgeArrayWhite.contains(a)) {
                canvas.setColor(edgeGrey.r, edgeGrey.g, edgeGrey.b, (float) (edgeGrey.a * (1 - t)));
                canvas.drawLine((int) v.x, (int) v.y, (int) u.x, (int) u.y);
            }
        }
        for (Edge a : newEdgeArrayWhite) {
            Pos v = State.get(a.a), u = State.get(a.b);
            if (prevEdgeArrayWhite.contains(a)) {
                canvas.setColor(edgeWhite);
            } else if (prevEdgeArrayGrey.contains(a)) {
                canvas.setColor((float) (edgeGrey.r * (1 - t) + edgeWhite.r * t),
                        (float) (edgeGrey.g * (1 - t) + edgeWhite.g * t),
                        (float) (edgeGrey.b * (1 - t) + edgeWhite.b * t),
                        (float) (edgeGrey.a * (1 - t) + edgeWhite.a * t)
                );
            } else {
                canvas.setColor(edgeWhite.r, edgeWhite.g, edgeWhite.b, (float) (edgeWhite.a * t));
            }
            canvas.drawLine((int) v.x, (int) v.y, (int) u.x, (int) u.y);
        }
        for (Edge a : prevEdgeArrayWhite) {
            Pos v = State.get(a.a), u = State.get(a.b);
            if (!newEdgeArrayWhite.contains(a) && !newEdgeArrayGrey.contains(a)) {
                canvas.setColor(edgeWhite.r, edgeWhite.g, edgeWhite.b, (float) (edgeWhite.a * (1 - t)));
                canvas.drawLine((int) v.x, (int) v.y, (int) u.x, (int) u.y);
            }
        }
        for (int i = 0; i < State.size(); i++) {
            Pos u = State.get(i);
            if (State.get(i).x == -1.) {
                continue;
            }
            if (i == v) {
                canvas.setColor(root);
                //System.out.println(u.x + " " + u.y);
                //System.out.println(newState.get(v).x + " " + newState.get(v).y);
            } else if (choosing.size() != 0 && chosen != -1 && i == choosing.get(chosen)) {
                canvas.setColor(highLighted);
            } else if (prevState.get(i).x != -1 && newState.get(i).x != -1) {
                canvas.setColor(neighbour);
            } else if (prevState.get(i).x != -1) {
                canvas.setColor(neighbour.r, neighbour.g, neighbour.b, (float) (neighbour.a * (1 - t)));
            } else {
                canvas.setColor(neighbour.r, neighbour.g, neighbour.b, (float) (neighbour.a * (t)));
            }
            canvas.fillCircle((int) u.x, (int) u.y, res / 50);
        }
        for (ArrayList<GraphCharacter> i : graph.characters){
            for(GraphCharacter j : i){
                if(State.get(j.wasOn).x == -1 || State.get(j.willBeOn).x == -1){
                    continue;
                }
                Pos u = State.get(j.wasOn);
                Pos v = State.get(j.willBeOn);
                if(j.playable){
                    canvas.setColor(colorPlayer);
                }else{
                    if(newState.get(j.wasOn).x != -1 && prevState.get(j.willBeOn).x != -1) {
                        canvas.setColor(colorEnemy);
                    }else if (newState.get(j.wasOn).x != -1){
                        canvas.setColor(colorEnemy.r, colorEnemy.g, colorEnemy.b,
                                (float) (colorEnemy.a * t));
                    }else{
                        canvas.setColor(colorEnemy.r, colorEnemy.g, colorEnemy.b,
                                (float) (colorEnemy.a * (1-t)));
                    }
                }
                canvas.fillCircle(
                        (int)((u.x)*(1-j.pos)+(v.x)*j.pos),
                        (int)((u.y)*(1-j.pos)+(v.y)*j.pos),
                        res/100
                );
            }
        }
    }

    public boolean setv(int v) {
        if (updating) {
            return false;
        }
        this.v = v;
        chosen = -1;
        getNewGraph();
        updating = true;
        timeUpdateStart = millis();
        return true;
    }

    public void go() {
        //Gdx.input.vibrate((int)(updateTime*1000), 255, false);
        if(updating){
            return;
        }
        graph.player.setMove(choosing.get(chosen));
        setv(choosing.get(chosen));
    }

    @Override
    public void draw() {
        if (texture != null) {
            texture.dispose();
        }
        update();
        texture = new Texture(canvas);
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public void dispose() {
        texture.dispose();
        canvas.dispose();
    }
    public interface OnCollide{
        public void onCollide();
    }
}
