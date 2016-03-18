package whatever.gamepool;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Just to parse gyroscope sygnals for our purposes
 *
 * Before using - move listener should be set, probably
 */
public class GyroRunner implements SensorEventListener{
    MoveListener mListener;
    public GyroRunner(){}
    public GyroRunner(MoveListener ListenToNoises)
    {
       mListener = ListenToNoises;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getStringType() == event.sensor.STRING_TYPE_GYROSCOPE) {
            if (event.values[1] > 1){
                mListener.onEvent(new MoveEvent(this, Directions.UP));
            }else if (event.values[1] < -1){
                mListener.onEvent(new MoveEvent(this, Directions.DOWN));
            }else if (event.values[0] > 1){
                mListener.onEvent(new MoveEvent(this, Directions.LEFT));
            }else if (event.values[0] < -1){
                mListener.onEvent(new MoveEvent(this, Directions.RIGHT));
            }
        }
    }
    public void setEvListener(MoveListener evListener)
    {
        mListener = evListener;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
