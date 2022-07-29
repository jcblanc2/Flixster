package com.example.flixster;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityDetailBinding;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;
import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {
    private static final String YOUTUBE_API_KEY = "AIzaSyCBHDCSgXo62YkcSZVREoo7kR6tdD9ola4";
    private static final String TAG = "DetailActivity";
    public static final String VIDEO_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    public static YouTubePlayerView youTubePlayerView;
    private ActivityDetailBinding binding_detail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        binding_detail = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        youTubePlayerView = binding_detail.player;

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        binding_detail.setMovie(movie);
        binding_detail.executePendingBindings();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEO_URL, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray result = json.jsonObject.getJSONArray("results");

                    if(result.length() == 0){
                        return;
                    }
                    String youtubeKey = result.getJSONObject(0).getString("key");
                    Log.d(TAG, youtubeKey);
                    initializeYoutube(youtubeKey, movie.getVoteAverage());
                } catch (JSONException e) {
                    Log.e(TAG, "Failed to parse json ", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });
    }


    private static void initializeYoutube(String youtubeKey, double vote) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onInitializationSuccess");
                if (vote > 5){
                    youTubePlayer.loadVideo(youtubeKey);
                }else {
                    youTubePlayer.cueVideo(youtubeKey);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onInitializationFailure");

            }
        });
    }
}