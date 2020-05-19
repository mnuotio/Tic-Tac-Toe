package tictactoe;

import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner;
    private final Game game;

    public UserInterface(Scanner scanner, Game game) {
        this.scanner = scanner;
        this.game = game;
    }

    public void start() {
        boolean xTurn = true; // True when player X's turn, false when player Y's turn
        game.printBoard();

        while (true) {
            String xy = askForCoordinates();
            if (!validateCoordinates(xy)) {
                continue;
            }
            int position = coordinatesToPosition(xy);
            if (game.getGameBoard().cellIsEmpty(position)) {
                game.getGameBoard().getCell(position).changeState(xTurn ? State.X : State.O);
            } else {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            game.printBoard();
            game.updateGameState();

            if (game.isComplete()) {
                game.printGameState();
                break;
            }
            xTurn = !xTurn; // Alternate player turn
        }
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

    public String askForCoordinates() {
        System.out.println("Enter the coordinates: ");
        return scanner.nextLine();
    }

    public int coordinatesToPosition(String coordinates) {
        String[] xy = coordinates.split(" ");
        return (Integer.parseInt(xy[0]) - 1) + (3 - Integer.parseInt(xy[1])) * 3;
    }
}