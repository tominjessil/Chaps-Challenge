package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Images;

/*
 * abstract class for solid objects (keys, doors, computerchips, exitlock) 
 */
public abstract class SolidObject{
	private int x, y, xPos, yPos;
	private Images img;
	private boolean collided; // Has the object collided with Chip or been altered in some way?
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getXPos(){
		return xPos;
	}
	public int getYPos(){
		return yPos;
	}
	
	public boolean getCollided(){
		return collided;
	}
	public void setCollided(boolean collided){
		this.collided = collided;
	}
	
	public Images getImg(){
		return img;
	}
	
	public void setPosition(int xPos, int yPos){
		this.xPos = xPos;
		this.yPos = yPos;
		this.x = xPos * 32;
		this.y = yPos * 32;
	}
	public void move(int dx, int dy){
		xPos += dx;
		yPos += dy;
		x = xPos * 32;
		y = yPos * 32;
	}
	
	public void moveUp(){
		move(0, -1);
	}
	public void moveDown(){
		move(0, 1);
	}
	public void moveLeft(){
		move(1, 0);
	}
	public void moveRight(){
		move(-1, 0);
	}
	
	public void setImg(Images img){
		this.img = img;
	}
	
	public abstract void onCollision(Chap c, int x, int y);
	
	public abstract void initialize();

	public abstract String toString();
	
	
}

