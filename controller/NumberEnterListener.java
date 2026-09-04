package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import model.NumberGuessGame;

public class NumberEnterListener implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField source = (JTextField) e.getSource();
		String text = source.getText();
		int guess;
		try {
			guess = Integer.parseInt(text);
			if (guess < 1 || guess > NumberGuessGame.MAX_KEY) {
				JOptionPane.showMessageDialog(App.win,
					 "Please enter a number between 1 and " + NumberGuessGame.MAX_KEY);
				return;
			}

		} catch (NumberFormatException error) {
			JOptionPane.showMessageDialog(App.win, 
				"Invalid input. Please enter a number between 1 and " + NumberGuessGame.MAX_KEY);
			return;
		}
		App.gameModel.play(guess);
		source.setText("");

		App.win.updateWindow();
	}
	
}
