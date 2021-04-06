package sin.item;

import sin.Game;
import sin.Handler;
import sin.lib.Vector;
import sin.mundus.materia.entity.Player;
import sin.mundus.materia.entity.RangedShot;

public class ItemRanged extends ItemEquipment {

    public ItemRanged(String name) {
        super(name, 1);
    }

    public void onEquip() {

    }

    public void onUse(Game game, int x, int y) {
        Handler handler = game.handler;
        Player p = game.player;
        RangedShot proj = new RangedShot(p.getXMid() - 4, p.getYMid() - 4, game);
        Vector vector = new Vector(p.getXMid() - 4, p.getYMid() - 4, x, y, 20);
        proj.setVelX(vector.getHorizComp());
        proj.setVelY(vector.getVertComp());
        handler.addEnt(proj);

    }

}
