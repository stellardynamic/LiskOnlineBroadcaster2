package lisksnake.liskonlinebroadcaster;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class SendPostRequest extends AsyncTask<String, Void, String> {
    private List<OnTransactionDoneListener> listeners = new ArrayList<OnTransactionDoneListener>();

    URL urlNetLink;
    String strURL, strTransaction, strNetHash;

    protected void onPreExecute(){}

    protected String doInBackground(String... arg0) {
        strURL = arg0[0];
        strTransaction = arg0[1];
        strNetHash = arg0[2];

        try {
            urlNetLink = new URL(strURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try{
            JSONObject postDataParams;
            try {
                postDataParams = new JSONObject(strTransaction);
            }
            catch(Exception e){
                return new String("ERROR: Transaction had no valid JSON format!");
            }
            Log.e("params",postDataParams.toString());
            HttpURLConnection conn = (HttpURLConnection) urlNetLink.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("os", "linux4.4.0-78-generic");
            conn.setRequestProperty("version", "1.0.0");
            conn.setRequestProperty("port", "1");
            conn.setRequestProperty("nethash", strNetHash);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(postDataParams.toString());
            writer.flush();
            writer.close();
            os.close();

            // check response:
            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK ) {
                BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line="";
                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            }
            else {
                return new String("false : "+responseCode);
            }
        }
        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        String strResult = "";
        String strMsgToListeners = "";
        try {
            JSONObject postResult = new JSONObject(result);
            JSONObject meta = postResult.getJSONObject("meta");
            strResult = meta.getString("status");

        } catch (JSONException e) {
            strMsgToListeners = "Error: No JSON format received";
            // Notify everybody that may be interested.
            for (OnTransactionDoneListener hl : listeners)
                hl.onDone(strMsgToListeners);
        }

        if (strResult == "true")
        {
            strMsgToListeners = "SUCCESS";
        }
        else
        {
            strMsgToListeners = "Error: See following text for details: \n";
            if (strResult == "") {
                strMsgToListeners = strMsgToListeners + result;
            } else{
                strMsgToListeners = strMsgToListeners + strResult;
            }
        }

        // Notify everybody that may be interested.
        for (OnTransactionDoneListener hl : listeners)
            hl.onDone(strMsgToListeners);
    }




    public void setOnTransactionDoneListener(SendPostRequest.OnTransactionDoneListener l) {
        listeners.add(l);
    }

    public interface OnTransactionDoneListener {
        void onDone(String var1);
    }
}
