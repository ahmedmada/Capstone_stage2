package com.e.k.m.a.capstone_stage2.Network;

import android.util.Log;

import com.e.k.m.a.capstone_stage2.Models.Trailer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();


    public ArrayList<Trailer> parseTrailerJson(String data){
        ArrayList<Trailer> arrayList = new ArrayList<>();
        try {
            JSONObject dataObject = new JSONObject(data);
            JSONArray dataArray = dataObject.getJSONArray("results");
            String[] result = new String[dataArray.length()];
            String name,key;
            JSONObject object1;
            Trailer ob;
            for (int i = 0; i < dataArray.length(); i++) {
                object1 = dataArray.getJSONObject(i);
                name = object1.optString("name");
                key = object1.optString("key");
                ob = new Trailer();
                ob.setName(name);
                ob.setKey(key);
                arrayList.add(ob);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,e.getMessage());
        }
        return arrayList;
    }


    private URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG,"Error With Creating Url"+e.getMessage());
        }
        return url;
    }
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else
                Log.e(TAG,"HttpResponseCode"+urlConnection.getResponseCode());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG,"Error With MakeHttpReqquest"+e.getMessage());
        }finally {
            if (urlConnection != null)
                urlConnection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }


        return jsonResponse;
    }
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output =new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }
    public ArrayList<Trailer> getTrailerList(String urlString){
        URL url = createUrl(urlString);
        String jsonString = "";
            try {
                jsonString = makeHttpRequest(url);
                return parseTrailerJson(jsonString);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG,e.getMessage());
                return null;
            }
    }
}