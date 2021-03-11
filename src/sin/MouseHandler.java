package sin;

import sin.lib.Coord;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter  {

    Game game;

    public MouseHandler(Game game) {
        this.game = game;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Screen: (" + e.getX() + ", " + e.getY() + ")");
        Coord co = game.getGamePos(e.getX(), e.getY());
        int x = co.x;
        int y = co.y;
        Coord coL = game.getMapPosFromGame(co.x, co.y);
        int lx = coL.x;
        int ly = coL.y;
        System.out.println("Game: (" + x + ", " + y + ")");
        System.out.println("Location: (" + lx + ", " + ly + ")");
        if(game.gameState == Game.State.Menu) {
            game.menu.mousePressed(e);
        }
        game.onClick();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
