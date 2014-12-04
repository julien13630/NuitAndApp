package imhuman.app.com.Model;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import imhuman.app.com.Data.UserBean;

/**
 * Created by Julien on 04/12/2014.
 */
public class Reseau {

    public enum RESEAU_MESSAGE {
        ERROR(0), LOGED(1), AllUserList(2), USER(3),;

        private final int value;
        private RESEAU_MESSAGE(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private static String SERVER_URL = "http://192.168.0.5/web/api/";
    public static String ERROR_MESSAGE = "";
    public static boolean LOGED = false;
    public static ArrayList<UserBean> ALLUSERLIST = new ArrayList<UserBean>();



    public Reseau() {

    }

    public static void getAllUser(final Handler handler)
    {
        new Thread(new Runnable() {


            public void run() {
                String result = sendRequest("account");
                if (result == "ERROR")
                {

                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }

                JSONObject json_data=null;
                try{

                    JSONArray jArray = new JSONArray(result);

                    ALLUSERLIST.clear();
                    for(int i=0;i<jArray.length();i++)
                    {

                        json_data = jArray.getJSONObject(i);

                        UserBean tmpUser = new UserBean();
                        tmpUser.setId(json_data.getInt("id"));
                        tmpUser.setEmail(json_data.getString("email"));
                        tmpUser.setNom(json_data.getString("nom"));
                        tmpUser.setPrenom(json_data.getString("prenom"));

                        ALLUSERLIST.add(tmpUser);
                        //MaBdd.insertClient(new Client(json_data.getString("NUM"), json_data.getString("NOM"), json_data.getString("NTOU"), 0, 0));
                        //myActivity.handler.sendEmptyMessage(3);

                    }


                }
                catch(JSONException e){
                    Log.i("tagjsonexp",""+e.toString());
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;

                } catch (ParseException e) {
                    Log.i("tagjsonpars",""+e.toString());
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                    return;
                }

                handler.sendEmptyMessage(RESEAU_MESSAGE.AllUserList.getValue());
            }
        }).start();



    }

    public static boolean logIn(String login, String mdp, final Handler handler)
    {
        new Thread(new Runnable() {


            public void run() {
                String result = sendRequest("account");
                JSONObject json_data=null;
                try{

                    JSONArray jArray = new JSONArray(result);


                    for(int i=0;i<jArray.length();i++)
                    {

                        json_data = jArray.getJSONObject(i);
                        //MaBdd.insertClient(new Client(json_data.getString("NUM"), json_data.getString("NOM"), json_data.getString("NTOU"), 0, 0));
                        //myActivity.handler.sendEmptyMessage(3);
                        java.lang.System.out.println("User "+i);

                    }


                }
                catch(JSONException e){
                    Log.i("tagjsonexp",""+e.toString());
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());

                } catch (ParseException e) {
                    Log.i("tagjsonpars",""+e.toString());
                    handler.sendEmptyMessage(RESEAU_MESSAGE.ERROR.getValue());
                }
            }
        }).start();

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
