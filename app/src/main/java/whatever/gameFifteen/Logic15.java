package whatever.gameFifteen;

import java.util.Random;

import whatever.gamepool.MoveEvent;

/**
 * Created by Mateusz on 2016-06-08.
 */
public class Logic15 {
    private static final int SIZE = 4;

    public static int[][] getBoard() {
        return board;
    }

    /*
        *  Matrix representing game board. Stores values for each field, zero for empty fields
        *  Accessing values like in matrix, from [0, 0] to [SIZE-1, SIZE-1]
        */
    private static int[][] board = new int[SIZE][SIZE];
    private static Random rand = new Random();
    private static int score = 0;
    private static int bestScore = 0;



    public static void reset() {
        board = new int[SIZE][SIZE];
        fill();
        moveRandom();
        System.out.print("DUUUUPAAA"+ getBoardParsedToString());
    }


    private static void fill()
    {
        for(int i = 0; i < SIZE*SIZE; i++)
        {
            board[(int)i/SIZE][i%SIZE] = i;
        }
    }
    private static void moveRandom(){
        boolean changed;

        for(int i = 0; i <SIZE*SIZE*SIZE; i++)
        {
             switch((int)(Math.random() * 4))
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
                default:
                    changed = false;
            }
            if(!changed){
                i--;}
        }
    }


    // To be replaced by event listener
    public static boolean onEvent(MoveEvent d){
        boolean moved = false;
        switch (d.getDirection()) {
            case UP:
                moved =moveUp();
                break;
            case RIGHT:
                moved=moveRight();
                break;
            case DOWN:
                moved=moveDown();
                break;
            case LEFT:
                moved=moveLeft();
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
        if (moved)
        {
            score++;
        }
        return checkIfFinished();
    }

    private static boolean moveRight() {
        for (int i = 0; i < SIZE; i++) {  // for each row
            for(int j = 1; j < SIZE; j++)
            {
                if(board[i][j] == SIZE*SIZE -1)
                {
                        board[i][j] = board[i][j-1];
                        board[i][j-1] = SIZE*SIZE -1;
                        return true;
                }
            }
        }
        return false;
    }

    private static boolean moveLeft() {
        for (int i = 0; i < SIZE; i++) {  // for each row
            for(int j = 0; j < SIZE-1; j++)
            {
                if(board[i][j] == SIZE*SIZE -1)
                {
                    board[i][j] = board[i][j+1];
                    board[i][j+1] = SIZE*SIZE -1;
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean moveDown() {
        for (int i = 1; i < SIZE; i++) {  // for each row
            for(int j = 0; j < SIZE; j++)
            {
                if(board[i][j] == SIZE*SIZE -1)
                {
                        board[i][j] = board[i-1][j];
                        board[i-1][j] = SIZE*SIZE -1;
                        return true;
                }
            }
        }
        return false;
    }

    private static boolean moveUp() {
        for (int i = 0; i < SIZE-1; i++) {  // for each row
            for(int j = 0; j < SIZE; j++)
            {
                if(board[i][j] == SIZE*SIZE -1)
                {
                    board[i][j] = board[i+1][j];
                    board[i+1][j] = SIZE*SIZE -1;
                    return true;
                }
            }
        }
        return false;
    }

    public static void loadState(String boardStr) {
        Logic15.bestScore = bestScore;
        if (boardStr != null) {
            Logic15.score = score;
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

    public static boolean checkIfFinished() {
        int last = board[0][0];
        for (int i = 1; i < SIZE*SIZE; i++)
        {
            if(last +1 != board[(int)i/SIZE][i%SIZE]){return false;}
            last = board[(int)i/SIZE][i%SIZE];

        }
        if(bestScore > score)
        {
            bestScore = score;
        }
        return true;
    }
    public static int[][] getSolved(){
        int[][] solvedBoard = new int[SIZE][SIZE];
        for(int i = 0; i < SIZE*SIZE; i++)
        {
            solvedBoard[(int)i/SIZE] [i%SIZE] = i;
        }
        return solvedBoard;
    }
    public static String getScore()
    {
        return "" + score;
    }

    public static String getBestScore()
    {
        return "" + bestScore;
    }
}
