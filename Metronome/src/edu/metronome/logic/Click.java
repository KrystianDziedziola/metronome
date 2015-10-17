package edu.metronome.logic;

import java.lang.String;
import java.net.URL;
import javax.sound.sampled.*;
import java.io.*;


public class Click {
	
	public final int MINIMUM_TEMPO = 30;
	public final int MAXIMUM_TEMPO = 230;
	public final int DEFAULT_TEMPO = 120;
	
	private final int SECONDS_PER_MINUTE = 60;
	private final int MILLISECONDS_PER_SECOND = 1000;
	
	private int currentTempo = DEFAULT_TEMPO;
	private int timeBetweenClicksInMilliseconds = convertBmpToMilliseconds(DEFAULT_TEMPO);
	private boolean isPlaying = false;
	private String currentClickSoundFileName = "woodBlockClick.wav";
	private Clip clickSoundClip;
	
	private TempoOutOfBoundsException tempoOutOfBoundsException = 
			new TempoOutOfBoundsException("Tempo out of bounds");
	
	private Thread clickThread = null;
	
	
	public Click() {
		initializeClickSound();
	}
	
	public int getCurrentTempo() {
		return currentTempo;
	}
	
	public void setTempo(int value) throws TempoOutOfBoundsException {
		if((value < MINIMUM_TEMPO) || (value > MAXIMUM_TEMPO)) {
			throw tempoOutOfBoundsException;
		} else {
			currentTempo = value;
		}
	}
	
	public void play() {
		isPlaying = true;
		if(clickThread == null) {
			timeBetweenClicksInMilliseconds = convertBmpToMilliseconds(currentTempo);
			createClickThread();
			clickThread.start();
		}
	}
	
	private void createClickThread() {
		clickThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					clickSoundClip.start();
					pauseClickThread(timeBetweenClicksInMilliseconds);
					resetClickSoundClip();
				}
			}
		});
	}
	
	private void pauseClickThread(int timeInMilliseconds) {
		try {
			Thread.sleep(timeInMilliseconds);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void resetClickSoundClip() {
		clickSoundClip.flush();
		clickSoundClip.setFramePosition(0);
	}
	
	public void stop() {
		isPlaying = false;
		if(clickThread != null) {
			clickThread.stop();
			clickThread = null;
		}
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
	
	private int convertBmpToMilliseconds(int bpm) {
		double beatsPerSecond = (double) SECONDS_PER_MINUTE / (double) bpm;
		double millisecondsBetweenClick = beatsPerSecond * MILLISECONDS_PER_SECOND;
		return (int) Math.round(millisecondsBetweenClick);
	}
}