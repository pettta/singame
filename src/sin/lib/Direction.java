package sin.lib;

import java.util.HashMap;
import java.util.Map;
/**
 * Name: Direction.java
 * Purpose: Keeps track of directions such as directions faced and their corresponding tileset row IDs.
 * Last Updated: 6/1/2021
 * Author: Zacharia Bridgers
 * Dependencies: None
 */
public enum Direction {

    N(0),
    S(1),
    W(2),
    E(3),
    NE(4),
    NW(5),
    SE(6),
    SW(7),
    None(8);

    public final int value;

    private static final Map<Integer, Direction> map = new HashMap<Integer, Direction>();

    private Direction(int value) {
        this.value = value;
    }

    static {
        for (Direction direc : Direction.values()) map.put(direc.value, direc);
    }

    public static Direction from(int value) {
        return map.get(value);
    }

}

