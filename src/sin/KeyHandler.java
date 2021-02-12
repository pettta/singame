package sin;

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

    Set<Integer> keys = new TreeSet<Integer>();

    // TODO This code might have to be reworked slightly to account for
    // say, slowdown effects or something.
    public KeyHandler(Game game) {
        this.game = game;
        playerSpeed = game.playerSpeed;
        playerSpeedDiagonal = playerSpeed / Lib.root;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if(!keys.contains(k)) {
            if(k == W) {
                if(keys.contains(A) || keys.contains(D)) {
                    game.player.setVelY(-playerSpeedDiagonal);
                    game.player.setVelX(game.player.getVelX() > 0 ? playerSpeedDiagonal : -playerSpeedDiagonal);
                } else {
                    game.player.setVelY(-playerSpeed);
                }
                game.player.updateImage();
            }
            if(k == A) {
                if(keys.contains(W) || keys.contains(S)) {
                    game.player.setVelX(-playerSpeedDiagonal);
                    game.player.setVelY(game.player.getVelY() > 0 ? playerSpeedDiagonal : -playerSpeedDiagonal);
                } else {
                    game.player.setVelX(-playerSpeed);
                }
                game.player.updateImage();
            }
            if(k == S) {
                if(keys.contains(A) || keys.contains(D)) {
                    game.player.setVelY(playerSpeedDiagonal);
                    game.player.setVelX(game.player.getVelX() > 0 ? playerSpeedDiagonal : -playerSpeedDiagonal);
                } else {
                    game.player.setVelY(playerSpeed);
                }
                game.player.updateImage();
            }
            if(k == D) {
                if(keys.contains(W) || keys.contains(S)) {
                    game.player.setVelX(playerSpeedDiagonal);
                    game.player.setVelY(game.player.getVelY() > 0 ? playerSpeedDiagonal : -playerSpeedDiagonal);
                } else {
                    game.player.setVelX(playerSpeed);
                }
                game.player.updateImage();
            }
            keys.add(k);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int k = e.getKeyCode();
        if(k == W) {
            game.player.updateLastDirection();
            if(keys.contains(S)) {
                if (keys.contains(A) || keys.contains(D)) {
                    game.player.setVelY(playerSpeedDiagonal);
                } else {
                    game.player.setVelY(playerSpeed);
                }
            } else {
                if (keys.contains(A) || keys.contains(D)) {
                    game.player.setVelX(game.player.getVelX() > 0 ? playerSpeed : -playerSpeed);
                }
                game.player.setVelY(0);
            }
            game.player.updateImage();
        }
        if(k == A) {
            game.player.updateLastDirection();
            if(keys.contains(D)) {
                if (keys.contains(W) || keys.contains(S)) {
                    game.player.setVelX(playerSpeedDiagonal);
                } else {
                    game.player.setVelX(playerSpeed);
                }


            } else {
                if (keys.contains(W) || keys.contains(S)) {
                    game.player.setVelY(game.player.getVelY() > 0 ? playerSpeed : -playerSpeed);
                }
                game.player.setVelX(0);
            }
            game.player.updateImage();
        }
        if(k == S) {
            game.player.updateLastDirection();
            if(keys.contains(W)) {
                if (keys.contains(A) || keys.contains(D)) {
                    game.player.setVelY(-playerSpeedDiagonal);
                } else {
                    game.player.setVelY(-playerSpeed);
                }
            } else {
                if (keys.contains(A) || keys.contains(D)) {
                    game.player.setVelX(game.player.getVelX() > 0 ? playerSpeed : -playerSpeed);
                }
                game.player.setVelY(0);
            }
            game.player.updateImage();
        }
        if(k == D) {
            game.player.updateLastDirection();
            if(keys.contains(A)) {
                if (keys.contains(W) || keys.contains(S)) {
                    game.player.setVelX(-playerSpeedDiagonal);
                } else {
                    game.player.setVelX(-playerSpeed);
                }
            } else {
                if (keys.contains(W) || keys.contains(S)) {
                    game.player.setVelY(game.player.getVelY() > 0 ? playerSpeed : -playerSpeed);
                }
                game.player.setVelX(0);
            }
            game.player.updateImage();
        }
        keys.remove(e.getKeyCode());
    }

}

