package imhuman.app.com.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.widget.Toast;


import imhuman.app.com.Data.UserBean;
import imhuman.app.com.Model.Reseau;
import imhuman.app.com.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btConnect = (Button) findViewById(R.id.Connexion);
        btConnect.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Connexion.class);
                startActivity(intent);
            }
        });

        Button btRegister = (Button) findViewById(R.id.Inscription);
        btRegister.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Inscription.class);
                startActivity(intent);
            }
        });

        Button btConvert = (Button) findViewById(R.id.Convertisseur);
        btConvert.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Convertisseur.class);
                startActivity(intent);
            }
        });

        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
