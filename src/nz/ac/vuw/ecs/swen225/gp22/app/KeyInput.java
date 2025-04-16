package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
* This class is used to handle the key inputs from the user.
* @author Ecco Competente
*/
public class KeyInput implements KeyListener{
    
    public int up, down, left, right, pause, escape, replaying;
    
    GUI guiPanel;
    
    public KeyInput(GUI guiPanel) {
        this.guiPanel = guiPanel;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(guiPanel.gameState == guiPanel.menuState){ //if the game is in the menu state
            keyStartState(keyCode); //call the keyStartState method
        }else if(guiPanel.gameState == guiPanel.playState && guiPanel.isPaused == false){ //if the game is in the play state and is not paused
            keyPlayState(keyCode);
        }else if(guiPanel.gameState == guiPanel.pauseState && guiPanel.isPaused ==true){ //if the game is in the pause state and is paused
            keyResumeState(keyCode);
        }else if(guiPanel.gameState == guiPanel.gameOverState){ //if the game is in the game over state
            keyGameOverState(keyCode);
        }else if (guiPanel.gameLevel == guiPanel.replay && guiPanel.gameState == guiPanel.pauseState){ //if the game is in the replay state
            keyReplayState(keyCode);
            // keyResumeRepState(keyCode);
        }else if(guiPanel.gameState == guiPanel.winState){ //if the game is in the win state
            keyWinState(keyCode);
        }else if(guiPanel.gameLevel == guiPanel.splashPage && guiPanel.gameState == guiPanel.pauseState){
            keyStart2(keyCode);
        }
    }
    
    /**
     * This method is used to handle the key inputs from the user when the game is in the Pause State.
     * @param keyCode - the key code of the key pressed by the user.
     */
    public void keyResumeState(int keyCode){
        if(keyCode == KeyEvent.VK_ESCAPE) {
            System.out.println("RESUMED");
            GUI.renderMazePanel.playMusic();
            guiPanel.gameState = guiPanel.playState;
            guiPanel.isPaused = false;
            guiPanel.timer.start();
            guiPanel.pauseButton.setText("Pause");
        }
    }

    /**
     * This method is used to handle the key inputs when the game is in the MenuState.
     * @param keyCode - the key code of the key pressed by the user.
     */
    public void keyStartState(int keyCode){
        if(keyCode == KeyEvent.VK_ENTER){
            System.out.println("ENTER GAME");
            guiPanel.gameState = guiPanel.playState;
            guiPanel.gameLevel = guiPanel.level1;
            guiPanel.setUpLevel();
            guiPanel.timer.start();
        }
    }

    /**
     * This method is used to handle the key inputs from the user when the game is in Game Over state.
     * @param keyCode - the key code of the key pressed by the user.
     */
    public void keyGameOverState(int keyCode){
        if(keyCode == KeyEvent.VK_ENTER){
            System.out.println("RETRY Level");
            guiPanel.gameState = guiPanel.playState;
            if(guiPanel.gameLevel == guiPanel.level1) guiPanel.gameLevel = guiPanel.level1;
            else if(guiPanel.gameLevel == guiPanel.level2) guiPanel.gameLevel = guiPanel.level2;
            guiPanel.setUpLevel();
        }
        else if(keyCode == KeyEvent.VK_1){
            System.out.println("RETRY Level 1");
            guiPanel.gameState = guiPanel.playState;
            guiPanel.gameLevel = guiPanel.level1;
            guiPanel.setUpLevel();
        }
        else if(keyCode == KeyEvent.VK_2){
            System.out.println("RETRY Level 2");
            guiPanel.gameState = guiPanel.playState;
            guiPanel.gameLevel = guiPanel.level2;
            guiPanel.setUpLevel();
        }
        else if(keyCode == KeyEvent.VK_ESCAPE){
            System.out.println("EXIT GAME");
            System.exit(0);
        }
        else if(keyCode == KeyEvent.VK_M){
            System.out.println("RETURN TO MENU");
            guiPanel.gameState = guiPanel.menuState;
            guiPanel.setUpMenu();
        }
    }
    
    /**
     * This method is used to handle the key inputs from the user when the game is in the Win State
     * @param keyCode - the key code of the key pressed by the user.
     */
    public void keyWinState(int keyCode){
        if(keyCode == KeyEvent.VK_ENTER){
            System.out.println("WIN");
            guiPanel.gameState = guiPanel.playState;
            if(guiPanel.gameLevel == guiPanel.level1) guiPanel.gameLevel = guiPanel.level2;
            guiPanel.setUpLevel();
        }
        else if(keyCode == KeyEvent.VK_1){
            System.out.println("RETRY Level 1");
            guiPanel.gameState = guiPanel.playState;
            guiPanel.gameLevel = guiPanel.level1;
            guiPanel.setUpLevel();
        }
        else if(keyCode == KeyEvent.VK_2){
            System.out.println("RETRY Level 2");
            guiPanel.gameState = guiPanel.playState;
            guiPanel.gameLevel = guiPanel.level2;
            guiPanel.setUpLevel();
        }
        else if(keyCode == KeyEvent.VK_ESCAPE){
            System.out.println("EXIT GAME");
            System.exit(0);
        }
        else if(keyCode == KeyEvent.VK_M){
            System.out.println("RETURN TO MENU");
            guiPanel.gameState = guiPanel.menuState;
            guiPanel.setUpMenu();
        }
    }
    
    /**
     * This method is used to handle the key inputs from the user when the game is replying a game manually
     * @param keyCode - the key code of the key pressed by the user.
     */
    public void keyReplayState(int keyCode){
        if(keyCode == KeyEvent.VK_RIGHT){
            System.out.println("REPLAYING");
            replaying = 1;
        }else if(keyCode == KeyEvent.VK_ESCAPE){
            GUI.stopGameSound();
            replaying = 0;
            guiPanel.gameState = guiPanel.menuState;
            guiPanel.setUpLevel();
        }
    }

    /**
     * This method is used to handle the key inputs from the user when the game is in the splash page state before starting level 2.
     * @param keyCode
     */
    public void keyStart2(int keyCode){
        if(keyCode == KeyEvent.VK_ENTER){
            guiPanel.gameState = guiPanel.playState;
            guiPanel.gameLevel = guiPanel.level2;
            guiPanel.setUpLevel();
        }
    }

    
    /**
     * This method is used to handle the key inputs from the user when the game is in play state.
     * @param keyCode - the key code of the key pressed by the user.
     */
    public void keyPlayState(int keyCode){
        if(keyCode == KeyEvent.VK_UP) {
            up = 1;
        }
        if(keyCode == KeyEvent.VK_DOWN) {
            down = 1;
        }
        if(keyCode == KeyEvent.VK_LEFT) {
            left = 1;
        }
        if(keyCode == KeyEvent.VK_RIGHT) {
            right = 1;
        }
        if(keyCode == KeyEvent.VK_SPACE) {
            guiPanel.gameState = guiPanel.pauseState;
            guiPanel.isPaused = true;
            System.out.println("Paused");
            GUI.stopGameSound();
            guiPanel.timer.stop();
            guiPanel.pauseButton.setText("Resume");
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_UP) {
            //move up
            up = 0;
        }
        if(key == KeyEvent.VK_DOWN) {
            //move down
            down = 0;
        }
        if(key == KeyEvent.VK_LEFT) {
            //move left
            left = 0;
            
        }
        if(key == KeyEvent.VK_RIGHT) {
            //move right
            right = 0;
            replaying = 0;
        }
        if(key == KeyEvent.VK_SPACE) {
            //pause
            pause = 0;
        }
        if(key == KeyEvent.VK_ESCAPE) {
            //escape
            escape = 0;
        } 
        
    } 
    
}