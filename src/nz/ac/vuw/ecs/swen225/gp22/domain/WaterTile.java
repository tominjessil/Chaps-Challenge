package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Images;

/*
 * class for water tiles
 */
public class WaterTile extends Tile{

	public WaterTile(int xPos, int yPos){
		setPosition(xPos, yPos);
		setPassable(true); 
		setPushable(false);
		setImg(Images.Water);
	}
	
	public void onWalk(Chap c){
        c.getState().die();
    }
	
	public void reset(){}

	public String toString(){
		return "water";
	}
	
}
