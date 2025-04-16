package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Images;

/*
 * Class for the ExitLock locked until chap has enough Chips
 */
public class ExitLock extends SolidObject{
	
	public ExitLock(int xp, int yp){
		setPosition(xp, yp);
		setImg(Images.ExitLock);
		initialize();
	}
	
	/*
	 * Handles Chap colliding with ExitLock
	 */
	public void onCollision(Chap c, int x, int y){
		if(getCollided()){}
		else if(c.getChips() >= c.getLevel().getChipsRequired()){
			setCollided(true);
			c.getLevel().removeObject(x, y);
		}
		else{
			throw new IllegalArgumentException("Chap does not have enough chips");
		}
	}
	
	public void initialize(){
		setImg(Images.ExitLock);
		setCollided(false);
	}

	public String toString(){
		return "exitLock";
	}
}
