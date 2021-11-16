package main;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {
    private HumanPaddle paddle1, paddle2;
    private Ball ball;
    private static final int SCREENWIDTH = 1280;
    private static final int SCREENHEIGHT = 720;
    private String gameState = "start";
    private int player1Score = 0;
    private int player2Score = 0;
    private Text scoreText = new Text(0 , 200, "     " + player1Score + "                 " + player2Score);
    private Text instructionsText = new Text(SCREENWIDTH / 2 - 100, 300 , "press SPACE to serve");


    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Pong");
        Group myRoot = new Group();
        Canvas canvas = new Canvas(SCREENWIDTH, SCREENHEIGHT);
        scoreText.setFont(Font.font("Verdana", 120));
        instructionsText.setFont(Font.font("Verdana", 20));


        ball = new Ball(SCREENWIDTH, SCREENHEIGHT);


        //create players
        paddle1 = new HumanPaddle(1);
        paddle2 = new HumanPaddle(2);


        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(keyPressed);
        canvas.setOnKeyReleased(keyReleased);
        myRoot.getChildren().addAll(scoreText, instructionsText, ball.draw(), canvas, paddle1.draw(), paddle2.draw());


        Scene scene = new Scene(myRoot);
        primaryStage.setScene(scene);
        primaryStage.show();
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                paddle1.move();
                paddle2.move();
                if(gameState.equals("play")) {
                    myRoot.getChildren().remove(instructionsText);
                    ball.move();
                }

                if(player2Score >= 10) {
                    gameState = "start";
                    myRoot.getChildren().add(instructionsText);
                    scoreText.setText("      Player 2 wins!");
                    player1Score = 0;
                    player2Score = 0;
                    ball.randomizeSpeed();
                } else if (player1Score >= 10) {
                    gameState = "start";
                    myRoot.getChildren().add(instructionsText);
                    scoreText.setText("      Player 1 wins!");
                    player2Score = 0;
                    player1Score = 0;
                    ball.randomizeSpeed();
                }

                if(gameState.equals("start")) {
                    ball.setY(SCREENHEIGHT / 2);
                    ball.setX(SCREENWIDTH / 2);

                }

                if(paddle1.getY() < 0) {
                    paddle1.setY(0);
                } else if (paddle1.getY() > 650) {
                    paddle1.setY(650);
                }

                if(paddle2.getY() < 0) {
                    paddle2.setY(0);
                } else if (paddle2.getY() > 650) {
                    paddle2.setY(650);
                }

                if(ball.getY() > SCREENHEIGHT + 50|| ball.getY() < 0) {
                    ball.setDy(-ball.getDy() * 1.03);
                }

                if (ball.collides(paddle1) || ball.collides(paddle2)) {
                    ball.setDx(-ball.getDx() * 1.03);
                }

                if(ball.getX() > SCREENWIDTH && gameState.equals("play")) {
                    player1Score++;
                    ball.randomizeSpeed();
                    if(ball.getDx() > 0) {
                        ball.setDx(ball.getDx() * - 1);
                    }
                    gameState = "start";
                    scoreText.setText("     " + player1Score + "                 " + player2Score);
                } else if (ball.getX() < 0 && gameState.equals("play")) {
                    player2Score++;
                    ball.randomizeSpeed();
                    if(ball.getDx() < 0) {
                        ball.setDx(ball.getDx() * - 1);
                    }
                    gameState = "start";
                    scoreText.setText("     " + player1Score + "                 " + player2Score);
                }

                myRoot.getChildren().removeAll(ball.getBall(), paddle1.getPaddle(), paddle2.getPaddle());
                myRoot.getChildren().addAll(ball.draw(), paddle1.draw(), paddle2.draw());
            }
        }.start();

    }

    private EventHandler<KeyEvent> keyReleased = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            switch (e.getCode()) {
                case W:
                case S:
                    paddle1.setDy(0);
                    break;
                case UP:
                case DOWN:
                    paddle2.setDy(0);
                    break;
                case SPACE:
                    if (gameState.equals("start")) {
                        gameState = "play";
                        scoreText.setText("     " + player1Score + "                 " + player2Score);
                    }
            }
        }
    };

    private EventHandler<KeyEvent> keyPressed = new EventHandler<KeyEvent>() {

        @Override
        public void handle(KeyEvent e) {
            switch (e.getCode()) {
                case W:
                    paddle1.setDy(-18);
                    break;
                case S:
                    paddle1.setDy(18);
                    break;
                case UP:
                    paddle2.setDy(-18);
                    break;
                case DOWN:
                    paddle2.setDy(18);
                    break;
            }
        }
    };

    public static void main(String[] args) {
        launch(args);
    }


}
