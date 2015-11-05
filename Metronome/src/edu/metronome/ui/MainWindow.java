package edu.metronome.ui;

import edu.metronome.logic.exceptions.*;
import edu.metronome.logic.Click;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import java.lang.String;
import java.util.*;
import java.awt.GridLayout;


public class MainWindow {
	
	private Click click = new Click();
	
	private final int TOP_MARIGIN_IN_PIXELS = 50;
	private final int BOTTOM_MARIGIN_IN_PIXELS = 15;
	private final int TEMPO_SPINNER_BOTTOM_MARIGIN = 20;
	private final int TEMPO_SLIDER_BOTTOM_MARIGIN = 30;
	private final int BASIC_PROPERTIES_PANEL_BOTTOM_MARIGIN = 15;
	
	private final String FONT_NAME = "Tahoma";
	private final int FONT_SIZE_IN_TEMPO_SPINNER = 50;
	
	private final int MINIMUM_TEMPO_VALUE = click.MINIMUM_TEMPO;
	private final int MAXIMUM_TEMPO_VALUE = click.MAXIMUM_TEMPO;
	private final int DEFAULT_TEMPO_VALUE = click.DEFAULT_TEMPO;
	private final int TEMPO_STEP_SIZE_FOR_SPINNER = 1;
	private final int MINOR_TICK_SIZE_FOR_SLIDER = 10;
	private final int MAJOR_TICK_SIZE_FOR_SLIDER = 50;
	
	private final int BASIC_PROPERTIES_PANEL_ROWS_COUNT = 2;
	private final int BASIC_PROPERTIES_PANEL_COLUMNS_COUNT = 2;
	private final int BASIC_PROPERTIES_PANEL_HORIZONTAL_GAP = 40;
	private final int BASIC_PROPERTIES_PANEL_VERTICAL_GAP = 0;
	
	private final int TIME_TRAINER_PROPERTIES_PANEL_ROWS_COUNT = 3;
	private final int TIME_TRAINER_PROPERTIES_PANEL_COLUMNS_COUNT = 2;
	private final int TIME_TRAINER_PROPERTIES_PANEL_HORIZONTAL_GAP = 40;
	private final int TIME_TRAINER_PROPERTIES_PANEL_VERTICAL_GAP = 0;
	
	private final boolean ARE_COMPONENTS_ON_TIME_TRAINER_ENABLED = false;
	
	private Container mainFramePane;
	
	private JFrame mainFrame;
	private Dimension mainFrameDimension = new Dimension(400, 500);
		
	private JButton togglePlayButton;
	private Dimension togglePlayButtonDimension = new Dimension(100, 50);
	
	private JSpinner tempoSpinner;
	private Dimension tempoSpinnerDimension = new Dimension(110, 80);
	
	private JSlider tempoSlider;
	private Dimension tempoSliderDimension = new Dimension(250, 45);
	
	private JPanel basicPropertiesPanel;
	private Dimension basicPropertiesPanelDimension = new Dimension(250, 50);
	private GridLayout basicPropertiesPanelGridLayout;
	
	private JLabel clickSoundLabel;
	private JComboBox<String> clickSoundComboBox;
	
	private JLabel beatsPerBarLabel;
	private JComboBox<Integer> beatsPerBarComboBox;
	
	private JPanel timeTrainerPropertiesPanel;
	private Dimension timeTrainerPropertiesPanelDimension = new Dimension(250, 75);
	private GridLayout timeTrainerPropertiesPanelGridLayout;
	
	private JLabel numberOfBarsWithClickLabel;
	private JComboBox<Integer> numberOfBarsWithClickComboBox;
	
	private JLabel numberOfBarsWithoutClickLabel;
	private JComboBox<Integer> numberOfBarsWithoutClickComboBox;
	
	private JCheckBox isTimeTrainerEnabledCheckBox;

	public MainWindow() {
		initialize();
	}
	
	public void show() {
		mainFrame.setVisible(true);
	}

	private void initialize() {
		initializeMainFrame();
		initializeMainFrameContent();
		initializeBasicPropertiesPanel();
		initializeBasicPropertiesPanelContent();
		initializeTimeTrainerPropertiesPanel();
		initializeTimeTrainerPropertiesPanelContent();
	}
	
	private void initializeMainFrame() {
		mainFrame = new JFrame();
		mainFramePane = mainFrame.getContentPane();
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
	
	private void initializeMainFrameContent() {
		initializeTogglePlayButton();
		initializeTempoSpinner();
		initializeTempoSlider();
	}
	
	private void initializeTogglePlayButton() {
		togglePlayButton = new JButton("Start");
		togglePlayButton.setSize(togglePlayButtonDimension);
		placeTogglePlayButton();
		defineTogglePlayClickEvent();
		mainFramePane.add(togglePlayButton);
	}
	
	private void defineTogglePlayClickEvent() {
		togglePlayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(click.isPlaying()) {
					togglePlayButton.setText("Start");
					click.stop();
				} else {
					togglePlayButton.setText("Stop");
					click.play();
				}
			}
		});
	}
	
	private void placeTogglePlayButton() {
		int topMarigin = mainFrame.getWidth() - BOTTOM_MARIGIN_IN_PIXELS;
		centerComponent(togglePlayButton, topMarigin);
	}
	
	private void centerComponent(JComponent componentName, int topMarigin) {
		int x = getXValueToPlaceComponentInTheCenter(componentName.getWidth());
		int y = topMarigin;
		componentName.setLocation(x, y);
	}
	
	private int getXValueToPlaceComponentInTheCenter(int componentWidth) {
		return ((mainFrame.getWidth() - componentWidth) / 2);
	}
	
	private void initializeTempoSpinner() {
		tempoSpinner = new JSpinner();
		setupTempoSpinnerProperties();
		placeTempoSpinner();
		defineTempoSpinnerChangeEvent();
		mainFramePane.add(tempoSpinner);
	}

	private void setupTempoSpinnerProperties() {
		tempoSpinner.setModel(new SpinnerNumberModel(DEFAULT_TEMPO_VALUE, MINIMUM_TEMPO_VALUE, 
													 MAXIMUM_TEMPO_VALUE, TEMPO_STEP_SIZE_FOR_SPINNER));
		tempoSpinner.setSize(tempoSpinnerDimension);
		tempoSpinner.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE_IN_TEMPO_SPINNER));
		tempoSpinner.setEditor(new JSpinner.DefaultEditor(tempoSpinner));
		centerTempoSpinnerText();
	}
	
	private void centerTempoSpinnerText() {
		JComponent editor = tempoSpinner.getEditor();
		JSpinner.DefaultEditor tempoSpinnerEditor = (JSpinner.DefaultEditor) editor;
		tempoSpinnerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);
	}
	
	private void placeTempoSpinner() {
		int topMarigin = TOP_MARIGIN_IN_PIXELS;
		centerComponent(tempoSpinner, topMarigin);
	}
	
	private void defineTempoSpinnerChangeEvent() {
		tempoSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				int tempoSpinnerValue = (int) tempoSpinner.getValue();
				tempoSlider.setValue(tempoSpinnerValue);
				try {
					click.setTempo(tempoSpinnerValue);
				} catch (TempoOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initializeTempoSlider() {
		tempoSlider = new JSlider();
		setupTempoSliderProperties();
		placeTempoSlider();
		defineTempoSliderChangeEvent();
		mainFramePane.add(tempoSlider);
	}
	
	private void setupTempoSliderProperties() {
		tempoSlider.setSize(tempoSliderDimension);
		setupTempoSliderTickProperties();
		setupTempoSliderValueProperties();
	}
	
	private void setupTempoSliderTickProperties() {
		tempoSlider.setMinorTickSpacing(MINOR_TICK_SIZE_FOR_SLIDER);
		tempoSlider.setMajorTickSpacing(MAJOR_TICK_SIZE_FOR_SLIDER);
		tempoSlider.setPaintTicks(true);
		tempoSlider.setPaintLabels(true);
	}
	
	private void setupTempoSliderValueProperties() {
		tempoSlider.setMaximum(MAXIMUM_TEMPO_VALUE);
		tempoSlider.setMinimum(MINIMUM_TEMPO_VALUE);
		tempoSlider.setValue(DEFAULT_TEMPO_VALUE);
	}
	
	private void placeTempoSlider() {
		int topMarigin = TOP_MARIGIN_IN_PIXELS + tempoSpinner.getHeight() + TEMPO_SPINNER_BOTTOM_MARIGIN;
		centerComponent(tempoSlider, topMarigin);
	}
	
	private void defineTempoSliderChangeEvent() {
		tempoSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				int tempoSliderValue = tempoSlider.getValue();
				tempoSpinner.setValue(tempoSliderValue);
				try {
					click.setTempo(tempoSliderValue);
				} catch (TempoOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initializeBasicPropertiesPanel() {
		basicPropertiesPanel = new JPanel();
		basicPropertiesPanel.setSize(basicPropertiesPanelDimension);
		//basicPropertiesPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		setupBasicPropertiesPanelLayout();
		placeBasicPropertiesPanel();
		mainFramePane.add(basicPropertiesPanel);
	}
	
	private void setupBasicPropertiesPanelLayout() {
		basicPropertiesPanelGridLayout = new GridLayout(BASIC_PROPERTIES_PANEL_ROWS_COUNT, BASIC_PROPERTIES_PANEL_COLUMNS_COUNT,
												BASIC_PROPERTIES_PANEL_HORIZONTAL_GAP, BASIC_PROPERTIES_PANEL_VERTICAL_GAP);
		basicPropertiesPanel.setLayout(basicPropertiesPanelGridLayout);
	}
	
	private void placeBasicPropertiesPanel() {
		int topMarigin = TOP_MARIGIN_IN_PIXELS + tempoSpinner.getHeight() + 
				tempoSlider.getHeight() + TEMPO_SLIDER_BOTTOM_MARIGIN;
		centerComponent(basicPropertiesPanel, topMarigin);
	}
	
	private void initializeBasicPropertiesPanelContent() {
		initializeClickSoundLabel();
		initializeBeatsPerBarLabel();
		initializeClickSoundCheckBox();
		initializeBeatsPerBarCheckBox();
	}
	
	private void initializeClickSoundLabel() {
		clickSoundLabel = new JLabel("Click sound:"/*, SwingConstants.CENTER*/);
		basicPropertiesPanel.add(clickSoundLabel);
	}
	
	private void initializeClickSoundCheckBox() {
		clickSoundComboBox = new JComboBox<String>(getSoundNames());
		defineClickSoundComboBoxChangeEvent();
		basicPropertiesPanel.add(clickSoundComboBox);
	}
	
	private void defineClickSoundComboBoxChangeEvent() {
		clickSoundComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedSoundIndex = clickSoundComboBox.getSelectedIndex();
				try {
					click.setCurrentClickSound(selectedSoundIndex);
				} catch (ClickSoundIndexOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private Vector<String> getSoundNames() {
		Vector<String> soundName = new Vector<String>();
		int numberOfSounds = click.getNumberOfClickSounds();
		for(int i = 0; i < numberOfSounds; i++) {
			soundName.add(click.getClickSoundClipName(i));
		}
		return soundName;
	}
	
	private void centerComboBoxText(JComboBox comboBox) {
		DefaultListCellRenderer renderer = new DefaultListCellRenderer(); 
		renderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); 
		comboBox.setRenderer(renderer); 
	}
	
	private void initializeBeatsPerBarLabel() {
		beatsPerBarLabel = new JLabel("Beats per bar:"/*, SwingConstants.CENTER*/);
		basicPropertiesPanel.add(beatsPerBarLabel);
	}
	
	private Vector<Integer> getBeatsPerBarValues() {
		Vector<Integer> beatsPerBar = new Vector<Integer>();
		for(int beatsCount = click.MINIMUM_BEATS_PER_BAR; beatsCount <= click.MAXIMUM_BEATS_PER_BAR; beatsCount++) {
			beatsPerBar.add(beatsCount);
		}
		return beatsPerBar;
	}
	
	private void initializeBeatsPerBarCheckBox() {
		beatsPerBarComboBox = new JComboBox<Integer>(getBeatsPerBarValues());
		int defaultIndex = click.DEFAULT_BEATS_PER_BAR - 1;
		beatsPerBarComboBox.setSelectedIndex(defaultIndex);
		defineBeatsPerBarComboBoxChangeEvent();
		basicPropertiesPanel.add(beatsPerBarComboBox);
	}
	
	private void defineBeatsPerBarComboBoxChangeEvent() {
		beatsPerBarComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedBeatPerBarValue = (int) beatsPerBarComboBox.getSelectedItem();
				try {
					click.setBeatsPerBar(selectedBeatPerBarValue);
				} catch (BeatsPerBarOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initializeTimeTrainerPropertiesPanel() {
		timeTrainerPropertiesPanel = new JPanel();
		timeTrainerPropertiesPanel.setSize(timeTrainerPropertiesPanelDimension);
		//timeTrainerPropertiesPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		setupTimeTrainerPropertiesPanelLayout();
		placeTimeTrainerPropertiesPanel();
		mainFramePane.add(timeTrainerPropertiesPanel);
	}
	
	private void setupTimeTrainerPropertiesPanelLayout() {
		timeTrainerPropertiesPanelGridLayout = new GridLayout(
				TIME_TRAINER_PROPERTIES_PANEL_ROWS_COUNT, TIME_TRAINER_PROPERTIES_PANEL_COLUMNS_COUNT,
				TIME_TRAINER_PROPERTIES_PANEL_HORIZONTAL_GAP, TIME_TRAINER_PROPERTIES_PANEL_VERTICAL_GAP);
		timeTrainerPropertiesPanel.setLayout(timeTrainerPropertiesPanelGridLayout);
	}
	
	private void placeTimeTrainerPropertiesPanel() {
		int topMarigin = TOP_MARIGIN_IN_PIXELS + tempoSpinner.getHeight() + 
				tempoSlider.getHeight() + TEMPO_SLIDER_BOTTOM_MARIGIN + 
				basicPropertiesPanel.getHeight() + BASIC_PROPERTIES_PANEL_BOTTOM_MARIGIN;
		centerComponent(timeTrainerPropertiesPanel, topMarigin);
	}
	
	private void initializeTimeTrainerPropertiesPanelContent() {
		initializeNumberOfBarsWithClickLabel();
		initializeNumberOfBarsWithoutClickLabel();
		initializeNumberOfBarsWithClickComboBox();
		initializeNumberOfBarsWithoutClickComboBox();
		setComponentsOnTimeTrainerPanelEnabled(ARE_COMPONENTS_ON_TIME_TRAINER_ENABLED);
		initializeIsTimeTrainerEnabledCheckBox();
	}
	
	private void initializeNumberOfBarsWithClickLabel() {
		numberOfBarsWithClickLabel = new JLabel("Bars with click:");
		timeTrainerPropertiesPanel.add(numberOfBarsWithClickLabel);
	}
	
	private void initializeNumberOfBarsWithClickComboBox() {
		numberOfBarsWithClickComboBox = new JComboBox<Integer>(getVectorContainingNumbersOfBars(
				click.MINIMUM_NUMBER_OF_BARS_WITH_CLICK, click.MAXIMUM_NUMBER_OF_BARS_WITH_CLICK));
		int defaultIndex = click.DEFAULT_NUMBER_OF_BARS_WITH_CLICK - 1;
		numberOfBarsWithClickComboBox.setSelectedIndex(defaultIndex);
		defineNumberOfBarsWithClickComboBoxChangeEvent();
		timeTrainerPropertiesPanel.add(numberOfBarsWithClickComboBox);
	}
	
	private Vector<Integer> getVectorContainingNumbersOfBars(int minimum, int maximum) {
		Vector<Integer> numberOfBarsClick = new Vector<Integer>();
		for(int barsCount = minimum; 
				barsCount <= maximum; barsCount++) {
			numberOfBarsClick.add(barsCount);
		}
		return numberOfBarsClick;
	}
	
	private void defineNumberOfBarsWithClickComboBoxChangeEvent() {
		numberOfBarsWithClickComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedValue = (int) numberOfBarsWithClickComboBox.getSelectedItem();
				try {
					click.setNumberOfBarsWithClick(selectedValue);
				} catch (NumberOfBarsOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void initializeNumberOfBarsWithoutClickLabel() {
		numberOfBarsWithoutClickLabel = new JLabel("Empty bars:");
		timeTrainerPropertiesPanel.add(numberOfBarsWithoutClickLabel);
	}
	
	private void initializeNumberOfBarsWithoutClickComboBox() {
		numberOfBarsWithoutClickComboBox = new JComboBox<Integer>(getVectorContainingNumbersOfBars(
				click.MINIMUM_NUMBER_OF_BARS_WITHOUT_CLICK, click.MAXIMUM_NUMBER_OF_BARS_WITHOUT_CLICK));
		int defaultIndex = click.DEFAULT_NUMBER_OF_BARS_WITHOUT_CLICK - 1;
		numberOfBarsWithoutClickComboBox.setSelectedIndex(defaultIndex);
		defineNumberOfBarsWithoutClickComboBoxChangeEvent();
		timeTrainerPropertiesPanel.add(numberOfBarsWithoutClickComboBox);
	}
	
	private void defineNumberOfBarsWithoutClickComboBoxChangeEvent() {
		numberOfBarsWithoutClickComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedValue = (int) numberOfBarsWithoutClickComboBox.getSelectedItem();
				try {
					click.setNumberOfBarsWithoutClick(selectedValue);
				} catch (NumberOfBarsOutOfBoundsException e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	//TODO: span all columns in bottom row for checkbox
	private void initializeIsTimeTrainerEnabledCheckBox() {
		isTimeTrainerEnabledCheckBox = new JCheckBox();
		setupIsTimeTrainerEnabledCheckBoxProperties();
		defineIsTimeTrainerEnabledCheckBoxChangeEvent();
		timeTrainerPropertiesPanel.add(isTimeTrainerEnabledCheckBox);
	}
	
	private void setupIsTimeTrainerEnabledCheckBoxProperties() {
		isTimeTrainerEnabledCheckBox.setText("Enable time trainer");
		isTimeTrainerEnabledCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 9));
		isTimeTrainerEnabledCheckBox.setSelected(click.isTimeTrainerEnabled());
	}
	
	private void defineIsTimeTrainerEnabledCheckBoxChangeEvent() {
		isTimeTrainerEnabledCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean isEnabled = isTimeTrainerEnabledCheckBox.isSelected();
				click.setTimeTrainerEnabled(isEnabled);
				setComponentsOnTimeTrainerPanelEnabled(isEnabled);
			}
		});
	}
	
	private void setComponentsOnTimeTrainerPanelEnabled(boolean isEnabled) {
		numberOfBarsWithClickComboBox.setEnabled(isEnabled);
		numberOfBarsWithClickLabel.setEnabled(isEnabled);
		numberOfBarsWithoutClickComboBox.setEnabled(isEnabled);
		numberOfBarsWithoutClickLabel.setEnabled(isEnabled);
	}
	
}