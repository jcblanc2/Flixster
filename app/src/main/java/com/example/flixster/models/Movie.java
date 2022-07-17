package com.example.flixster.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 *  A class to menage the movies
 */
public class Movie {
    private String posterPath, title, overView, backdropPath;
    private double voteAverage;

    // The Constructor
    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.title = jsonObject.getString("title");
        this.overView = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.voteAverage = jsonObject.getDouble("vote_average");
    }

    // Get data from Json file and them to a list
    public static List<Movie> fromJsonArray(JSONArray movieArray) throws JSONException {
        List<Movie> listMovies = new ArrayList<Movie>();
        for (int i = 0; i < movieArray.length(); i++){
            listMovies.add(new Movie(movieArray.getJSONObject(i)));
        }

        return listMovies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", this.posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", this.backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverView() {
        return overView;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

}


