package com.smile.retrofitapp.dao;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import com.smile.retrofitapp.models.Comment;

public class HttpURLConnection {
    private static final String TAG = "HttpURLConnection";
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    public static ArrayList<Comment> getComments() {
        final String webUrl = BASE_URL + "comments";
        Log.d(TAG, "getComments.WebUrl = " + webUrl);

        ArrayList<Comment> comments = new ArrayList();

        URL url;
        java.net.HttpURLConnection myConnection = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            url = new URL(webUrl);
            myConnection = (java.net.HttpURLConnection)url.openConnection();
            myConnection.setConnectTimeout(15000);
            myConnection.setReadTimeout(15000);
            myConnection.setRequestMethod("GET");
            myConnection.setDoInput(true);
            int responseCode = myConnection.getResponseCode();
            if (responseCode == java.net.HttpURLConnection.HTTP_OK) {
                Log.i(TAG, "getComments.REST Web Service -> Succeeded to connect.");
                inputStream = myConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                StringBuilder sb = new StringBuilder();
                int readBuff;
                while ( (readBuff=inputStreamReader.read()) != -1) {
                    sb.append((char)readBuff);
                }
                String result = sb.toString();  // the result
                Log.d(TAG, "getComments.Web output = " + result);
                // JSONObject json = new JSONObject(result);
                // Log.d(TAG, "getComments.JSONObject = " + json);
                JSONArray jsonArray = new JSONArray(result);
                Log.d(TAG, "getComments.jsonArray = " + jsonArray);

                int postId;
                int id;
                String name;
                String email;
                String body;

                JSONObject json;
                for (int i=0; i<jsonArray.length(); i++) {
                    json = jsonArray.getJSONObject(i);
                    postId = json.getInt("postId");
                    id = json.getInt("id");
                    name = json.getString("name");
                    email = json.getString("email");
                    body = json.getString("body");

                    comments.add(new Comment(postId, id, name, email, body));
                }
            } else {
                Log.i(TAG, "getComments.REST Web Service -> Failed to connect.");
                comments = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, "getComments.REST Web Service -> Failed due to exception.");
            comments = null;
        }
        finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                  inputStream.close();
                }
                if (myConnection != null) {
                    myConnection.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return comments;
    }
}
