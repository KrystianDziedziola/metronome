package edu.metronome.logic;

import java.util.*;

public class Click {
	
	public final int MINIMUM_TEMPO = 30;
	public final int MAXIMUM_TEMPO = 230;
	public final int DEFAULT_TEMPO = 120;
	public final int MINIMUM_BEATS_PER_BAR = 1;
	public final int MAXIMUM_BEATS_PER_BAR = 9;
	public final int DEFAULT_BEATS_PER_BAR = 4;
	
	private final int SECONDS_PER_MINUTE = 60;
	private final int MILLISECONDS_PER_SECOND = 1000;
	private final int BEAT_AT_BEGINNING_OF_BAR = 1;
	
	private int currentTempo = DEFAULT_TEMPO;
	private int timeBetweenClicksInMilliseconds = convertBmpToMilliseconds(DEFAULT_TEMPO);
	private boolean isPlaying = false;
	
	private ArrayList<ClickSound> clickSoundClip = new ArrayList<ClickSound>();
	private int indexOfCurrentlyChosenSound = 0;
	
	private TempoOutOfBoundsException tempoOutOfBoundsException = 
			new TempoOutOfBoundsException();
	private ClickSoundIndexOutOfBoundsException clickSoundIndexOutOfBoundsException = 
			new ClickSoundIndexOutOfBoundsException();
	private BeatsPerBarOutOfBoundsException beatsPerBarOutOfBoundsException = 
			new BeatsPerBarOutOfBoundsException();
	
	private Thread clickThread = null;
	
	private int currentBeat = 1;
	private int beatsPerBar = 4;
	
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
		if((index >= 0) && (index < ClickSound.getNumberOfSounds())) {
			indexOfCurrentlyChosenSound = index;
		} else {
			throw clickSoundIndexOutOfBoundsException;
		}
	}
	
	public void setBeatsPerBar(int beatsPerBar) throws BeatsPerBarOutOfBoundsException {
		if((beatsPerBar >= MINIMUM_BEATS_PER_BAR) && (beatsPerBar <= MAXIMUM_BEATS_PER_BAR)) {
			this.beatsPerBar = beatsPerBar;
		} else {
			throw beatsPerBarOutOfBoundsException;
		}
	}
	
	
	private void initializeClickSoundClips() {
		clickSoundClip.add(new ClickSound("Cowbell", "cowbell.wav", "cowbellAccent.wav"));
		//clickSoundClip.add(new ClickSound("Clave", "clave.wav", "claveAccent.wav"));
		//clickSoundClip.add(new ClickSound("Ping", "ping.wav", "pingAccent.wav"));
		//clickSoundClip.add(new ClickSound("Rim", "rim.wav", "rimAccent.wav"));
		clickSoundClip.add(new ClickSound("Classic", "classic.wav", "classicAccent.wav"));
	}
	
	private void createClickThread() {
		clickThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(isPlaying) {
					playAppropriateClickSound();
					pauseClickThread(timeBetweenClicksInMilliseconds);
					increaseCurrentBeat();
				}
			}
		});
	}
	
	private void playAppropriateClickSound() {
		if(currentBeat == BEAT_AT_BEGINNING_OF_BAR) {
			clickSoundClip.get(indexOfCurrentlyChosenSound).playAccentedSound();
		} else {
			clickSoundClip.get(indexOfCurrentlyChosenSound).playUnaccentedSound();
		}
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
	
	private void increaseCurrentBeat() {
		if(currentBeat < beatsPerBar) {
			currentBeat++;
		} else {
			currentBeat = BEAT_AT_BEGINNING_OF_BAR;
		}
	}
}