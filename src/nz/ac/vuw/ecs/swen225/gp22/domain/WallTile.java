package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Images;

/*
 * Class for wall tile
 */
public class WallTile extends Tile{

	public WallTile(int xPos, int yPos){
		setPosition(xPos, yPos);
		setPassable(false); 
		setPushable(false);
		setImg(Images.Wall);
	}
	
	public void onWalk(Chap c){}
	
	public void reset(){}

	public String toString(){
		return "wall";
	}
	
}

