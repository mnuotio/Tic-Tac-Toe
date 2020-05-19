package tictactoe;

import java.util.ArrayList;

public class GameBoard {
    private final ArrayList<Cell> cells;

    public GameBoard() {
        cells = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            cells.add(new Cell(State.E, i));
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

    public boolean cellIsEmpty(int cellNumber) {
        return cells.get(cellNumber).getState() == State.E;
    }

    public boolean rowCheck(State state, int rowNumber) {
        return cells.get(rowNumber).getState() == state &&
                cells.get(rowNumber + 1).getState() == state &&
                cells.get(rowNumber + 2).getState() == state;
    }

    public boolean colCheck(State state, int colNumber) {
        return cells.get(colNumber).getState() == state &&
                cells.get(colNumber + 3).getState() == state &&
                cells.get(colNumber + 6).getState() == state;
    }

    public boolean diagonalCheck(State state) {

        if (cells.get(4).getState() == state) {
            if (cells.get(0).getState() == state &&
                    cells.get(8).getState() == state) {
                return true;
            } else return cells.get(2).getState() == state &&
                    cells.get(6).getState() == state;
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