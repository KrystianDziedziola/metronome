package edu.metronome.logic;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.*;

public class ClickSound {

	private String soundName;
	private String unaccentedSoundFileName;
	private String accentedSoundFileName;
	private Clip accentedSoundClip = null;
	private Clip unaccentedSoundClip = null;
	private static int numberOfSounds = 0;
	
	public ClickSound(String soundName, String unaccentedSoundFileName, String accentedSoundFileName) {
		numberOfSounds++;
		this.soundName = soundName;
		this.unaccentedSoundFileName = unaccentedSoundFileName;
		this.accentedSoundFileName = accentedSoundFileName;
		initializeClickSounds();
	}
	
	public void playAccentedSound() {
		accentedSoundClip.start();
	}
	
	public void playUnaccentedSound() {
		unaccentedSoundClip.start();
	}
	
	public void resetAccentedSound() {
		accentedSoundClip.flush();
		accentedSoundClip.setFramePosition(0);
	}
	
	public void resetUnaccentedSound() {
		unaccentedSoundClip.flush();
		unaccentedSoundClip.setFramePosition(0);
	}
	
	public String getSoundName() {
		return soundName;
	}
	
	public static int getNumberOfSounds() {
		return numberOfSounds;
	}
	
	private void initializeClickSounds() {
		try {
			unaccentedSoundClip = getSoundClip(unaccentedSoundFileName);
			accentedSoundClip = getSoundClip(accentedSoundFileName);
		} catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	    } catch (IOException e) {
	         e.printStackTrace();
	    } catch (LineUnavailableException e) {
	    	e.printStackTrace();
	    }
	}
	
	//TODO: jak przez referencjê?
	private Clip getSoundClip(String soundFileName) 
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		URL clickSoundUrl = getClass().getClassLoader().getResource(soundFileName);
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(clickSoundUrl); 
		Clip soundClip = AudioSystem.getClip();
		soundClip.open(inputStream);
		return soundClip;
	}
	
}
