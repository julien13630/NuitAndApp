package imhuman.app.com.View;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import imhuman.app.com.Data.UserBean;
import imhuman.app.com.Model.Reseau;
import imhuman.app.com.R;


public class MainActivity extends Activity {

    public  Handler handler = new Handler(){

        public void handleMessage(android.os.Message msg) {
            switch (msg.what){
                case 0://ERROR
                    Toast.makeText(MainActivity.this, Reseau.ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                break;
                case 1://LOGGED
                    UserBean LoggedUser = Reseau.LOGEDUSER;
                    Toast.makeText(MainActivity.this, LoggedUser.getPrenom() + " " + LoggedUser.getNom(), Toast.LENGTH_LONG).show();
                    break;

            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Reseau.logIn("yann.guineau@y-nov.com", "123", handler);

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
