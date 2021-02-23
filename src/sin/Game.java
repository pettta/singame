package sin;

import sin.display.HUD;
import sin.display.Menu;
import sin.lib.Lib;
import sin.materia.entity.Player;
import sin.materia.entity.WormShooter;
import sin.mundus.Map;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 640, HEIGHT = 480;
    public static final float playerSpeed = 7;
    public static final boolean debugMode = true;
    private boolean running = false;
    private boolean initComplete = false;

    private Thread thread;
    private Random r;


    public Handler handler;
    public Map map;
    public Menu menu;
    public Player player;
    public HUD hud;
    public State gameState;



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
        new Window(WIDTH, HEIGHT, "Sinbusters", this);
        map = new Map(this,"testMap02.json", "tileset_world.png");
        init();

    }

    public void init() {
        System.out.println(32 / 16 + (32 % 16 > 0 ? 1 : 0));
        player = new Player(883, 438, playerSpeed, this);
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

    // The render method. Called as much as possible.
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);
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
            hud.render(g);
        } else if (gameState == State.Menu) {
            menu.render(g);
        } else if (gameState == State.Inventory) {

        }
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
