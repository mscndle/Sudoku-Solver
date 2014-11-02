import java.util.ArrayList;
import java.util.Comparator;

//
//  Each slot is represented as an individual object
//
class Slot implements Comparable<Slot> {
    int row;
    int col;
    int value;  //1-9 and 0 indicates empty array

    boolean solved;
    ArrayList<Integer> optionsList;

    public Slot(int row, int col, int value) { //constructor
        this.row = row;
        this.col = col;
        this.value = value;
        this.solved = this.value != 0;  //default is false~unsolved
        this.optionsList = new ArrayList<Integer>();
    }

    @Override
    public int compareTo(Slot s) {
        int L1 = this.optionsList.size();
        int L2 = s.optionsList.size();

        if (L1 < L2)        {   return -1;  }
        else if (L1 > L2)   {   return 1;   }
        else                {   return 0;   }

    }

    @SuppressWarnings("unchecked")
    public Slot getSlotCopy() {

        Slot clone = new Slot(this.row, this.col, this.value);
        clone.solved = this.solved;
        clone.optionsList = (ArrayList<Integer>) this.optionsList.clone();

        return clone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Slot position: " + this.row + "," + this.col + "\n");
        sb.append("Value of Slot: " + this.value + "\n");
        sb.append("Options list : " + this.optionsList.toString() + "\n\n");

        return sb.toString();
    }
}