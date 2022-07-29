package com.example.flixster.models;


import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;
import java.util.ArrayList;
import java.util.List;

/**
 *  A class to menage the movies
 */

@Parcel
public class Movie {
    private String posterPath, title, overView, backdropPath;
    private int movieId;
    private double voteAverage;
    public static Context context;

    // empty constructor needed by the Parceler library
    public Movie(){

    }

    // The Constructor
    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.title = jsonObject.getString("title");
        this.overView = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.voteAverage = jsonObject.getDouble("vote_average");
        this.movieId = jsonObject.getInt("id");
    }

    // Get data from Json file and them to a list
    public static List<Movie> fromJsonArray(JSONArray movieArray) throws JSONException {
        List<Movie> listMovies = new ArrayList<Movie>();
        for (int i = 0; i < movieArray.length(); i++){
            listMovies.add(new Movie(movieArray.getJSONObject(i)));
        }

        return listMovies;
    }

    public String getPosterPath() {return String.format("https://image.tmdb.org/t/p/w342/%s", this.posterPath);}

    public String getBackdropPath() {return String.format("https://image.tmdb.org/t/p/w342/%s", this.backdropPath);}

    public String getTitle() {
        return title;
    }

    public String getOverView() {
        return overView;
    }

    public int getMovieId() {
        return movieId;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}


