package whatever.gamepool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import whatever.game2048.Menu2048Activity;
import whatever.gameRace.MenuRaceActivity;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.gameList);
        final ItemAdapter arrayAdapter = new ItemAdapter(this,
                R.layout.item_layout);
        listView.setAdapter(arrayAdapter);

        final Context context = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent SelectedToGame = new Intent(context, ItemContainer.getInstance().getActivityClass(position));
                startActivity(SelectedToGame);
            }
        });
    }
}
