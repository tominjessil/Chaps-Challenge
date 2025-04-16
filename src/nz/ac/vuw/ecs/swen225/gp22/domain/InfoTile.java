package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.app.GUI;
import nz.ac.vuw.ecs.swen225.gp22.app.Main;
import nz.ac.vuw.ecs.swen225.gp22.renderer.Images;

/*
 * Class for Info tiles
 */
public class InfoTile extends Tile{
	private String info;

	public InfoTile(int xp, int yp, String info){
		setPosition(xp, yp);
		setImg(Images.InfoTile);
		setPassable(true);
		setPushable(false);
		setInfo(info);
	}
	
	public void onWalk(Chap c){
		//display info
		//if(c.getX() != this.getX() && c.getY() != this.getY()){
		//GUI.popupTile();
		//}
	}

	public void setInfo(String info){
		this.info = info;
	}

	public String getInfo(){
		return this.info;
	}
	
	public void reset(){}

	public String toString(){
		return "infoField";
	}
	
}
