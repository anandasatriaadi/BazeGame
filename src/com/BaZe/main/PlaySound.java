package com.BaZe.main;

import java.io.File;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlaySound {
	public static synchronized void playSound(final String url) {
		  new Thread(new Runnable() {
			  // The wrapper thread is unnecessary, unless it blocks on the
			  // Clip finishing; see comments.
		    public void run() {
		      try {
				  Baze.Logs("Playing Sound" + url);
				  Clip clip = AudioSystem.getClip();
				  AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("resources/sounds/" + url).getAbsoluteFile());
				  clip.open(inputStream);
				  clip.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}
}
