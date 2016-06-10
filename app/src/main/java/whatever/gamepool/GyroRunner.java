package whatever.gamepool;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Just to parse gyroscope sygnals for our purposes
 * <p/>
 * Before using - move listener should be set, probably
 */
public class GyroRunner implements SensorEventListener {
    private MoveListener mListener;
    private final int MOVEMENT_VALUE = 3;
    private static long lastChange;

    public GyroRunner() {
        lastChange = System.nanoTime();
    }

    public GyroRunner(MoveListener ListenToNoises) {
        mListener = ListenToNoises;
        lastChange = System.nanoTime();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getStringType().equals(event.sensor.STRING_TYPE_GYROSCOPE)) {
            if ((System.nanoTime() - lastChange)/Math.pow(10, 8) > 6 && (Math.abs(event.values[0]) > MOVEMENT_VALUE
                    || Math.abs(event.values[1]) > MOVEMENT_VALUE)) {
                System.out.println(System.nanoTime() + "\t\t" + lastChange + "\t\t" + (System.nanoTime() - lastChange) + "\t\t" + (System.nanoTime() - lastChange)/Math.pow(10,8));
                lastChange = System.nanoTime();
                if (event.values[1] > MOVEMENT_VALUE) {
                    mListener.onEvent(new MoveEvent(this, Directions.RIGHT));
                } else if (event.values[1] < -MOVEMENT_VALUE) {
                    mListener.onEvent(new MoveEvent(this, Directions.LEFT));
                } else if (event.values[0] > MOVEMENT_VALUE) {
                    mListener.onEvent(new MoveEvent(this, Directions.DOWN));
                } else if (event.values[0] < -MOVEMENT_VALUE) {
                    mListener.onEvent(new MoveEvent(this, Directions.UP));
                }
            }
        }
    }

    public void setEvListener(MoveListener evListener) {
        mListener = evListener;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public static enum Directions {
        UP, RIGHT, DOWN, LEFT
    }
}
