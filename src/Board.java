import java.util.ArrayList;
import java.util.InputMismatchException;

//
//  Encapsulates the sudoku board and slots
//
public class Board {

    //TODO - convert to Singleton
    Slot[][] slots;

    class Slot {
        int value;  //1-9 and 0 indicates empty array

        boolean solved;
        int numOptions;
        ArrayList<Integer> optionsList;

        public Slot(int value) { //constructor
            this.value = value;
            this.solved = this.value != 0;  //default is false~unsolved
        }

        @Override
        public String toString() {
            return String.valueOf(this.value);
        }
    }

    //
    //  Constructor only checks if the board is a square
    //  Sudoku variants have not been tested, use 9x9 to be safe
    //
    public Board(int[][] board) {
        if (board.length != board[0].length) {
            throw new InputMismatchException("Board is not a square");
        }

        int N = board.length;
        this.slots = new Slot[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.slots[i][j] = new Slot(board[i][j]);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int N = this.slots.length;
        if (N == 0) {
            throw new IllegalStateException("Board not initialized");
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(slots[i][j].value);
                sb.append(" "); //avoid chaining to save cost
            }
            sb.append("\n");
        }

        return sb.toString().trim();
    }

}
