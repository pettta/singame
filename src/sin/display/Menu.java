package sin.display;

import sin.Game;
import sin.save.SaveHandler;
import sin.lib.Coord;
import sin.lib.Lib;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Name: Menu.java
 * Purpose: Manages the menu interaction and display.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public class Menu {

    // TODO change the way i render this cuz i do it silly, just dont feel like messing with annoying text rendering

    Game game;
    BufferedImage background, saveBackground, dumb1, dumb2;
    ArrayList<Rectangle> buttons;

    public MenuState state;

    public int selected;

    public enum MenuState {
        Main,
        Saves,
        Controls,
        Gameplay,
        Audio,
        Video;
    }

    public Menu(Game game) {
        this.game = game;
        background = Lib.getImage("src/resources/display/menu.png");
        saveBackground = Lib.getImage("src/resources/display/saveMenu.png");
        dumb1 = Lib.getImage("src/resources/display/dumb1.png");
        dumb2 = Lib.getImage("src/resources/display/dumb2.png");
        buttons = new ArrayList<Rectangle>();
        // 11, 57; 11, 102... 11, 192
        for(int x = 11; x < 176; x += 164) {
            for (int y = 57; y < 193; y += 45) {
                buttons.add(new Rectangle(x, y, 134, 22));
            }
        }
    }


    public void mousePressed(MouseEvent e) {
        Coord gamePos = game.getGamePos(e.getX(), e.getY());
        int x = gamePos.x;
        int y = gamePos.y;
        // Saves // Save
        if(Lib.locIn(x, y, buttons.get(0))) {
            if(state == MenuState.Main) {
                state = MenuState.Saves;
            } else if (state == MenuState.Saves) {
                selected = selected == 1 ? 0 : 1;
            }
        // Controls // Load
        } else if (Lib.locIn(x, y, buttons.get(1))) {
            if(state == MenuState.Main) {

            } else if (state == MenuState.Saves) {
                selected = selected == 2 ? 0 : 2;
            }
        // Gameplay
        } else if (Lib.locIn(x, y, buttons.get(2))) {
            if(state == MenuState.Main) {

            } else if (state == MenuState.Saves) {
                selected = 0;
            }
        // To Main Screen // Back
        } else if (Lib.locIn(x, y, buttons.get(3))) {
            if(state == MenuState.Main) {
                System.exit(1);
            } else if (state == MenuState.Saves) {
                state = MenuState.Main;
                selected = 0;
            }

        // Audio // Slot 1
        } else if (Lib.locIn(x, y, buttons.get(4))) {
            if(state == MenuState.Main) {

            } else if (state == MenuState.Saves) {
                if(selected == 1) {
                    SaveHandler.save(game, "slot1.json");
                } else if (selected == 2) {
                    SaveHandler.loadSave(game, "slot1.json");
                }
                game.audioPlayer.playOnce("buttonWorked.wav");
                selected = 0;
            }
        // Video // Slot 2
        } else if (Lib.locIn(x, y, buttons.get(5))) {
            if(state == MenuState.Main) {

            } else if (state == MenuState.Saves) {
                if(selected == 1) {
                    SaveHandler.save(game, "slot2.json");
                } else if (selected == 2) {
                    SaveHandler.loadSave(game,"slot2.json");
                }
                game.audioPlayer.playOnce("buttonWorked.wav");
                selected = 0;
            }
        // EMPTY // Slot 3
        } else if (Lib.locIn(x, y, buttons.get(6))) {
            if(state == MenuState.Main) {

            } else if (state == MenuState.Saves) {
                if(selected == 1) {
                    SaveHandler.save(game, "slot3.json");
                } else if (selected == 2) {
                    SaveHandler.loadSave(game,"slot3.json");
                }
                game.audioPlayer.playOnce("buttonWorked.wav");
                selected = 0;
            }
        // Back to Game // Autosave
        } else if (Lib.locIn(x, y, buttons.get(7))) {
            if(state == MenuState.Main) {
                game.gameState = Game.State.Game;
            } else if (state == MenuState.Saves) {
                if(selected == 1) {
                    SaveHandler.save(game,"auto.json");
                } else if (selected == 2) {
                    SaveHandler.loadSave(game, "auto.json");
                }
                game.audioPlayer.playOnce("buttonWorked.wav");
                selected = 0;
            }
        }
        else {
            if(state == MenuState.Main) {

            } else if (state == MenuState.Saves) {
                selected = 0;
            }
        }
    }

    public void renderButtonHitboxes(Graphics g) {
        for(int i = 0; i < buttons.size(); i ++) {
            Rectangle cur = buttons.get(i);
            g.setColor(Color.RED);
            g.drawRect(cur.x, cur.y, cur.width, cur.height);
        }
    }

    public void render(Graphics g) {
        // Font font = new Font("arial", 1, 50);
        // g.setFont(font);
        // g.setColor(Color.white);
        if(state == MenuState.Main) {
            g.drawImage(background, 0, 0, null);
        } else if (state == MenuState.Saves){
            g.drawImage(saveBackground, 0, 0, null);
            if(selected == 1) {
                g.drawImage(dumb1, 0, 0, null);
            } else if (selected == 2) {
                g.drawImage(dumb2, 0, 0, null);
            }
        }

    }

}
