package whatever.Fifteen;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

import whatever.gamepool.GyroRunner;
import whatever.gamepool.MoveEvent;
import whatever.gamepool.MoveListener;
import whatever.gamepool.R;

public class Game15Activity extends Activity  implements MoveListener {
    private SensorManager mSensorManager;
    private GyroRunner mSensorListener;
    protected PowerManager.WakeLock mWakeLock;
    private Displayer displayer;
    public int width;
    public int height;
    boolean isFinished;
    View container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game15);

        initialize();

        int SIZE = 4;
        Bitmap bmp = BitmapFactory.decodeResource( getResources(),
                Menu15Activity.getImageResourceByIndex(getIntent().getIntExtra("selectedImageIndex",0)));
        Logic15.reset();


        GridLayout mContainerView = (GridLayout) findViewById(R.id.container);
        mContainerView.setBackgroundResource(R.drawable.background);
        displayer = new Displayer(this, SIZE, bmp);
        displayer.setDisplay(Logic15.getBoard());

        mSensorListener = new GyroRunner(this);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_GAME);
        //Obiekt GameLogic obsługuje wyświetlanie, jest listenerem który dostaje wiadomości od mSensorListener (kiedy się poruszyć)
        //Jedyne co trzeba zrobić to wyświetlać zmieniające sie stany, za każdym razem kiedy apka dostanie sygnał

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
        mWakeLock.acquire();


        findViewById(R.id.container).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isFinished) {
                    Game15Activity.this.finish();
                    return false;
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    displayer.setDisplay( Logic15.getSolved());

                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    displayer.setDisplay( Logic15.getBoard());
                }
                return true;
            }
        });
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
        SharedPreferences.Editor editor = getSharedPreferences("prefs15", MODE_PRIVATE).edit();
        editor.putString("boardStr", Logic15.getBoardParsedToString());
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
        if (Logic15.onEvent(e)) {
            mSensorManager.unregisterListener(mSensorListener);
            Toast.makeText(this, "GAME OVER. YOU WON", Toast.LENGTH_LONG).show();
            isFinished = true;
        }
        displayer.setDisplay( Logic15.getBoard());
    }
}
