package whatever.gameRace;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Arrays;

import whatever.gamepool.GyroAngle;
import whatever.gamepool.R;
import whatever.gamepool.RotationEvent;
import whatever.gamepool.RotationListener;

public class GameRaceActivity extends Activity implements RotationListener {
    protected PowerManager.WakeLock mWakeLock;
    private RaceView rw;
    private SensorManager mSensorManager;
    private GyroAngle gyroInterpreter;
    private boolean isFinished = false;
    private Point size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);

        Display display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        LogicRace.setRes(size.x);

        setContentView(createView());

        rw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFinished) {
                    GameRaceActivity.this.finish();
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    LogicRace.getInstance().isSpeededUp = true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    LogicRace.getInstance().isSpeededUp = false;
                }
                return true;
            }
        });
        gyroInterpreter = new GyroAngle(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(gyroInterpreter, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
    }

    private View createView() {
        rw = new RaceView(this, null, getIntent().getIntExtra("colorId", 0));

        RelativeLayout myLayout = new RelativeLayout(this);

        RelativeLayout.LayoutParams rwParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, size.x);
        rwParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rwParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
        rwParams.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);

        myLayout.addView(rw, rwParams);

        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View v = inflater.inflate(R.layout.race_score, null);

        RelativeLayout.LayoutParams vParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, size.y - size.x);
        vParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        vParams.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

        myLayout.addView(v, vParams);
        return myLayout;
    }

    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        SharedPreferences.Editor editor = getSharedPreferences("prefsRace", MODE_PRIVATE).edit();
        editor.putInt("score", LogicRace.getInstance().getScore());
        editor.putInt("maxScore", LogicRace.getBestScore());
        editor.commit();
        mSensorManager.unregisterListener(gyroInterpreter, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));
        LogicRace.getInstance().isSpeededUp = false;
        super.onDestroy();


    }

    public void onEvent(RotationEvent e) {
        int[] curr = LogicRace.getInstance().move(e.getDirectionY());
        if (!Arrays.equals(curr, (new int[]{-1, -1}))) {
            rw.boom(curr);
            isFinished = true;

            mSensorManager.unregisterListener(gyroInterpreter, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION));
            Toast.makeText(this, "GAME OVER\nClick on screen to exit", Toast.LENGTH_LONG).show();
        } else {
            rw.invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isFinished) {
            finish();
        }
        return false;
    }
}
