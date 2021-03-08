package sin;

import sin.display.HUD;
import sin.display.Menu;
import sin.lib.Lib;
import sin.mundus.materia.entity.Player;
import sin.mundus.materia.entity.WormShooter;
import sin.mundus.map.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 320, HEIGHT = 240;
    public static final float playerSpeed = 5;
    public static final boolean debugMode = true;
    private boolean running = false;
    private boolean initComplete = false;

    public int curWidth, curHeight;

    private Thread thread;
    private Random r;


    public Handler handler;
    public Map map;
    public Menu menu;
    public Player player;
    public HUD hud;
    public State gameState;
    public Window window;



    public enum State {
        Menu,
        Game,
        Inventory
    }

    public Game() {
        Lib.init();
        gameState = State.Game;
        handler = new Handler();
        menu = new Menu(this);
        this.addMouseListener(new MouseHandler(this));
        this.addKeyListener(new KeyHandler(this));
        hud = new HUD();
        r = new Random();
        window = new Window(WIDTH, HEIGHT, "Sinbusters", this);
        map = new Map(this,"testMap02.json", "tileset_world.png");
        init();

    }

    public void init() {
        System.out.println(32 / 16 + (32 % 16 > 0 ? 1 : 0));
        player = new Player(400, 900, playerSpeed, this);
        WormShooter worm = new WormShooter(60, 60, this);
        handler.addEnt(player);
        WormShooter worm1 = new WormShooter(714, 475, this);
        WormShooter worm2 = new WormShooter(1122, 715, this);
        WormShooter worm3 = new WormShooter(718, 957, this);
        WormShooter worm4 = new WormShooter(317, 722, this);
        handler.addEnt(worm1);
        handler.addEnt(worm2);
        handler.addEnt(worm3);
        handler.addEnt(worm4);
        initComplete = true;
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
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    // The tick method. Called as much as possible.
    private void tick() {
        if(gameState == State.Game) {
            handler.tick();
            hud.tick();
        }
    }

    JFrame frame;
    double expansionX, expansionY, expansion, gameX, gameY;

    private void prepareFrame(Graphics g, Graphics2D g2d) {
        // Make it so these values only update when need be.
        JFrame frame = window.frame;
        Dimension frameDimensions = frame.getBounds().getSize();
        curWidth = frameDimensions.width;
        curHeight = frameDimensions.height;
        expansionX = (double)curWidth / (double)WIDTH;
        expansionY = (double)curHeight / (double)HEIGHT;
        expansion = expansionX > expansionY ? expansionY : expansionX;
        gameX = expansion * WIDTH;
        gameY = expansion * HEIGHT;
        g2d.translate((int)(((double)curWidth-(double)gameX)/2d), (int)(((double)curHeight-(double)gameY)/2d));
        g2d.scale(expansion, expansion);

        g.setColor(Color.black);
        g.fillRect(0, 0, curWidth, curHeight);

    }

    public void encloseFrame(Graphics g, Graphics g2d) {
        int gapHeight = (curHeight - HEIGHT) / 2;
        int gapWidth = (curWidth - WIDTH) / 2;
        g.setColor(Color.black);

        g.fillRect(0, HEIGHT, curWidth, gapHeight);
        g.fillRect(WIDTH, 0, gapWidth, curHeight);

        g.fillRect(-gapWidth - 1, -gapHeight - 1, curWidth + 1, gapHeight + 1);
        g.fillRect(-gapWidth - 1, -gapHeight - 1, gapWidth + 1, curHeight + 1);

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
        prepareFrame(g, g2d);

        if(gameState == State.Game) {
            float difx = player.getXMid() - WIDTH / 2;
            float dify = player.getYMid() - HEIGHT / 2;
            g2d.translate(-difx, -dify);
            // TRANSLATION START
            map.render(g);
            handler.render(g);
            handler.renderTop(g);
            map.renderTop(g);
            // TRANSLATION END
            g2d.translate(difx, dify);
            //hud.render(g);
        } else if (gameState == State.Menu) {
            menu.render(g);
        } else if (gameState == State.Inventory) {

        }

        encloseFrame(g, g2d);
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
}
