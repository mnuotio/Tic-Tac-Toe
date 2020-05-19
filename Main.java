package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Game myGame = new Game();
        UserInterface ui = new UserInterface(scanner, myGame);
        ui.start();
    }
}

enum State { // 3 possible states for a cell: X, O, or E (empty)
    E, X, O;
}

enum GameState { // 4 possible game states
    INCOMPLETE, DRAW, XWINS, OWINS;
}
