package com.javarush.games.snake;
import com.javarush.engine.cell.*;


import java.util.ArrayList;
import java.util.List;
public class Snake {
    private List<GameObject> snakeParts = new ArrayList<GameObject>();
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private Direction direction = Direction.LEFT;
    public boolean isAlive = true;

    public Snake(int x, int y){
        GameObject gameObject1 = new GameObject(x, y);
        GameObject gameObject2 = new GameObject(x+1, y);
        GameObject gameObject3 = new GameObject(x+2, y);
        snakeParts.add(gameObject1);
        snakeParts.add(gameObject2);
        snakeParts.add(gameObject3);
    }
    public void draw(Game snake){
        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject part = snakeParts.get(i);
            String sign = (i != 0) ? BODY_SIGN : HEAD_SIGN;
            if(isAlive){
                snake.setCellValueEx(part.x, part.y, Color.NONE, sign, Color.BLACK, 75);
            } else snake.setCellValueEx(part.x, part.y, Color.NONE, sign, Color.RED, 75);

        }
    }
    public void setDirection(Direction direction){
        if ((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) && snakeParts.get(0).x == snakeParts.get(1).x){
            return;
        } else if((this.direction == Direction.UP || this.direction == Direction.DOWN) && snakeParts.get(0).y == snakeParts.get(1).y){
            return;
        }
        if(direction == Direction.LEFT && this.direction == Direction.RIGHT){
            return;
        } else if(direction == Direction.RIGHT && this.direction == Direction.LEFT){
            return;
        } else if(direction == Direction.DOWN && this.direction == Direction.UP){
            return;
        } else if(direction == Direction.UP && this.direction == Direction.DOWN){
            return;
        }
        this.direction = direction;
    }
    public void move(Apple apple){
       GameObject newHead = createNewHead();
       if (newHead.x<0 || newHead.x >= SnakeGame.WIDTH || newHead.y<0 || newHead.y>= SnakeGame.HEIGHT){
           isAlive= false;
           return;
       }
       if(checkCollision(newHead)){
           isAlive = false;
           return;
       }
       snakeParts.add(0, newHead);

       if (newHead.x==apple.x && newHead.y == apple.y){
           apple.isAlive = false;
       } else removeTail();
    }
    public GameObject createNewHead(){
        GameObject oldHead = snakeParts.get(0);
        if(direction == Direction.LEFT){
             return new GameObject(oldHead.x-1, oldHead.y);
        } else if(direction == Direction.RIGHT){
            return new GameObject(oldHead.x+1, oldHead.y );
        } else if (direction == Direction.DOWN){
            return new GameObject(oldHead.x, oldHead.y+1);
        } else return new GameObject(oldHead.x, oldHead.y-1);


    }
    public void removeTail(){
    int i = snakeParts.size();
    snakeParts.remove(i-1);
    }
    public boolean checkCollision(GameObject snake){
        for (GameObject elem:snakeParts){
            if (elem.x == snake.x && elem.y== snake.y){
                return true;
            }
        }
        return false;
    }
    public int getLength(){
        return snakeParts.size();
    }
}
