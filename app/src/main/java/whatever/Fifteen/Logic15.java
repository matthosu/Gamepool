package whatever.Fifteen;

import android.app.Activity;

import java.util.Random;

import whatever.Fifteen.Displayer;
import whatever.Fifteen.Game15Activity;
import whatever.gamepool.MoveEvent;
import whatever.gamepool.MoveListener;

/**
 * Created by Mateusz on 2016-06-08.
 */
public class Logic15 implements MoveListener {
    private static final int SIZE = 4;
    private Displayer displayer;
    Game15Activity game;

    /*
    *  Matrix representing game board. Stores values for each field, zero for empty fields
    *  Accessing values like in matrix, from [0, 0] to [SIZE-1, SIZE-1]
    */
    private static int[][] board;
    private static Random rand = new Random();

    public Logic15(Activity a) {
        displayer = new Displayer(a, SIZE);
        game = (Game15Activity) a;

        if (board == null) {
            resetGame();
        }
        displayer.setDisplay(board);
    }

    public static void reset()
    {
        board = null;
    }
    public void resetGame() {
        board = new int[SIZE][SIZE];
        fill();
        moveRandom();
        displayer.setDisplay(board);
    }


    private static void fill()
    {
        int j = 0;
        for(int i = 0; i < SIZE*SIZE; i++)
        {
            board[i%SIZE][j] = i;
            if(j == SIZE)
            {
                j = 0;
            }
        }
    }
    private void moveRandom(){
        boolean changed;

        for(int i = 0; i < 10; i++)
        {
            changed = false;
            switch((int)Math.random() *4)
            {
                case 0:
                    changed = moveUp();
                    break;
                case 1:
                    changed = moveRight();
                    break;
                case 2:
                    changed = moveDown();
                    break;
                case 3:
                    changed = moveLeft();
                    break;
            }
            if(!changed){i--;}
        }
    }


    // To be replaced by event listener
    public void onEvent(MoveEvent d) {
        boolean changed = false;
        switch (d.getDirection()) {
            case UP:
                changed = moveUp();
                break;
            case RIGHT:
                changed = moveRight();
                break;
            case DOWN:
                changed = moveDown();
                break;
            case LEFT:
                changed = moveLeft();
                break;
            default:
                try {
                    throw new Exception("+++Divide By Cucumber Error. Please Reinstall Universe" +
                            " And Reboot +++");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }

        if (checkIfFinished()) {
            resetGame();
        }
        displayer.setDisplay(board);
    }

    private static boolean moveRight() {
        boolean changed = false;
        for (int i = 0; i < SIZE; i++) {  // for each row
            for(int j = 0; i < SIZE; i++)
            {
                if(board[i][j] == SIZE -1)
                {
                    if(i > 0){
                        board[i][j] = board[i-1][j];
                        board[i-1][j] = SIZE -1;
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    private boolean moveLeft() {
        boolean changed = false;
        for (int i = 0; i < SIZE-1; i++) {  // for each row
            for(int j = 0; i < SIZE; i++)
            {
                if(board[i][j] == SIZE -1)
                {
                    board[i][j] = board[i+1][j];
                    board[i+1][j] = SIZE -1;
                    changed = true;

                }
            }
        }
        return changed;
    }

    private boolean moveUp() {
        boolean changed = false;
        for (int i = 0; i < SIZE; i++) {  // for each row
            for(int j = 0; i < SIZE; i++)
            {
                if(board[i][j] == SIZE -1)
                {
                    if(j > 0){
                        board[i][j] = board[i][j-1];
                        board[i][j-1] = SIZE -1;
                        changed = true;
                    }
                }
            }
        }
        return changed;
    }

    private boolean moveDown() {
        boolean changed = false;
        for (int i = 0; i < SIZE; i++) {  // for each row
            for(int j = 0; i < SIZE-1; i++)
            {
                if(board[i][j] == SIZE -1)
                {
                    board[i][j] = board[i+1][j];
                    board[i+1][j] = SIZE -1;
                    changed = true;

                }
            }
        }
        return changed;
    }

    public static void loadState(String boardStr) {
        if (boardStr != null) {
            Logic15.board = parseStringToBoard(boardStr);
        }
    }

    private static int[][] parseStringToBoard(String boardStr) {
        board = new int[SIZE][SIZE];
        String[] values = boardStr.split(",");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = Integer.parseInt(values[i * SIZE + j]);
            }
        }
        return board;
    }

    public static String getBoardParsedToString() {
        String res = "";
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                res += board[i][j] + ",";
            }
        }
        return res;
    }

    public boolean checkIfFinished() {
        int last = board[0][0];
        for (int i = 1; i < SIZE*SIZE; i++)
        {
            if(last +1 != board[(int)i/SIZE][i%SIZE]){return false;}
            last = board[(int)i/SIZE][i%SIZE];

        }
        return true;
    }

}
