package edu.metronome.logic;

public class BeatsPerBarOutOfBoundsException extends Exception{
	
	public BeatsPerBarOutOfBoundsException() {
		super("Beats per bar value is out of bounds");
	}
}
