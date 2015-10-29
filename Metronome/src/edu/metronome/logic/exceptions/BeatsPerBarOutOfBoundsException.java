package edu.metronome.logic.exceptions;

public class BeatsPerBarOutOfBoundsException extends Exception{
	
	public BeatsPerBarOutOfBoundsException() {
		super("Beats per bar value is out of bounds");
	}
}
