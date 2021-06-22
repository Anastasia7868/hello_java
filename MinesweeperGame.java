package com.javarush.games.minesweeper;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private static final String MINE = "\uD83D\uDCA3";
    private static final String FLAG = "\uD83D\uDEA9";
    private int countFlags;
    private boolean isGameStopped;
    private int countClosedTiles = SIDE*SIDE;
    private int score;



    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }
    @Override
    public void onMouseLeftClick(int x, int y) {

        if (isGameStopped){
            restart();
            return;
        }
        openTile(x,y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x, y);
    }
    public void setScore(int score){
        this.score = score;
    }

    private void createGame() {
        //isGameStopped = false;
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.ORANGE);
                setCellValue(x, y, "");
            }
        }
        countFlags = countMinesOnField;
        countMineNeighbors();
    }
    private void countMineNeighbors(){
        for(int y=0; y< SIDE; y++){
            for(int x = 0; x< SIDE; x++){
                GameObject gameObject = gameField[y][x];
                if (!gameObject.isMine){
                    for (GameObject neiborhood: getNeighbors(gameObject)){
                        if (neiborhood.isMine){
                            gameObject.countMineNeighbors++;
                        }
                    }
                }
            }
        }
    }

    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }
    private void openTile(int x, int y) {
        GameObject gameObject = gameField[y][x];
        if (gameObject.isOpen || gameObject.isFlag || isGameStopped){
            return;
        }
        gameObject.isOpen = true;
        setCellColor(x, y, Color.GREEN);

        if (gameObject.isMine) {
            setCellValueEx(gameObject.x, gameObject.y, Color.RED, MINE);
            gameOver();
        } else {
            setCellNumber(x, y, gameObject.countMineNeighbors);
        }

        if(!gameObject.isMine && gameObject.countMineNeighbors ==0){
            setCellValue(x, y, "");
            for (GameObject neiborhood: getNeighbors(gameObject)){
                if (!neiborhood.isOpen){
                    openTile(neiborhood.x, neiborhood.y);
                }
            }
        } else if(!gameObject.isMine && gameObject.countMineNeighbors !=0){
            setCellNumber(x, y, gameObject.countMineNeighbors);
        }
        if (gameObject.isOpen){
            countClosedTiles--;
            if (countClosedTiles == countMinesOnField && !gameObject.isMine){
                win();
            }
        }
        if (gameObject.isOpen && !gameObject.isMine){
            score +=5;
            setScore(score);
        }


    }

    private void markTile(int x, int y){
        GameObject gameObject = gameField[y][x];
        if (gameObject.isOpen || (countFlags ==0 && !gameObject.isFlag) || isGameStopped == true){
            return;
        }
        if (gameObject.isFlag){
            countFlags++;
            gameObject.isFlag = false;
            setCellValue(x, y, "");
            setCellColor(x, y, Color.ORANGE);
        } else {
            countFlags--;
            gameObject.isFlag = true;
            setCellValue(x, y, FLAG);
            setCellColor(x, y, Color.YELLOW);
        }
    }
    private void gameOver(){
        isGameStopped = true;
        showMessageDialog(Color.RED, "Game Over", Color.BLACK, 80);
    }
    private void win(){
        isGameStopped = true;
        showMessageDialog(Color.GREEN, "Win", Color.BLACK, 80);
    }
    private void restart(){
        isGameStopped = false;
        countClosedTiles = SIDE*SIDE;
        score = 0;
        setScore(score);
        countMinesOnField = 0;
        createGame();
    }


}