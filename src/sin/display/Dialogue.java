package sin.display;

import sin.lib.Lib;
import sin.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Dialogue {

    public boolean dialogue;
    public boolean cycling;

    public Game game;
    public BufferedImage box;
    public Font font;

    public ArrayList<String> lines;
    public int lineIndex;
    public int firstIndex;
    public int secondIndex;

    public int counter;

    public Dialogue(Game game) {
        this.game = game;
        box = Lib.getImage("src/resources/display/textBox.png");
        dialogue = false;
        lines = new ArrayList<String>();
        Font newFont = null;
        try {
            newFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/pixeled.ttf")).deriveFont(9.5F);
        } catch (IOException | FontFormatException e) {
            System.out.println("Font not found!");
        }
        font = newFont;
    }

    public void render(Graphics g) {
        if(dialogue) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.scale(2, 2);
            g.drawImage(box, 0, 0, null);
            g2d.scale(.5, .5);

            g.setFont(font);
            g.setColor(Color.BLACK);
            // 30 Characters
            g.drawString(lines.get(lineIndex).substring(0, firstIndex), 36, 180);
            g.drawString((lineIndex + 1 < lines.size() ? lines.get(lineIndex + 1) : "").substring(0, secondIndex), 36, 200);
        }
    }

    // Don't have words longer than 30 characters for this please. :D
    public void talk(String string) {
        String[] splitStr = string.split("\\s+");

        String line = "";
        for(int i = 0; i < splitStr.length; i++) {
            String word = splitStr[i];
            String tmpLine = line;
            tmpLine += (tmpLine.equals("") ? "" : " ") + word;
            System.out.println(tmpLine.length());
            if(tmpLine.length() > 30) {
                lines.add(line);
                line = word;

            } else {
                line = tmpLine;
            }
            if(i == splitStr.length - 1) {
                lines.add(line);
            }
            System.out.println(i);
            System.out.println(splitStr.length);
            System.out.println(word + "___________________");
        }
        for (String a : lines) {
            System.out.println(a);
        }
        dialogue = true;
        cycling = true;
    }

    public void end() {
        lineIndex = 0;
        firstIndex = 0;
        secondIndex = 0;
        lines = new ArrayList<String>();
        dialogue = false;
    }

    public void next() {
        if(!cycling) {
            if (lineIndex < lines.size() - 2) {
                lineIndex += 2;
                firstIndex = 0;
                secondIndex = 0;
                cycling = true;
            } else {
                end();
            }
        } else {
            cycling = false;
            firstIndex = lines.get(lineIndex).length();
            secondIndex = lines.get(lineIndex + 1).length();
        }
    }

    public void tick() {
        if(dialogue) {
            if(cycling) {
                if (firstIndex < lines.get(lineIndex).length()) {
                    firstIndex++;
                } else if (secondIndex < lines.get(lineIndex + 1).length()){
                    firstIndex = lines.get(lineIndex).length();
                    secondIndex++;
                } else {
                    cycling = false;
                }
            }
        }
    }

}
