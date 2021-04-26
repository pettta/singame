package sin;

import sin.display.Menu;
import sin.lib.Lib;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.TreeSet;

public class KeyHandler extends KeyAdapter {

    public static final int W = KeyEvent.VK_W;
    public static final int A = KeyEvent.VK_A;
    public static final int S = KeyEvent.VK_S;
    public static final int D = KeyEvent.VK_D;

    private float playerSpeed;
    private float playerSpeedDiagonal;

    private Game game;

    public Set<Integer> keys = new TreeSet<Integer>();

    // TODO This code might have to be reworked slightly to account for speed changing effects.
    public KeyHandler(Game game) {
        this.game = game;
        playerSpeed = game.playerSpeed;
        playerSpeedDiagonal = 4;
    }

    // TODO Make this code work when keys are pressed in menus and off tab.
    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if(!keys.contains(k)) {
            if(game.gameState == Game.State.Game) {
                if (k == W) {
                    if (keys.contains(A) || keys.contains(D)) {
                        game.player.setVelY(-playerSpeedDiagonal);
                        game.player.setVelX(playerSpeedDiagonal * Math.signum(game.player.getVelX()));
                    } else {
                        game.player.setVelY(-playerSpeed);
                    }
                    game.player.updateImage();
                }
                if (k == A) {
                    if (keys.contains(W) || keys.contains(S)) {
                        game.player.setVelX(-playerSpeedDiagonal);
                        game.player.setVelY(playerSpeedDiagonal * Math.signum(game.player.getVelY()));
                    } else {
                        game.player.setVelX(-playerSpeed);
                    }
                    game.player.updateImage();
                }
                if (k == S) {
                    if (keys.contains(A) || keys.contains(D)) {
                        game.player.setVelY(playerSpeedDiagonal);
                        game.player.setVelX(playerSpeedDiagonal * Math.signum(game.player.getVelX()));
                    } else {
                        game.player.setVelY(playerSpeed);
                    }
                    game.player.updateImage();
                }
                if (k == D) {
                    if (keys.contains(W) || keys.contains(S)) {
                        game.player.setVelX(playerSpeedDiagonal);
                        game.player.setVelY(playerSpeedDiagonal * Math.signum(game.player.getVelY()));
                    } else {
                        game.player.setVelX(playerSpeed);
                    }
                    game.player.updateImage();
                }
            }
            if(k == KeyEvent.VK_E) {
                if(game.gameState == Game.State.Game) {
                    game.gameState = Game.State.Inventory;
                } else if(game.gameState == Game.State.Inventory) {
                    game.gameState = Game.State.Game;
                }
            }
            if(k == KeyEvent.VK_F) {
                if(game.gameState == Game.State.Game) {
                    if(!game.dialogue.dialogue) {
                        game.dialogue.talk("My name is Yoshikage Kira. I'm 33 years old. My house is in the northeast section of Morioh, where all the villas are, and I am not married. I work as an employee for the Kame Yu department stores, and I get home every day by 8 PM at the latest. I don't smoke, but I occasionally drink. I'm in bed by 11 PM, and make sure I get eight hours of sleep, no matter what. After having a glass of warm milk and doing about twenty minutes of stretches before going to bed, I usually have no problems sleeping until morning. Just like a baby, I wake up without any fatigue or stress in the morning. I was told there were no issues at my last check-up. I'm trying to explain that I'm a person who wishes to live a very quiet life. I take care not to trouble myself with any enemies, like winning and losing, that would cause me to lose sleep at night. That is how I deal with society, and I know that is what ");
                    } else {
                        game.dialogue.next();
                    }
                }
            }
            if(k == KeyEvent.VK_ESCAPE) {
                if(game.gameState == Game.State.Game) {
                    game.gameState = Game.State.Menu;
                    game.menu.state = Menu.MenuState.Main;
                } else if(game.gameState == Game.State.Menu) {
                    game.gameState = Game.State.Game;
                } else if(game.gameState == Game.State.Inventory) {
                    game.gameState = Game.State.Game;
                }
                System.out.println(game.gameState);
            }
            if(game.gameState == Game.State.Game) {
                if(k == KeyEvent.VK_F) {
                    game.player.specialAttack();
                }
            }
            keys.add(k);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if(game.gameState == Game.State.Game) {
            if (k == W) {
                game.player.updateLastDirection();
                if (keys.contains(S)) {
                    if (keys.contains(A) || keys.contains(D)) {
                        game.player.setVelY(playerSpeedDiagonal);
                    } else {
                        game.player.setVelY(playerSpeed);
                    }
                } else {
                    if (keys.contains(A) || keys.contains(D)) {
                        game.player.setVelX(playerSpeed * Math.signum(game.player.getVelX()));
                    }
                    game.player.setVelY(0);
                }
                game.player.updateImage();
            }
            if (k == A) {
                game.player.updateLastDirection();
                if (keys.contains(D)) {
                    if (keys.contains(W) || keys.contains(S)) {
                        game.player.setVelX(playerSpeedDiagonal);
                    } else {
                        game.player.setVelX(playerSpeed);
                    }


                } else {
                    if (keys.contains(W) || keys.contains(S)) {
                        game.player.setVelY(playerSpeed * Math.signum(game.player.getVelY()));
                    }
                    game.player.setVelX(0);
                }
                game.player.updateImage();
            }
            if (k == S) {
                game.player.updateLastDirection();
                if (keys.contains(W)) {
                    if (keys.contains(A) || keys.contains(D)) {
                        game.player.setVelY(-playerSpeedDiagonal);
                    } else {
                        game.player.setVelY(-playerSpeed);
                    }
                } else {
                    if (keys.contains(A) || keys.contains(D)) {
                        game.player.setVelX(playerSpeed * Math.signum(game.player.getVelX()));
                    }
                    game.player.setVelY(0);
                }
                game.player.updateImage();
            }
            if (k == D) {
                game.player.updateLastDirection();
                if (keys.contains(A)) {
                    if (keys.contains(W) || keys.contains(S)) {
                        game.player.setVelX(-playerSpeedDiagonal);
                    } else {
                        game.player.setVelX(-playerSpeed);
                    }
                } else {
                    if (keys.contains(W) || keys.contains(S)) {
                        game.player.setVelY(playerSpeed * Math.signum(game.player.getVelY()));
                    }
                    game.player.setVelX(0);
                }
                game.player.updateImage();
            }
        }
        keys.remove(e.getKeyCode());
    }

}

