package imhuman.app.com.View;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import imhuman.app.com.Data.UserBean;
import imhuman.app.com.Model.Reseau;
import imhuman.app.com.R;

public class Inscription extends Activity {

    public Handler handler = new Handler(){

        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 0://ERROR
                    Toast.makeText(Inscription.this, Reseau.ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                    break;
                case 6://ADDED

                    Toast.makeText(Inscription.this, "Compte créé", Toast.LENGTH_LONG).show();
                    break;

            }
        };
    };

    private EditText etEmail, etPassword, etName, etLastName, etAdresse;
    private Button bInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etName = (EditText) findViewById(R.id.etName);
        etLastName = (EditText) findViewById(R.id.etFName);
        etAdresse = (EditText) findViewById(R.id.etAdresse);

        bInscription = (Button) findViewById(R.id.btInscription);


        bInscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserBean newUser = new UserBean();
                newUser.setNom(etLastName.getText().toString());
                newUser.setPrenom(etName.getText().toString());
                newUser.setEmail(etEmail.getText().toString());
                newUser.setPassword(etPassword.getText().toString());
                newUser.setAdresse(etAdresse.getText().toString());
                Reseau.createUser(newUser, handler);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inscription, menu);
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
