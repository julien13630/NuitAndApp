package imhuman.app.com.Model;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import imhuman.app.com.Data.ActionBean;
import imhuman.app.com.Data.PaysBean;
import imhuman.app.com.Data.UserBean;

/**
 * Created by Julien on 04/12/2014.
 */
public class Reseau {

    public enum RESEAU_MESSAGE {
        ERROR(0), LOGED(1), ALLUSERLIST(2), USER(3),PAYSLIST(4), ACTIONSLIST(5), ADDUSER(6), CONVERT(7), ASSO(8);

        private final int value;
        private RESEAU_MESSAGE(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private static String SERVER_URL = "http://voidmx.net/lndli/api/";
    public static String ERROR_MESSAGE = "";
    public static boolean LOGED = false;
    public static UserBean LOGEDUSER = null;
    public static double value;
    public static String selectedAction;
    public static ArrayList<UserBean> ALLUSERLIST = new ArrayList<UserBean>();
    public static ArrayList<PaysBean> PAYSLIST = new ArrayList<PaysBean>();
    public static ArrayList<ActionBean> ACTIONSLIST = new ArrayList<ActionBean>();


    public static void getAllUser(final Handler handler)
    {
        new Thread(new Runnable() {



            public void run() {
                String result = sendRequest("account", null);
                if (result == "ERROR")
                {

                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }

                JSONObject json_data=null;
                try{

                    JSONArray jArray = new JSONArray(result);

                    if (jArray.length() == 0) {
                        handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                        return;
                    }

                    ALLUSERLIST.clear();
                    for(int i=0;i<jArray.length();i++)
                    {

                        json_data = jArray.getJSONObject(i);

                        UserBean tmpUser = new UserBean();
                        tmpUser.setId(json_data.getInt("id"));
                        tmpUser.setEmail(json_data.getString("Email"));
                        tmpUser.setNom(json_data.getString("Nom"));
                        tmpUser.setPrenom(json_data.getString("Prenom"));

                        ALLUSERLIST.add(tmpUser);
                        //MaBdd.insertClient(new Client(json_data.getString("NUM"), json_data.getString("NOM"), json_data.getString("NTOU"), 0, 0));
                        //myActivity.handler.sendEmptyMessage(3);

                    }



                } catch (Exception e) {
                    Log.i("tagjsonpars",""+e.toString());
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }

                handler.sendEmptyMessage(RESEAU_MESSAGE.ALLUSERLIST.getValue());
            }
        }).start();



    }

    public static boolean logIn(final String mail,final String mdp, final Handler handler)
    {
        new Thread(new Runnable() {


            public void run() {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("email", mail));
                nameValuePairs.add(new BasicNameValuePair("password", mdp));
                String result = sendRequest("account/login",nameValuePairs );
                if (result == "ERROR")
                {

                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }
                try{
                    JSONObject obj = new JSONObject(result);

                    try{
                        ERROR_MESSAGE = obj.getString("error");
                        handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());

                        return;
                    }
                    catch(Exception e)
                    {
                        UserBean tmpUser = new UserBean();
                        tmpUser.setId(obj.getInt("id"));
                        tmpUser.setEmail(obj.getString("Email"));
                        tmpUser.setNom(obj.getString("Nom"));
                        tmpUser.setPrenom(obj.getString("Prenom"));

                        LOGEDUSER = tmpUser;
                        LOGED = true;
                        handler.sendEmptyMessage(RESEAU_MESSAGE.LOGED.getValue());
                        return;
                    }


                }
                catch(Exception e) {
                    Log.i("tagjsonexp", "" + e.toString());
                    ERROR_MESSAGE = "Erreur données invalides";
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }
            }
        }).start();

        return false;
    }

    public static void getListPays(final Handler handler)
    {
        new Thread(new Runnable() {



            public void run() {
                String result = sendRequest("account", null);
                if (result == "ERROR")
                {
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }

                JSONObject json_data=null;
                try{

                    JSONArray jArray = new JSONArray(result);

                    if (jArray.length() == 0) {
                        handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                        return;
                    }

                    ALLUSERLIST.clear();
                    for(int i=0;i<jArray.length();i++)
                    {

                        json_data = jArray.getJSONObject(i);

                        PaysBean tmpPays = new PaysBean();
                        tmpPays.setId(json_data.getInt("id"));
                        tmpPays.setName(json_data.getString("Nom"));


                        PAYSLIST.add(tmpPays);
                        //MaBdd.insertClient(new Client(json_data.getString("NUM"), json_data.getString("NOM"), json_data.getString("NTOU"), 0, 0));
                        //myActivity.handler.sendEmptyMessage(3);

                    }



                } catch (Exception e) {
                    Log.i("tagjsonpars",""+e.toString());
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }

                handler.sendEmptyMessage(RESEAU_MESSAGE.PAYSLIST.getValue());
            }
        }).start();
    }

    public static void convert(final double somme,final Handler handler)
    {
        new Thread(new Runnable() {


            public void run() {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("value",somme+""));
                String result = sendRequest("mission/search",nameValuePairs );
                Log.i("tagjsonexp", "" + result);
                if (result == "ERROR")
                {
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }

                try {

                    JSONObject jObject = new JSONObject(result);

                    ACTIONSLIST.clear();

                    ActionBean tmp = new ActionBean();
                    tmp.setType("medicament");
                    tmp.setText(jObject.getString("medicament"));
                    ACTIONSLIST.add(tmp);

                    tmp = new ActionBean();
                    tmp.setType("arbre");
                    tmp.setText(jObject.getString("arbre"));
                    ACTIONSLIST.add(tmp);

                    tmp = new ActionBean();
                    tmp.setType("ecole");
                    tmp.setText(jObject.getString("ecole"));
                    ACTIONSLIST.add(tmp);

                    tmp = new ActionBean();
                    tmp.setType("repas");
                    tmp.setText(jObject.getString("repas"));
                    ACTIONSLIST.add(tmp);

                    tmp = new ActionBean();
                    tmp.setType("kit");
                    tmp.setText(jObject.getString("kit"));
                    ACTIONSLIST.add(tmp);


                }
                catch(Exception e) {
                    Log.i("tagjsonexp", "" + e.toString());
                    ERROR_MESSAGE = "Erreur données invalides";
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }

                handler.sendEmptyMessage(RESEAU_MESSAGE.CONVERT.getValue());
                value = somme;
            }
        }).start();


    }

    public static void getAsso(final String action, final String continent,final Handler handler)
    {
        new Thread(new Runnable() {

            public void run() {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("value", "" + value));
                nameValuePairs.add(new BasicNameValuePair("continent", continent));
                nameValuePairs.add(new BasicNameValuePair("categorie", action));

                String result = sendRequest("mission/search", nameValuePairs);
                Log.i("tagjsonpars", "" + result);
                if (result == "ERROR") {
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }

                JSONObject json_data = null;
                try {

                    JSONArray jArray = new JSONArray(result);

                    if (jArray.length() == 0) {
                        handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                        return;
                    }



                        json_data = jArray.getJSONObject(0);

                        selectedAction = json_data.getString("message");




                        //MaBdd.insertClient(new Client(json_data.getString("NUM"), json_data.getString("NOM"), json_data.getString("NTOU"), 0, 0));
                        //myActivity.handler.sendEmptyMessage(3);


                    handler.sendEmptyMessage(RESEAU_MESSAGE.ASSO.getValue());
                } catch (Exception e) {
                    Log.i("tagjsonexp", "" + e.toString());
                    ERROR_MESSAGE = "Erreur données invalides";
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }
            }
        }).start();

    }

    public static void createUser(final UserBean newUser,final Handler handler)
    {
        new Thread(new Runnable() {


            public void run() {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("nom",newUser.getNom()));
                nameValuePairs.add(new BasicNameValuePair("prenom",newUser.getPrenom()));
                nameValuePairs.add(new BasicNameValuePair("email",newUser.getEmail()));
                nameValuePairs.add(new BasicNameValuePair("password",newUser.getPassword()));
                nameValuePairs.add(new BasicNameValuePair("code_postal","00000"));
                nameValuePairs.add(new BasicNameValuePair("adresse","a"));
                nameValuePairs.add(new BasicNameValuePair("pays",""));

                String result = sendRequest("account/create",nameValuePairs );
                if (result == "ERROR")
                {
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }

                handler.sendEmptyMessage(RESEAU_MESSAGE.ADDUSER.getValue());
            }
        }).start();
    }
    private static String sendRequest(String request, List<NameValuePair> nameValuePairs)
    {

        String result = null;
        InputStream is = null;
        JSONObject json_data=null;
        if (nameValuePairs == null)
            nameValuePairs = new ArrayList<NameValuePair>();

        try{
            //commandes httpClient
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(SERVER_URL+request);


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
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }
        catch(Exception e){
            Log.i("taghttppost",""+e.toString());
            ERROR_MESSAGE = "Erreur lors de la connexion au serveur";
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
            ERROR_MESSAGE = "Erreur lors de la réception des données";
            return "ERROR";
        }

        return result;


    }
}
