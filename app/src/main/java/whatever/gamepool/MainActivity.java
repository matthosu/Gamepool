package whatever.gamepool;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.GridLayout;
import android.os.PowerManager;

public class MainActivity extends Activity {

    private GridLayout mContainerView;
    private SensorManager mSensorManager;
    private GyroRunner mSensorListener;
    protected PowerManager.WakeLock mWakeLock;
    Logic2048 GameLogic;
    GyroRunner Mover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainerView = (GridLayout) findViewById(R.id.container);
        mContainerView.setBackgroundResource(R.drawable.background);

        GameLogic = new Logic2048(this);
        mSensorListener = new GyroRunner(GameLogic);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
                            //Obiekt GameLogic obsługuje wyświetlanie, jest listenerem który dostaje wiadomości od mSensorListener (kiedy się poruszyć)
                            //Jedyne co trzeba zrobić to wyświetlać zmieniające sie stany, za każdym razem kiedy apka dostanie sygnał

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
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
        super.onDestroy();
    }

}
