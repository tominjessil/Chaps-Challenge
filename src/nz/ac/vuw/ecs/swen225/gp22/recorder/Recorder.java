package nz.ac.vuw.ecs.swen225.gp22.recorder;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import org.jdom2.*;
import org.jdom2.JDOMException;
import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import nz.ac.vuw.ecs.swen225.gp22.app.GUI;
import nz.ac.vuw.ecs.swen225.gp22.persistency.Persistency;

/**
 * Recorder class code which holds the code for basic xml saving of moves and
 * replaying the recorded files.
 *
 * @author Kevin Lee
 */
public class Recorder {
	private static final ArrayList<Move> moves = new ArrayList<>();
	private static double replaySpeed = 1;
	private static int count = 0;
	
    public record Move(String player, Direction dir, int index) { }


    /**
     * Setter for replay speed
     * @param speed replay Speed selected by user
     */
    public static void setReplaySpeed(double speed) {
        replaySpeed = speed;
    }

    /**
     * Getter for replay speed field 
     * 
     * @return replay speed
     */
    public static double getReplaySpeed() {
        return replaySpeed;
    }


    /**
     * Loads the moves stored in the xml file
     *
     * @throws JDOMException Xml file Error
     * @throws IOException File Error
     */
    public static void loadRecording() throws JDOMException, IOException { // load from xml file
        moves.clear(); // clearing the moves
        count = 0;
        //System.out.println("Loading recording from xml file"); //debugging code

        // loading File:
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder
                .build(new File("src/nz/ac/vuw/ecs/swen225/gp22/recorder/recordedFiles/recording.xml"));

        Element root = document.getRootElement(); // move from xml
        List<Element> nodes = root.getChildren();

        for (Element element : nodes) {
            int moveId = Integer.parseInt(element.getAttributeValue("id"));
            String direction = element.getChildText("direction");
            String player = element.getChildText("player");

            Direction dir = Direction.DOWN;
            if (direction.equals("UP"))
                dir = Direction.UP;
            else if (direction.equals("DOWN"))
                dir = Direction.DOWN;
            else if (direction.equals("LEFT"))
                dir = Direction.LEFT;
            else if (direction.equals("RIGHT"))
                dir = Direction.RIGHT;
            // int index = Integer.valueOf(moveId);

            Move move = new Move(player, dir, moveId);
            moves.add(move);
        }
    }


    /**
     * Saves the recorded moves into a xml file for replaying
     *
     * @throws JDOMException xml file Exception
     * @throws FileNotFoundException invalid file
     */
    public static void saveRecording() throws JDOMException, FileNotFoundException {
        // output to Console :
        System.out.println("Saved Recording : ");
        moves.stream().forEach(m -> System.out.println(m.player + " " + m.dir()));

        XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
        FileOutputStream fileOutputStream = new FileOutputStream(
                "src/nz/ac/vuw/ecs/swen225/gp22/recorder/recordedFiles/recording.xml");
        Document document = new Document();
        document.setRootElement(new Element("Moves"));
        Element root = document.getRootElement();

        for (Move move : moves) {
            Element action = new Element("move");
            action.setAttribute("id", "" + move.index()); // id is the order that move was played
            action.addContent(new Element("direction").setText("" + move.dir()));
            action.addContent(new Element("player").setText(move.player()));
            root.addContent(action);
        }
        // Write file out through xml outputter
        try {
            outputter.output(document, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Saves the state of the board before recording the moves.
     *
     * @param board state of the board
     * @throws IOException file error
     */
    public static void saveBoard(Object board) throws IOException {
        Persistency.saveBoard(board, "recorderBoard.xml", "src/nz/ac/vuw/ecs/swen225/gp22/recorder/recordedFiles/", GUI.chap);
    }


    /**
     * Runs the replay of the moves recorded
     * @param app GUI
     */
    public static void runAutoReplay(GUI app) {
        app.setupReplay();
        Runnable runnable = () -> {
            System.out.println("AUTO REPLAY RUNNING");
            while (!moves.isEmpty()) {
                try {
                    Thread.sleep(250 / (long) replaySpeed); // TODO field with GUI
                    // singleMove(app);
                    try {
                        if (moves.size() > 0) {
                            Move move = moves.get(0);
                            if (move.player().equals("chap")) {
                                if(app.replayType == app.manual){
                                    if (app.keyIn.replaying == 1) {
                                        app.replayChap(move.dir());
                                        moves.remove(0);
                                    }
                                }else{
                                    app.replayChap(move.dir());
                                    moves.remove(0);
                                }
                            } else {
                                System.out.println("Error finding chap");
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Index out of Bounds on Single Move");
                    }
                } catch (InterruptedException e) {
                    System.out.println("ERROR");
                }
            }
            app.gameState = app.menuState;
            app.setUpLevel();
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    /**
     * Records chap's movement when called
     * @param dir Direction of Chap's movement
     */
    public static void chapMove(Direction dir) {
        moves.add(new Move("chap", dir, count));
        count++;
    }


    /**
     * Records enemy's movement when called.
     * @param dir Direction of enemy's movement
     */
    public static void enemyMove(Direction dir) {
        moves.add(new Move("enemy", dir, count));
        count++;
    }
}
