package sin.materia.sprite;

import sin.lib.Direction;

import java.awt.image.BufferedImage;

public class Polysprite {

    private BufferedImage[][] images;

    /**
     * Keep in mind that though your brain might relate rows to horizontals
     * the amount of rows in a grid is, well, the height of the grid.
     * The amount of rows is how tall and long your columns are.
     * The amount of columns is how wide and long your rows are.
     * The amount of columns is the width of the grid.
     * That is, row index changes your vertical location and column index changes
     * your horizontal location.
     * In an array it goes [rowIndex][columnIndex].
     * That is, [y][x].
     */
    public Polysprite(String sheetLoc, int colCount, int rowCount, int width, int height) {
        SpriteSheet sheet = new SpriteSheet(sheetLoc);
        int colsPerSprite = width / 16 + (width % 16 > 0 ? 1 : 0);
        int rowsPerSprite = height / 16 + (height % 16 > 0 ? 1 : 0);
        images = new BufferedImage[rowCount][colCount];
        for(int x = 0; x < colCount; x++) {
            for(int y = 0; y < rowCount; y++) {
                images[y][x] = sheet.grabImage(x * colsPerSprite, y * rowsPerSprite, width, height);
            }
        }
    }

    /**
     * Receives the current image from the sprite sheet based on the direction the NPC is moving,
     * the direction they had last time they stopped moving, and the index of their current point in their
     * animation cycle.
     */
    public BufferedImage getCurImage(int index, Direction direction, Direction lastDirection) {
        return images[direction != Direction.None ? direction.value : lastDirection != Direction.None ? lastDirection.value : Direction.S.value][direction != Direction.None ? index : 0];
    }

    public BufferedImage getCurImage(int index) {
        return images[0][index];
    }

}
