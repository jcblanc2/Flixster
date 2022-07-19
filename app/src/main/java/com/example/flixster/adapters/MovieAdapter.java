package com.example.flixster.adapters;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;
    List<Movie> movies;
    public static final String TAG = "MovieAdapter";

    // The constructor
    public MovieAdapter(Context Context, List<Movie> movies) {
        this.context = Context;
        this.movies = movies;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // Choice the layout to display for popular and less popular movie
        if (viewType == 0) {
            View v1 = inflater.inflate(R.layout.viewholder_lesspopular, parent, false);
            viewHolder = new ViewHolder1(v1);
        } else {
            View v2 = inflater.inflate(R.layout.viewholder_popular, parent, false);
            viewHolder = new ViewHolder2(v2);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Log.i(TAG, "onBindViewHolder");

        // Choice the right ViewHolder for the movieRecyclerView.
        if (viewHolder.getItemViewType() == 0) {
            ViewHolder1 vh1 = (ViewHolder1) viewHolder;
            configureViewHolder1(vh1, position);
        } else {
            ViewHolder2 vh2 = (ViewHolder2) viewHolder;
            configureViewHolder2(vh2, position);
        }

    }


    // Configure the first ViewHolder (Less popular movie)
    private void configureViewHolder1(ViewHolder1 vh1, int position) {
        Movie movie = (Movie) movies.get(position);
        String imgUrl;
        vh1.getTvTitle().setText(movie.getTitle());
        vh1.getTvOverView().setText(movie.getOverView());

        if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imgUrl = movie.getBackdropPath();
        }else {
            imgUrl = movie.getPosterPath();
        }
        Glide.with(context).load(imgUrl)
                .placeholder(R.drawable.loading2)
                .error(R.drawable.not_found)
                .into(vh1.getiPoster());

        // Register the click on the whole row
        vh1.container1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("title", movie.getTitle());
                i.putExtra("movie", Parcels.wrap(movie));
                context.startActivity(i);

            }
        });

    }

    // Configure the second ViewHolder (popular movie)
    private void configureViewHolder2(ViewHolder2 vh2, int position) {
        Movie movie = (Movie) movies.get(position);
        Glide.with(context).load(movie.getBackdropPath())
                .placeholder(R.drawable.loading2)
                .error(R.drawable.not_found)
                    .into(vh2.getBackdropPath());

        // Register the click on the whole row
        vh2.container2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("title", movie.getTitle());
                i.putExtra("movie", Parcels.wrap(movie));
                context.startActivity(i);

            }
        });

    }


    @Override
    public int getItemCount() {return movies.size();}


    @Override
    public int getItemViewType(int position) {
        // Check the vote_average
        int val;
        if (movies.get(position).getVoteAverage() <= 5) {
            val = 0;
        } else{
            val =  1;
        }
        return val;
    }


    // ViewHolder layout files for less popular movies
    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvOverView;
        ImageView iPoster;
        RelativeLayout container1;

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvOverView() {
            return tvOverView;
        }

        public ImageView getiPoster() {
            return iPoster;
        }

        public RelativeLayout getContainer1() {
            return container1;
        }


        public ViewHolder1(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.txtTitle);
            tvOverView = itemView.findViewById(R.id.txtOverView);
            iPoster = itemView.findViewById(R.id.imgPoster);
            container1 = itemView.findViewById(R.id.container1);
        }
    }


    // ViewHolder layout files for popular movies
    public static class ViewHolder2 extends RecyclerView.ViewHolder {
        ImageView backdropPath;
        RelativeLayout container2;

        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            backdropPath = itemView.findViewById(R.id.imgBackdropPath);
            container2 = itemView.findViewById(R.id.container2);
        }

        public ImageView getBackdropPath() {
            return backdropPath;
        }
        public RelativeLayout getContainer2() {
            return container2;
        }

    }

}
