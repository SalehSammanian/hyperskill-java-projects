package main;

import javafx.scene.shape.Circle;

import java.util.Random;

public class Ball {
    private double x, y;
    private double dx, dy;
    private final int SCREENWIDTH, SCREENHEIGHT;
    private Circle ball;
    private Random random;

    public Ball(int SCREENWIDTH, int SCREENHEIGHT) {
        this.random = new Random(System.nanoTime());
        this.SCREENHEIGHT = SCREENHEIGHT;
        this.SCREENWIDTH = SCREENWIDTH;
        this.y = SCREENHEIGHT / 2;
        this.x = SCREENWIDTH / 2;
        randomizeSpeed();
        this.ball = draw();
    }
    public void move() {
        this.x += this.dx;
        this.y += this.dy;
    }

    public Circle draw() {
        this.ball = new Circle(x, y, 7);
        return this.ball;
    }

    public void randomizeSpeed() {
        this.random  = new Random(System.nanoTime());
        this.dx = this.random.nextInt(31) - 15;
        this.dy = this.random.nextInt(31) - 15;
        if(this.dx < 3 && this.dx > -3) {
            this.dx = this.random.nextInt(31) - 15;
        }
        if(this.dy < 3 && this.dy > -3) {
            this.dy = this.random.nextInt(31) - 15;
        }
    }

    public boolean collides(Paddle paddle) {
       if(this.x > paddle.getX() && this.x < paddle.getX() + 15 && this.y >  paddle.getY() && this.y < paddle.getY() + 120) {
           return true;
       }
       return false;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public Circle getBall() {
        return ball;
    }

    public void setBall(Circle ball) {
        this.ball = ball;
    }
}
