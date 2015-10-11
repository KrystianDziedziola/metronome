package edu.metronome.ui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Container;
import java.awt.Font;
import java.awt.TextField;
import java.lang.String;

public class MainWindow {
	
	private final int TOP_MARIGIN_IN_PIXELS = 50;
	private final String FONT_NAME = "Tahoma";
	private final int FONT_SIZE_IN_TEMPO_TEXT_FIELD = 50;
	
	private Container mainFramePane;
	
	private JFrame mainFrame;
	private Dimension mainFrameDimension = new Dimension(400, 500);
		
	private JButton togglePlayButton;
	private Dimension togglePlayButtonDimension = new Dimension(100, 50);
	
	private JTextField tempoTextField;
	private Dimension tempoTextFieldDimension = new Dimension(100, 80);

	public MainWindow() {
		initialize();
	}
	
	public void show() {
		mainFrame.setVisible(true);
	}

	private void initialize() {
		initializeMainFrame();
		mainFramePane = mainFrame.getContentPane();
		initializeButtons();
		initializeTextFields();
	}
	
	private void initializeMainFrame() {
		mainFrame = new JFrame();
		setupMainFrameProperties();
		centerMainFrame();
	}
	
	private void setupMainFrameProperties() {
		mainFrame.setTitle("Metronome");
		mainFrame.setSize(mainFrameDimension);
		mainFrame.setResizable(false);
		mainFrame.getContentPane().setLayout(null);
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
		placeTogglePlayButton();
		mainFramePane.add(togglePlayButton);
	}
	
	private void placeTogglePlayButton() {
		int x = getXValueToPlaceComponentInTheCenter(togglePlayButton.getWidth());
		togglePlayButton.setLocation(x, 0);
	}
	
	private int getXValueToPlaceComponentInTheCenter(final int COMPONENT_WIDTH) {
		return ((mainFrame.getWidth() - COMPONENT_WIDTH) / 2);
	}
	
	private void initializeTextFields() {
		initializeTempoTextField();
	}
	
	private void initializeTempoTextField() {
		tempoTextField = new JTextField();
		setupTempoTextFieldProperties();
		placeTempoTextField();
		mainFramePane.add(tempoTextField);
	}
	
	private void setupTempoTextFieldProperties() {
		tempoTextField.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE_IN_TEMPO_TEXT_FIELD));
		tempoTextField.setHorizontalAlignment(JTextField.CENTER);
		//TODO: create a limit of characters this in text field and tempo range 0-250
		//tempoTextField.setDocument(new TextFieldLimit(3));
		tempoTextField.setSize(tempoTextFieldDimension);
	}
	
	private void placeTempoTextField() {
		int x = getXValueToPlaceComponentInTheCenter(tempoTextField.getWidth());
		tempoTextField.setLocation(x, TOP_MARIGIN_IN_PIXELS);
	}
}
