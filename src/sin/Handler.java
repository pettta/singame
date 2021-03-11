package sin;

import sin.mundus.materia.Materia;
import sin.mundus.materia.entity.Entity;
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

    public void render(Graphics g, int minX, int maxX, int minY, int maxY) {
        for(int i = 0; i < list.size(); i++) {
            Materia mat = list.get(i);
            if(mat.getX() > minX - mat.getWidth() && mat.getX() < maxX && mat.getY() > minY - mat.getHeight() && mat.getY() < maxY) {
                mat.render(g);
            }
        }
    }
    public void renderTop(Graphics g, int minX, int maxX, int minY, int maxY) {
        for(int i = 0; i < list.size(); i++) {
            Materia mat = list.get(i);
            if(mat.getX() > minX - mat.getWidth() && mat.getX() < maxX && mat.getY() > minY - mat.getHeight() && mat.getY() < maxY) {
                mat.renderTop(g);
            }
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
