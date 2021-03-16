package sin.lib;

import java.util.HashMap;
import java.util.Map;

public enum Sin {

    pride(0),
    greed(1),
    lust(2),
    envy(3),
    gluttony(4),
    wrath(5),
    sloth(6);

    public final int value;

    private static final Map<Integer, Sin> map = new HashMap<Integer, Sin>();

    private Sin(int value) {
        this.value = value;
    }

    static {
        for (Sin direc : Sin.values()) map.put(direc.value, direc);
    }

    public static Sin from(int value) {
        return map.get(value);
    }

}

