package sin;

import sin.lib.Coord;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter  {

    Game game;

    public MouseHandler(Game game) {
        this.game = game;
    }

    public void printData(int ex, int ey) {
        System.out.println("Screen: (" + ex + ", " + ey + ")");
        Coord co = game.getGamePos(ex, ey);
        int x = co.x;
        int y = co.y;
        Coord coL = game.getMapPosFromGame(co.x, co.y);
        int lx = coL.x;
        int ly = coL.y;
        System.out.println("Game: (" + x + ", " + y + ")");
        System.out.println("Location: (" + lx + ", " + ly + ")");
        System.out.println("Tile: (" + lx / 16 + ", " + ly / 16 + ")");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(SwingUtilities.isLeftMouseButton(e)) {
            printData(e.getX(), e.getY());
            game.player.meleeAttack();
            if (game.gameState == Game.State.Menu) {
                game.menu.mousePressed(e);
            }
            if (game.gameState == Game.State.Inventory) {
                game.inventory.mousePressed(e);
            }
            game.onClick();
        }
        if(SwingUtilities.isRightMouseButton(e)) {
            Coord co = game.getMapPos(e.getX(), e.getY());
            game.player.rangedAttack(co.x, co.y);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
