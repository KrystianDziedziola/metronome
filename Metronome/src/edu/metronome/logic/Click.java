package edu.metronome.logic;

import java.util.*;

public class Click {
	
	public final int MINIMUM_TEMPO = 30;
	public final int MAXIMUM_TEMPO = 230;
	public final int DEFAULT_TEMPO = 120;
	
	private final int SECONDS_PER_MINUTE = 60;
	private final int MILLISECONDS_PER_SECOND = 1000;
	
	private int currentTempo = DEFAULT_TEMPO;
	private int timeBetweenClicksInMilliseconds = convertBmpToMilliseconds(DEFAULT_TEMPO);
	private boolean isPlaying = false;
	
	
	private ArrayList<ClickSound> clickSoundClip = new ArrayList<ClickSound>();
	private int indexOfCurrentlyChosenSound = 0;
	
	private TempoOutOfBoundsException tempoOutOfBoundsException = 
			new TempoOutOfBoundsException();
	private ClickSoundIndexOutOfBoundsException clickSoundIndexOutOfBoundsException = 
			new ClickSoundIndexOutOfBoundsException();
	
	private Thread clickThread = null;
	
	public Click() {
		initializeClickSoundClips();
	}
	
	public int getCurrentTempo() {
		return currentTempo;
	}
	
	public void setTempo(int value) throws TempoOutOfBoundsException {
		if((value < MINIMUM_TEMPO) || (value > MAXIMUM_TEMPO)) {
			throw tempoOutOfBoundsException;
		} else {
			currentTempo = value;
			timeBetweenClicksInMilliseconds = convertBmpToMilliseconds(currentTempo);
		}
	}
	
	public void play() {
		isPlaying = true;
		if(clickThread == null) {
			createClickThread();
			clickThread.start();
		}
	}
	
	public void stop() {
		isPlaying = false;
		clickThread = null;
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	
	public String getClickSoundClipName(int index) {
		return clickSoundClip.get(index).getSoundName();
	}
	
	public int getNumberOfClickSounds() {
		return ClickSound.getNumberOfSounds();
	}
	
	public void setCurrentClickSound(int index) throws ClickSoundIndexOutOfBoundsException {
		if((index < 0) || (index >= ClickSound.getNumberOfSounds())) {
			throw clickSoundIndexOutOfBoundsException;
		} else {
			indexOfCurrentlyChosenSound = index;
		}
	}
	
	
	private void initializeClickSoundClips() {
		//clickSoundClip.add(new ClickSound("Wood", "woodBlockClick.wav"));
		//clickSoundClip.add(new ClickSound("Steel", "steelBlockClick.wav"));
		clickSoundClip.add(new ClickSound("Cowbell", "cowbell.wav", "cowbellAccent.wav"));
	}
	
	private void createClickThread() {
		clickThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(isPlaying) {
					//TODO: dorobic granie akcentow i wybieranie 
					clickSoundClip.get(indexOfCurrentlyChosenSound).playUnaccentedSound();
					pauseClickThread(timeBetweenClicksInMilliseconds);
					clickSoundClip.get(indexOfCurrentlyChosenSound).resetUnaccentedSound();
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
	
	private int convertBmpToMilliseconds(int bpm) {
		double beatsPerSecond = (double) SECONDS_PER_MINUTE / (double) bpm;
		double millisecondsBetweenClick = beatsPerSecond * MILLISECONDS_PER_SECOND;
		return (int) Math.round(millisecondsBetweenClick);
	}
}