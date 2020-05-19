package tictactoe;

public class Game {

    private GameState gameState;
    private final GameBoard gameBoard;

    public Game() {
        this.gameBoard = new GameBoard();
        this.gameState = GameState.INCOMPLETE;
    }

    public GameState determineGameState() {

        if (gameBoard.makesLine(State.X)) {
            return GameState.XWINS;
        }
        if (gameBoard.makesLine(State.O)) {
            return GameState.OWINS;
        }
        if (!gameBoard.hasEmptyCells()) {
            return GameState.DRAW;
        }
        return GameState.INCOMPLETE;
    }

    public void updateGameState() {
        this.gameState = this.determineGameState();
    }

    public void printBoard() {
        this.gameBoard.print();
    }

    public void printGameState() {
        switch (this.gameState) {
            case DRAW:
                System.out.println("Draw");
                break;
            case XWINS:
                System.out.println("X wins");
                break;
            case OWINS:
                System.out.println("O wins");
                break;
        }
    }

    public GameBoard getGameBoard() {
        return this.gameBoard;
    }

    public boolean isComplete() {
        return this.gameState != GameState.INCOMPLETE;
    }
}

