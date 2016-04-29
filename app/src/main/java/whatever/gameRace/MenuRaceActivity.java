package whatever.gameRace;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import whatever.gamepool.R;

public class MenuRaceActivity extends Activity {
    private final int[] carImages = {R.drawable.car0, R.drawable.car1, R.drawable.car2, R.drawable.car3,
            R.drawable.car4, R.drawable.car5, R.drawable.car6, R.drawable.car7, R.drawable.car8, R.drawable.car9};
    private  final String[] difficultyLevels = {"easy", "medium", "hard"};
    ImageView imageSwitcher;
    Button levelSwitcher;
    private int position_car = 0; //index in carImage - answer for: which one is selected?
    private int position_level = 0; //intex in difficultyLevels - answer for: which difficulty is selected?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_race);

        imageSwitcher = (ImageView) findViewById(R.id.carImageView);
        levelSwitcher = (Button) findViewById(R.id.race_levelValue);
        /*SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        int maxScore = prefs.getInt("maxScore", -1);
        int score = prefs.getInt("score", -1);
        String boardStr = prefs.getString("boardStr", null);
        if (maxScore != -1) {
            LogicRace.loadState(score, boardStr, maxScore);
        }*/

        onSwitch(null);
    }

    public void onSwitch(View view) {
        position_car = (position_car + 1) % carImages.length;
        imageSwitcher.setImageResource(carImages[position_car]);
    }

    public void onLevelChanged(View view) {
        position_level = (position_level + 1) % difficultyLevels.length;
        levelSwitcher.setText(difficultyLevels[position_level]);
    }
}
