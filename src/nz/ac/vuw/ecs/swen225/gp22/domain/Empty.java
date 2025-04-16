package nz.ac.vuw.ecs.swen225.gp22.domain;

import nz.ac.vuw.ecs.swen225.gp22.renderer.Images;

public class Empty extends SolidObject{

    public Empty(int xp, int yp){
        setPosition(xp, yp);
        setImg(Images.Floor);
        initialize();
    }
    
    public void initialize(){
        setImg(Images.Floor);
        setCollided(false);
    }
    
    public String toString(){
        return "Empty";
    }

    public void onCollision(Chap c, int x, int y){
        //do nothing
    }
}