package sin;

import sin.materia.Materia;
import sin.materia.entity.Entity;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    LinkedList<Entity> list = new LinkedList<Entity>();

    public void tick() {
        for(int i = 0; i < list.size(); i++) {
            Entity ent = list.get(i);
            ent.tick();
        }
    }

    public void render(Graphics g) {
        for(int i = 0; i < list.size(); i++) {
            Materia mat = list.get(i);
            mat.render(g);
        }
    }

    public void addEnt(Entity ent) {
        this.list.add(ent);
    }

    public void delEnt(Entity ent) {
        this.list.remove(ent);
    }

    public LinkedList<Entity> getList() {
        return list;
    }

}
