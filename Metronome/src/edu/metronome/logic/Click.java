package edu.metronome.logic;

import java.util.*;
import edu.metronome.logic.exceptions.*;

public class Click {
	
	public final int MINIMUM_TEMPO = 30;
	public final int MAXIMUM_TEMPO = 230;
	public final int DEFAULT_TEMPO = 120;
	public final int MINIMUM_BEATS_PER_BAR = 1;
	public final int MAXIMUM_BEATS_PER_BAR = 9;
	public final int DEFAULT_BEATS_PER_BAR = 4;
	public final int MAXIMUM_NUMBER_OF_BARS_WITH_CLICK = 8;
	public final int MINIMUM_NUMBER_OF_BARS_WITH_CLICK = 1;
	public final int DEFAULT_NUMBER_OF_BARS_WITH_CLICK = 3;
	public final int MAXIMUM_NUMBER_OF_BARS_WITHOUT_CLICK = 8;
	public final int MINIMUM_NUMBER_OF_BARS_WITHOUT_CLICK = 1;
	public final int DEFAULT_NUMBER_OF_BARS_WITHOUT_CLICK = 1;
	
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
	private NumberOfBarsOutOfBoundsException numberOfBarsOutOfBoundsException = 
			new NumberOfBarsOutOfBoundsException();
	
	private Thread clickThread = null;
	
	private int currentBeat = 1;
	private int beatsPerBar = DEFAULT_BEATS_PER_BAR;
	
	private int numberOfBarsWithClick = DEFAULT_NUMBER_OF_BARS_WITH_CLICK;
	private int numberOfBarsWithoutClick = DEFAULT_NUMBER_OF_BARS_WITHOUT_CLICK;
	private int currentBar = 1;
	
	private boolean isTimeTrainerEnabled = false;
	
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
		prepareForPlaying();
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
	
	public void setNumberOfBarsWithClick(int number) throws NumberOfBarsOutOfBoundsException {
		if(isNumberOfBarsInRange(number)){
			numberOfBarsWithClick = number;
		} else {
			throw numberOfBarsOutOfBoundsException;
		}
	}
	
	public void setNumberOfBarsWithoutClick(int number) throws NumberOfBarsOutOfBoundsException {
		if(isNumberOfBarsInRange(number)){
			numberOfBarsWithoutClick = number;
		} else {
			throw numberOfBarsOutOfBoundsException;
		}
	}
	
	public boolean isTimeTrainerEnabled() {
		return isTimeTrainerEnabled;
	}
	
	public void setTimeTrainerEnabled(boolean isEnabled) {
		isTimeTrainerEnabled = isEnabled;
	}
	
	private boolean isNumberOfBarsInRange(int number) {
		if((number >= MINIMUM_NUMBER_OF_BARS_WITH_CLICK) && (number <= MAXIMUM_NUMBER_OF_BARS_WITH_CLICK)) {
			return true;
		} else {
			return false;
		}
	}
	
	private void initializeClickSoundClips() {
		clickSoundClip.add(new ClickSound("Cowbell", "cowbell.wav", "cowbellAccent.wav"));
		clickSoundClip.add(new ClickSound("Classic", "classic.wav", "classicAccent.wav"));
		//clickSoundClip.add(new ClickSound("Clave", "clave.wav", "claveAccent.wav"));
		//clickSoundClip.add(new ClickSound("Ping", "ping.wav", "pingAccent.wav"));
		//clickSoundClip.add(new ClickSound("Rim", "rim.wav", "rimAccent.wav"));
		//FIXME: do they don't work because of their format?
	}
	
	private void createClickThread() {
		clickThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(isPlaying) {
					playAppropriateClickSound();
					pauseClickThread(timeBetweenClicksInMilliseconds);
					/*if(currentBeat == beatsPerBar) {
						changeCurrentBarNumber();
					}*/
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
	
	private void changeCurrentBarNumber() {
		//if(currentBar == )
		currentBar++;
	}
	
	private void increaseCurrentBeat() {
		if(currentBeat < beatsPerBar) {
			currentBeat++;
		} else {
			currentBeat = BEAT_AT_BEGINNING_OF_BAR;
		}
	}
	
	private void prepareForPlaying() {
		isPlaying = true;
		currentBeat = BEAT_AT_BEGINNING_OF_BAR;
	}
	
}