package com.example.flixster;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.models.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public static final String nowPlayingUrl = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    public static final String TAG = "MainActivity";
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<Movie>();
        AsyncHttpClient client = new AsyncHttpClient();
        RecyclerView rcView = findViewById(R.id.rvMovies);

        //Create the adapter
        MovieAdapter adapter = new MovieAdapter(this, movies);

        //Set the adapter on the recyclerView
        rcView.setAdapter(adapter);

        // Set The layout manager on the recyclerView
        rcView.setLayoutManager(new LinearLayoutManager(this));

        // Query to get list movie
        client.get(nowPlayingUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.e(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray result = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Result: " + result.toString());
                    movies.addAll(Movie.fromJsonArray(result));
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.i(TAG, "Hit JSONException: " + e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG, "onFailure");
            }
        });
    }
}