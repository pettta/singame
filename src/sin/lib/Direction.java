package sin.lib;

import java.util.HashMap;
import java.util.Map;

public enum Direction {
    N(0),  // 0
    S(1),  // 1
    W(2),  // 3
    E(3),  // 2
    NE(4), // 4
    NW(5), // 5
    SE(6), // 6
    SW(7), // 7
    None(8); // 8

    public final int value;

    private Direction(int value) {
        this.value = value;
    }

    private static final Map<Integer, Direction> map = new HashMap<Integer, Direction>();

    static {
        for (Direction direc : Direction.values())
            map.put(direc.value, direc);
    }

    public static Direction from(int value) {
        return map.get(value);

    }

}

