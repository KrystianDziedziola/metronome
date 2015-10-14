package edu.metronome.main;

import java.awt.EventQueue;

import edu.metronome.ui.MainWindow;

public class Main {

	public Main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new Main(args);
	}
}
