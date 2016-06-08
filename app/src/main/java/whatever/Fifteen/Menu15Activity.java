package whatever.Fifteen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import whatever.game2048.Game2048Activity;
import whatever.game2048.Logic2048;
import whatever.gamepool.R;

public class Menu15Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2048);
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String boardStr = prefs.getString("boardStr", null);
        final Intent openGameVew = new Intent(this, Game2048Activity.class);

        Button bRestartGame = (Button) findViewById(R.id.b2048_ResetGame);
        Button bBack = (Button) findViewById(R.id.b2048_Back);

        bRestartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logic15.reset();
                startActivity(openGameVew);
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
    }

}
