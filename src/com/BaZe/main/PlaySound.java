package com.BaZe.main;

import java.io.File;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class PlaySound {
	public static synchronized void playSound(final String filename, int volume) {
		  new Thread(new Runnable() {
			  public void run() {
			      try {
					  Baze.Logs("Playing Sound" + filename);
					  Clip clip = AudioSystem.getClip();
					  AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("resources/sounds/" + filename).getAbsoluteFile());


					  clip.open(inputStream);
					  clip.start();
					  FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					  float range = control.getMinimum();
					  float result = range * (1 - volume / 100.0f);
					  control.setValue(result);
			      } catch (Exception e) {
			        System.err.println(e.getMessage());
			      }
			  }
		  }).start();
	}
}