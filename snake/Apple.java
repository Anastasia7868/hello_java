package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class Apple extends GameObject {
    private static final String APPLE_SIGN = "\uD83D\uDC2D";
    public boolean isAlive = true;
    public Apple(int x, int y){
        super(x, y);
    }
    public void draw(Game apple){
        apple.setCellValueEx(x, y, Color.NONE, APPLE_SIGN, Color.BLACK, 75);
    }
}
