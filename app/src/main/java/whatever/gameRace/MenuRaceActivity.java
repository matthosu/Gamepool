package whatever.gameRace;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import whatever.gamepool.R;

public class MenuRaceActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_race);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        /*int maxScore = prefs.getInt("maxScore", -1);
        int score = prefs.getInt("score", -1);
        String boardStr = prefs.getString("boardStr", null);
        if (maxScore != -1) {
            LogicRace.loadState(score, boardStr, maxScore);
        }*/
    }
}
