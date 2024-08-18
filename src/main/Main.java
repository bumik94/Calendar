package main;


import main.components.CalendarTable;

import javax.swing.*;
import java.awt.*;


public class Main {

    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Calendar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
//        c.insets = new Insets(1, 1, 1, 1);

        // Lay out components and set visibility
        frame.add(new CalendarTable(), c);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
}