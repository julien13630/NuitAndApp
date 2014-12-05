package imhuman.app.com.View;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import imhuman.app.com.Model.Reseau;
import imhuman.app.com.R;

public class SearchResult extends Activity {

    private ListView listView1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        Actions weather_data[] = new Actions[]
                {
                        new Actions(R.drawable.arbre_icon, Reseau.ACTIONSLIST.get(1).getText()),
                        new Actions(R.drawable.ecole_icon, Reseau.ACTIONSLIST.get(2).getText()),
                        new Actions(R.drawable.kitscolaire_icon, Reseau.ACTIONSLIST.get(4).getText()),
                        new Actions(R.drawable.medicament_icon, Reseau.ACTIONSLIST.get(0).getText()),
                        new Actions(R.drawable.repas_icon, Reseau.ACTIONSLIST.get(3).getText())
                };

        ActionsAdapter adapter = new ActionsAdapter(this,
                R.layout.listview_item_row, weather_data);


        listView1 = (ListView)findViewById(R.id.listView1);

        View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listView1.addHeaderView(header);

        listView1.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
