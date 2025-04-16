package nz.ac.vuw.ecs.swen225.gp22.domain;

import java.awt.Point;
import java.util.Map;

/*
 * Class for level object
 */
public class Level{
	private Tile[][] tiles;
	private SolidObject[][] objects;
	private Map<String, Integer> inventory;
	private Point startingPosition;
	private Point chapPosition;
	private int chipsRequired;
	private int timer;
	
	/*
	 * Set of level constructors
	 */
	public Level(int xDimension, int yDimension, int startX, int startY, int chipsRequired){
		tiles = new Tile[xDimension][yDimension];
		objects = new SolidObject[xDimension][yDimension];
		startingPosition = new Point(startX, startY);
		this.chipsRequired = chipsRequired;
	}
	
	public Level(int xDimension, int yDimension, int chipsRequired){
		tiles = new Tile[xDimension][yDimension];
		objects = new SolidObject[xDimension][yDimension];
		startingPosition = new Point(0, 0);
		chapPosition = new Point((int)(startingPosition.getX()), (int)(startingPosition.getY()));
		this.chipsRequired = chipsRequired;
	}
	
	public Level(int chipsRequired){
		tiles = new Tile[25][25];
		objects = new SolidObject[25][25];
		startingPosition = new Point(0, 0);
		chapPosition = new Point((int)(startingPosition.getX()), (int)(startingPosition.getY()));
		this.chipsRequired = chipsRequired;
	}
	
	public Level(){
		tiles = new Tile[25][25];
		objects = new SolidObject[25][25];
		startingPosition = new Point(0, 0);
		chapPosition = new Point((int)(startingPosition.getX()), (int)(startingPosition.getY()));
		chipsRequired = 0;
	}
	
	/*
	 * Set of setters and getters for levels
	 */
	public Tile[][] getTiles(){
		return tiles;
	}
	
	public SolidObject[][] getObjects(){
		return objects;
	}
	
	public Tile getTile(int x, int y){
		return this.tiles[x][y];
	}
	
	public Tile getTile(Point p){
		return tiles[(int)p.getX()][(int)p.getY()];
	}
	
	public SolidObject getObject(int x, int y){
		return objects[x][y];
	}

	public Boolean hasObject(int x, int y){
		return objects[x][y] != null;
	}
		
	public void setTile(int x, int y, Tile tile){
		tiles[x][y] = tile;
	}
	
	public void setObject(int x, int y, SolidObject object){
		objects[x][y] = object;
	}
	
	public void setObject(int x, int y, Door object){
		objects[x][y] = object;
	}
	
	public void setObject(int x, int y, Key object){
		objects[x][y] = object;
	}

	public void removeObject(int x, int y){
		objects[x][y] = null;
	}
	
	public Point getStartingPosition(){
		return startingPosition;
	}
	
	public int getStartingX(){
		return (int)(startingPosition.getX());
	}
	public int getStartingY(){
		return (int)(startingPosition.getY());
	}
	
	public void setStartingPosition(int x, int y){
		startingPosition.setLocation(x, y);
	}
	
	public int getXMax(){
		return tiles.length - 1;
	}
	public int getYMax(){
		return tiles[0].length - 1;
	}
	public Point getChapPosition(){
		return chapPosition;
	}
	
	public int getChipsRequired(){
		return chipsRequired;
	}
	public void setChipsRequired(int chipsRequired){
		this.chipsRequired = chipsRequired;
	}

	public int getTimer(){
		return this.timer;
	}

	public void setTimer(int timer){
		this.timer = timer;
	}

	/*
	 * Set of methods for handling inventory
	 */
	public void setInv(Map<String, Integer> inv){
		this.inventory = inv;
	}

	public Map<String, Integer> getInv(){
		return this.inventory;
	}

	public int getKey(String key){
		return this.inventory.get(key);
	}

	public void putKey(String key, int count){
		inventory.put(key, count);
	}
	
	public boolean hasRedKey(){
		return inventory.get("red") > 0;
	}
	public boolean hasBlueKey(){
		return inventory.get("blue") > 0;
	}
	public boolean hasYellowKey(){
		return inventory.get("yellow") > 0;
	}
	public boolean hasGreenKey(){
		return inventory.get("green") > 0;
	}

	public void loseKeys(){
		inventory.forEach((k, v) -> inventory.put(k,0));
	}
	
	public void reset(){
		for(int i = 0; i < objects.length; i++){
			for(int j = 0; j < objects[i].length; j++){
				if(objects[i][j] != null){
					objects[i][j].initialize();
				}
				tiles[i][j].reset();
			}
		}
	}
	
}
