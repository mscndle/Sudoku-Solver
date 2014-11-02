import java.io.*;
import java.sql.SQLOutput;

//
// Run solver from this class
//
// Enter sudoku puzzle in the input.txt file with rows on separate
// lines and using single space between numbers.
// 0 - indicates empty value
//
public class Main {


    public static void main(String[] args) {
        int[][] boardInt = new int[9][9];

        try {
            readInputBoard(boardInt);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        Board board = new Board(boardInt);
        System.out.println(board.toString());
        System.out.println("-----------");


        Gameplay play = new Gameplay(board);
        play.findEmptySlots();
        play.play();



    }

    public static void readInputBoard(int[][] board) throws IOException {
        int N = board.length;

        String line;
        String[] lineInputStr;
        BufferedReader br = new BufferedReader(new FileReader(
                new File("/Users/mscndle/Developer/IdeaProjects/SudokuSolver/src/input.txt")));

        for (int i = 0; i < N; i++) {

            if ((line = br.readLine()) == null) {   break;  }
            lineInputStr = line.split(" ");

            for (int j = 0; j < N; j++) {
                if (lineInputStr[j].equals("-")) {
                    board[i][j] = 0;
                } else {
                    board[i][j] = Integer.parseInt(lineInputStr[j]);
                }
            }
        }
    }




}
