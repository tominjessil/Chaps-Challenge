package nz.ac.vuw.ecs.swen225.gp22.renderer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.stream.Stream;
import java.awt.Graphics2D;
import java.awt.Graphics;

import nz.ac.vuw.ecs.swen225.gp22.app.GUI;
import nz.ac.vuw.ecs.swen225.gp22.domain.Level;
import nz.ac.vuw.ecs.swen225.gp22.domain.SolidObject;
import nz.ac.vuw.ecs.swen225.gp22.domain.Tile;

/**
 * This class is used to render the maze
 * 
 * @author livapurane
 *
 */
public class RenderMazePanel extends JPanel{
    private Tile[][] tiles; //2D array of tiles
    private SolidObject[][] tileObjects; //2D array of tile objects
    private Level level; //level object

    public static int tileSize = 72; //size of each tile
    private int screenHeight = 520; //height of the screen for the mazepanel
    private int screenWidth = 520; //width of the screen for the mazepanel

    private int worldX; //x coordinate of the world
    private int worldY; //y coordinate of the world

    private int screenX = screenWidth/2 - (tileSize/2); //x coordinate of the screen
    private int screenY = screenHeight/2 - (tileSize/2); //y coordinate of the screen

    private int maxWorldCol; //maximum number of columns in the maze
    private int maxWorldRow; //maximum number of rows in the maze

    private final int FIRSTINVKEYX= 336; //x coordinate of the first inventory key
    private final int INVKEYWIDTH = 59;
    private final int INVKEYHEIGHT = 58;

    private JLabel chipsLeft = new JLabel("");
    private JLabel chipTitle = new JLabel("Chips Left:");
    private JLabel timeTitle = new JLabel("Time Left:");
    private int chipsLeftTextX; 
    private Dimension size = new Dimension(200,200); 

    private JLabel timerText = new JLabel();

    Sound sound = Sound.getInstance();

    /**
     * Constructor for the RenderMazePanel
     * 
     * @param level the level stores the tiles and tileObjects used for the render
     */
    public RenderMazePanel(Level level){
        this.level = level;
    }

    /**
     * Preload all the images used in the game
     */
    public void loadAllImages(){
        Stream.of(Images.values()).forEach(img -> img.loadImg(img.getName())); //Stream through all of the images and load them
    }

    /**
     * Paint the maze to the screen using the graphics object and the tile and tileObject arrays
     * 
     * @param g the graphics object used to draw the maze
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g); 

        //Cast the graphics object to a Graphics2d object
        Graphics2D g2d = (Graphics2D) g; 

        //Get the tiles and tileObjects from the level
        tiles = level.getTiles(); 
        tileObjects = level.getObjects(); 
        
        //worldX and worldY are dependent on the chaps position multiplied by 3 to get the tile size
        worldX = GUI.chap.getX() *3;
        worldY = GUI.chap.getY() *3;

        //Set the maxWorldCol and maxWorldRow to the size of the tile array
        maxWorldCol = tiles.length;
        maxWorldRow = tiles[0].length; 
        for(int worldRow = 0; worldRow < maxWorldRow; worldRow++){
            for(int worldCol = 0; worldCol < maxWorldCol; worldCol++){

                //Calculate the screen position of the tile
                Tile tile = tiles[worldCol][worldRow];
                SolidObject object = tileObjects[worldCol][worldRow];
                int wX = worldCol * tileSize;
                int wY = worldRow * tileSize; 
                int sX = wX - worldX + screenX;
                int sY = wY - worldY + screenY; 

                //Draw only if in view
                if(wX + (tileSize*2) > worldX - screenX && 
                    wX - (tileSize) < worldX + screenX && 
                    wY + (tileSize*2)> worldY - screenX && 
                    wY - (tileSize*1)< worldY + screenY){
                    //Draw the tile
                    g2d.drawImage(tile.getImg().getImg(), sX, sY, tileSize, tileSize, null);
                    //Draw the tileObject if it exists
                    if(object != null) g2d.drawImage(object.getImg().getImg(), sX, sY, tileSize, tileSize, null);
                }
            }
        }
        //Draw the chap in the center of the screen
        g2d.drawImage(Images.Chap.getImg(), screenX, screenY, tileSize, tileSize, null);
        //Draw the sidebar
        drawSidebarPanel(g2d);
    }

    /**
     * Draw the sidebar panel to the screen
     * 
     * @param g the graphics object used to draw the sidebar panel
     */
    public void drawSidebarPanel(java.awt.Graphics g){
        g.drawImage(Images.SideBar.getImg(), screenWidth-10, 0, 290, 550,null);
        
        //Draw the Keys in the sidebar if they are in the inventory
        if(level.hasBlueKey()) g.drawImage(Images.BlueKey.getImg(), screenWidth+42, FIRSTINVKEYX, INVKEYWIDTH, INVKEYHEIGHT, null);
        if(level.hasRedKey()) g.drawImage(Images.RedKey.getImg(), screenWidth+109, FIRSTINVKEYX, INVKEYWIDTH, INVKEYHEIGHT, null);
        if(level.hasGreenKey()) g.drawImage(Images.GreenKey.getImg(), screenWidth+174, FIRSTINVKEYX, INVKEYWIDTH, INVKEYHEIGHT, null);
        if(level.hasYellowKey()) g.drawImage(Images.YellowKey.getImg(), screenWidth+41, FIRSTINVKEYX+63, INVKEYWIDTH, INVKEYHEIGHT, null);
        
        //Draw the chips left
        loadChipsLeft();
    }

    /**
     * Load the chips left text to the sidebar panel
     */
    private void loadChipsLeft(){      
        setLayout(null);          
        int chips = level.getChipsRequired() - GUI.chap.getChips(); 
        chipsLeft.setText("" + chips); 
        
        //Set the chips left text to the correct position
        if(chips == 10) chipsLeftTextX = 80;
        else chipsLeftTextX = 110; 

        //Put all the labels in an array
        JLabel[] textPanels = {timerText, chipsLeft, chipTitle, timeTitle};

        timerText.setText(GUI.timeDString);
        timerText.setBounds(520 + 40, 20, size.width, size.height);
        timerText.setFont(new Font("Verdana", 1, 58)); 

        chipsLeft.setBounds(screenWidth + chipsLeftTextX, 120, size.width, size.height);
        chipsLeft.setFont(new Font("Verdana", 1, 70)); 
        
        chipTitle.setBounds(screenWidth + 75, 80, size.width, size.height);
        chipTitle.setFont(new Font("Verdana", 1, 20)); 

        timeTitle.setBounds(screenWidth + 75, -15, size.width, size.height);
        timeTitle.setFont(new Font("Verdana", 1, 20)); 

        for(JLabel l : textPanels){
            l.setForeground(new Color(196, 180, 133));
            add(l);
        }
    }

    /*Play the music
    * 
    */
    public void playMusic(){
        sound.setFile(0);
        sound.play();
        sound.loop();
    }

    /*Stop playing the music
    *    
    */
    public void stopMusic(){
        sound.stop();
    }

    /* Play the sound effect for the give int
    * 
    * @param i the int of the sound effect to play
    */
    public void playSE(int i){
        sound.setSoundEF(i);
        sound.playSoundE();
    }
}
