package whatever.gameRace;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;

import whatever.gamepool.EndGameEvent;
import whatever.gamepool.EndGameListener;
import whatever.gamepool.GyroAngle;

public class GameRaceActivity extends Activity implements EndGameListener {
        private Canvas gameArea;
    private GyroAngle gyroInterpreter;
    private SensorManager mSensorManager;
    protected PowerManager.WakeLock mWakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RaceView rw = new RaceView(this, null);
        setContentView(rw);
        gyroInterpreter = new GyroAngle(LogicRace.getInstance());
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(gyroInterpreter, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        LogicRace.getInstance().setEndGameListener(this);

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
    }
    public void onEvent(EndGameEvent e)
    {
        finish();
    }

    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        SharedPreferences.Editor editor = getSharedPreferences("prefsRace", MODE_PRIVATE).edit();
        editor.putInt("score", 0); // !!!! jak zrobicie 'getScore()' dla LogicRace to podmiencie !!!!
        editor.putInt("maxScore", LogicRace.getBestScore());
        editor.commit();
        super.onDestroy();
    }
}
