package edu.metronome.logic;

import java.lang.String;
import java.net.URL;
import javax.sound.sampled.*;
import java.io.*;

public class Click {
	
	private final int MINIMUM_TEMPO = 30;
	private final int MAXIMUM_TEMPO = 230;
	private final int DEFAULT_TEMPO = 120;
	private final int SECONDS_PER_MINUTE = 60;
	private final int MILLISECONDS_PER_SECOND = 1000;
	
	private int currentTempo = DEFAULT_TEMPO;
	private double timeBetweenClicksInMilliseconds = convertBmpToMilliseconds(DEFAULT_TEMPO);
	private boolean isPlaying = false;
	private String currentClickSoundFileName = "woodBlockClick.wav";
	private Clip clickSoundClip;
	
	public Click() {
		initializeClickSound();
	}
	
	public int getMinimumTempo() {
		return MINIMUM_TEMPO;
	}
	
	public int getMaximumTempo() {
		return MAXIMUM_TEMPO;
	}
	
	public int getCurrentTempo() {
		return currentTempo;
	}
	
	public int getDefaultTempo() {
		return DEFAULT_TEMPO;
	}
	
	public void setTempo(int value) {
		if((value < MINIMUM_TEMPO) || (value > MAXIMUM_TEMPO)) {
			//TODO: throw exception
		} else {
			currentTempo = value;
		}
	}
	
	public void play() {
		System.out.println("Playing");
		System.out.println("Bpm: " + currentTempo);
		System.out.println("Ms: " + convertBmpToMilliseconds(currentTempo));
		isPlaying = true;
		clickSoundClip.start();
	}
	
	//TODO: click is playing only one time for now
	
	public void stop() {
		System.out.println("Stopped");
		isPlaying = false;
		clickSoundClip.stop();
		/*
		try {
			Thread.sleep(timeBetweenClicksInMilliseconds);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		//*/
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	
	private void initializeClickSound() {
		try {
			URL clickSoundUrl = getClass().getClassLoader().getResource(currentClickSoundFileName);
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(clickSoundUrl); 
			clickSoundClip = AudioSystem.getClip();
			clickSoundClip.open(inputStream);
		} catch (UnsupportedAudioFileException e) {
	         e.printStackTrace();
	    } catch (IOException e) {
	         e.printStackTrace();
	    } catch (LineUnavailableException e) {
	    	e.printStackTrace();
	    }
	}
	
	//TODO: change casting, types, round or something
	private double convertBmpToMilliseconds(int bpm) {
		double beatsPerSecond = (double) (SECONDS_PER_MINUTE) / (double) (bpm);
		return (beatsPerSecond * MILLISECONDS_PER_SECOND);
	}
}
