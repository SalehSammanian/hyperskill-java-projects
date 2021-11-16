package main;


import javafx.scene.shape.Rectangle;

public abstract class Paddle {
    private static final int WIDTH = 15;
    private static final int HEIGHT = 120;


    public abstract void move();
    public abstract int getY();
    public abstract int getX();
    public abstract void setDy(int dy);
    public abstract int getDy();
    public abstract void setY(int i);
    public abstract Rectangle draw();


    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

}
