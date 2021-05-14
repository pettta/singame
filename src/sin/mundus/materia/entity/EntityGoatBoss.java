package sin.mundus.materia.entity;

import org.json.JSONObject;
import sin.Game;
import sin.lib.Lib;
import sin.lib.Vector;
import sin.mundus.materia.sprite.Polysprite;
import sin.save.ISaveable;

import java.awt.*;

public class EntityGoatBoss extends Entity{
    private int counter;
    private int spriteIndex;
    Polysprite ps;

    public EntityGoatBoss(float x, float y, Game game){
        super(x, y, 64, 80, EntityType.Enemy, game);
        this.health = 600;

    }
}
