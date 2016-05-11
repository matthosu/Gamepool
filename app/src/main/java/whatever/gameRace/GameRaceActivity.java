package whatever.gameRace;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import whatever.gamepool.EndGameEvent;
import whatever.gamepool.EndGameListener;
import whatever.gamepool.GyroAngle;

public class GameRaceActivity extends Activity implements EndGameListener {
        private Canvas gameArea;
    private GyroAngle gyroInterpreter;
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RaceView rw = new RaceView(this, null);
        setContentView(rw);
        gyroInterpreter = new GyroAngle(LogicRace.getInstance());
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(gyroInterpreter, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        LogicRace.getInstance().setEndGameListener(this);
    }
    public void onEvent(EndGameEvent e)
    {
        finish();
    }
}
