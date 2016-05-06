package whatever.gameRace;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;

import whatever.gamepool.EndGameEvent;
import whatever.gamepool.EndGameListener;

public class GameRaceActivity extends Activity implements EndGameListener {
        private Canvas gameArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RaceView rw = new RaceView(this, null);
        setContentView(rw);
    }
    public void onEvent(EndGameEvent e)
    {
        finish();
    }
}
