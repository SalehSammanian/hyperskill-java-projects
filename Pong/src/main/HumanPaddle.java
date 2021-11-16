package main;

import javafx.scene.shape.Rectangle;

public class HumanPaddle extends Paddle{
    private double x, y;
    private int dy = 0;
    private Rectangle paddle;

    public HumanPaddle(int player) {
        if (player == 1) {
            x = 20;
            y = 20;
        }else if (player == 2) {
            x = 1250;
            y = 650;
        }
        this.paddle = draw();

    }


    @Override
    public void move() {
        this.y += this.dy;
    }

    public Rectangle draw() {
        this.paddle = new Rectangle(x, y, getWIDTH(), getHEIGHT());
        return this.paddle;
    }

    @Override
    public int getY() {
        return (int) y;
    }

    public int getDy() {
        return dy;
    }

    @Override
    public void setY(int i) {
        this.y = i;
    }

    public int getX() {
        return (int) x;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public Rectangle getPaddle(){
        return this.paddle;
    }
}
