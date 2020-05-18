package tictactoe;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter cells: ");

        String fieldInput = InputReceiver.getFieldLayout();
        Game myGame = new Game(fieldInput);

        myGame.printBoard();

        while (true) { // This functionality should be moved!
            String xy = InputReceiver.askForCoordinates();
            if (!InputReceiver.validateCoordinates(xy)) {
                continue;
            }
            int position = InputReceiver.coordinatesToPosition(xy);
            if (myGame.getGameBoard().cellIsEmpty(position)) {
                myGame.getGameBoard().getCell(position).changeState(State.X);
                break;
            } else {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
        myGame.printBoard();

    }

}

class InputReceiver {
    static Scanner scanner = new Scanner(System.in);

    public static String getFieldLayout() {
        return scanner.nextLine().replaceAll("_", "E");
    }

    public static boolean validateCoordinates(String input) {
        if (input.matches("[123] [123]")) {
            return true;
        } else if (input.matches("[0-9] [0-9]")) {
            System.out.println("Coordinates should be from 1 to 3!");
        } else {
            System.out.println("You should enter numbers!");
        }
        return false;
    }

    public static String askForCoordinates() {
        System.out.println("Enter the coordinates: ");
        return scanner.nextLine();
    }

    public static int coordinatesToPosition(String coordinates) {
        String[] xy = coordinates.split(" ");
        return (Integer.parseInt(xy[0]) - 1) + (3 - Integer.parseInt(xy[1])) * 3;
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

    public GameBoard getGameBoard() {
        return this.gameBoard;
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

    public boolean cellIsEmpty (int cellNumber) {
        if (cells.get(cellNumber).getState() == State.E) {
            return true;
        }
            return false;
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

    public Cell getCell(int position) {
        return cells.get(position);
    }

}

enum State { // 3 possible states for a cell: X, O, or E (empty)
    E, X, O;
}

enum GameState { // 5 possible game states
    INCOMPLETE, DRAW, XWINS, OWINS, IMPOSSIBLE;
}
