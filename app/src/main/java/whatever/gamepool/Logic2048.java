package whatever.gamepool;

import java.util.Random;

/**
 * Created by Whatever on 2016-03-17.
 * Class implementing 2048 game logic - basic operations of mowing elements up, down, left, right
 */
public class Logic2048 {
    private final int SIZE = 4;
    /*
    *  Matrix representing game board. Stores values for each field, zero for empty fields
    *  Accessing values like in matrix, from [0, 0] to [SIZE-1, SIZE-1]
    */
    private final int [][] board;
    private Random rand;
    private int score;

    public Logic2048() {
        board = new int[SIZE][SIZE];
        rand = new Random();
        score = 0;
        fillRandom();
        fillRandom();
    }

    public int[][] getBoard(){
        return board;
    }

    public int getScore(){
        return score;
    }

    private void fillRandom() {
        int x = 0, y = 0;
        int n = rand.nextInt(SIZE*SIZE);
        boolean valueIs2 = rand.nextDouble() < 0.9;
        // Find n'th empty position, if reached end of board, start over
        while( n > 0 ){
            if( board[x][y] == 0 ) n--; // Counting only empty fields
            y++;
            if( y == SIZE - 1 ){
                x++;
                y = 0;
            }
            if( x == SIZE ){
                x = 0;
            }
        }
        // Set selected field using appropriate value
        if ( valueIs2 ){
            board[x][y] = 2;
        } else {
            board[x][y] = 4;
        }
    }

    // To be replaced by event listener
    public void moveAndFill(int direction){
        // direction described by number position on clock board, for example 12 for up, 9 for left
        direction = direction % 12;
        switch(direction){
            case 0:
                moveUp();
                break;
            case 3:
                moveRight();
                break;
            case 6:
                moveDown();
                break;
            case 9:
                moveLeft();
                break;
            default:
                try{
                    throw new Exception("+++Divide By Cucumber Error. Please Reinstall Universe" +
                            " And Reboot +++");
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
        fillRandom();
    }

    private void moveRight() {
        for ( int i = 0; i < SIZE; i++ ){  // for each row
            int positionToWrite = SIZE -1;          // position for join
            int cache = board [i][positionToWrite]; // value for join

            for( int j =  SIZE - 2; j >= 0; j-- ){
                if( (board[i][j] == cache) & (cache != 0) ){
                    score += 2 * cache;
                    board[i][positionToWrite] = 2 * cache;
                    positionToWrite--;
                    cache = 0;
                } else if ( (cache == 0) & (board[i][j] != 0) ) {
                    cache = board[i][j];
                } else if ( board [i][j] != cache ){
                    cache = board [i][j];
                    positionToWrite--;
                }
            }
            if ( cache != 0 ){
                board[i][positionToWrite] = cache;
                positionToWrite--;
            }
            while ( positionToWrite > 0 ){
                board[i][positionToWrite] = 0;
                positionToWrite--;
            }
        }
    }
    private void moveLeft() {
        for ( int i = 0; i < SIZE; i++ ){  // for each row
            int positionToWrite = 0;                // position for join
            int cache = board [i][positionToWrite]; // value for join

            for( int j =  1; j < SIZE; j++ ){
                if( (board[i][j] == cache) & (cache != 0) ){
                    score += 2 * cache;
                    board[i][positionToWrite] = 2 * cache;
                    positionToWrite++;
                    cache = 0;
                } else if ( (cache == 0) & (board[i][j] != 0) ) {
                    cache = board[i][j];
                } else if ( board [i][j] != cache ){
                    cache = board [i][j];
                    positionToWrite++;
                }
            }
            if ( cache != 0 ){
                board[i][positionToWrite] = cache;
                positionToWrite++;
            }
            while ( positionToWrite < SIZE ){
                board[i][positionToWrite] = 0;
                positionToWrite++;
            }
        }
    }
    private void moveDown() {
        for ( int i = 0; i < SIZE; i++ ){  // for each column
            int positionToWrite = SIZE - 1;         // position for join
            int cache = board [positionToWrite] [i];// value for join

            for( int j =  SIZE - 2; j >= 0; j-- ){
                score += 2 * cache;
                if( (board[j][i] == cache) & (cache != 0) ){
                    board[positionToWrite][i] = 2 * cache;
                    positionToWrite--;
                    cache = 0;
                } else if ( (cache == 0) & (board[j][i] != 0) ) {
                    cache = board[j][i];
                } else if ( board[j][i] != cache ){
                    cache = board[j][i];
                    positionToWrite--;
                }
            }
            if ( cache != 0 ){
                board[positionToWrite][i] = cache;
                positionToWrite--;
            }
            while ( positionToWrite > 0 ){
                board[positionToWrite][i] = 0;
                positionToWrite--;
            }
        }
    }
    private void moveUp() {
        for ( int i = 0; i < SIZE; i++ ){  // for each column
            int positionToWrite = 0;                // position for join
            int cache = board[positionToWrite][i];  // value for join

            for( int j =  1; j < SIZE; j++ ){
                if( (board[j][i] == cache) & (cache != 0) ){
                    score += 2 * cache;
                    board[positionToWrite][i] = 2 * cache;
                    positionToWrite++;
                    cache = 0;
                } else if ( (cache == 0) & (board[j][i] != 0) ) {
                    cache = board[j][i];
                } else if ( board [j][i] != cache ){
                    cache = board [j][i];
                    positionToWrite++;
                }
            }
            if ( cache != 0 ){
                board[positionToWrite][i] = cache;
                positionToWrite++;
            }
            while ( positionToWrite < SIZE ){
                board[positionToWrite][i] = 0;
                positionToWrite++;
            }
        }
    }
}
