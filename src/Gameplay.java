import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

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

    //
    //  Returns a string representation of all the slots in the priority queue
    //
    @Override
    public String toString() {
        int N = this.slotsQueue.size();

        return this.slotsQueue.toString();
    }





}
