package imhuman.app.com.Model;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;


import android.util.Log;
/**
 * Created by Julien on 04/12/2014.
 */
public class Reseau {

    private static String SERVER_URL = "http://192.168.0.5/web/api/";


    public Reseau() {
        getAllUser();
    }

    public static void getAllUser()
    {
        new Thread(new Runnable() {

            public void run() {
                sendRequest("account");
            }
        }).start();



    }
    public static boolean logIn(String login, String mdp)
    {
        sendRequest("account/read?");
        return false;
    }

    private static String sendRequest(String request)
    {



        String result = null;
        InputStream is = null;
        JSONObject json_data=null;


        try{
            //commandes httpClient
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpGet httppost = new HttpGet(SERVER_URL+request);
            HttpParams httpParameters = new BasicHttpParams();
            // Set the timeout in milliseconds until a connection is established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 3000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 5000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.setParams(httpParameters);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }
        catch(Exception e){
            Log.i("taghttppost",""+e.toString());
            return "ERROR";
        }

        //conversion de la réponse en chaine de caractère
        try
        {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));

            StringBuilder sb  = new StringBuilder();

            String line = null;

            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }

            is.close();

            result = sb.toString();
        }
        catch(Exception e)
        {
            Log.i("tagconvertstr",""+e.toString());
            return "ERROR";
        }

        return result;
        //recuperation des donnees json
        /*try{

            JSONArray jArray = new JSONArray(result);


            for(int i=0;i<jArray.length();i++)
            {

                json_data = jArray.getJSONObject(i);
                //MaBdd.insertClient(new Client(json_data.getString("NUM"), json_data.getString("NOM"), json_data.getString("NTOU"), 0, 0));
                //myActivity.handler.sendEmptyMessage(3);
                java.lang.System.out.println("Client "+i);

            }


        }
        catch(JSONException e){
            Log.i("tagjsonexp",""+e.toString());
            return "ERROR";

        } catch (ParseException e) {
            Log.i("tagjsonpars",""+e.toString());
            return "ERROR";

        }*/

    }
}
