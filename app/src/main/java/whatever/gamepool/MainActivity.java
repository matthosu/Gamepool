package whatever.gamepool;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.GridLayout;

public class MainActivity extends WearableActivity {

    private GridLayout mContainerView;
    private SensorManager mSensorManager;
    private GyroRunner mSensorListener;
    Logic2048 GameLogic;
    GyroRunner Mover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContainerView = (GridLayout) findViewById(R.id.container);
        mContainerView.setBackgroundResource(R.drawable.background);

        GameLogic = new Logic2048(this);
        mSensorListener = new GyroRunner(GameLogic);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
                            //Obiekt GameLogic obsługuje wyświetlanie, jest listenerem który dostaje wiadomości od mSensorListener (kiedy się poruszyć)
                            //Jedyne co trzeba zrobić to wyświetlać zmieniające sie stany, za każdym razem kiedy apka dostanie sygnał
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundResource(R.color.black);
        } else {
            mContainerView.setBackground(null);
        }
    }
}
