package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.util.HashMap;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.gp22.app.GUI;



/*
 * Class for Chap
 */
public class Chap{
	private int chips;
	private int x, y, xPos, yPos; 
	private int lastXPos, lastYPos; 
	private Direction direction; 
	private Level level;
	
	public enum Direction{
		UP, DOWN, LEFT, RIGHT
	}
	
	private ChapState state; 
	private AliveState alive;
	private DeadState dead;
	private WinState victory;
	
	/*
	 * Chap Constructor
	 */
	public Chap(int xPos, int yPos, Level level){
		this.xPos = xPos;
		this.yPos = yPos;
		x = xPos * 24; //decided on 24 by carefull maths
		y = yPos * 24;
		direction = Direction.DOWN;
		alive = new AliveState(this);
		dead = new DeadState(this);
		victory = new WinState(this);
		state = alive;
		this.level = level;
	}
	
	/*
	 * Chaps X position on the board
	 */
	public int getXPos(){
		return xPos;
	}
	/*
	 * Chaps Y position on the board
	 */
	public int getYPos(){
		return yPos;
	}
	/*
	 * Chaps position in the 2D array
	 */
	public int getX(){
		return x;
	}
	/*
	 * Chaps position in the 2D array
	 */
	public int getY(){
		return y;
	}
	/*
	 * Set chaps position on the board
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
	/*
	 * Chaps last Xpos on the board
	 */
	public int getLastXPos(){
		return lastXPos;
	}
	/*
	 * Chaps last Ypos on the board
	 */
	public int getLastYPos(){
		return lastYPos;
	}
	
	public ChapState getState(){
		return state;
	}
	public AliveState getAliveState(){
		return alive;
	}
	public DeadState getDeadState(){
		return dead;
	}
	
	public WinState getWinState(){
		return victory;
	}
	public void setState(ChapState state){
		this.state = state;
	}
	/*
	 * Returns the ammount of Computer Chips Chap has picked up
	 */
	public int getChips(){
		return chips;
	}
	
	/*
	 * Chap collects a Computer Chip
	 */
	public void obtainChip(){
		//if(!(level.getObject(this.xPos, this.yPos) instanceof ComputerChip)){
		//	throw new IllegalStateException("There is no ComputerChip here: " + getYPos() + getXPos());
		//}
		//GUI.renderMazePanel.playChip();
		int uncollectedChips = level.getChipsRequired() - getChips(); 
		chips++;
		int uncollectedChips2 = level.getChipsRequired() - getChips();
		assert uncollectedChips2 == uncollectedChips - 1;
		GUI.playGameSound(1);

	}
	
	public void clearChips(){
		chips = 0;
	}
	
	/*
	 * Collection of methods for when Chap collects a key
	 */
	public void getRedKey(int x, int y){
		if(!(level.getObject(x, y) instanceof Key)){
			throw new IllegalStateException("There is no Key here: " + getYPos() + getXPos());
		}
		GUI.playGameSound(2);
		int count = level.getKey("red");
		Map<String, Integer> temp = new HashMap<String, Integer>(level.getInv());
		temp.put("red", count + 1);
		level.setInv(temp);
		int count2 = level.getKey("red");
		assert count2 == count + 1;
	}
	public void getBlueKey(int x, int y){
		if(!(level.getObject(x, y) instanceof Key)){
			throw new IllegalStateException("There is no Key here: " + getYPos() + getXPos());
		}
		GUI.playGameSound(2);
		int count = level.getKey("blue");
		Map<String, Integer> temp = level.getInv();
		temp.put("blue", count + 1);
		level.setInv(temp);
		int count2 = level.getKey("blue");
		assert count2 == count + 1;
	}
	public void getYellowKey(int x, int y){
		if(!(level.getObject(x, y) instanceof Key)){
			throw new IllegalStateException("There is no Key here: " + getYPos() + getXPos());
		}
		GUI.playGameSound(2);
		int count = level.getKey("yellow");
		Map<String, Integer> temp = level.getInv();
		temp.put("yellow", count + 1);
		level.setInv(temp);
		int count2 = level.getKey("yellow");
		assert count2 == count + 1;
	}
	public void getGreenKey(int x, int y){
		if(!(level.getObject(x, y) instanceof Key)){
			throw new IllegalStateException("There is no Key here: " + getYPos() + getXPos());
		}
		GUI.playGameSound(2);
		int count = level.getKey("green");
		Map<String, Integer> temp = level.getInv();
		temp.put("green", count + 1);
		level.setInv(temp);
		int count2 = level.getKey("green");
		assert count2 == count + 1;
	}
	
	/*
	 * Collection of methods for when Chap uses a key
	 */
	public void useRedKey(){
		int count = level.getKey("red");
		if(count<=0){
			throw new IllegalStateException("Chap has no Red Key");
		} else{
			level.putKey("red", count-1);
			int count2 = level.getKey("red");
			assert count2 == count - 1;
		}
	}
	public void useBlueKey(){
		int count = level.getKey("blue");
		if(count<=0){
			throw new IllegalStateException("Chap has no Blue Key");
		} else{
			level.putKey("blue", count-1);
			int count2 = level.getKey("blue");
			assert count2 == count - 1;
		}
	}
	public void useYellowKey(){
		int count = level.getKey("yellow");
		if(count<=0){
			throw new IllegalStateException("Chap has no Yellow Key");
		} else{
			level.putKey("yellow", count-1);
			int count2 = level.getKey("yellow");
			assert count2 == count - 1;
		}
	}
	public void useGreenKey(){
		int count = level.getKey("green");
		if(count<=0){
			throw new IllegalStateException("Chap has no Green Key");
		} else{
			level.putKey("green", count-1);
			int count2 = level.getKey("green");
			assert count2 == count - 1;
		}
	}
	
	/*
	 * Collection of methods that handle Chap's movement
	 */
	public void move(int dx, int dy){
		if(!level.getTile(xPos+dx, yPos+dy).isPassable()) {
			throw new IllegalArgumentException("Chap cannot phase through walls");
		}
		if(level.hasObject(xPos + dx, yPos + dy)){
			CollisionCheck(xPos + dx, yPos + dy);
		}
		lastYPos = yPos;
		xPos += dx;
		yPos += dy;
		x += (dx * 24);
		y += (dy * 24);
		level.getTile(xPos, yPos).onWalk(this);
	}
	
	public void moveUp(){
		if(state == alive){
			move(0, -1);
			if(direction != Direction.UP)
				direction = Direction.UP;
		}
	}
	public void moveDown(){
		if(state == alive){
			move(0, 1);
			if(direction != Direction.DOWN)
				direction = Direction.DOWN;
		}
		
	}
	public void moveLeft(){
		if(state == alive){
			move(-1, 0);
			if(direction != Direction.LEFT)
				direction = Direction.LEFT;
		}
	}
	public void moveRight(){
		if(state == alive){
			move(1, 0);
			if(direction != Direction.RIGHT)
				direction = Direction.RIGHT;
		}
	}

	/*
	 * Method to handle SolidObject collisions
	 */
	public void CollisionCheck(int x, int y){
		level.getObject(x, y).onCollision(this, x, y);
	}

	public Level getLevel(){
		return this.level;
	}

	/*
	 * return string for chap
	 */
	public String toString(){
		return "chap";
	}
}
