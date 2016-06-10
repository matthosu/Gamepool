package whatever.gameFifteen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import whatever.gameRace.LogicRace;
import whatever.gamepool.R;

public class Menu15Activity extends Activity {
    protected PowerManager.WakeLock mWakeLock;
    Intent openGameVew;
    private final static int[] images = {R.drawable.yoda, R.drawable.lady, R.drawable.mist, R.drawable.lotr,
            R.drawable.witcher, R.drawable.starry_night, R.drawable.car6, R.drawable.car7, R.drawable.car8, R.drawable.car9};
    ImageView imageSwitcher;
    private int selectedImage = -1; //index in images - answer for: which one is selected?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_15);

        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();

        imageSwitcher = (ImageView) findViewById(R.id.carImageView);
        imageSwitcher.setScaleType(ImageView.ScaleType.FIT_CENTER);
        SharedPreferences prefs = getSharedPreferences("prefs15", MODE_PRIVATE);
        int maxScore = prefs.getInt("maxScore", -1);
        int score = prefs.getInt("score", -1);
        if (maxScore != -1) {
            LogicRace.getInstance().loadState(score, maxScore);        }

        openGameVew = new Intent(this, Game15Activity.class);

        Button rRestartGame = (Button) findViewById(R.id.race_ResetGame);
        Button rResetScore = (Button) findViewById(R.id.race_ResetScore);
        Button rBack = (Button) findViewById(R.id.race_Back);

        rRestartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logic15.reset();
                updateScores();
                openGameVew.putExtra("selectedImageIndex", selectedImage);
                startActivity(openGameVew);
            }
        });
        rResetScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Logic15.resetBestScore();
                updateScores();
            }
        });
        rBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(openGameVew);
            }
        });

        onSwitch(null);
        //startActivity(openGameVew);
    }

    public void onSwitch(View view) {
        selectedImage = (selectedImage + 1) % images.length;
        imageSwitcher.setImageResource(images[selectedImage]);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateScores();
    }

    private void updateScores() {
        TextView score = (TextView) findViewById(R.id.race_ScoreValue);
        TextView maxScore = (TextView) findViewById(R.id.race_BestScoreValue);
        //score.setText(String.valueOf(Logic15.getScore()));
        //maxScore.setText(String.valueOf(Logic15.getBestScore()));
    }
    public static int getImageResourceByIndex(int imageIndex){
        return imageIndex < images.length ? images[imageIndex] : images[0];
    }

}
