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
		resetSound(accentedSoundClip);
		accentedSoundClip.start();
	}
	
	public void playUnaccentedSound() {
		resetSound(unaccentedSoundClip);
		unaccentedSoundClip.start();
	}
	
	public String getSoundName() {
		return soundName;
	}
	
	public static int getNumberOfSounds() {
		return numberOfSounds;
	}
	
	private void resetSound(Clip soundName) {
		soundName.flush();
		soundName.setFramePosition(0);
	}
	
	private void initializeClickSounds() {
		try {
			unaccentedSoundClip = getSoundClip(unaccentedSoundFileName);
			accentedSoundClip = getSoundClip(accentedSoundFileName);
		} catch (Exception e) {
	         e.printStackTrace();
	    }
	}
	
	private Clip getSoundClip(String soundFileName) 
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		URL clickSoundUrl = getClass().getClassLoader().getResource(soundFileName);
		AudioInputStream inputStream = AudioSystem.getAudioInputStream(clickSoundUrl); 
		Clip soundClip = AudioSystem.getClip();
		soundClip.open(inputStream);
		return soundClip;
	}
	
}
