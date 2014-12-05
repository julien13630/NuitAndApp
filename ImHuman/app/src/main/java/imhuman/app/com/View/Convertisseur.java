package imhuman.app.com.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.logging.Handler;

import imhuman.app.com.Data.UserBean;
import imhuman.app.com.Model.Reseau;
import imhuman.app.com.R;

public class Convertisseur extends Activity {

    public android.os.Handler handler = new android.os.Handler(){

        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 0://ERROR
                    Toast.makeText(Convertisseur.this, Reseau.ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                    break;
                case 7://CONVERT

                    Intent intent = new Intent(Convertisseur.this, SearchResult.class);
                    startActivity(intent);
                    break;

            }
        };
    };

    private Button btEuros;
    private EditText sommeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convertisseur);

        btEuros = (Button) findViewById(R.id.btEuros);
        sommeText = (EditText) findViewById(R.id.editTextDon);
        btEuros.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Reseau.convert(Double.parseDouble(sommeText.getText().toString()),handler);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_convertisseur, menu);
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
