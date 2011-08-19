package org.crusty.engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class SoundManager {
	
	private static InputStream loadSound(String str) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(str);
		if (url == null) {
			System.out.println("Can't find: " + str);
			return null;
		}
		InputStream in = null;
		try {
			in = url.openStream();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return in;
	}
	
	/** Look for sound in static HashMap, and play it */
	public static void playSound(String str) {
		InputStream in = null;
		try {
			in = loadSound(str);
			if (in == null)
				return;
			AudioStream as = new AudioStream(in);       
			AudioPlayer.player.start(as);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
