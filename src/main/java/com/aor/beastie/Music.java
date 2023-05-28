package com.aor.beastie;

import javax.sound.sampled.*;
import java.io.File;

public class Music {
    public static void soundEffect(String filename){
        try {
            File musicpath = new File(filename).getAbsoluteFile();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicpath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void playMusic() {
        try {
            File musicpath = new File("src/main/resources/mainSong.wav").getAbsoluteFile();
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicpath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
