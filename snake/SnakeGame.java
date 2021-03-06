package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private static final int GOAL= 28;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private int score;


    public void initialize(){
        setScreenSize(WIDTH, HEIGHT);
        createGame();


    }
    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if(apple.isAlive == false){
            createNewApple();
            score += 5;
            setScore(score);
            turnDelay -=10;
            setTurnTimer(turnDelay);
        }
        if(!snake.isAlive){
            gameOver();
        }
        if (snake.getLength() > GOAL){
            win();
        }

        drawScene();
    }
    @Override
    public void onKeyPress(Key key) {
        if (key == Key.SPACE && isGameStopped){
            createGame();
        }
        if(key == Key.LEFT){
            snake.setDirection(Direction.LEFT);
        } else if(key == Key.RIGHT){
            snake.setDirection(Direction.RIGHT);
        } else if(key == Key.UP){
            snake.setDirection(Direction.UP);
        } else if(key == Key.DOWN){
            snake.setDirection(Direction.DOWN);
        }
    }
    private void createGame(){
        snake = new Snake(WIDTH/2, HEIGHT/2);
//      apple = new Apple(5, 5);
        createNewApple();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
        score = 0;
        setScore(score);
//        Apple apple = new Apple(7, 7);
//        apple.draw(this);

    }
    private void drawScene(){
        for(int x =0; x < WIDTH; x ++){
            for (int y = 0; y< HEIGHT; y++){
                setCellValueEx(x, y, Color.YELLOWGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }
    private void createNewApple(){
        Apple newApple;
        do {
            int x = getRandomNumber(WIDTH);
            int y = getRandomNumber(HEIGHT);
            newApple = new Apple(x, y);
        } while (snake.checkCollision(newApple));
        apple = newApple;

    }
    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.RED, "Game Over", Color.BLACK, 75);
    }
    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "You win", Color.BLACK, 75);
    }
}
