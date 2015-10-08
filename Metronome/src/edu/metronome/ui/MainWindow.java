package edu.metronome.ui;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow {
	private JFrame mainFrame;
	private Dimension mainFrameDimension = new Dimension(400, 500);
		
	private JButton togglePlayButton;
	private Dimension togglePlayButtonDimension = new Dimension(100, 50);

	public MainWindow() {
		initialize();
	}
	
	public void show() {
		mainFrame.setVisible(true);
	}
	
	

	private void initialize() {
		initializeMainFrame();
		initializeButtons();
	}
	
	private void initializeMainFrame() {
		mainFrame = new JFrame();
		mainFrame.getContentPane().setLayout(null);
		setupMainFrameProperties();
		centerMainFrame();
	}
	
	private void setupMainFrameProperties() {
		mainFrame.setTitle("Metronome");
		mainFrame.setSize(mainFrameDimension);
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void centerMainFrame() {
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((screenDimension.getWidth() - mainFrame.getWidth()) / 2);
		int y = (int) ((screenDimension.getHeight() - mainFrame.getHeight()) / 2);
		mainFrame.setLocation(x, y);
	}
	
	private void initializeButtons() {
		initializeTogglePlayButton();
	}
	
	private void initializeTogglePlayButton() {
		togglePlayButton = new JButton("Start");
		togglePlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
			}
		});
		togglePlayButton.setSize(togglePlayButtonDimension);
		
		//togglePlayButton.setLocation();
		mainFrame.getContentPane().add(togglePlayButton);
	}
}
