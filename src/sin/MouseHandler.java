package sin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter  {

    Game game;

    public MouseHandler(Game game) {
        this.game = game;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(game.gameState == Game.State.Menu) {
            game.menu.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
