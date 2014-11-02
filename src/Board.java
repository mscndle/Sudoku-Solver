import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;

//
//  Encapsulates the sudoku board and slots
//
public class Board {

    //TODO - convert to Singleton
    Slot[][] slots;
    Board board;

    //
    //  Constructor only checks if the board is a square
    //  Sudoku variants have not been tested, use 9x9 to be safe
    //
    public Board(int[][] intBoard) {
        if (intBoard.length != intBoard[0].length) {
            throw new InputMismatchException("Board is not a square");
        }

        int N = intBoard.length;
        this.slots = new Slot[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.slots[i][j] = new Slot(i, j, intBoard[i][j]);
            }
        }
    }

    //
    // Returns a lit of empty slots at any given time
    //
    public List<Slot> getEmptySlots() {
        List<Slot> emptySlots = new ArrayList<Slot>();
        int N = this.slots.length;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {

                Slot s = this.slots[i][j];

                if (s.value == 0) {
                    emptySlots.add(s);
                }
            }
        }

        return emptySlots;
    }


    //
    //  Place a value on the slot, might not be the final value
    //
    public void insertValue(Slot slot, int value) {
        slot.value = value;
        //slot.solved = true; //this might be an issue - if the move is not final, how to reverse it back?
        //check options left - if no options are left, it's final

        if (slot.optionsList.size() == 0) {
            slot.solved = true;
        }
    }

    //
    // Removes a value incorrectly added to the board
    //
    public void deleteValue(Slot slot) {
        slot.value = 0; //remove whatever value was previously there
    }

    //
    // Initially computes the possibilities for each empty slot
    //
    public void setSlotOptionsList(Slot slot) {
        if (slot.value != 0) {  throw new InputMismatchException("Slot is already filled!");    }

        int N = this.slots.length;
        int row = slot.row;
        int col = slot.col;

        int[] allPossibilities = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        ArrayList<Integer> optionsList = new ArrayList<Integer>();

        // Check non-empty cells in the columns
        for (int i = 0; i < N; i++) {
            int val = this.slots[row][i].value;

            if (val != 0) { // this value is used up elsewhere and is not a possibility
                allPossibilities[val] = 0;
            }
        }

        // repeat the same for row
        for (int i = 0; i < N; i++) {
            int val = this.slots[i][col].value;

            if (val != 0) {
                allPossibilities[val] = 0;
            }
        }

        //
        // now for the box
        // easy to find the quadrant
        //  0,0  0,1  0,2
        //  1,0  1,1  1,2
        //  2,0  2,1  2,2
        //
        int start_x = (row/3) * 3;
        int start_y = (col/3) * 3;

        int end_x = start_x + 2;
        int end_y = start_y + 2;

        for (int i = start_x; i < end_x; i++) {
            for (int j = start_y; j < end_y; j++) {
                int val = this.slots[i][j].value;

                if (val != 0) {
                    allPossibilities[val] = 0;
                }
            }
        }

        // move all the possibilities to the ArrayList and set it for that slot
        for (int i : allPossibilities) {
            if (i != 0) {
                optionsList.add(i);
            }

            slot.optionsList = optionsList;
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
