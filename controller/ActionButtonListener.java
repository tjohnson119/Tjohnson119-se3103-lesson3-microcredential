package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.PlayStrategy;
import view.AppWindow;

public class ActionButtonListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		String command =e.getActionCommand();
		if (command.equals(AppWindow.EXIT_ACTION)) {
			System.exit(0); // quit the application
		} else if (command.equals(AppWindow.NEW_GAME_ACTION)) {
			App.gameModel.start();
		} else if (command.equals(AppWindow.HIGH_LOW_ACTION)) {
			App.gameModel.setStrategy(PlayStrategy.HighLow);
		} else if (command.equals(AppWindow.CLOSER_AWAY_ACTION)) {
			App.gameModel.setStrategy(PlayStrategy.CloserAway);
		} else {
			// should not happen if UI is implemented correctly
			throw new IllegalArgumentException("Unknown action command: " + command);
		}
		App.win.updateWindow(); // update the UI after processing the action
	}
	
}
