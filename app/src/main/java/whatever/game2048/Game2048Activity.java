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
import android.widget.TextView;

import whatever.gamepool.GyroRunner;
import whatever.gamepool.MoveEvent;
import whatever.gamepool.MoveListener;
import whatever.gamepool.R;

public class Game2048Activity extends Activity  implements MoveListener {
    private Displayer displayer;

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
        mSensorListener = new GyroRunner(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
        //Obiekt GameLogic obsługuje wyświetlanie, jest listenerem który dostaje wiadomości od mSensorListener (kiedy się poruszyć)
        //Jedyne co trzeba zrobić to wyświetlać zmieniające sie stany, za każdym razem kiedy apka dostanie sygnał

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
        mWakeLock.acquire();
        System.out.println("Doszło TUTAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJ");
        displayer = new Displayer(this, Logic2048.getSIZE());

        displayer.setDisplay(Logic2048.getBoard());

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

    @Override
    public void onEvent(MoveEvent e) {
        if (GameLogic.onEvent(e)) {
            Logic2048.resetGame();
        }

        TextView tf = (TextView) findViewById(R.id.t2048_ScoreValue);
        tf.setText("" + Logic2048.getScore());

        TextView btf = (TextView) findViewById(R.id.t2048_BestScoreValue);
        btf.setText("" + Logic2048.getMaxScore());
        displayer.setDisplay(Logic2048.getBoard());
    }
}
