package sin.lib;

import org.json.JSONObject;
import sin.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

    /**
     * If var is not within [min, max] inclusive, then returns
     * the nearest bound. Otherwise just returns var.
     */
    public static int clamp(int var, int min, int max) {
        if(var >= max) return max;
        else if (var <= min) return min;
        else return var;
    }

    public static float clamp(float var, float min, float max) {
        if(var >= max) return max;
        else if (var <= min) return min;
        else return var;
    }

    /**
     * Increments your cur value, setting it back to the min if it is
     * already equal to the max.
     */
    public static int cycle(int cur, int min, int max) {
        if (cur < max) return cur + 1;
        else return min;
    }

    public static float cycle(float cur, float min, float max) {
        if (cur < max) return cur + 1;
        else return min;
    }

    /**
     * Returns true if (mx, my) is within a rectangle that starts at (x, y)
     * with the defined width and height.
     */
    public static boolean locIn(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width && my > y && my < y + height) return true;
        return false;
    }

    public static boolean locIn(int mx, int my, Rectangle rect) {
        return locIn(mx, my, rect.x, rect.y, rect.width, rect.height);
    }

    public static BufferedImage getImage(String loc) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(loc));
        } catch (IOException e) {
            Game.dprint("No such thing exists: " + loc);
            image = Game.error;
        }
        return image;
    }

    public static void writeJSON(JSONObject obj, String loc) {
        try (FileWriter file = new FileWriter(loc)) {
            file.write(obj.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject loadJSON(String filename)  {
        try {
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            return new JSONObject(content);
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
