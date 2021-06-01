package sin;

import javax.swing.*;
import java.awt.*;
/**
 * Name: Window.java
 * Purpose: Handles basic window display.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class Window extends Canvas {

    JFrame frame;

    public Window(int width, int height, String title, Game game) {
        frame = new JFrame(title);
        Dimension dim = new Dimension(width, height);
        Dimension max = new Dimension(7680, 4320);
        frame.setPreferredSize(dim);
        frame.setMaximumSize(max);
        frame.setMinimumSize(dim);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
        ImageIcon icon = new ImageIcon("src/resources/icon.png");
        frame.setIconImage(icon.getImage());
    }

}
