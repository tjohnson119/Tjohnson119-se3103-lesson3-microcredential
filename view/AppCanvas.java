package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import controller.App;
import model.GameState;

public class AppCanvas extends JPanel{
	public static final int CANVAS_WIDTH = 800;
	public static final int CANVAS_HEIGHT = 300;
	public static final int X_POS = 50;
	
	private final Font initScreenFont;
	private final Font playScreenFont;
	private final Font gameOverScreenFont;
	private final Color normalColor = Color.BLACK;
	private final Color highlightColor = Color.RED;

	public AppCanvas(){
		setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
		initScreenFont = new Font("Segoe UI Emoji", Font.BOLD,24);
		playScreenFont = new Font("Segoe UI Emoji", Font.BOLD, 20);
		gameOverScreenFont = new Font("Segoe UI Emoji", Font.BOLD, 22);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		var state = App.gameModel.getState();
		if (state == GameState.INIT) {
			drawInitScreen(g2);
		} else if (state == GameState.PLAYING) {
			drawGamePlayingScreen(g2);
		} else if (state == GameState.OVER) {
			drawGameOverScreen(g2);
		}
	}

	private void drawGamePlayingScreen(Graphics2D g2) {
		g2.setFont(playScreenFont);
		g2.setColor(normalColor);
		var gameModel = App.gameModel;
		var y = CANVAS_HEIGHT / 2;
		g2.drawString(gameModel.progressMessage, X_POS, y);
		if (gameModel.isShowKeyOn()) {
			var keyMsg = String.format("The key is: %d", gameModel.getKey());
			g2.setColor(highlightColor);
			g2.drawString(keyMsg, X_POS, y + 40);
		}

		String promptMsg;
		if (gameModel.getAttempts() == 0) {
			promptMsg = "Enter your first guess!";
		} else {
			promptMsg = String.format("Your Guess: %d (Attempts: %d)",
				gameModel.getGuess(), gameModel.getAttempts());
		}
		g2.setColor(normalColor);
		g2.drawString(promptMsg, X_POS, y + 80);
	}

	private void drawGameOverScreen(Graphics2D g2) {
		g2.setFont(gameOverScreenFont);
		g2.setColor(highlightColor);
		var msg1 = String.format("%s (Attempts: %d)",
			App.gameModel.progressMessage, App.gameModel.getAttempts());
		g2.drawString(msg1, X_POS, CANVAS_HEIGHT/2);
		var msg2 = "Press <New Game> to Play Again!";
		g2.drawString(msg2, X_POS, CANVAS_HEIGHT/2 + 40);
		
	}

	private void drawInitScreen(Graphics2D g2) {
		g2.setFont(initScreenFont);
		g2.setColor(normalColor);
		var message = App.gameModel.progressMessage;
		g2.drawString(message, X_POS, CANVAS_HEIGHT/2 );
		message = "Press <New Game> to start playing.";
		g2.drawString(message, X_POS, CANVAS_HEIGHT/2 + 40);
	}

}


