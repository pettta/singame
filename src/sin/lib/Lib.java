package sin.lib;

public class Lib {

    private static float[] tan = new float[361];
    private static float[] sin = new float[361];
    private static float[] cos = new float[361];

    public static float root;

    public static void initTrig() {
        for(int i = 0; i <= 360; i++) {
            tan[i] = (float) Math.tan(i);
            sin[i] = (float) Math.sin(i);
            cos[i] = (float) Math.cos(i);
        }
    }

    public static void init() {
        initTrig();
        root = (float) Math.sqrt(2);

    }

    public static float tan(int in) {
        return tan[in];
    }

    public static float sin(int in) {
        return sin[in];
    }

    public static float cos(int in) {
        return cos[in];
    }

    public static int clamp(int var, int min, int max) {
        if(var >= max) return max;
        else if (var <= min) return min;
        else return var;
    }

    public static int cycle(int cur, int min, int max) {
        if (cur < max) return cur + 1;
        else return min;
    }

    public static boolean locIn(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width && my > y && my < y + height) return true;
        return false;
    }

    public static Direction getNonelessDirection(Direction dir) {
        if(dir == Direction.None) return Direction.S;
        return dir;
    }

}
