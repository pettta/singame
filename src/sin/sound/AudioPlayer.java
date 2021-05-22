package sin.sound;

import sin.Game;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class AudioPlayer {

    public Clip clip;


    public void playAudio(String loc) {
        if(clip != null) {
            clip.stop();
        }
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/resources/music/" + loc));
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println("Error loading music.");
        }

    }

    public void playOnce(String loc) {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("src/resources/music/" + loc));
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println("Error loading music.");
        }

    }

}
