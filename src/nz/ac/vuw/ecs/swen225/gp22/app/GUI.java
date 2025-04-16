package nz.ac.vuw.ecs.swen225.gp22.app;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.Renderer;
import javax.swing.SwingConstants;

import org.jdom2.JDOMException;

import nz.ac.vuw.ecs.swen225.gp22.domain.*;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;
import nz.ac.vuw.ecs.swen225.gp22.recorder.*;
import nz.ac.vuw.ecs.swen225.gp22.renderer.*;

/**
* This class is the GUI of the game.
* 
* @author Ecco Competente
*/

public class GUI extends JPanel implements Runnable {
    
    public short gameState;
    public final short playState = 1;
    public final short pauseState = 2;
    public final short menuState = 3;
    public final short gameOverState = 4;
    public final short infoState = 5;
    public final short winState = 6;
    
    public short replayType;
    public final short manual = 1;
    public final short auto = 2;
    
    private boolean isRecording = false;
    public boolean isPaused = false;
    
    public KeyInput keyIn = new KeyInput(this);
    public static Chap chap;
    public static Recorder recorder;
    public static Level l1;
    public static Renderer renderer;
    public static RenderMazePanel renderMazePanel = null;
    public static Persistency persistency;
    public Timer timer;
    
    public static int time = 60;
    public ArrayList<JButton> buttons = new ArrayList<>();
    
    public short gameLevel;
    public short level1 = 1;
    public short level2 = 2;
    public short replay = 3;
    public short loadState = 4;
    public short splashPage = 5;
    
    public static String timeDString; // time display string
    
    // Menu Buttons
    public JButton startButton = new JButton("Start");
    public JButton loadButton = new JButton("Load");
    public JButton exitButton = new JButton("Exit");
    public JButton saveButton = new JButton("Save");
    public JButton pauseButton = new JButton("Pause");
    
    private JMenuBar menuBar;
    
    private JMenuItem exitItem;
    private JMenuItem saveItem;
    private JMenuItem rulesItem;
    private JMenuItem loadItem;
    private JMenuItem lvl1;
    private JMenuItem lvl2;
    private JMenuItem startR;
    
    private JMenuItem startRecording;
    private JMenuItem stopRecording;
    private JMenuItem replaySpeedx1;
    private JMenuItem replaySpeedx150;
    private JMenuItem replaySpeedx200;
    
    private JMenu recordGame;
    private JMenu replayGame;
    private JMenu mReplayGame;
    
    public String levelsURL = "src/nz/ac/vuw/ecs/swen225/gp22/persistency/levels/";
    public String savedGamesURL = "src/nz/ac/vuw/ecs/swen225/gp22/persistency/savedGames/";
    // private ArrayList<JButton> allButtons = new ArrayList<>();
    
    Thread threadGame;
    
    /**
    * This class is the GUI of the game.
    * 
    * @author Ecco Competente
    * 
    */
    public GUI() {
        this.setPreferredSize(new Dimension(800, 600));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyIn);
        BorderLayout l = new BorderLayout();
        this.setLayout(l);
        this.setFocusable(true);
    }
    
    /**
    * This method is used to start thread of the game
    */
    public void setUpThread() {
        threadGame = new Thread(this);
        threadGame.start();
    }
    
    /**
    * This method is used to setup the game and start the game
    */
    public void setup() {
        gameState = menuState;
        setUpMenu();
    }
    
    /**
    * This method restarts the game panel
    */
    public void clearPanel() {
        Main.gui.removeAll();
        Main.gui.revalidate();
        Main.gui.repaint();
        
        // remove timer and restart it
        if (timer != null) {
            timer.stop();
            time = 60;
        }
        
    }
    
    /**
    * This method is used to set up the replay board
    */
    public void setupReplay() {

        try {
            Recorder.loadRecording();
        } catch (JDOMException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println("LOADED REPLAY");
        try {
            l1 = Persistency.loadBoard("recorderBoard.xml", "src/nz/ac/vuw/ecs/swen225/gp22/recorder/recordedFiles/");
        } catch (JDOMException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        chap = new Chap(l1.getStartingX(), l1.getStartingY(), l1);
        renderMazePanel = new RenderMazePanel(l1);
        renderMazePanel.loadAllImages();
        renderMazePanel.paintComponent(getGraphics());
        playGameSound(0);
        timer.start();
        add(renderMazePanel);
    }
    
    /**
    * This method run is used to run the game multiple times constantly checking
    * and updating the game
    */
    @Override
    public void run() {
        long timer = 0; // timer for the game
        long prevTime = System.nanoTime(); // previous time
        long currTime; // current time
        long elapseTime; // time elapsed
        double intDraw = 1000000000 / 15; // interval to draw
        double d = 0; // delta
        
        while (threadGame != null) {
            
            currTime = System.nanoTime();
            elapseTime = currTime - prevTime; // time elapsed
            d += elapseTime / intDraw;
            timer += elapseTime;
            prevTime = currTime;
            
            if (d >= 1) {
                updateGame();
                this.requestFocus();
                d--;
            }
            timer = timer >= 1000000000 ? 0 : timer;
        }
        
    }
    /**
     * This method is used to play a specific sound
     * @param i - the sound to be played
     */
    public static void playGameSound(int i){
        if(i == 0){
            renderMazePanel.playMusic();
        } else {
            renderMazePanel.playSE(i);
        }
    }

    public static void stopGameSound(){

  
            // System.out.println("Stopping music");

            try{
                System.out.println("Stopping music");

                renderMazePanel.stopMusic();
            } catch (Exception e){
                System.out.println("Music already stopped");
            }
    }
    
    /**
    * This method is used to update base on the level of the game
    */
    public void setUpLevel() {
        clearPanel(); // clear the panel
        if (gameState == playState) {
            // if the game is in play state
            System.out.println("Game State: " + gameState);
            if (gameLevel == level1) { // if the game level is level 1
                System.out.println("LOADED LEVEL1");
                loadLv1Timer();
                addButtons(); // add buttons
                addMenu(); // add menu
                try {
                    l1 = Persistency.loadBoard("level1.xml", levelsURL);
                } catch (JDOMException | IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                chap = new Chap(l1.getStartingX(), l1.getStartingY(), l1); // pass level
                renderMazePanel = new RenderMazePanel(l1);
                renderMazePanel.loadAllImages();
                renderMazePanel.paintComponent(getGraphics());
                playGameSound(0);
                add(renderMazePanel);
            } else if (gameLevel == level2) { // if the game level is level 2
                System.out.println("LOADED LEVEL 2");
                loadLv1Timer();
                addButtons(); // add buttons
                addMenu(); // add menu
                try {
                    l1 = Persistency.loadBoard("level2.xml", levelsURL);
                } catch (JDOMException | IOException e) {
                    e.printStackTrace();
                }
                chap = new Chap(l1.getStartingX(), l1.getStartingY(), l1); // past level
                renderMazePanel = new RenderMazePanel(l1);
                renderMazePanel.loadAllImages();
                renderMazePanel.paintComponent(getGraphics());
                playGameSound(0);
                add(renderMazePanel);
            } else if (gameLevel == loadState) {
                System.out.println("LOADED MODE");
                addButtons(); // add buttons
                addMenu(); // add menu
                stopGameSound();
                try {
                    l1 = Persistency.loadBoard("savedGame.xml",
                    savedGamesURL);
                    chap = new Chap(l1.getStartingX(), l1.getStartingY(), l1); // level 2 position
                    renderMazePanel = new RenderMazePanel(l1);
                    renderMazePanel.loadAllImages();
                    renderMazePanel.paintComponent(getGraphics());
                    add(renderMazePanel);
                    timer.start(); // start timer
                    playGameSound(0);
                } catch (JDOMException | IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (gameState == pauseState && gameLevel == replay) {
            stopGameSound();
            if (replayType == manual) {
                Recorder.runAutoReplay(this);
            } else if (replayType == auto) {
                Recorder.runAutoReplay(this);
            }
        } else if (gameState == menuState) {
            setUpMenu(); // set up menu
        } else if (gameLevel == splashPage && gameState == pauseState) {
            setUpLevel2(); // set up level 2
        }
    }
    
    /**
    * This method is used to set up the game over screen
    */
    public void setUpGameOver() {
        clearPanel();
        System.out.println("Game Over");
        if (gameState == gameOverState) {
            System.out.println("gameover");
            JPanel panel = new JPanel();
            panel.setLocation(200, 200);
            panel.setSize(new Dimension(600, 450));
            panel.setBackground(new Color(69, 58, 47));
            JLabel title1 = new JLabel("<html><p><br/><br/>NICE TRY<p/></html>", SwingConstants.CENTER); //
            JLabel title2 = new JLabel("<html><p>Game Over<p/></html>", SwingConstants.CENTER);
            JLabel startText = new JLabel("<html><p><br/><br/><br/><br/><br/>Press ENTER To Retry...<p/></html>",
            SwingConstants.CENTER);
            ArrayList<JLabel> labels = new ArrayList<>(Arrays.asList(title1, title2));
            labels.forEach(l -> l.setFont(new Font("Verdana", 1, 70)));
            startText.setFont(new Font("Verdana", 1, 30));
            labels.add(startText);
            labels.forEach(l -> l.setForeground(new Color(196, 180, 133)));
            labels.forEach(l -> panel.add(l));
            this.setVisible(true);
            this.add(panel, BorderLayout.CENTER);
        }
    }
    
    /**
    * This method is used to show the splash page saying the player won
    */
    public void setWinGame() {
        clearPanel();
        stopGameSound();
        System.out.println("Game WINNER");
        if (gameState == winState) {
            JPanel panel = new JPanel();
            panel.setLocation(200, 200);
            panel.setSize(new Dimension(600, 450));
            panel.setBackground(new Color(69, 58, 47));
            JLabel title1 = new JLabel("<html><p><br/><br/>WINNER<p/></html>", SwingConstants.CENTER); //
            JLabel title2 = new JLabel("<html><p></br><p/></html>", SwingConstants.CENTER);
            JLabel startText = new JLabel("<html><p><br/><br/><br/><br/><br/>Press ENTER To Retry...<p/></html>",
            SwingConstants.CENTER);
            ArrayList<JLabel> labels = new ArrayList<>(Arrays.asList(title1, title2));
            labels.forEach(l -> l.setFont(new Font("Verdana", 1, 70)));
            startText.setFont(new Font("Verdana", 1, 30));
            labels.add(startText);
            labels.forEach(l -> l.setForeground(new Color(196, 180, 133)));
            labels.forEach(l -> panel.add(l));
            this.setVisible(true);
            this.add(panel, BorderLayout.CENTER);
        }
    }
    
    /**
    * This is the method that is called when the game is in the menu state and
    * display menu texts
    */
    public void setUpMenu() {
       
        clearPanel();
        stopGameSound();
        this.setBackground(new Color(69, 58, 47));
        JPanel panel = new JPanel();
        panel.setLocation(200, 200);
        panel.setSize(new Dimension(600, 450));
        panel.setBackground(new Color(69, 58, 47));
        JLabel title1 = new JLabel("<html><p><br/><br/>Welcome to<p/></html>", SwingConstants.CENTER); //
        JLabel title2 = new JLabel("<html><p>Chap's Challenge<p/></html>", SwingConstants.CENTER);
        JLabel startText = new JLabel("<html><p><br/><br/><br/><br/><br/>Press ENTER To Play...<p/></html>",
        SwingConstants.CENTER);
        ArrayList<JLabel> labels = new ArrayList<>(Arrays.asList(title1, title2));
        labels.forEach(l -> l.setFont(new Font("Verdana", 1, 70)));
        startText.setFont(new Font("Verdana", 1, 30));
        labels.add(startText);
        labels.forEach(l -> l.setForeground(new Color(196, 180, 133)));
        labels.forEach(l -> panel.add(l));
        this.setVisible(true);
        this.add(panel, BorderLayout.CENTER);
    }
    
    public void setUpLevel2() {
        clearPanel();
        stopGameSound();
        this.setBackground(new Color(69, 58, 47));
        JPanel panel = new JPanel();
        panel.setLocation(200, 200);
        panel.setSize(new Dimension(600, 450));
        panel.setBackground(new Color(69, 58, 47));
        JLabel title1 = new JLabel("Well Done", SwingConstants.CENTER); //
        JLabel title2 = new JLabel("<html><p><p/></html>", SwingConstants.CENTER);
        JLabel startText = new JLabel("<html><p><br/><br/><br/><br/><br/>Press ENTER To Continue...<p/></html>",
        SwingConstants.CENTER);
        ArrayList<JLabel> labels = new ArrayList<>(Arrays.asList(title1, title2));
        labels.forEach(l -> l.setFont(new Font("Verdana", 1, 70)));
        startText.setFont(new Font("Verdana", 1, 30));
        labels.add(startText);
        labels.forEach(l -> l.setForeground(new Color(196, 180, 133)));
        labels.forEach(l -> panel.add(l));
        this.setVisible(true);
        this.add(panel, BorderLayout.CENTER);
    }
    
    
    /**
    * This method is used to update the game
    */
    public void updateGame() {
        if (gameState == playState) {
            if (renderMazePanel != null) {
                moveChap();
            }
        } else if (gameState == pauseState && gameLevel != replay) {
            timer.stop(); // stop timer
        }else if(gameState == menuState){
            
        }
    }
    
    /**
    * This method is used to move the chap base on the input of player and records
    * movement
    */
    public void moveChap() {
        try {
            if (keyIn.up == 1) { // up
                System.out.println("chap up");
                chap.moveUp();
                renderMazePanel.repaint();
                if (isRecording == true) { // record movement
                    Recorder.chapMove(Direction.UP); // record right
                }
                if (chap.getState() == chap.getWinState() && gameLevel == level2) {
                    System.out.println("WIN Entire Game");
                    gameState = winState;
                    setWinGame();
                }  
                else if(gameLevel == level2 || gameLevel == loadState){
                    if (chap.getState() == chap.getDeadState()) {
                        System.out.println("Chap is Dead");
                        gameState = gameOverState;
                        stopGameSound();
                        setUpGameOver();
                    }
                }
            } else if (keyIn.down == 1) { // down
                System.out.println("chap down");
                chap.moveDown();
                renderMazePanel.repaint();
                if (isRecording) { // record movement
                    Recorder.chapMove(Direction.DOWN); // record down
                }
                if (chap.getState() == chap.getDeadState() && gameLevel == level2) {
                    System.out.println("Chap is Dead");
                    gameState = gameOverState;
                    stopGameSound();
                    setUpGameOver();
                }
            } else if (keyIn.left == 1) { // left
                System.out.println("chap left");
                chap.moveLeft();
                renderMazePanel.repaint();
                if (isRecording) { // record movement
                    Recorder.chapMove(Direction.LEFT); // record left
                }
                // if chap is in the win state, then set the game state to win state
                if(gameLevel == level1 || gameLevel == loadState){
                    if (chap.getState() == chap.getWinState()) {
                        gameState = pauseState;
                        gameLevel = splashPage;
                        stopGameSound();
                        setUpLevel();
                    } 
                }
                else if (chap.getState() == chap.getDeadState() && gameLevel == level2) {
                    System.out.println("Chap is Dead");
                    gameState = gameOverState;
                    stopGameSound();
                    setUpGameOver();
                }
            } else if (keyIn.right == 1) { // right
                System.out.println("chap right");
                chap.moveRight();
                renderMazePanel.repaint();
                if (isRecording) { // record movement
                    Recorder.chapMove(Direction.RIGHT); // record right
                }
                if (chap.getState() == chap.getDeadState() && gameLevel == level2) {
                    System.out.println("Chap is Dead");
                    gameState = gameOverState;
                    stopGameSound();
                    setUpGameOver();
                }
            }
        } catch (Exception e) {
        }
    }
    
    /**
    * This method is used to set up auto replay
    */
    public void replayChap(Direction dir) {
        try {
            if (dir == Direction.UP) {
                chap.moveUp();
                renderMazePanel.repaint();
            } else if (dir == Direction.DOWN) {
                chap.moveDown();
                renderMazePanel.repaint();
            } else if (dir == Direction.LEFT) {
                chap.moveLeft();
                renderMazePanel.repaint();
            } else if (dir == Direction.RIGHT) {
                chap.moveRight();
                renderMazePanel.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    /**
    * This method is used to add the buttons to the game
    */
    public void addButtons() {
        var buttonPanel = new JPanel(); // panel for buttons
        buttonPanel.setFocusable(false); // set focusable to false
        buttonPanel.setLayout(new GridLayout(1, 4)); // set layout to grid layout
        buttonPanel.setBackground(new Color(0, 110, 51)); // set background color
        buttons.addAll(Arrays.asList(pauseButton, saveButton, loadButton, exitButton)); // add buttons to arraylist
        for (JButton jb : buttons) {
            buttonPanel.add(jb); // add buttons to panel
            jb.setFocusable(false);
            jb.setFocusCycleRoot(false);
            jb.addActionListener(e -> {
                if (jb.getText().equals("Pause") || jb.getText().equals("Resume")) {// if pause pressed or resumed
                    isPaused = !(isPaused); // change pause state
                    gameState = isPaused ? pauseState : playState; // change game state
                    if (isPaused) { // if paused
                        jb.setText("Resume"); // change text to resume
                        timer.stop(); // stop timer
                       stopGameSound();
                    } else {
                        jb.setText("Pause"); // change text to pause
                        timer.start(); // start timer
                    }
                } else if (jb.getText().equals("Save")) { // if save pressed
                    save(l1, chap); // save game
                } else if (jb.getText().equals("Load")) {
                    load();
                } else if (jb.getText().equals("Exit")) { // if exit pressed
                    exitQ(); // exit game popup
                }
            });
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }
    
    /**
    * This method is used to add the menu to the game
    */
    public void addMenu() {
        menuBar = new JMenuBar(); // menu bar
        var Game = new JMenu("Game"); // game menu
        var Options = new JMenu("Options"); // options menu
        var Level = new JMenu("Level"); // level menu
        var Help = new JMenu("Help");// help menu
        menuBar.add(Game); // add game menu to menu bar
        menuBar.add(Options); // add options menu to menu bar
        menuBar.add(Level); // add level menu to menu bar
        menuBar.add(Help); // add help menu to menu bar
        
        recordGame = new JMenu("Record Game"); // record game menu
        replayGame = new JMenu("Auto Replay"); // replay game menu
        mReplayGame = new JMenu("Manual Replay");
        exitItem = new JMenuItem(); // exit menu item
        saveItem = new JMenuItem(); // save menu item
        rulesItem = new JMenuItem();
        loadItem = new JMenuItem(); // load menu item
        startR = new JMenuItem("Start Replay");
        lvl1 = new JMenuItem(); // levels menu item
        lvl2 = new JMenuItem();
        replaySpeedx1 = new JMenuItem("x1"); // replay speed menu item
        replaySpeedx150 = new JMenuItem("x1.5");
        replaySpeedx200 = new JMenuItem("x2");
        startRecording = new JMenuItem("Start Recording"); // start and stop recording menu item
        stopRecording = new JMenuItem("Stop Recording");
        startRecording.addActionListener(e -> startRecording()); // start and stop recording
        stopRecording.addActionListener(e -> stopRecording());
        replaySpeedx1.addActionListener(e -> setSpeed1());
        replaySpeedx150.addActionListener(e -> setSpeed150());
        replaySpeedx200.addActionListener(e -> setSpeed200());
        startR.addActionListener(e -> runMreplay());
        
        // populate the menu items and key bindings
        populateMenuItems(saveItem, "Save", KeyEvent.VK_S,
        InputEvent.CTRL_DOWN_MASK);
        populateMenuItems(exitItem, "Exit", KeyEvent.VK_X,
        InputEvent.CTRL_DOWN_MASK);
        populateMenuItems(rulesItem, "Rules", KeyEvent.VK_H,
        InputEvent.CTRL_DOWN_MASK);
        populateMenuItems(loadItem, "Load", KeyEvent.VK_R,
        InputEvent.CTRL_DOWN_MASK);
        populateMenuItems(lvl1, "Load Level 1", KeyEvent.VK_1,
        InputEvent.CTRL_DOWN_MASK);
        populateMenuItems(lvl2, "Load Level 2", KeyEvent.VK_2,
        InputEvent.CTRL_DOWN_MASK);
        
        // add menu items to menu
        recordGame.add(startRecording);
        recordGame.add(stopRecording);
        replayGame.add(replaySpeedx1);
        replayGame.add(replaySpeedx150);
        replayGame.add(replaySpeedx200);
        mReplayGame.add(startR);
        Game.add(saveItem);
        Game.add(exitItem);
        Options.add(loadItem);
        Options.add(recordGame);
        Options.add(replayGame);
        Options.add(mReplayGame);
        Help.add(rulesItem);
        Level.add(lvl1);
        Level.add(lvl2);
        add(menuBar, BorderLayout.NORTH); // add menu bar to gui
    }
    
    /**
    * This method is used to set recording to true
    */
    public void startRecording() {
        isRecording = true;
        try {
            Recorder.saveBoard(l1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
    * This method is used to set recording to false
    */
    public void stopRecording() {
        if (isRecording) { // if recording
            try {
                Recorder.saveRecording(); // save recording
                isRecording = false; // set recording to false
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JDOMException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "You must be recording to save a recording");
        }
    }
    
    /**
    * This method is used to set the replay speed to 1
    */
    public void setSpeed1() {
        Recorder.setReplaySpeed(1);
        gameState = pauseState;
        gameLevel = replay;
        replayType = auto;
        setUpLevel();
    }
    
    /**
    * This method is used to set the replay speed to 1.5
    */
    public void setSpeed150() {
        Recorder.setReplaySpeed(1.50);
        gameState = pauseState;
        gameLevel = replay;
        replayType = auto;
        setUpLevel();
    }
    
    /**
    * This method is used to set the replay speed to 2
    */
    public void setSpeed200() {
        Recorder.setReplaySpeed(2.0);
        gameState = pauseState;
        gameLevel = replay;
        replayType = auto;
        setUpLevel();
    }
    
    /**
    * This method is used to run the manual replay
    */
    public void runMreplay() {
        //jdialogue saying to press right arrow to do a step by step replay
        JOptionPane.showMessageDialog(null, "Press the right arrow key to do a step by step replay");
        stopGameSound();
        replayType = manual;
        gameState = pauseState;
        gameLevel = replay;
        setUpLevel();
    }
    
    /**
    * This method is used to populate the menu items
    *
    * @param item       the menu item
    * @param title      the text of the menu item
    * @param keyEvent   the key of the menu item
    * @param inputEvent the mask of the menu item
    */
    public void populateMenuItems(JMenuItem item, String title, int keyEvent, int inputEvent) {
        item.setText(title); // set text
        item.setAccelerator(KeyStroke.getKeyStroke(keyEvent, inputEvent)); // set key binding
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent arg0) {
                // TODO Auto-generated method stub
                if (item.getText().equals("Exit")) { // if exit shortcut pressed
                    exitQ();
                } else if (item.getText().equals("Save")) { // if save shortcut pressed
                    save(l1, chap);
                } else if (item.getText().equals("Load")) {
                    load();
                }  else if (item.getText().equals("Rules")) {
                    rules();
                } 
                else if (item.getText().equals("Load Level 1")) {
                    if (gameLevel == level1 ||  gameLevel == level2 || gameLevel == loadState) {
                        // reset game
                        gameLevel = level1;
                        stopGameSound();
                        setUpLevel();
                    }
                } else if (item.getText().equals("Load Level 2")) {
                    // should be in level 2
                    if (gameLevel == level2 || gameLevel == level1 || gameLevel == loadState) {
                        gameLevel = level2;
                        stopGameSound();
                        setUpLevel();
                    }
                }
            }
        });
    }
    
    /**
    * This method is used to show a popup when the user tries to exit the game
    */
    public void exitQ() {
        gameState = pauseState; // pause game
        System.out.println("exit");
        int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit",
        JOptionPane.YES_NO_OPTION);
        if (exit == JOptionPane.YES_OPTION) { // if yes
            System.exit(0); // exit game
        } else {
            gameState = playState; // if no change game state to playState
            timer.start(); // start timer
        }
    }
    
    /**
    * This method is used to save the game
    */
    public void save(Level l, Chap c) {
        System.out.println("Saved");
        try {
            Persistency.saveBoard(l, "savedGame.xml", savedGamesURL, c);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // exit
        System.exit(0);
    }
    
    /**
    * This method is used to show a window with all the rules and short cuts
    */
    public void rules(){
        //Jdialogue with the rules and scroll pane
        String rules =  "Rules of Chap's Challenge:\n\n1. Move Chap around the maze using the arrow keys.\n\n2. Collect all the keys to unlock the door.\n\n3. Collect all the chips to win the game.\n\n4. Avoid the Monster and the water.\n\n5. Press space to pause the game.\n\n6. Press escape to Resume the game.\n\n7. Press ctrl + s to save the game.\n\n8. Press ctrl + x to exit the game.\n\n9. Press ctrl + h to view the rules.\n\n10. Press ctrl + r to resume a saved game -.\n\n11. Press ctrl + 1 to start a new game at level 1.\n\n12. Press ctrl + 2 to start a new game at level 2.\n\n13. Press Esc to exit game when GameOver.\n\n14. Press 1 to start a new game at level 1 when on Game Over.\n\n15. Press 2 to start a new game at level 2 when Game Over.\n\n16. Press Esc when Replaying is done to go back to Main Menu";
        JTextArea textArea = new JTextArea(rules);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
        JOptionPane.showMessageDialog(null, scrollPane, "Rules", JOptionPane.PLAIN_MESSAGE);
        
    }
    /**
    * This method is used to set up timer
    */
    public void loadLv1Timer() {
        timer = new Timer(1000, new ActionListener() { // populate timer
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (time > 0) { // if time is greater than 0
                    time--; // decrement time
                    // format lv1Time to display as mm:ss
                    timeDString = String.format("%02d:%02d", time / 60, time % 60);
                    // System.out.print("\r " + timeDString);
                } else {
                    timer.stop(); // stop timer so that there is no leak
                    gameState = gameOverState; // change game state to game over
                    stopGameSound();
                    setUpGameOver();
                }
            }
        });
        timer.start();// start timer
    }
    
    /**
    * @return file that has the current game state of Game
    */
    public File load() {
        // pause game
        gameState = pauseState;
        // open a file chooser
        JFileChooser fc = new JFileChooser(); // create file chooser
        fc.setCurrentDirectory(new File(savedGamesURL));
        // return the file with the path
        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile(); // get file
            System.out.println("Opening: " + file.getName() + "." + "\n");
            System.out.println("GAME LOADED");
            gameState = playState; // change game state to play
            gameLevel = loadState; // set game level to load state
            setUpLevel();
            return file; // return file
        } else {
            gameState = playState;
            System.out.println("Open command cancelled by user." + "\n");
            timer.start();
            return null; // return null
        }
    }
    
}
