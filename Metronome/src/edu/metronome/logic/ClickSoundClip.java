package edu.metronome.logic;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

public class ClickSoundClip {

	private String soundName;
	private String fileName;
	private Clip soundClip = null;
	private static int numberOfSounds = 0;
	
	public ClickSoundClip(String soundName, String fileName) {
		numberOfSounds++;
		this.soundName = soundName;
		this.fileName = fileName;
		initializeClickSound();
	}
	
	public void play() {
		soundClip.start();
	}
	
	public void reset() {
		soundClip.flush();
		soundClip.setFramePosition(0);
	}
	
	public String getSoundName() {
		return soundName;
	}
	
	public static int getNumberOfSounds() {
		return numberOfSounds;
	}
	
	private void initializeClickSound() {
		try {
			URL clickSoundUrl = getClass().getClassLoader().getResource(fileName);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(clickSoundUrl); 
			soundClip = AudioSystem.getClip();
			soundClip.open(inputStream);
		} catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	    } catch (IOException e) {
	         e.printStackTrace();
	    } catch (LineUnavailableException e) {
	    	e.printStackTrace();
	    }
	}
	
}
