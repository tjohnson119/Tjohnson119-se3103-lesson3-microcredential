package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import controller.ActionButtonListener;
import controller.App;
import controller.NumberEnterListener;
import controller.ShowKeyButtonListener;
import model.GameState;
import model.PlayStrategy;
import model.AttemptMode;

public class AppWindow extends JFrame{

	private AppCanvas canvas;
	private JTextField numberField;
	private JCheckBox showKeybutton;
	private JRadioButton highLowButton;
	private JRadioButton closerAwayButton;
	private JRadioButton unlimitedButton;
	private JRadioButton tenAttemptsButton;
	private JButton newGameButton;
	private JButton exitButton;
	public static final String HIGH_LOW_ACTION = "High/Low";
	public static final String CLOSER_AWAY_ACTION = "Closer/Away";
	public static final String NEW_GAME_ACTION = "New Game";
	public static final String EXIT_ACTION = "Exit";
	public static final String UNLIMITED_ATTEMPTS_ACTION = "Unlimited";
	public static final String TEN_ATTEMPTS_ACTION = "10 Attempts";


	public void init() {
		setTitle("Number Guess Game");
		setLocation(300, 200);

		var cp = getContentPane();
		canvas = new AppCanvas();
		cp.add(canvas, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		cp.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new GridLayout(4, 1));


		JPanel numberPanel = new JPanel();
		southPanel.add(numberPanel);
		numberPanel.setBorder(new TitledBorder("Your Guess"));
		numberPanel.add(new JLabel("Enter (1-100): "));
		numberField = new JTextField(10);
		numberPanel.add(numberField);

		// strategy panel
		JPanel strategyPanel = new JPanel();
		southPanel.add(strategyPanel);
		strategyPanel.setBorder(new TitledBorder("Select Strategy"));
		highLowButton = new JRadioButton("High/Low", App.gameModel.getStrategy() == PlayStrategy.HighLow
		);
		closerAwayButton = new JRadioButton("Closer/Away", App.gameModel.getStrategy() == PlayStrategy.CloserAway
		);
		strategyPanel.add(highLowButton);
		strategyPanel.add(closerAwayButton);

		ButtonGroup strategyGroup = new ButtonGroup();
		strategyGroup.add(highLowButton);
		strategyGroup.add(closerAwayButton);

		// attempts panel
		JPanel attemptsPanel = new JPanel();
		southPanel.add(attemptsPanel);
		attemptsPanel.setBorder(new TitledBorder("Select Attempts"));
		unlimitedButton = new JRadioButton("Unlimited", App.gameModel.getAttemptMode() == AttemptMode.UNLIMITED);
		tenAttemptsButton = new JRadioButton("10 Attempts", App.gameModel.getAttemptMode() == AttemptMode.TEN_ATTEMPTS);
		attemptsPanel.add(unlimitedButton);
		attemptsPanel.add(tenAttemptsButton);

		ButtonGroup attemptsGroup = new ButtonGroup();
		attemptsGroup.add(unlimitedButton);
		attemptsGroup.add(tenAttemptsButton);

		// action panel
		JPanel actionPanel = new JPanel();
		southPanel.add(actionPanel);
		actionPanel.setBorder(new TitledBorder("Actions"));
		showKeybutton = new JCheckBox("Show Key");
		actionPanel.add(showKeybutton);
		newGameButton = new JButton("New Game");
		actionPanel.add(newGameButton);
		exitButton = new JButton("Exit");
		actionPanel.add(exitButton);

		// add action listeners
		var actionListener = new ActionButtonListener();
		highLowButton.addActionListener(actionListener);
		closerAwayButton.addActionListener(actionListener);
		newGameButton.addActionListener(actionListener);
		exitButton.addActionListener(actionListener);

		showKeybutton.addItemListener(new ShowKeyButtonListener());
		numberField.addActionListener(new NumberEnterListener());
		unlimitedButton.addActionListener(actionListener);
		tenAttemptsButton.addActionListener(actionListener);

		updateWindow();

	}

	public void updateWindow() {
		// enable/disable buttons
		var state = App.gameModel.getState();
		newGameButton.setEnabled(state !=  GameState.PLAYING);
		numberField.setEnabled(state == GameState.PLAYING);
		highLowButton.setEnabled(state != GameState.PLAYING);
		closerAwayButton.setEnabled(state != GameState.PLAYING);
		unlimitedButton.setEnabled(state != GameState.PLAYING);
		tenAttemptsButton.setEnabled(state != GameState.PLAYING);

		// always enabled:show key, exit button

		canvas.repaint();

	}
 	
}
