package whatever.game2048;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Display;
import android.widget.GridLayout;

import whatever.gamepool.GyroRunner;
import whatever.gamepool.R;

public class Game2048Activity extends Activity {

    private SensorManager mSensorManager;
    private GyroRunner mSensorListener;
    protected PowerManager.WakeLock mWakeLock;
    public static Logic2048 GameLogic;
    public int width;
    public int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);

        initialize();

        GridLayout mContainerView = (GridLayout) findViewById(R.id.container);
        mContainerView.setBackgroundResource(R.drawable.background);
        GameLogic = new Logic2048(this);
        mSensorListener = new GyroRunner(GameLogic);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
        //Obiekt GameLogic obsługuje wyświetlanie, jest listenerem który dostaje wiadomości od mSensorListener (kiedy się poruszyć)
        //Jedyne co trzeba zrobić to wyświetlać zmieniające sie stany, za każdym razem kiedy apka dostanie sygnał

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
        mWakeLock.acquire();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        this.mWakeLock.release();
        SharedPreferences.Editor editor = getSharedPreferences("prefs", MODE_PRIVATE).edit();
        editor.putString("boardStr", Logic2048.getBoardParsedToString());
        editor.putInt("score", Logic2048.getScore());
        editor.putInt("maxScore", Logic2048.getMaxScore());
        editor.commit();
        super.onDestroy();
    }

    private void initialize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
    }
}
