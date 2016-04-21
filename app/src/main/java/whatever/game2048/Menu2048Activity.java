package whatever.game2048;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import whatever.gamepool.R;

public class Menu2048Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2048);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        int maxScore = prefs.getInt("maxScore", -1);
        int score = prefs.getInt("score", -1);
        String boardStr = prefs.getString("boardStr", null);
        if (maxScore != -1) {
            Logic2048.loadState(score, boardStr, maxScore);
        }

        final Intent openGameVew = new Intent(this, Game2048Activity.class);

        Button bRestartGame = (Button) findViewById(R.id.b2048_ResetGame);
        Button bResetScore = (Button) findViewById(R.id.b2048_ResetScore);
        Button bBack = (Button) findViewById(R.id.b2048_Back);

        bRestartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logic2048.resetGame();
                updateScores();
                startActivity(openGameVew);
            }
        });
        bResetScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logic2048.resetMaxScore();
                updateScores();
            }
        });
        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(openGameVew);
            }
        });
        startActivity(openGameVew);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateScores();
    }

    private void updateScores() {
        TextView score = (TextView) findViewById(R.id.t2048_ScoreValue);
        TextView maxScore = (TextView) findViewById(R.id.t2048_BestScoreValue);
        score.setText(String.valueOf(Logic2048.getScore()));
        maxScore.setText(String.valueOf(Logic2048.getMaxScore()));
    }
}
