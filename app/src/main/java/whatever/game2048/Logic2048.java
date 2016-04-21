package whatever.game2048;

import android.app.Activity;

import java.util.Random;

import whatever.gamepool.MoveEvent;
import whatever.gamepool.MoveListener;

/**
 * Class implementing 2048 game logic - basic operations of mowing elements up, down, left, right
 */
public class Logic2048 implements MoveListener {
    private static final int SIZE = 4;
    private Displayer displayer;
    Game2048Activity game;

    /*
    *  Matrix representing game board. Stores values for each field, zero for empty fields
    *  Accessing values like in matrix, from [0, 0] to [SIZE-1, SIZE-1]
    */
    private static int[][] board;
    private static Random rand = new Random();
    private static int score = 0;
    private static int maxScore = 0;

    public Logic2048(Activity a) {
        displayer = new Displayer(a, SIZE);
        game = (Game2048Activity) a;

        if (board == null) {
            resetGame();
        }
        displayer.setDisplay(board);
    }

    public static int getScore() {
        return score;
    }

    public static int getMaxScore() {
        return maxScore;
    }

    public static void resetGame() {
        score = 0;
        board = new int[SIZE][SIZE];
        fillRandom();
        fillRandom();
    }

    public static void resetMaxScore() {
        maxScore = 0;
        resetGame();
    }

    private static void fillRandom() {
        int x = 0, y = 0;
        int n = rand.nextInt(SIZE * SIZE) + 1;
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
        if (changed) fillRandom();

        if (checkIfFinished()) {
            Logic2048.resetGame();
        }
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
                    if (score > maxScore) maxScore = score;
                    board[i][positionToWrite] = 2 * cache;
                    positionToWrite--;
                    cache = 0;
                } else if ((cache == 0) & (board[i][j] != 0)) {
                    cache = board[i][j];
                } else if (board[i][j] != cache & (board[i][j] != 0)) {
                    board[i][positionToWrite] = cache;
                    cache = board[i][j];
                    positionToWrite--;
                }
            }
            if (cache != 0) {
                if (board[i][positionToWrite] != cache) changed = true;
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
                    if (score > maxScore) maxScore = score;
                    board[i][positionToWrite] = 2 * cache;
                    positionToWrite++;
                    cache = 0;
                } else if ((cache == 0) & (board[i][j] != 0)) {
                    cache = board[i][j];
                } else if (board[i][j] != cache & (board[i][j] != 0)) {
                    board[i][positionToWrite] = cache;
                    cache = board[i][j];
                    positionToWrite++;
                }
            }
            if (cache != 0) {
                if (board[i][positionToWrite] != cache) changed = true;
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
                    if (score > maxScore) maxScore = score;
                    board[positionToWrite][i] = 2 * cache;
                    positionToWrite++;
                    cache = 0;
                } else if ((cache == 0) & (board[j][i] != 0)) {
                    cache = board[j][i];
                } else if (board[j][i] != cache & (board[j][i] != 0)) {
                    board[positionToWrite][i] = cache;
                    cache = board[j][i];
                    positionToWrite++;
                }
            }
            if (cache != 0) {
                if (board[positionToWrite][i] != cache) changed = true;
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
                if ((board[j][i] == cache) & (cache != 0)) {
                    changed = true;
                    score += 2 * cache;
                    if (score > maxScore) maxScore = score;
                    board[positionToWrite][i] = 2 * cache;
                    positionToWrite--;
                    cache = 0;
                } else if ((cache == 0) & (board[j][i] != 0)) {
                    cache = board[j][i];
                } else if (board[j][i] != cache & (board[j][i] != 0)) {
                    board[positionToWrite][i] = cache;
                    cache = board[j][i];
                    positionToWrite--;
                }
            }
            if (cache != 0) {
                if (board[positionToWrite][i] != cache) changed = true;
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

    public static void loadState(int score, String boardStr, int maxScore) {
        Logic2048.maxScore = maxScore;
        if (boardStr != null) {
            Logic2048.score = score;
            Logic2048.board = Logic2048.parseStringToBoard(boardStr);
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
        // look for free tile
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        // if none found check if merge possible
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (canBeMerged(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean canBeMerged(int i, int j) {
        return i > 0 && board[i][j] == board[i - 1][j]
                || i < SIZE - 1 && board[i][j] == board[i + 1][j]
                || j > 0 && board[i][j] == board[i][j - 1]
                || j < SIZE - 1 && board[i][j] == board[i][j + 1];
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
