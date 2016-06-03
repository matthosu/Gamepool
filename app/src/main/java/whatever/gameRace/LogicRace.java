package whatever.gameRace;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import whatever.gamepool.EndGameListener;

public class LogicRace {
    private final static Random rand = new Random();
    private static final LogicRace instance = new LogicRace();
    public boolean isSpeededUp;
    private static int RESOLUTION_X = (int) (280 * 3.8);
    private static int RESOLUTION_Y = (int) (280 * 3.8);
    private static int SIDEROAD_WIDTH = 15;
    private static int NUM_OF_LANES = 3;
    private static int CAR_WIDTH = (RESOLUTION_X - 30) / NUM_OF_LANES;
    private static int CAR_HEIGHT = (int) (CAR_WIDTH * 1.5);
    private float SPEED = 0.02f;
    private float SPAWN_FACTOR = 0.01f;

    private static int bestScore = 0;
    private long startTime;
    private LinkedList<Car> obstacles;
    private Car player;
    private int roadMove;
    private int playerRL = 0;
    private int score;
    private boolean[] isLaneTaken;
    private boolean[] lanesSecondRowTaken;

    public int getRoadMove() {
        return roadMove;
    }

    private LogicRace() {
        initialize();
    }

    public static LogicRace getInstance() {
        return instance;
    }

    public int getCarWidth() {
        return CAR_WIDTH;
    }

    public int getCarHeight() {
        return CAR_HEIGHT;
    }

    private void initialize() {
        obstacles = new LinkedList<>();

        System.out.println("******************** " + RESOLUTION_X + ", " + RESOLUTION_Y );
        player = new Car(RESOLUTION_X / 2 - CAR_WIDTH / 2, RESOLUTION_Y - CAR_HEIGHT - 5,
                CAR_WIDTH, CAR_HEIGHT);
        isLaneTaken = new boolean[NUM_OF_LANES];
        lanesSecondRowTaken = new boolean[NUM_OF_LANES];

        addObstacle();
        startTime = System.nanoTime();
        score = 0;
        roadMove = 0;

    }

    private synchronized boolean addObstacle() {
        boolean[] freeLanes = getFreeLanes();
        System.out.print("FREE LANEs: |");
        boolean anyLaneAvailable = false;
        for (int i = 0; i < freeLanes.length; i++) {
            System.out.print(freeLanes[i] ? (i + " | ") : "");
            if (freeLanes[i]) anyLaneAvailable = true;
        }
        if (!anyLaneAvailable) return false;
        System.out.println();

        int i = 0;
        int lane = rand.nextInt(NUM_OF_LANES);
        while (i < NUM_OF_LANES && !freeLanes[lane]) {
            lane = (lane + 1) % NUM_OF_LANES;
            i++;
        }
        System.out.println("Trying to add on lane no. : " + lane + " which is " + (willLeaveWayOut(lane) ? "free" : "taken"));

        if (freeLanes[lane] && willLeaveWayOut(lane))

        /*boolean willAdd = false;
        for(Car obs:obstacles){
            if (obs.getPosY() > CAR_HEIGHT) break;
            if(obs.getPosY() < 0){
                isLaneTaken[(obs.getPosX()-SIDEROAD_WIDTH)%CAR_WIDTH] = true;
            }else if(obs.getPosY() < (CAR_HEIGHT/3*2))
            {
                isLaneTaken[(obs.getPosX()-SIDEROAD_WIDTH)%CAR_WIDTH] = true;
                lanesSecondRowTaken[(obs.getPosX()-SIDEROAD_WIDTH)%CAR_WIDTH] = true;
            }else{
                lanesSecondRowTaken[(obs.getPosX()-SIDEROAD_WIDTH)%CAR_WIDTH] = true;
            }

        }
        ArrayList<Integer>lanesToAdd = new ArrayList<>();
        for(int i = 0; i < NUM_OF_LANES; i++)
        {
            if(!isLaneTaken[i] || !lanesSecondRowTaken[i])
            {
                lanesToAdd.add(i);
            }
        }

        System.out.print("\nDEBKWIA ");
        for (Integer i: lanesToAdd) {
            System.out.print(i + " ");

        }
        int lane = -1;
        if(lanesToAdd.size() > 1)
        {
            willAdd = true;
            lane = lanesToAdd.get((int) Math.floor(Math.random()*(lanesToAdd.size())));

        }

        isLaneTaken = new boolean[NUM_OF_LANES];
        lanesSecondRowTaken = new boolean[NUM_OF_LANES];

        if(willAdd){
            System.out.print("\nDEBKWIA ");
            System.out.print(lane);

         */ {
            obstacles.add(new Car(SIDEROAD_WIDTH + lane * CAR_WIDTH, -CAR_HEIGHT, CAR_WIDTH, CAR_HEIGHT));
            return true;
        } else {
            return false;
        }
    }

    private boolean willLeaveWayOut(int lane) {
        System.out.println("WESZło0");
        int playerLane = (int) ((player.getPosX() - SIDEROAD_WIDTH + 0.2 * CAR_WIDTH) / CAR_WIDTH);
        System.out.println("WESZło1");
        if (lane >= playerLane - 1 && lane <= playerLane + 1) {
            System.out.println("samochód na pozycji : " + playerLane);
            int waysOut = 3;
            if (!laneAvailable(playerLane - 1)) {
                waysOut--;
                System.out.println("zajęte L");
            }

            if (!laneAvailable(playerLane)) {
                waysOut--;
                System.out.println("zajęty C");
            }

            if (!laneAvailable(playerLane + 1)) {
                waysOut--;
                System.out.println("zajęte R");
            }
            return waysOut > 1;
        } else return laneAvailable(lane);

    }

    public synchronized boolean[] getFreeLanes() {
        boolean[] freeLanes = new boolean[NUM_OF_LANES];
        for (int i = 0; i < freeLanes.length; i++) freeLanes[i] = true;
        for (int i = 0; i < obstacles.size() && i <= NUM_OF_LANES; i++) {
            if (obstacles.get(obstacles.size() - i - 1).getPosY() < CAR_HEIGHT)
                freeLanes[(obstacles.get(obstacles.size() - i - 1).getPosX() - SIDEROAD_WIDTH) / getCarWidth()] = false;
        }
        return freeLanes;
    }

    private boolean laneAvailable(int lane) {
        if (lane < 0 || lane >= NUM_OF_LANES) return false;
        for (Car c : obstacles) {
            if (c.getPosY() < CAR_HEIGHT && (c.getPosX() - SIDEROAD_WIDTH) / CAR_WIDTH == lane)
                return false;
        }
        return true;
    }

    private boolean laneAvailable2(int lane) {
        if (lane < 0 || lane >= NUM_OF_LANES) return false;
        for (Car c : obstacles) {
            if (c.getPosY() < CAR_HEIGHT && (c.getPosX() - SIDEROAD_WIDTH) / CAR_WIDTH == lane)
                return false;
        }
        return true;
    }

    public int[] move(double playerMove) {
        int elapsedTime = (int) ((System.nanoTime() - startTime) / 1000000000);
        if (elapsedTime > 1) {
            playerRL = (int) Math.signum(Math.round(playerMove * 100000) / 100000);
            if (rand.nextDouble() < SPAWN_FACTOR * NUM_OF_LANES || obstacles.size() <= 1)
                addObstacle();

            if (player.getPosX() + playerMove < SIDEROAD_WIDTH) {
                player.setPosX(SIDEROAD_WIDTH);
            } else if (player.getPosX() + playerMove > RESOLUTION_X - CAR_WIDTH - SIDEROAD_WIDTH) {
                player.setPosX(RESOLUTION_X - CAR_WIDTH - SIDEROAD_WIDTH);
            } else {
                player.setPosX((int) (player.getPosX() + playerMove));
            }

            for (Iterator<Car> carIterator = obstacles.iterator(); carIterator.hasNext(); ) {
                Car c = carIterator.next();
                if (!isSpeededUp) c.moveDown((int) (5 + elapsedTime * SPEED));
                else {
                    c.moveDown((int) (15 + elapsedTime * SPEED));
                }
                if (player.checkCollision(c)) return player.getCollisionCenter(c);
                if (c.getPosY() > RESOLUTION_Y) carIterator.remove();
            }
            roadMove += (int) (5 + 2 * elapsedTime * SPEED);
            if (elapsedTime > bestScore) bestScore = elapsedTime;
        }

        return new int[]{-1, -1};
    }

    public void restartGame() {
        instance.initialize();
    }

    public LinkedList<Car> getObstacles() {
        return obstacles;
    }

    public Car getPlayer() {
        return player;
    }


    public int getNumOfLanes() {
        return NUM_OF_LANES;
    }

    public int getResX() {
        return RESOLUTION_X;
    }

    public int getResY() {
        return RESOLUTION_Y;
    }

    public int getPlayerRL() {
        return playerRL;
    }

    public static int getBestScore() {
        return bestScore;
    }

    public static void resetBestScore() {
        bestScore = 0;
    }

    public synchronized void setNumOfLanes(int num) {
        if (num >= 3 && num <= 5) {
            NUM_OF_LANES = num;
            CAR_WIDTH = (RESOLUTION_X - 30) / NUM_OF_LANES;
            CAR_HEIGHT = (int) (CAR_WIDTH * 1.5);
            Car.INLET_X = (int) (0.127 * CAR_WIDTH);
            Car.INLET_Y = (int) (0.082 * CAR_HEIGHT);
        }
    }

    public void loadState(int score, int maxScore) {
        this.score = score;
        bestScore = maxScore;
    }

    public int getScore() {
        return score;
    }

    public static void setRes(int res) {
        RESOLUTION_X = res;
        RESOLUTION_Y = res;

        SIDEROAD_WIDTH = 15;
        CAR_WIDTH = (RESOLUTION_X - 30) / NUM_OF_LANES;
        CAR_HEIGHT = (int) (CAR_WIDTH * 1.5);

    }
}
