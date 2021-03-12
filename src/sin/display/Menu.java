package sin.display;

import sin.Game;
import sin.lib.Coord;
import sin.lib.Lib;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Menu {

    Game game;
    BufferedImage background;
    ArrayList<Rectangle> buttons;

    public Menu(Game game) {
        this.game = game;
        background = Lib.getImage("src/resources/display/menu.png");
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
        if(Lib.locIn(x, y, buttons.get(0))) {

        } else if (Lib.locIn(x, y, buttons.get(1))) {

        } else if (Lib.locIn(x, y, buttons.get(2))) {

        } else if (Lib.locIn(x, y, buttons.get(3))) {
            System.exit(1);
        } else if (Lib.locIn(x, y, buttons.get(4))) {

        } else if (Lib.locIn(x, y, buttons.get(5))) {

        } else if (Lib.locIn(x, y, buttons.get(6))) {

        } else if (Lib.locIn(x, y, buttons.get(7))) {
            game.gameState = Game.State.Game;
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
        g.drawImage(background, 0, 0, null);

    }

}
