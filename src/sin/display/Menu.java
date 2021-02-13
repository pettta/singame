package sin.display;

import sin.Game;
import sin.lib.Lib;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu {

    Game game;

    public Menu(Game game) {
        this.game = game;
    }

    public void mousePressed(MouseEvent e) {
        int ex = e.getX();
        int ey = e.getY();
        if (Lib.locIn(ex, ey, 210, 150, 200, 50) && game.gameState == Game.State.Menu) {
            game.gameState = Game.State.Game;
        }
    }

    public void render(Graphics g) {
        Font font = new Font("arial", 1, 50);
        g.setFont(font);
        g.setColor(Color.white);
        g.drawString("MENU", 100, 50);
        g.drawRect(210, 150, 200, 50);
    }

}
