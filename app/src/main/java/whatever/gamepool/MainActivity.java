package whatever.gamepool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private final Class[] activityContainer = {Menu2048Activity.class};
    private final String[] activityDescriptions = {"2048\nAn easy game, where we have to merge numbers"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.gameList);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, activityDescriptions);
        listView.setAdapter(arrayAdapter);

        final Context context = this;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent SelectedToGame = new Intent(context, activityContainer[position]);
                startActivity(SelectedToGame);
            }
        });
    }
}
