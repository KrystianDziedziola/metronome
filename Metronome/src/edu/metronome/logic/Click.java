package edu.metronome.logic;

public class Click {
	
	private final int MINIMUM_TEMPO = 30;
	private final int MAXIMUM_TEMPO = 230;
	private final int DEFAULT_TEMPO = 120;
	
	private int currentTempo;
	
	public Click() {
		currentTempo = DEFAULT_TEMPO;
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
	
	public void setTempo(final int VALUE) {
		if((VALUE < MINIMUM_TEMPO) || (VALUE > MAXIMUM_TEMPO)) {
			//TODO: throw exception
		} else {
			currentTempo = VALUE;
		}
	}
}
