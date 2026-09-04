package model;

import java.util.Random;

public class NumberGuessGame {
	public static final int MAX_KEY = 100;
	public static final int MAX_ATTEMPTS = 10;
	
	private int key;
	private int guess;
	private boolean showKeyOn;
	private int attempts;
	public String progressMessage;
	// state and strategy and attempt mode
	private GameState state;
	private PlayStrategy strategy;
	private AttemptMode attemptMode = AttemptMode.UNLIMITED;

	public NumberGuessGame() {
		this.state = GameState.INIT;
		this.strategy = PlayStrategy.HighLow;
		this.attempts = 0;
		this.showKeyOn = false;
		this.progressMessage = "Welcome to the Number Guessing Game!";
		key = -1;
		guess = -1;

	}

	private int generateGameKey() {
		Random r = new Random();
		int newKey;
		do {
			newKey = r.nextInt(MAX_KEY) + 1;
		} while (newKey == key);
		return newKey;
	}
	
	//<New Game> button pressed
	public void start() {
		this.key = generateGameKey();
		this.guess = -1;
		this.attempts = 0;
		this.state = GameState.PLAYING;
		this.progressMessage = "New game started! Make your guess.";
	}


	// when user enters a guess and press <Enter> key
	public void play(int guess) {
		if ( guess < 1 || guess > MAX_KEY) {
			// this should not happen if UI is implemented correctly
			throw new IllegalArgumentException("Guess must be between 1 and " + MAX_KEY);
		}

		++this.attempts;

		if (strategy == PlayStrategy.HighLow) {
			playHighLow(guess);

		} else if (strategy == PlayStrategy.CloserAway) {
			playCloserAway(guess);

		} else {
			throw new IllegalStateException ("Unknown strategy: " + strategy);
		}

		if (attemptMode == AttemptMode.TEN_ATTEMPTS // attempt mode is 10 attempts
			&& attempts >= MAX_ATTEMPTS // attempt reached limit
			&& guess != key) { //guess is not key
				progressMessage = "You Lose! The key was " + key;
				state = GameState.OVER;
		} else if (attemptMode == AttemptMode.TEN_ATTEMPTS
			&& attempts == MAX_ATTEMPTS - 1
			&& guess != key) {
				progressMessage = "Last attempt! " + progressMessage;
			}

	}

	private void playHighLow(int guess) {
		this.guess = guess;
		// \u26a1 is the Unicode for a lightning bolt emoji
		if ( guess < key) {
			this.progressMessage = "\u26a1 Go Higher!";
		} else if ( guess > key) {
			this.progressMessage = "\u26a1 Go Lower!";
		} else {
			this.progressMessage = "\u26a1 Got it! The key was " + key ;
			this.state = GameState.OVER;
		} 

	}

	private void playCloserAway(int guess) {
		// this.guess = guess;
		if ( guess == key ) {
			this.progressMessage = "\u26a1 Got it! The key was " + key;
			this.state = GameState.OVER;
		} else {
			if ( attempts == 1) {
				this.progressMessage = "\u26a1 First guess! No comparison yet.";
			} else {
				int prevDiff = Math.abs(this.guess-key);
				int currDiff = Math.abs(guess - key);
				if (currDiff < prevDiff) {
					this.progressMessage = "\u26a1 Closer!";
				} else if ( currDiff > prevDiff) {
					this.progressMessage = "\u26a1 Away!";
				} else {
					this.progressMessage = "\u26a1 Same distance!";
				}
			}
			this.guess = guess;
				
		}
		
	}

	public AttemptMode getAttemptMode() {
		return attemptMode;
	}

	public void setAttempMode(AttemptMode attemptMode) {
		this.attemptMode = attemptMode;
	}



	public int getAttempts() {
		return attempts;
	}

	public GameState getState() {
		return state;
	}

	public PlayStrategy getStrategy() {
		return strategy;
	}

	public boolean isShowKeyOn() {
		return showKeyOn;
	}

	public int getGuess() {
		return guess;
	}

	public int getKey() {
		return key;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public void setStrategy(PlayStrategy strategy) {
		this.strategy = strategy;
	}

	public void setShowKey(boolean showKeyOn) {
		this.showKeyOn = showKeyOn;
	}

	public void setGuess(int guess) {
		this.guess = guess;
	}

	public void setKey( int key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return String.format("Key(%d), Guess(%d), Attempts(%d)", key, guess, attempts);
	}

}
