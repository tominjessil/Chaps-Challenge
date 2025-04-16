package nz.ac.vuw.ecs.swen225.gp22;
import nz.ac.vuw.ecs.swen225.gp22.app.*;
import java.awt.event.*;
import java.awt.Robot;
import java.awt.AWTException;
import java.util.*;
import org.junit.Test;

/**
 *FuzzTest for the game, runs automatically
 */
public class FuzzTest{
    final Random RAND = new Random();
    final Map<Integer,List<Integer>> NOTOPDIR = new HashMap<>(){
        {
            put(KeyEvent.VK_RIGHT,List.of(KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN));
            put(KeyEvent.VK_LEFT,List.of(KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN));
            put(KeyEvent.VK_UP,List.of(KeyEvent.VK_UP, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT));
            put(KeyEvent.VK_DOWN,List.of(KeyEvent.VK_DOWN, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT));
        }
    };
    final List<Integer> DIRECTIONS = new ArrayList<>(List.of(KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT));
    /**
     *Junit test, runs both levels providing random inputs through arrow keys
     *Then exits
     */
    @Test
    public void fuzzTest() throws Throwable{
        Main.main(new String[0]);
        MyRobot rob;
        rob = new MyRobot();
        rob.delay(100);
        testLvl1(rob);
        testLvl2(rob);
        System.exit(0); // exit game
    }
    /**
     * Tests the first level
     * Runs the game, press random arrow keys
     */
    public void testLvl1 (MyRobot rob){
        System.out.println("TESTING LEVEL 1\n---------------------------------");
        Main.gui.gameLevel = 1;
        Main.gui.setUpLevel();
        rob.pressAndRelease(KeyEvent.VK_ENTER);

        int preDir = DIRECTIONS.get(RAND.nextInt(4));
        for(int i = 0; i < 400; ++i){ //created robot presses random key 120 times every half second
            int key = NOTOPDIR.get(preDir).get(RAND.nextInt(3)); //Never uses opposite direction as previous
            preDir = key;
            rob.pressAndRelease(key);
        }
    }
    /**
     * Tests the second level
     * Runs the game, press random arrow keys
     */
    public void testLvl2(MyRobot rob){
        System.out.println("\nTESTING LEVEL 2\n---------------------------------");
        Main.gui.gameState = 1;//playState
        Main.gui.gameLevel = 2;
        Main.gui.setUpLevel();

        //needed to run lvl 2 straight away
        rob.keyPress(KeyEvent.VK_CONTROL);
        rob.keyPress(KeyEvent.VK_2);
        rob.keyRelease(KeyEvent.VK_CONTROL);
        rob.keyRelease(KeyEvent.VK_2);

        int preDir = DIRECTIONS.get(RAND.nextInt(4));
        for(int i = 0; i < 400; ++i){ //created robot presses random key 120 times every half second
            int key = NOTOPDIR.get(preDir).get(RAND.nextInt(3)); //Never uses opposite direction as previous
            preDir = key;
            rob.pressAndRelease(key);
            System.out.println(Main.gui.gameState);
            if(Main.gui.gameState == (short)4){//if gameOverState, press enter to restart
                rob.pressAndRelease(KeyEvent.VK_ENTER);
            }
        }
    }
}
class MyRobot extends Robot{
    public MyRobot() throws AWTException {
        super();
    }
    public void pressAndRelease(int keyCode){
        keyPress(keyCode);
        delay(75);
        keyRelease(keyCode);
        delay(75);
    }
}