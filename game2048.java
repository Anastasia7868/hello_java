package com.javarush.games.game2048;

import com.javarush.engine.cell.*;

import java.security.Signature;

public class Game2048 extends Game {
    private static final int SIDE = 4;
    private int[][] gameField = new int[SIDE][SIDE];
    private boolean isGameStopped = false;
    private int score = 0;
    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
        drawScene();
    }
    private void createGame(){
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();
    }
    private void drawScene(){
        for(int x = 0; x<SIDE; x++){
            for(int y = 0; y<SIDE; y++){
                setCellColoredNumber(x, y, gameField[y][x]);
            }
        }
    }
    private void createNewNumber(){
        int x = getRandomNumber(SIDE);
        int y = getRandomNumber(SIDE);
        if (gameField[y][x] == 0){
            int num = getRandomNumber(10);
            if (num == 9){
                gameField[y][x] = 4;
            } else {
                gameField[y][x] = 2;
            }
        } else createNewNumber();
        if (getMaxTileValue() == 2048){
            win();
        }

    }
    private Color getColorByValue(int value){
        switch (value){
            case 0:
                return Color.WHITE;
            case 2:
                return Color.PURPLE;
            case 4:
                return Color.VIOLET;
            case 8:
                return Color.BLUE;
            case 16:
                return Color.AQUA;
            case 32:
                return Color.GREENYELLOW;
            case 64:
                return Color.GREEN;
            case 128:
                return Color.ORANGE;
            case 256:
                return Color.ORANGERED;
            case 512:
                return Color.RED;
            case 1024:
                return Color.CORAL;
            case 2048:
                return Color.DARKVIOLET;
            default:
                return Color.NONE;
        }
    }

    private void setCellColoredNumber(int x, int y, int value){
        Color color = getColorByValue(value);
        String str;
        if (value > 0){
            str = "" +value;
        } else str = "";
        setCellValueEx(x, y, color, str);
    }

    private boolean compressRow(int[] row){
        int insertPosition = 0;
        boolean result = false;
        for (int x = 0; x < SIDE; x++) {
            if (row[x] > 0) {
                if (x != insertPosition) {
                    row[insertPosition] = row[x];
                    row[x] = 0;
                    result = true;
                }
                insertPosition++;
            }
        }
        return result;
    }

    private boolean mergeRow(int[] row){
        boolean result = false;
        for (int x = 0; x< row.length-1; x++){
            if (row[x] != 0 && row[x] == row[x+1]){
                row[x] = row[x]+row[x+1];
                row[x+1] = 0;
                result = true;
                score+=row[x];
                setScore(score);

            }
        }
        return result;
    }

    @Override
    public void onKeyPress(Key key) {
        if (isGameStopped) {
            if (key == key.SPACE){
                isGameStopped = false;
                score = 0;
                setScore(score);
                createGame();
                drawScene();
            } else return;
        }
        if (canUserMove() == false){
            gameOver();
            return;
        }
        if (key == key.LEFT){
            moveLeft();
        } else if (key == key.RIGHT){
            moveRight();
        } else if (key ==key.UP){
            moveUp();
        } else if (key == key.DOWN){
            moveDown();
        } else {
            return;
        }
        drawScene();
    }
    private void moveLeft(){
        boolean newNumberNeed = false;
        for (int[] row: gameField){
            boolean wasCompressed = compressRow(row);
            boolean wasMerged = mergeRow(row);
            if (wasMerged) {
                compressRow(row);
            }
            if (wasCompressed || wasMerged) {
                newNumberNeed= true;
            }
        }
        if (newNumberNeed) {
            createNewNumber();
        }

    }
    private void moveRight(){
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();

    }
    private void moveUp(){
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
    }
    private void moveDown(){
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }
    private void rotateClockwise(){
        int[][] newField = new int[SIDE][SIDE];
        for (int x =0 ; x<SIDE; x++){
            for (int y =0; y< SIDE; y++){
                newField[y][SIDE-1-x] = gameField[x][y];

            }
        }
        gameField=newField;
    }
    private int getMaxTileValue(){
        int max = gameField[0][0];
        for(int y=0; y< SIDE; y++){
            for (int x=0; x< SIDE; x++){
                if (max< gameField[y][x]){
                    max = gameField[y][x];
                }
            }
        }
        return max;
    }
    private void win(){
        showMessageDialog(Color.YELLOW, "YOU WIN", Color.BLACK, 80);
        isGameStopped= true;
    }
    private boolean canUserMove(){
        for (int y = 0; y< SIDE; y++){
            for (int x = 0; x<SIDE; x++){
                if (gameField[y][x] == 0){
                    return true;
                } else if(y < SIDE - 1 && gameField[y][x] == gameField[y + 1][x]){
                    return true;
                } else if ((x < SIDE - 1) && gameField[y][x] == gameField[y][x + 1]){
                    return true;
                }
            }
        }
        return false;
    }
    private void gameOver(){
        showMessageDialog(Color.RED, "Game Over", Color.BLACK, 80);
        isGameStopped = true;
    }
}
