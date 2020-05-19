package tictactoe;

public class Cell {

    private State state;
    private final int position;

    public Cell(State state, int position) {
        this.state = state;
        this.position = position;
    }

    public State getState() {
        return this.state;
    }

    public void changeState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.state == State.E ? " " : this.state.toString();
    }
}
