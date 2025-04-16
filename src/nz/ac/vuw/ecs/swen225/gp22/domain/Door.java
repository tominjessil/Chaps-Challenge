package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.app.GUI;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Images;

/*
 * Class for Doors
 */
public class Door extends SolidObject{

	private Images colour;
	
	public Door(int xp, int yp, String stringImage){
		setPosition(xp, yp);
		this.colour = getColourImage(stringImage);
		setImg(colour);
		initialize();
	}

	public Images getColourImage(String imageColour){
		switch(imageColour){
			case "greenDoor":
				return Images.GreenDoor;
			case "blueDoor":
				return Images.BlueDoor;
			case "yellowDoor":
				return Images.YellowDoor;
			case "redDoor":
				return Images.RedDoor;
			default:
				return Images.RedDoor;
		}
	}
	
	/*
	 * Handles Chip colliding with doors
	 */
	public void onCollision(Chap c, int x, int y){
		if(getCollided()){}
		else if(this.colour == Images.BlueDoor && c.getLevel().hasBlueKey()){
				c.useBlueKey();
				setCollided(true);
				c.getLevel().removeObject(x, y);
				GUI.playGameSound(4);
		}
		else if(this.colour == Images.RedDoor && c.getLevel().hasRedKey()){
				c.useRedKey();
				setCollided(true);
				c.getLevel().removeObject(x, y);
				GUI.playGameSound(4);
		}
		else if(this.colour == Images.GreenDoor && c.getLevel().hasGreenKey()){
				c.useGreenKey();
				setCollided(true);
				c.getLevel().removeObject(x, y);
				GUI.playGameSound(4);
		}
		else if(this.colour == Images.YellowDoor && c.getLevel().hasYellowKey()){
				c.useYellowKey();
				setCollided(true);
				c.getLevel().removeObject(x, y);
				GUI.playGameSound(4);
		}
		else{
			throw new IllegalArgumentException("Chap does not have the key");
		}
	}
	
	public void initialize(){
		setImg(this.colour);
		setCollided(false);
	}

	public String toString(){
		return this.colour.getName();
	}
}
