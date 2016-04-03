package whatever.gamepool;

import android.app.Activity;

import java.util.EventListener;
import java.util.Random;

/**
 * Class implementing 2048 game logic - basic operations of mowing elements up, down, left, right
 */
public class Logic2048 implements MoveListener {
    private final int SIZE = 4;
    private Displayer displayer;
    /*
    *  Matrix representing game board. Stores values for each field, zero for empty fields
    *  Accessing values like in matrix, from [0, 0] to [SIZE-1, SIZE-1]
    */
    private final int[][] board;
    private Random rand;
    private int score;

    public Logic2048(Activity a) {
        displayer = new Displayer(a, SIZE);
        board = new int[SIZE][SIZE];
        rand = new Random();
        score = 0;
        fillRandom();
        fillRandom();
        displayer.setDisplay(board);
    }

    public int[][] getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    private void fillRandom() {
        int x = 0, y = 0;
        int n = rand.nextInt(SIZE * SIZE);
        boolean valueIs2 = rand.nextDouble() < 0.9;
        // Find n'th empty position, if reached end of board, start over
        while (n > 0) {
            y++;
            if (y == SIZE) {
                x++;
                y = 0;
            }
            if (x == SIZE) {
                x = 0;
            }
            if (board[x][y] == 0) n--; // Counting only empty fields

        }
        // Set selected field using appropriate value
        if (valueIs2) {
            board[x][y] = 2;
        } else {
            board[x][y] = 4;
        }
    }

    // To be replaced by event listener
    public void onEvent(MoveEvent d) {
        // direction described by number position on clock board, for example 12 for up, 9 for left
        switch (d.getDirection()) {
            case UP:
                moveUp();
                break;
            case RIGHT:
                moveRight();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
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
        fillRandom();
        displayer.setDisplay(board);
    }

    private boolean moveRight() {
        boolean changed = false;
        for (int i = 0; i < SIZE; i++) {  // for each row
            int positionToWrite = SIZE - 1;          // position for join
            int cache = board[i][positionToWrite]; // value for join

            for (int j = SIZE - 2; j >= 0; j--) {
                if ((board[i][j] == cache) & (cache != 0)) {
                    changed = true;
                    score += 2 * cache;
                    board[i][positionToWrite] = 2 * cache;
                    positionToWrite--;
                    cache = 0;
                } else if ((cache == 0) & (board[i][j] != 0)) {
                    cache = board[i][j];
                } else if (board[i][j] != cache & (board[i][j] != 0)) {
                    changed = true;
                    board[i][positionToWrite] = cache;
                    cache = board[i][j];
                    positionToWrite--;
                }
            }
            if (cache != 0) {
                changed = true;
                board[i][positionToWrite] = cache;
                positionToWrite--;
            }
            while (positionToWrite >= 0) {
                board[i][positionToWrite] = 0;
                positionToWrite--;
            }
        }
        return changed;
    }

    private boolean moveLeft() {
        boolean changed = false;
        for (int i = 0; i < SIZE; i++) {  // for each row
            int positionToWrite = 0;                // position for join
            int cache = board[i][positionToWrite]; // value for join

            for (int j = 1; j < SIZE; j++) {
                if ((board[i][j] == cache) & (cache != 0)) {
                    changed = true;
                    score += 2 * cache;
                    board[i][positionToWrite] = 2 * cache;
                    positionToWrite++;
                    cache = 0;
                } else if ((cache == 0) & (board[i][j] != 0)) {
                    cache = board[i][j];
                } else if (board[i][j] != cache & (board[i][j] != 0)) {
                    changed = true;
                    board[i][positionToWrite] = cache;
                    cache = board[i][j];
                    positionToWrite++;
                }
            }
            if (cache != 0) {
                changed = true;
                board[i][positionToWrite] = cache;
                positionToWrite++;
            }
            while (positionToWrite < SIZE) {
                board[i][positionToWrite] = 0;
                positionToWrite++;
            }
        }
        return changed;
    }

    private boolean moveUp() {
        boolean changed = false;
        for (int i = 0; i < SIZE; i++) {  // for each column
            int positionToWrite = 0;                // position for join
            int cache = board[positionToWrite][i];  // value for join

            for (int j = 1; j < SIZE; j++) {
                if ((board[j][i] == cache) & (cache != 0)) {
                    changed = true;
                    score += 2 * cache;
                    board[positionToWrite][i] = 2 * cache;
                    positionToWrite++;
                    cache = 0;
                } else if ((cache == 0) & (board[j][i] != 0)) {
                    cache = board[j][i];
                } else if (board[j][i] != cache & (board[j][i] != 0)) {
                    changed = true;
                    board[positionToWrite][i] = cache;
                    cache = board[j][i];
                    positionToWrite++;
                }
            }
            if (cache != 0) {
                changed = true;
                board[positionToWrite][i] = cache;
                positionToWrite++;
            }
            while (positionToWrite < SIZE) {
                board[positionToWrite][i] = 0;
                positionToWrite++;
            }
        }
        return changed;
    }

    private boolean moveDown() {
        boolean changed = false;
        for (int i = 0; i < SIZE; i++) {  // for each column
            int positionToWrite = SIZE - 1;         // position for join
            int cache = board[positionToWrite][i];// value for join

            for (int j = SIZE - 2; j >= 0; j--) {
                score += 2 * cache;
                if ((board[j][i] == cache) & (cache != 0)) {
                    changed = true;
                    board[positionToWrite][i] = 2 * cache;
                    positionToWrite--;
                    cache = 0;
                } else if ((cache == 0) & (board[j][i] != 0)) {
                    cache = board[j][i];
                } else if (board[j][i] != cache & (board[j][i] != 0)) {
                    changed = true;
                    board[positionToWrite][i] = cache;
                    cache = board[j][i];
                    positionToWrite--;
                }
            }
            if (cache != 0) {
                changed = true;
                board[positionToWrite][i] = cache;
                positionToWrite--;
            }
            while (positionToWrite >= 0) {
                board[positionToWrite][i] = 0;
                positionToWrite--;
            }
        }
        return changed;
    }

    /*public void print() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Logic2048 test = new Logic2048();
        test.print();
        System.out.println("---START---\n");
    }*/
}
