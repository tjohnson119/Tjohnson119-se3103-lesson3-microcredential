package test;

import model.GameState;
import model.NumberGuessGame;
import model.PlayStrategy;

public class NumberGuessGameTester {

    public static void main(String[] args) {
        testConstructorInitialState();
        testStartResetsGame();
        testInvalidGuessThrows();
        testHighLowBelowKey();
        testHighLowAboveKey();
        testHighLowExactGuessWins();
        testCloserAwayFirstGuess();
        testCloserAwayCloserGuess();
        testCloserAwayAwayGuess();
        testCloserAwaySameDistanceGuess();
        testUnknownStrategyThrows();
        testSettersAndToString();

        System.out.println("All NumberGuessGame tests passed.");
    }

    private static void testConstructorInitialState() {
        NumberGuessGame game = new NumberGuessGame();

        check(game.getState() == GameState.INIT, "Initial state should be INIT");
        check(game.getStrategy() == PlayStrategy.HighLow, "Default strategy should be HighLow");
        check(game.getAttempts() == 0, "Attempts should start at 0");
        check(!game.isShowKeyOn(), "showKeyOn should start as false");
        check(game.getGuess() == -1, "Initial guess should be -1");
        check(game.getKey() == -1, "Initial key should be -1");
        check(game.progressMessage.startsWith("Welcome"), "Welcome message should be set");
    }

    private static void testStartResetsGame() {
        NumberGuessGame game = new NumberGuessGame();
        game.setKey(50);
        game.setGuess(75);
        game.setAttempts(4);
        game.setState(GameState.OVER);

        game.start();

        check(game.getState() == GameState.PLAYING, "start() should set state to PLAYING");
        check(game.getAttempts() == 0, "start() should reset attempts to 0");
        check(game.getGuess() == -1, "start() should reset guess to -1");
        check(game.getKey() >= 1 && game.getKey() <= NumberGuessGame.MAX_KEY,
                "start() should generate a key in the valid range");
        check(game.progressMessage.contains("New game started"),
                "start() should set the new game progress message");
    }

    private static void testInvalidGuessThrows() {
        NumberGuessGame game = new NumberGuessGame();

        expectIllegalArgument(game, 0, "Guess below the valid range should fail");
        expectIllegalArgument(game, 101, "Guess above the valid range should fail");
        expectIllegalArgument(game, -1, "Negative guess should fail");
    }

    private static void testHighLowBelowKey() {
        NumberGuessGame game = new NumberGuessGame();
        game.setKey(50);
        game.setState(GameState.PLAYING);

        game.play(25);

        check(game.getState() == GameState.PLAYING, "Guess below key should not end the game");
        check(game.getGuess() == 25, "Guess should be stored after play");
        check(game.progressMessage.equals("\u26a1 Go Higher!"), "Lower number should prompt to go higher");
    }

    private static void testHighLowAboveKey() {
        NumberGuessGame game = new NumberGuessGame();
        game.setKey(50);
        game.setState(GameState.PLAYING);

        game.play(75);

        check(game.getState() == GameState.PLAYING, "Guess above key should not end the game");
        check(game.getGuess() == 75, "Guess should be stored after play");
        check(game.progressMessage.equals("\u26a1 Go Lower!"), "Higher number should prompt to go lower");
    }

    private static void testHighLowExactGuessWins() {
        NumberGuessGame game = new NumberGuessGame();
        game.setKey(42);
        game.setState(GameState.PLAYING);

        game.play(42);

        check(game.getState() == GameState.OVER, "Exact guess should end the game");
        check(game.getGuess() == 42, "Exact guess should be stored");
        check(game.progressMessage.contains("Got it! The key was 42"),
                "Winning message should include the correct key");
    }

    private static void testCloserAwayFirstGuess() {
        NumberGuessGame game = new NumberGuessGame();
        game.setStrategy(PlayStrategy.CloserAway);
        game.setKey(50);
        game.setState(GameState.PLAYING);

        game.play(40);

        check(game.getAttempts() == 1, "First guess should count as one attempt");
        check(game.getState() == GameState.PLAYING, "First guess should not end the game");
        check(game.getGuess() == 40, "First guess should be recorded");
        check(game.progressMessage.contains("First guess! No comparison yet."),
                "First closer-away guess should explain there is no comparison yet");
    }

    private static void testCloserAwayCloserGuess() {
        NumberGuessGame game = new NumberGuessGame();
        game.setStrategy(PlayStrategy.CloserAway);
        game.setKey(50);
        game.setState(GameState.PLAYING);
        game.setGuess(40);
        game.setAttempts(1);

        game.play(45);

        check(game.getAttempts() == 2, "Second guess should increment attempt count");
        check(game.getGuess() == 45, "Recent guess should update in closer-away mode");
        check(game.progressMessage.equals("\u26a1 Closer!"), "A smaller distance should report Closer!");
    }

    private static void testCloserAwayAwayGuess() {
        NumberGuessGame game = new NumberGuessGame();
        game.setStrategy(PlayStrategy.CloserAway);
        game.setKey(50);
        game.setState(GameState.PLAYING);
        game.setGuess(30);
        game.setAttempts(1);

        game.play(80);

        check(game.getGuess() == 80, "More recent guess should replace the previous one");
        check(game.progressMessage.equals("\u26a1 Away!"), "A larger distance should report Away!");
    }

    private static void testCloserAwaySameDistanceGuess() {
        NumberGuessGame game = new NumberGuessGame();
        game.setStrategy(PlayStrategy.CloserAway);
        game.setKey(50);
        game.setState(GameState.PLAYING);
        game.setGuess(30);
        game.setAttempts(1);

        game.play(70);

        check(game.getGuess() == 70, "Guess should still update on equal-distance comparisons");
        check(game.progressMessage.equals("\u26a1 Same distance!"),
                "Equal distance should report Same distance!");
    }

    private static void testUnknownStrategyThrows() {
        NumberGuessGame game = new NumberGuessGame();
        game.setStrategy(null);

        try {
            game.play(50);
            throw new AssertionError("Unknown strategy should throw IllegalStateException");
        } catch (IllegalStateException expected) {
            check(expected.getMessage().contains("Unknown strategy"),
                    "IllegalStateException should mention the unknown strategy");
        }
    }

    private static void testSettersAndToString() {
        NumberGuessGame game = new NumberGuessGame();
        game.setAttempts(7);
        game.setState(GameState.OVER);
        game.setStrategy(PlayStrategy.CloserAway);
        game.setShowKey(true);
        game.setGuess(13);
        game.setKey(99);

        check(game.getAttempts() == 7, "setAttempts should update the value");
        check(game.getState() == GameState.OVER, "setState should update the state");
        check(game.getStrategy() == PlayStrategy.CloserAway, "setStrategy should update the strategy");
        check(game.isShowKeyOn(), "setShowKey should update the visibility flag");
        check(game.getGuess() == 13, "setGuess should update the guess");
        check(game.getKey() == 99, "setKey should update the key");

        String text = game.toString();
        check(text.contains("Key(99)"), "toString should include the key");
        check(text.contains("Guess(13)"), "toString should include the guess");
        check(text.contains("Attempts(7)"), "toString should include the attempt count");
    }

    private static void expectIllegalArgument(NumberGuessGame game, int guess, String message) {
        try {
            game.play(guess);
            throw new AssertionError(message);
        } catch (IllegalArgumentException expected) {
            check(expected.getMessage().contains("Guess must be between"),
                    "IllegalArgumentException should explain the valid range");
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
