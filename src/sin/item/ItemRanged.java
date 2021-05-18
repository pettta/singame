package sin.item;

import sin.Game;
import sin.Handler;
import sin.lib.Vector;
import sin.mundus.materia.entity.EntityPlayer;
import sin.mundus.materia.entity.EntityRangedShot;

public class ItemRanged extends ItemEquipment {

    public ItemRanged(String name) {
        super(name, 1, ItemType.Ranged);
    }

    public void onEquip() {

    }

    public void onUse(Game game, int x, int y) {
        Handler handler = game.handler;
        EntityPlayer p = game.player;
        EntityRangedShot proj = new EntityRangedShot(p.getXMid() - 4, p.getYMid() - 4, game);
        Vector vector = new Vector(p.getXMid() - 4, p.getYMid() - 4, x, y, 20);
        proj.setVelX(vector.getHorizComp());
        proj.setVelY(vector.getVertComp());
        handler.addEnt(proj);

    }

}
