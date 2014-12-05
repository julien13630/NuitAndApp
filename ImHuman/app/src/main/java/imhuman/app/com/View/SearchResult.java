package imhuman.app.com.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import imhuman.app.com.Model.Reseau;
import imhuman.app.com.R;

public class SearchResult extends Activity {

    public android.os.Handler handler = new android.os.Handler(){

        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 0://ERROR
                    Toast.makeText(SearchResult.this, Reseau.ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                    break;
                case 8://ASSO

                    Intent intent = new Intent(SearchResult.this, Dons.class);
                    startActivity(intent);
                    break;

            }
        };
    };

    private ListView listView1;
    private int selectedAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        Actions weather_data[] = new Actions[]
                {
                        new Actions(R.drawable.medicament_icon, Reseau.ACTIONSLIST.get(0).getText()),
                        new Actions(R.drawable.arbre_icon, Reseau.ACTIONSLIST.get(1).getText()),
                        new Actions(R.drawable.ecole_icon, Reseau.ACTIONSLIST.get(2).getText()),
                        new Actions(R.drawable.repas_icon, Reseau.ACTIONSLIST.get(3).getText()),
                        new Actions(R.drawable.kitscolaire_icon, Reseau.ACTIONSLIST.get(4).getText())
                };

        final ArrayList<String> Continent = new ArrayList<String>();
        Continent.add("Europe");
        Continent.add("Amérique");
        Continent.add("Asie");
        Continent.add("Oceanie");
        Continent.add("Afrique");
        Continent.add("Antarctique");


        ActionsAdapter adapter =new ActionsAdapter(this,
                R.layout.listview_item_row, weather_data);


        listView1 = (ListView)findViewById(R.id.listView1);

        View header = (View)getLayoutInflater().inflate(R.layout.listview_header_row, null);
        listView1.addHeaderView(header);

        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchResult.this);
                selectedAction = position-1;
                builder.setTitle("Selectionnez la zone géographique");
                ListView lw = new ListView(SearchResult.this);
                lw.setAdapter(new ArrayAdapter<String>(SearchResult.this, android.R.layout.simple_list_item_activated_1, Continent));
                lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Reseau.getAsso(Reseau.ACTIONSLIST.get(selectedAction).getType(), Continent.get(position), handler);
                    }
                });
                builder.setView(lw);
                builder.create().show();


            }
        });
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
