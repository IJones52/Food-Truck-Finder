package com.example.ismaelgwen.foodtruckfinder;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bal_imjonestaalib on 5/24/2016.
 */
public class Tweet {
    public String username;
    public String message;
    public String image_url;
    public Tweet(String username, String message, String url) {
        this.username = username;
        this.message = message;
        this.image_url = url;
    }
    public ArrayList<Tweet> getTweets(String searchTerm, int page) {
        String searchUrl =
                "http://search.twitter.com/search.json?q=@"
                        + searchTerm + "&rpp=100&page=" + page;
        ArrayList<Tweet> tweets =
                new ArrayList<Tweet>();
        HttpClient client = new  DefaultHttpClient();
        HttpGet get = new HttpGet(searchUrl);
        ResponseHandler<String> responseHandler =
                new BasicResponseHandler();
        String responseBody = null;
        try {
            responseBody = client.execute(get, responseHandler);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        JSONObject jsonObject = null;
        JSONParser parser=new JSONParser();
        try {
            Object obj = parser.parse(responseBody);
            jsonObject=(JSONObject)obj;
        }catch(Exception ex){
            Log.v("TEST","Exception: " + ex.getMessage());
        }
        JSONArray arr = null;
        try {
            Object j = jsonObject.get("results");
            arr = (JSONArray)j;
        } catch(Exception ex){
            Log.v("TEST", "Exception: " + ex.getMessage());
        }
        try{
        for(int i = 0; i < arr.length(); i++) {
            Tweet tweet = new Tweet(
                    ((JSONObject)arr.get(i)).get("from_user").toString(),
                    ((JSONObject)arr.get(i)).get("text").toString(),
                    ((JSONObject)arr.get(i)).get("profile_image_url").toString()
            );
            tweets.add(tweet);
        }}catch (JSONException e){}
        return tweets;
    }
}