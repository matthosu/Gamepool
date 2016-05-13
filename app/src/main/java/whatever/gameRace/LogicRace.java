package whatever.gameRace;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import whatever.gamepool.EndGameListener;

public class LogicRace {
    private final static Random rand = new Random();
    private static final LogicRace instance = new LogicRace();

    private final static int RESOLUTION_X = (int) (280 * 3);
    private final static int RESOLUTION_Y = (int) (280 * 3);
    private int NUM_OF_LANES = 3;
    private int CAR_WIDTH = (RESOLUTION_X - 30) / NUM_OF_LANES;
    private int CAR_HEIGHT = (int) (CAR_WIDTH * 1.5);
    private float SPEED = 0.02f;
    private float SPAWN_FACTOR = 0.001f;

    private static int bestScore = 0;
    private long startTime;
    private LinkedList<Car> obstacles;
    private Car player;
    private int roadMove;
    private int playerRL = 0;
    private int score;

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
        player = new Car(RESOLUTION_X / 2 - CAR_WIDTH / 2, RESOLUTION_Y - CAR_HEIGHT - 5,
                CAR_WIDTH, CAR_HEIGHT);
        addObstacle();
        startTime = System.nanoTime();
        score = 0;
        roadMove = 0;
        System.out.println("##################************ " + startTime / 1000000000 + "*************#################");
    }

    private synchronized boolean addObstacle() {
        boolean[] freeLanes = getFreeLanes();
        int i = 0;
        int lane = rand.nextInt(NUM_OF_LANES);
        while (i < NUM_OF_LANES && !freeLanes[lane]) {
            lane = (lane + 1) % NUM_OF_LANES;
            i++;
        }
        if (freeLanes[lane] && willLeaveWayOut(lane)) {
            obstacles.add(new Car(15 + lane * CAR_WIDTH, -CAR_HEIGHT, CAR_WIDTH, CAR_HEIGHT));
            return true;
        } else {
            return false;
        }
    }

    private boolean willLeaveWayOut(int lane) {
        int playerLane = (player.getPosX() - 15 + (int) (CAR_WIDTH * 0.2)) / CAR_WIDTH;
        if (lane >= playerLane - 1 && lane <= playerLane + 1) {
            int waysOut = 3;
            if (playerLane == NUM_OF_LANES - 1 || playerLane == 0) waysOut--;
            if (!laneAvailable(playerLane - 1)) waysOut--;
            if (!laneAvailable(playerLane)) waysOut--;
            if (!laneAvailable(playerLane + 1)) waysOut--;
            return waysOut >= 2;
        } else return laneAvailable(lane);

    }

    public synchronized boolean[] getFreeLanes() {
        boolean[] freeLanes = new boolean[NUM_OF_LANES];
        for (int i = 0; i < freeLanes.length; i++) freeLanes[i] = true;
        for (int i = 0; i < obstacles.size() && i <= NUM_OF_LANES; i++) {
            if (obstacles.get(obstacles.size() - i - 1).getPosY() < CAR_HEIGHT * 3)
                freeLanes[(obstacles.get(obstacles.size() - i - 1).getPosX() - 15) / getCarWidth()] = false;
        }
        return freeLanes;
    }

    private boolean laneAvailable(int lane) {
        for (Car c : obstacles) {
            if (c.getPosY() < CAR_HEIGHT * 3 && (c.getPosX() - 15) / NUM_OF_LANES == lane)
                return false;
        }
        return true;
    }

    public boolean move(double playerMove) {
        int elapsedTime = (int) ((System.nanoTime() - startTime) / 1000000000);
        if (elapsedTime > 1) {
            playerRL = (int) Math.signum(Math.round(playerMove * 100000) / 100000);
            if (rand.nextDouble() < SPAWN_FACTOR * NUM_OF_LANES || obstacles.size() <= 1)
                addObstacle();
            if (player.getPosX() + playerMove < 15) {
                player.setPosX(15);
            } else if (player.getPosX() + playerMove > RESOLUTION_X - CAR_WIDTH - 15) {
                player.setPosX(RESOLUTION_X - CAR_WIDTH - 15);
            } else {
                player.setPosX((int) (player.getPosX() + playerMove));
            }

            for (Iterator<Car> carIterator = obstacles.iterator(); carIterator.hasNext(); ) {
                Car c = carIterator.next();
                c.moveDown((int) (5 + elapsedTime * SPEED));
                if (player.checkCollision(c)) return false;
                if (c.getPosY() > RESOLUTION_Y) carIterator.remove();
            }
            roadMove += (int) (5 + 2 * elapsedTime * SPEED);
            score = elapsedTime;
            if (elapsedTime > bestScore) bestScore = elapsedTime;
        }

        return true;
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
            Car.INLET_X = (int) (0.1 * CAR_WIDTH);
            Car.INLET_Y = (int) (0.1 * CAR_HEIGHT);
        }
    }

    public void loadState(int score, int maxScore) {
        this.score = score;
        bestScore = maxScore;
    }

    public int getScore() {
        return score;
    }
}
