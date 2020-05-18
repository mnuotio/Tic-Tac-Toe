package tictactoe;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter cells: ");

        String fieldInput = scanner.nextLine();
        fieldInput = fieldInput.replaceAll("_", "E");
        Game myGame = new Game(fieldInput);
        myGame.printBoard();
        myGame.printGameState();

    }

}

class Game {

    private GameState gameState;
    private final GameBoard gameBoard;

    public Game() {
        this.gameBoard = new GameBoard();
        this.gameState = GameState.INCOMPLETE;
    }

    public Game(String status) {
        this.gameBoard = new GameBoard(status);
        this.updateGameState();
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public GameState determineGameState() {
        // game is possible?
        if (this.gameBoard.isValidGame()) {
            if (gameBoard.makesLine(State.X)) {
                return GameState.XWINS;
            }
            if (gameBoard.makesLine(State.O)) {
                return GameState.OWINS;
            }
            if (!gameBoard.hasEmptyCells()) {
                return GameState.DRAW;
            }
            if (gameBoard.hasEmptyCells()) {
                return GameState.INCOMPLETE;
            }
        }
        return GameState.IMPOSSIBLE;
    }

    public void updateGameState() {
        this.gameState = this.determineGameState();
    }

    public void printBoard() {
        this.gameBoard.print();
    }

    public void printGameState() {
        switch (this.gameState) {
            case INCOMPLETE:
                System.out.println("Game not finished");
                break;
            case DRAW:
                System.out.println("Draw");
                break;
            case XWINS:
                System.out.println("X wins");
                break;
            case OWINS:
                System.out.println("O wins");
                break;
            case IMPOSSIBLE:
                System.out.println("Impossible");
                break;
        }
    }

}


class Cell {

    private State state;
    private final int position;

    public Cell(State state, int position) {
        this.state = state;
        this.position = position;
    }

    public State getState() {
        return this.state;
    }

    public int getPosition() {
        return this.position;
    }

    public void changeState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state == State.E ? "_" : this.state.toString();
    }


}

class GameBoard {
    private ArrayList<Cell> cells;

    public GameBoard() {
        cells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            cells.add(new Cell(State.E, i));
        }
    }

    public GameBoard(String status) {
        cells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            cells.add(new Cell(State.valueOf(Character.toString(status.charAt(i))), i));
        }
    }

    public void print() {
        System.out.println("---------");
        for (int i = 0; i < 9; i += 3) {
            System.out.println("| " + cells.get(i)
                    + " " + cells.get(i + 1)
                    + " " + cells.get(i + 2) + " |");
        }
        System.out.println("---------");
    }


    public boolean rowCheck(State state, int rowNumber) {
        if (cells.get(rowNumber).getState() == state &&
                cells.get(rowNumber + 1).getState() == state &&
                cells.get(rowNumber + 2).getState() == state) {
            return true;
        }
        return false;
    }

    public boolean colCheck(State state, int colNumber) {
        if (cells.get(colNumber).getState() == state &&
                cells.get(colNumber + 3).getState() == state &&
                cells.get(colNumber + 6).getState() == state) {
            return true;
        }
        return false;
    }

    public boolean diagonalCheck(State state) {

        if (cells.get(4).getState() == state) {
            if (cells.get(0).getState() == state &&
                    cells.get(8).getState() == state) {
                return true;
            } else if (cells.get(2).getState() == state &&
                    cells.get(6).getState() == state) {
                return true;
            }
        }
        return false;
    }

    public boolean makesLine(State state) {
        for (int i = 0; i < 3; i++) {
            if (rowCheck(state, (i * 3))) {
                return true;
            } else if (colCheck(state, i)) {
                return true;
            } else if (diagonalCheck(state)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidGame() {
        if (makesLine(State.X) && makesLine(State.O) ||
                Math.abs(cellCount(State.X) - cellCount(State.O)) >= 2) {
            return false;
        }
        return true;
    }

    public int cellCount(State state) {
        int cellCount = 0;
        for (Cell c : cells) {
            if (c.getState() == state) {
                cellCount++;
            }
        }
        return cellCount;
    }

    public boolean hasEmptyCells() {
        for (Cell c : cells) {
            if (c.getState() == State.E) {
                return true;
            }
        }
        return false;
    }

}

enum State { // 3 possible states for a cell: X, O, or E (empty)
    E, X, O;
}

enum GameState { // 5 possible game states
    INCOMPLETE, DRAW, XWINS, OWINS, IMPOSSIBLE;
}
