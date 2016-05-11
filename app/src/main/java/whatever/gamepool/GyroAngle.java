package whatever.gamepool;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class GyroAngle implements SensorEventListener {   // need to be registered in listeners to SENSOR_ORIENTATION in activity
    private RotationListener mListener;
    private SensorManager hSensManager;
    private final int MOVEMENT_VALUE = 3;
    private long lastChange;

    public GyroAngle() {
        lastChange = System.nanoTime();
    }

    public GyroAngle(RotationListener ListenToNoises) {
        mListener = ListenToNoises;
        lastChange = System.nanoTime();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getStringType().equals(event.sensor.STRING_TYPE_ORIENTATION)) {
            mListener.onEvent(new RotationEvent(this, -event.values[2]));
        }
    }

    public void setEvListener(RotationListener evListener) {
        mListener = evListener;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
