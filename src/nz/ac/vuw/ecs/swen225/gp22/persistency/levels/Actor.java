package nz.ac.vuw.ecs.swen225.gp22.persistency.levels;

import java.util.Random;
import nz.ac.vuw.ecs.swen225.gp22.domain.Chap.Direction;

public class Actor {
    int xPos = 0;
    int yPos = 0;
    
    private Direction direction; 
	
	public enum Direction{
		UP, DOWN, LEFT, RIGHT
	}

    public Actor(int x, int y){
        xPos = x;
        yPos = y;
    }

    public int getX(){
        return this.xPos;
    }

    public int getY(){
        return this.yPos;
    }

    public Direction getDirection(int val){
        Direction toReturn = null;
        switch(val){
            case 0:
                toReturn = Direction.UP;
            case 1:
                toReturn = Direction.DOWN;
            case 2:
                toReturn = Direction.LEFT;
            case 3:
                toReturn = Direction.RIGHT;
        }
        return toReturn;
    }

    public boolean checkCollison(int nextX, int nextY){
        return false;
    }

    public void run(){
        Random random = new Random();
        int dirInt = random.nextInt(4);
        Direction nextDirect = getDirection(dirInt);
        if(checkCollison(dirInt, dirInt)){

        }
    }



}
