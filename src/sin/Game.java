package sin;

import sin.display.Dialogue;
import sin.display.HUD;
import sin.display.Inventory;
import sin.display.Menu;
import sin.lib.Coord;
import sin.lib.Lib;
import sin.mundus.materia.entity.EntityNPC;
import sin.mundus.materia.entity.EntityPlayer;
import sin.mundus.materia.entity.EntityWormBoss;
import sin.mundus.materia.entity.EntityWormShooter;
import sin.mundus.map.Map;
import sin.mundus.materia.entity.EntityWormBoss;
import sin.sound.AudioPlayer;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static final boolean printFPS = false;

    public static final int WIDTH = 320, HEIGHT = 240;
    public static final float playerSpeed = 5;
    public static final boolean debugMode = true;
    private boolean running = false;
    private boolean initComplete = false;

    private Thread thread;
    private Random r;
    private Insets insets;

    public Dialogue dialogue;

    public int curWidth, curHeight, gapWidth, gapHeight;
    public double expansionX, expansionY, expansion, gameX, gameY;

    public Handler handler;
    public Map map;
    public Menu menu;
    public EntityPlayer player;
    public HUD hud;
    public State gameState;
    public Window window;
    public Frame frame;
    public Inventory inventory;
    public AudioPlayer audioPlayer;

    public boolean cycleInc;
    public int cycle20;

    public static BufferedImage error;

    public enum State {
        Menu,
        Game,
        Inventory
    }



    public Game() {
        cycle20 = 0;
        cycleInc = true;
        Lib.init();
        gameState = State.Game;
        handler = new Handler();
        menu = new Menu(this);
        this.addMouseListener(new MouseHandler(this));
        this.addKeyListener(new KeyHandler(this));
        hud = new HUD(this);
        r = new Random();
        prepareWindow();
        error = Lib.getImage("src/resources/items/error.png");
        map = new Map(this,"testMap02.json", "tileset_world.png");
        inventory = new Inventory(this);
        audioPlayer = new AudioPlayer();
        audioPlayer.playAudio("DungeonTrack2.wav");
        dialogue = new Dialogue(this);
        init();

    }

    public void prepareWindow() {
        window = new Window(WIDTH, HEIGHT, "Sinbusters", this);
        frame = window.frame;
        frame.pack();
        insets = frame.getInsets();
        setInnerSize(WIDTH, HEIGHT);
        frame.setMinimumSize(getOuterSizeFromInner(WIDTH, HEIGHT));
    }

    public void init() {
        player = new EntityPlayer(370, 900, playerSpeed, this);
        EntityWormShooter worm = new EntityWormShooter(60, 60, this);
        handler.addEnt(player);
        EntityWormShooter worm1 = new EntityWormShooter(714, 475, this);
        EntityWormShooter worm2 = new EntityWormShooter(1122, 715, this);
        EntityWormShooter worm3 = new EntityWormShooter(718, 957, this);
        EntityWormShooter worm4 = new EntityWormShooter(317, 722, this);
        EntityNPC npc = new EntityNPC(544, 791, 2, this);
        handler.addEnt(npc);
        handler.addEnt(worm1);
        handler.addEnt(worm2);
        handler.addEnt(worm3);
        handler.addEnt(worm4);
        initComplete = true;
        player.setGreed(40);
        player.setEnvy(20);
        player.setWrath(10);
        dprint("Initialization complete!");
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Waits to finish initialization before the game loop starts. Fixes null pointers.
        while(!initComplete) {
            dprint("Waiting...");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.requestFocus();
        long lastTime = System.nanoTime();
        // Ticks per second.
        double amountOfTicks = 20;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                tick();
                delta--;
            }
            if(running) {
                render();
            }
            frames++;
            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                if(printFPS) {
                    System.out.println("FPS: " + frames);
                }
                frames = 0;
            }
        }
        stop();
    }

    public Dimension getInnerSize() {
        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        if (insets != null) {
            size.height -= insets.top + insets.bottom;
            size.width -= insets.left + insets.right;
        }
        return size;
    }

    public void setInnerSize(int x, int y) {
        frame.setSize(getOuterSizeFromInner(x, y));
    }

    public Dimension getOuterSizeFromInner(int x, int y) {
        Insets insets = frame.getInsets();
        return new Dimension(x + insets.left + insets.right, y + insets.top + insets.bottom);
    }

    // The tick method. Called as much as possible.
    private void tick() {
        cycle20 += cycleInc ? 1 : -1;
        if (cycle20 == 0 || cycle20 == 20) {
            cycleInc = !cycleInc;
        }
        if(gameState == State.Game) {
            handler.tick();
            hud.tick();
        }
    }

    public void onClick() {
        dprint("Inner Size: " + getInnerSize().width + ", " + getInnerSize().height);
        dprint("Frame Size: " + frame.getBounds().width + ", " + frame.getBounds().height);
        dprint("Insets: " + insets.top + ", " + insets.bottom + ", " + insets.left + ", " + insets.right);
        dprint("Gaps: " + gapWidth + ", " + gapHeight);
        dprint("Expansion: " + expansion);
        dprint("");
    }

    private void frameRenderStart(Graphics g, Graphics2D g2d) {
        // Make it so these values only update when need be.
        Dimension frameDimensions = getInnerSize();
        curWidth = frameDimensions.width;
        curHeight = frameDimensions.height;
        expansionX = (double) curWidth / (double) WIDTH;
        expansionY = (double) curHeight / (double) HEIGHT;
        expansion = expansionX > expansionY ? expansionY : expansionX;
        gameX = expansion * WIDTH;
        gameY = expansion * HEIGHT;
        g2d.translate((int)(((double)curWidth-(double)gameX)/2.0), (int)(((double)curHeight-(double)gameY)/2.0));
        g2d.scale(expansion, expansion);
        g.setColor(Color.black);
        g.fillRect(0, 0, curWidth, curHeight);
    }

    public void frameRenderEnd(Graphics g, Graphics2D g2d) {
        boolean isWide = expansionX > expansionY;
        gapHeight = isWide ? 0 : (int) (curHeight - (HEIGHT * expansion)) / 2;
        gapWidth = isWide ? (int) (curWidth - (WIDTH * expansion)) / 2 : 0;
        g.setColor(Color.black);

        g.fillRect(0, HEIGHT, curWidth, gapHeight);
        g.fillRect(WIDTH, 0, gapWidth, curHeight);

        g.fillRect(-gapWidth, -gapHeight, curWidth, gapHeight);
        g.fillRect(-gapWidth, -gapHeight, gapWidth, curHeight);
        g2d.scale(1/expansion, 1/expansion);
        g2d.translate((int)(((double)curWidth-(double)gameX)/-2d), (int)(((double)curHeight-(double)gameY)/-2d));
    }

    // The render method. Called as much as possible.
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        frameRenderStart(g, g2d);

        if(gameState != State.Menu) {
            int difX = (int) (player.getXMid() - WIDTH / 2);
            int difY = (int) (player.getYMid() - HEIGHT / 2);
            int minX = (int) (player.getXMid() - WIDTH / 2);
            int maxX = (int) (player.getXMid() + WIDTH / 2);
            int minY = (int) (player.getYMid() - HEIGHT / 2);
            int maxY = (int) (player.getYMid() + HEIGHT / 2);
            g2d.translate(-difX, -difY);
                // TRANSLATION START
                map.render(g, minX, maxX, minY, maxY);
                handler.render(g, minX, maxX, minY, maxY);
                handler.renderTop(g, minX, maxX, minY, maxY);
                map.renderTop(g, minX, maxX, minY, maxY);
                // TRANSLATION END
            g2d.translate(difX, difY);
            //Slot slot = new Slot(new ItemStack(null, 5), ItemType.Melee);
            g.setColor(Color.WHITE);
            g.drawString("03", 10, 0);
            //slot.draw(g,0, 0);
            if (gameState == State.Inventory) { inventory.render(g); }
            else if (gameState == State.Game) { hud.render(g); dialogue.render(g); }
        } else if (gameState == State.Menu) {
            menu.render(g);
        }

        frameRenderEnd(g, g2d);

        bs.show();
    }

    // The main method. Calls game, which starts both the constructor and the runnable run() method.
    public static void main(String args[]) {
        new Game();
    }

    // A method that prints if debug mode, a boolean in this class, is set to true.
    public static void dprint(Object object) {
        if(debugMode) {
            System.out.println(object);
        }
    }

    // Returns the handler.
    public Handler getHandler() {
        return handler;

    }

    // From a coordinate on the screen, returns the matching coordinate in the actual unstretched, drawed game window.
    public Coord getGamePos(int x, int y) {
        x -= gapWidth;
        y -= gapHeight;
        x /= expansion;
        y /= expansion;
        return new Coord(x, y);
    }

    // From a coordinate on the screen, returns the matching coordinate on the map.
    public Coord getMapPos(int x, int y) {
        x = getGamePos(x, y).x;
        y = getGamePos(x, y).y;
        return getMapPosFromGame(x, y);
    }

    // From a coordinate on the game screen, gets the corresponding map location.
    public Coord getMapPosFromGame(int x, int y) {
        float difX = player.getXMid() - WIDTH / 2;
        float difY = player.getYMid() - HEIGHT / 2;
        return new Coord(x + (int) difX, y + (int) difY);
    }

    // From a coordinate on the map, gets the coordinate in the game that is on screen.
    public Coord getGamePosFromMap(int x, int y) {
        float difX = player.getXMid() - WIDTH / 2;
        float difY = player.getYMid() - HEIGHT / 2;
        return new Coord(x - (int) difX, y - (int) difY);
    }
}
