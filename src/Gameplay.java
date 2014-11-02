import java.util.*;

//
// Main solver logic
//
public class Gameplay {

    Board board;    //  stores a reference to the board object
    PriorityQueue<Slot> slotsQueue;

    //  Constructor expects a board object input
    public Gameplay(Board board) {
        this.board = board;
        this.slotsQueue = new PriorityQueue<Slot>();
    }


    //
    // Moves empty slots into a queue for processing
    //
    public void findEmptySlots() {
        if (this.board == null) {   throw new IllegalStateException("Gameplay class has null Board pointer");   }

        List<Slot> emptySlots = this.board.getEmptySlots();

        for (Slot s : emptySlots) {
            this.board.setSlotOptionsList(s);
            slotsQueue.add(s);
        }
    }

    public boolean isLegalMove(Slot s, int value) {
        int N = this.board.slots.length;
        int row = s.row;
        int col = s.col;

        // Check non-empty cells in the columns
        for (int i = 0; i < N; i++) {
            int val = this.board.slots[row][i].value;

            if (val == value) { return false;   }
        }

        // repeat the same for row
        for (int i = 0; i < N; i++) {
            int val = this.board.slots[i][col].value;

            if (val == value) { return false;   }
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
                int val = this.board.slots[i][j].value;

                if (val == value) { return false;   }
            }
        }

        return true;
    }

    //
    // Solves the puzzle
    //
    public void play() {
        if (slotsQueue.isEmpty()) {
            throw new InputMismatchException("Puzzle solved or not initialized!");
        }

        while (!this.slotsQueue.isEmpty()) {
            Slot s = this.slotsQueue.remove();  //get best slot to play
            this.board.setSlotOptionsList(s);   //recalculate options

            if (s.optionsList.size() == 1) {    //good choice
                int value = s.optionsList.remove(0);
                this.board.insertValue(s, value);

                System.out.println("------------------------");
                System.out.println("Value :" + value + " inserted at " + s.row + "," + s.col);
                System.out.println(this.board.toString());
                System.out.println();

            } else {    //multiple options

                PriorityQueue<Slot> slotsQueueCopy = cloneSlotsQueue();
                int value = -1;

                System.out.println("---options list-----");
                System.out.println(s.optionsList.toString());
                for (int i = 0; i < s.optionsList.size(); i++) {

                    value = s.optionsList.get(i);
                    this.board.insertValue(s, value);    //insert ith optionsList element on board
                    System.out.println("attempting to insert: " + s.optionsList.get(i) + " at " + s.row + "," + s.col);

                    if (playHelper(slotsQueueCopy) == -1) { //incorrect option

                        s.optionsList.remove(i);    //remove bad value from optionsList
                        this.board.deleteValue(s);  //remove value from the board

                    } else {
                        //inserted value was correct, break and move on
                        System.out.println("breaking out, good value found ");
                        break;
                    }

                    slotsQueueCopy = cloneSlotsQueue(); //next optionsList create another clone
                }

//                int value = s.optionsList.remove(0);
                this.board.insertValue(s, value);

                System.out.println("------------------------");
                System.out.println("Value :" + value + " inserted at " + s.row + "," + s.col);
                System.out.println(this.board.toString());
                System.out.println();

            }
        }

    }

    private int playHelper(PriorityQueue<Slot> slotsQueueCopy) {
        System.out.println("Reached the helper function");

        return 0;
    }

    //
    // Returns a copy of the slots queue in it's current state
    //
    private PriorityQueue<Slot> cloneSlotsQueue() {
        PriorityQueue<Slot> queueCopy = new PriorityQueue<Slot>();

        for (Slot s : this.slotsQueue) {
            queueCopy.add(s.getSlotCopy());
        }

        return queueCopy;
    }


    //
    //  Returns a string representation of all the slots in the priority queue
    //
    @Override
    public String toString() {
        return this.slotsQueue.toString();
    }


}
