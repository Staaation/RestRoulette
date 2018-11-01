package com.pete.restroulette;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Pete on 3/1/2018.
 */

public class GooglePlaces {

    private static final String TAG = "GooglePlaces";
    private static final String API_KEY = "AIzaSyBq7xtuD2xRnKRiTmjQXOPJutUhnK2bDLE";

    private static final Uri ENDPOINT = Uri
            .parse("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
            .buildUpon()
            .appendQueryParameter("location","39.905919"+","+"-75.013937")
            .appendQueryParameter("radius", "8406.4")
            .appendQueryParameter("type", "restaurant")
            .appendQueryParameter("key", API_KEY)
            .build();


    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpsURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() +
                        ": with " +
                        urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public List<SearchResults> search(){
        String url = ENDPOINT.toString();
        return downloadSearchResults(url);
    }

    public List<SearchResults> downloadSearchResults(String url) {
        List<SearchResults> items = new ArrayList<>();
        try {
            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(items, jsonBody);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return items;
    }

    private void parseItems(List<SearchResults> items, JSONObject jsonBody)
            throws IOException, JSONException {


        JSONArray photoJsonArray = jsonBody.getJSONArray("results");

        for(int i = 0; i < photoJsonArray.length(); i++){
            JSONObject photoJsonObject = photoJsonArray.getJSONObject(i);

            SearchResults item = new SearchResults();
            item.setId(photoJsonObject.getString("place_id"));
            item.setName(photoJsonObject.getString("name"));

            /*if(!photoJsonObject.has("url_s")) {
                continue;
            }

            item.setUrl(photoJsonObject.getString("url_s"));
            item.setOwner(photoJsonObject.getString("owner"));*/
            items.add(item);
        }
    }
}
