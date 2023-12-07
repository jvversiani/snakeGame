package SnakeGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Random;


public class EatAppleSounds implements Runnable {
    private static final Random random = new Random();
    private static final String[] eating = new String[]{
            "src/Sounds/eatSound1.wav", "src/Sounds/eatSound2.wav", "src/Sounds/eatSound3.wav"};

    @Override
    public void run() {
        eatApple();
    }

    public static void eatApple() {
        try {
            File audioFile = new File(eating[random.nextInt(0, eating.length)]);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);

            // Get a Clip object to play the sound
            Clip clip = AudioSystem.getClip();

            // Open the audio input stream and start playing
            clip.open(audioInputStream);
            clip.start();

            // Optionally, wait for the clip to finish playing
             Thread.sleep(clip.getMicrosecondLength() / 1000);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}