package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Images;

/*
 * Class for keys
 */
public class Key extends SolidObject{
	private Images colour;
	public Key(int xp, int yp, String stringImage){
		setPosition(xp, yp);
		this.colour = getColourImage(stringImage);
		setImg(colour);
		initialize();
	}
	public Images getColourImage(String imageColour){
		switch(imageColour){
			case "greenKey":
				return Images.GreenKey;
			case "blueKey":
				return Images.BlueKey;
			case "yellowKey":
				return Images.YellowKey;
			case "redKey":
				return Images.RedKey;
			default:
				return Images.BlueKey;
		}
	}

	/*
	 * Handles Chap collsion with keys
	 */
	public void onCollision(Chap c, int x, int y){
		if(!getCollided()){
			if(this.colour == Images.BlueKey) {
				c.getBlueKey(x,y);
				setCollided(true);
				c.getLevel().removeObject(x, y);
			}
			else if(this.colour == Images.RedKey) {
				c.getRedKey(x,y);
				setCollided(true);
				c.getLevel().removeObject(x, y);
			}
			else if(this.colour == Images.GreenKey) {
				c.getGreenKey(x,y);
				setCollided(true);
				c.getLevel().removeObject(x, y);
			}
			else if(this.colour == Images.YellowKey) {
				c.getYellowKey(x,y);
				setCollided(true);
				c.getLevel().removeObject(x, y);
			}
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

