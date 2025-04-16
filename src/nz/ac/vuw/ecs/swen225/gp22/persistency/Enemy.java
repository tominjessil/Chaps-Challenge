package nz.ac.vuw.ecs.swen225.gp22.persistency;

import nz.ac.vuw.ecs.swen225.gp22.domain.Chap;
import nz.ac.vuw.ecs.swen225.gp22.domain.SolidObject;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Images;

/*
 * Class for enemy actor
 */
public class Enemy extends SolidObject {
    private int x, y, xPos, yPos; 
	private int lastXPos, lastYPos; 
	private int targetY1, targetY2, currentTarget;
	private Direction direction; 

    public enum Direction{
		UP, DOWN, LEFT, RIGHT
	}

    public Enemy(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		this.targetY1 = yPos + 1;
		this.targetY2 = yPos - 1;
		this.currentTarget = 1;
		setImg(Images.Enemy);
		x = xPos * 24; //decided on 24 by carefull maths
		y = yPos * 24;
		direction = Direction.DOWN;
	}

    	/*
	 * Enemy X position on the board
	 */
	public int getXPos(){
		return xPos;
	}
	/*
	 * Enemy Y position on the board
	 */
	public int getYPos(){
		return yPos;
	}
	/*
	 * Enemy position in the 2D array
	 */
	public int getX(){
		return x;
	}
	/*
	 * Enemy position in the 2D array
	 */
	public int getY(){
		return y;
	}
	/*
	 * Set enemy position on the board
	 */
	public void setPosition(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		x = xPos * 24;
		y = yPos * 24;
	}
	public Direction getDirection(){
		return direction;
	}
	public void setDirection(Direction dir){
		direction = dir;
	}

	/**
	 * returns enemy last Xpos
	 * @return
	 */
	public int getLastXPos(){
		return lastXPos;
	}
	
	 
	/*
	 * Enemy last Ypos on the board
	 * @return
	 */
	public int getLastYPos(){
		return lastYPos;
	}

    /*
     * Collection of movement methods for Enemy
     */
    public void move(int dx, int dy){
		lastYPos = yPos;
		xPos += dx;
		yPos += dy;
		x += (dx * 24);
		y += (dy * 24);
		if(currentTarget == 1 && yPos == targetY1){
			currentTarget = 2;
		}
		if(currentTarget == 2 && yPos == targetY2){
			currentTarget = 1;
		}
	}
	
	/**
	 * Moves enemy up
	 */
	public void moveUp(){
			move(0, -1);
			if(direction != Direction.UP)
				direction = Direction.UP;
	}
	public void moveDown(){
			move(0, 1);
			if(direction != Direction.DOWN)
				direction = Direction.DOWN;
	}
	public void moveLeft(){
			move(-1, 0);
			if(direction != Direction.LEFT)
				direction = Direction.LEFT;
	}
	public void moveRight(){
			move(1, 0);
			if(direction != Direction.RIGHT)
				direction = Direction.RIGHT;
		}

	/*
	 * Used to update ememy in GUI updateGame()
	 */
	public void updateEnemy(){
		if(currentTarget == 1){
			moveUp();
		}
		if(currentTarget == 2){
			moveDown();
		}
	}

    /*
	 * return string for enemy
	 */
	public String toString(){
		return "enemy";
	}

	@Override
	public void onCollision(Chap c, int x, int y) {
		c.getState().die();
	}

	@Override
	public void initialize() {
		setImg(Images.Enemy);
		setCollided(false);
		
	}
}
