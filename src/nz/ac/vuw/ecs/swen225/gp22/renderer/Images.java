package nz.ac.vuw.ecs.swen225.gp22.renderer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class is used to store all the images used in the game
 * 
 * @author livapurane
 *
 */
public enum Images {
    Wall("wall"),
    Floor("floor"),
    Exit("exit"),                     
    ExitLock("exitLock"),            

    ComputerChip("computerChip"),

    Chap("chap"),   
    
    //All the coloured doors
    RedDoor("redDoor"),
    GreenDoor("greenDoor"),
    BlueDoor("blueDoor"),
    YellowDoor("yellowDoor"),

    //All the coloured keys
    RedKey("redKey"),
    GreenKey("greenKey"),
    BlueKey("blueKey"),
    YellowKey("yellowKey"),

    Water("water"),
    Enemy("enemy"),

    InfoTile("infoField"),

    SideBar("sidebar"),
    ;
    
    private BufferedImage img;
    private String name;

    /**
     * Constructor for the Images enum
     * 
     * @param name the name of the image
     */
    private Images(String name){
        this.name = name; 
        this.img = loadImg(name);
    }

    /**
     * Return the BufferedImage of the enum value 
     * 
     * @return the BufferedImage of the enum value
     */
    public BufferedImage getImg(){
        return img;
    }

    /**
     * Return the name of the image
     * 
     * @return the name of the image
     */
    public String getName(){
        return name;
    }


    /**
     * load the image from the disk
     * 
     * @param path the path to where the image is stored
     * @return BufferedImage of the object 
     */
    public BufferedImage loadImg(String path){
        try {
            BufferedImage img = ImageIO.read(new File("src/nz/ac/vuw/ecs/swen225/gp22/renderer/GameImages/" + path + ".png"));
            return img;
        } catch (IOException e) { 
            throw new RuntimeException(e);}
    }
}
