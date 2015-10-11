package edu.metronome.logic;

public class Click {
	
	private final int DEFAULT_TEMPO = 120;
	
	private int currentTempo;
	
	public Click() {
		currentTempo = DEFAULT_TEMPO;
	}
	
	public int getCurrentTempo() {
		return currentTempo;
	}
}
