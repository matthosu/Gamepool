package whatever.gameRace;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;

import whatever.gamepool.GyroAngle;
import whatever.gamepool.RotationEvent;
import whatever.gamepool.RotationListener;

public class GameRaceActivity extends Activity implements RotationListener {
    protected PowerManager.WakeLock mWakeLock;
    private RaceView rw;
    private SensorManager mSensorManager;
    private GyroAngle gyroInterpreter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        rw = new RaceView(this, null, getIntent().getIntExtra("colorId",0));
        setContentView(rw);
        gyroInterpreter = new GyroAngle(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(gyroInterpreter, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
    }

    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        SharedPreferences.Editor editor = getSharedPreferences("prefsRace", MODE_PRIVATE).edit();
        editor.putInt("score", LogicRace.getInstance().getScore());
        editor.putInt("maxScore", LogicRace.getBestScore());
        editor.commit();
        mSensorManager.unregisterListener(gyroInterpreter, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));

        super.onDestroy();


    }

    public void onEvent(RotationEvent e) {
        if (!LogicRace.getInstance().move(e.getDirectionY(), rw.isClicked)) {
            rw.isClicked = false;
            finish();
        } else {
            rw.invalidate();
        }
    }

}
