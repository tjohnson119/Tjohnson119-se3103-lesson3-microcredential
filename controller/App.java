package controller;

import javax.swing.JFrame;

import model.NumberGuessGame;
import view.AppWindow;

public class App {

	public static final AppWindow win = new AppWindow();
	public static final NumberGuessGame gameModel = new NumberGuessGame();

	public static void main(String[] args) {
		win.init();
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		win.pack();
		win.setVisible(true);
		// gameModel.start();
		// gameModel.play(50);
	}
	
}
