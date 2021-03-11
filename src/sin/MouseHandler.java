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
        int ex = e.getX();
        int ey = e.getY();
        float difx = game.player.getXMid() - game.WIDTH / 2;
        float dify = game.player.getYMid() - game.HEIGHT / 2;
        int lx = ex + (int) difx;
        int ly = ey + (int) dify;
        System.out.println("Screen: (" + ex + ", " + ey + ")");
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
