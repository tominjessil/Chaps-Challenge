package nz.ac.vuw.ecs.swen225.gp22.app;

import javax.swing.JFrame;

//run the GUI
public class Main extends JFrame{
    public static int width = 800;
    public static int height = 600;
    public static GUI gui;
    public static void main(String[] args){
        JFrame mainFrame = new JFrame("Chap's Challenge");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        gui = new GUI();
        mainFrame.add(gui);


        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);

        gui.setup();
        gui.setUpThread(); 

    }
}