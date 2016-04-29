package whatever.gameRace;

import android.graphics.Bitmap;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Whatever on 2016-04-21.
 */
public class LogicRace {
    private static LogicRace instance = new LogicRace();

    private final static int RESOLUTION_X = 280;
    private final static int RESOLUTION_Y = 280;
    private final static int NUM_OF_LANES = 5;
    private final static int CAR_WIDTH = (RESOLUTION_X - 30) / NUM_OF_LANES; // = 30 :p
    private final static int CAR_HEIGHT = (int) (CAR_WIDTH * 1.5);
    private final static float SPEED = 0.01f;
    private final static Random rand = new Random();

    private static int bestScore = 0;

    private long startTime;
    private LinkedList<Car> obstacles;
    private Car player;

    private LogicRace(){
        initialize();
    }

    public static LogicRace getInstance(){
        return instance;
    }
    public int getCarWidth(){
        return CAR_WIDTH;
    }
    public int getCarHeight(){
        return CAR_HEIGHT;
    }

    private void initialize(){
        obstacles = new LinkedList<>();
        player = new Car(RESOLUTION_X / 2 - CAR_WIDTH / 2, RESOLUTION_Y - CAR_HEIGHT - 5,
                CAR_WIDTH, CAR_HEIGHT);
        addObstacle();
        addObstacle();
        startTime = System.nanoTime();
    }

    private boolean addObstacle() {
        if( !isFull() ) {
            int lane = rand.nextInt(NUM_OF_LANES);
            while( !laneAvailable(lane) ) lane = (lane + 1) % NUM_OF_LANES;
            obstacles.add(new Car(15 + lane * CAR_WIDTH, 0, CAR_WIDTH, CAR_HEIGHT));
            return true;
        } else return false;
    }

    private boolean addObstacle(Bitmap[] img) {
        if( !isFull() ) {
            int lane = rand.nextInt(NUM_OF_LANES);
            while( !laneAvailable(lane) ) lane = (lane + 1) % NUM_OF_LANES;
            obstacles.add(new Car(15 + lane * CAR_WIDTH, 0, CAR_WIDTH, CAR_HEIGHT, img));
            return true;
        } else return false;
    }

    private boolean isFull() {
        boolean[] freeLanes = new boolean[NUM_OF_LANES];
        for (boolean b : freeLanes) b = false;
        for (Car c: obstacles) {
            if(c.getPosY() < CAR_HEIGHT * 1.5) {
                freeLanes[(c.getPosX()-15)/NUM_OF_LANES] = false;
            }
        }
        int count = 0;
        for(boolean b : freeLanes) if(b) count++;
        return count <= 1;
    }

    private boolean laneAvailable(int lane) {
        for (Car c: obstacles) {
            if( c.getPosY() < CAR_HEIGHT * 1.5 && (c.getPosX()-15)/NUM_OF_LANES == lane ) return false;
        }
        return true;
    }
    public boolean move(double playerMove){
        int elapsedTime = (int)( (System.nanoTime() - startTime) / 1000000000 );
        player.setPosX((int) (player.getPosX() + playerMove));

        for(Iterator<Car> carIterator = obstacles.iterator(); carIterator.hasNext();) {
            Car c = carIterator.next();
            c.moveDown((int) (1 + elapsedTime * SPEED));
            if(player.checkCollision(c)) return false;
            if(c.getPosY() > RESOLUTION_Y) carIterator.remove();
        }

        if(elapsedTime > bestScore) bestScore = elapsedTime;
        return true;
    }
    private void endGame() {

    }
    public void restartGame(){
        initialize();
    }

    public LinkedList<Car> getObstacles(){
        return obstacles;
    }
    public Car getPlayer(){
        return player;
    }
}
