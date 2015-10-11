package edu.metronome.ui;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

import edu.metronome.logic.Click;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Container;
import java.awt.Font;
import java.lang.String;
import java.text.NumberFormat;

public class MainWindow {
	
	private final int TOP_MARIGIN_IN_PIXELS = 50;
	private final int BOTTOM_MARIGIN_IN_PIXELS = 50;
	
	private final String FONT_NAME = "Tahoma";
	private final int FONT_SIZE_IN_TEMPO_TEXT_FIELD = 50;
	
	private final int MINIMUM_TEMPO_VALUE = 0;
	private final int MAXIMUM_TEMPO_VALUE = 255;
	
	private Container mainFramePane;
	
	private JFrame mainFrame;
	private Dimension mainFrameDimension = new Dimension(400, 500);
		
	private JButton togglePlayButton;
	private Dimension togglePlayButtonDimension = new Dimension(100, 50);
	
	private JFormattedTextField tempoTextField;
	private Dimension tempoTextFieldDimension = new Dimension(100, 80);
	
	private Click click = new Click();

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
		int y = mainFrame.getWidth() - BOTTOM_MARIGIN_IN_PIXELS;
		togglePlayButton.setLocation(x, y);
	}
	
	private int getXValueToPlaceComponentInTheCenter(final int COMPONENT_WIDTH) {
		return ((mainFrame.getWidth() - COMPONENT_WIDTH) / 2);
	}
	
	private void initializeTextFields() {
		initializeTempoTextField();
	}
	
	private void initializeTempoTextField() {
		tempoTextField = new JFormattedTextField(getTextFormatForTempoTextField());
		setupTempoTextFieldProperties();
		placeTempoTextField();
		mainFramePane.add(tempoTextField);
	}
	
	private NumberFormatter getTextFormatForTempoTextField() {
		NumberFormat inputFormat = NumberFormat.getInstance();
		NumberFormatter inputFormatter = new NumberFormatter(inputFormat);
		inputFormatter.setValueClass(Integer.class);
		inputFormatter.setMinimum(MINIMUM_TEMPO_VALUE);
		inputFormatter.setMaximum(MAXIMUM_TEMPO_VALUE);
		inputFormatter.setCommitsOnValidEdit(true);
		return inputFormatter;
	}
	
	private void setupTempoTextFieldProperties() {
		tempoTextField.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE_IN_TEMPO_TEXT_FIELD));
		tempoTextField.setHorizontalAlignment(JFormattedTextField.CENTER);
		tempoTextField.setText(Integer.toString(click.getCurrentTempo()));
		tempoTextField.setSize(tempoTextFieldDimension);
	}
	
	private void placeTempoTextField() {
		int x = getXValueToPlaceComponentInTheCenter(tempoTextField.getWidth());
		int y = TOP_MARIGIN_IN_PIXELS;
		tempoTextField.setLocation(x, y);
	}
}
