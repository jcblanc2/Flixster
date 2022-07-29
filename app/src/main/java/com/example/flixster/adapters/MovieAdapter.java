package com.example.flixster.adapters;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ViewholderLesspopularBinding;
import com.example.flixster.databinding.ViewholderPopularBinding;
import com.example.flixster.models.Movie;
import org.parceler.Parcel;
import org.parceler.Parcels;
import java.util.List;

public  class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static Context context;
    List<Movie> movies;
    public static final String TAG = "MovieAdapter";
    public static final int popular = 1;
    public static final int lessPopular = 0;

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
        if (viewType == lessPopular) {
            ViewholderLesspopularBinding binding_less_popular = DataBindingUtil.inflate(inflater,
                    R.layout.viewholder_lesspopular, parent, false);
            viewHolder = new ViewHolder1(binding_less_popular);
        } else {
            ViewholderPopularBinding binding_popular = DataBindingUtil.inflate(inflater,
                    R.layout.viewholder_popular, parent, false);
            viewHolder = new Viewholder2(binding_popular);
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Log.i(TAG, "onBindViewHolder");

        // Choice the right ViewHolder for the movieRecyclerView.
        if (viewHolder.getItemViewType() == lessPopular) {
            ViewHolder1 vh1 = (ViewHolder1) viewHolder;
            configureViewHolder1(vh1, position);
        } else {
            Viewholder2 vh2 = (Viewholder2) viewHolder;
            configureViewHolder2(vh2, position);
        }

    }


    // Configure the first ViewHolder (Less popular movie)
    private void configureViewHolder1(ViewHolder1 vh1, int position) {
        Movie movie = (Movie) movies.get(position);

        vh1.binding_less_popular.setMovie(movie);
        vh1.binding_less_popular.executePendingBindings();

//      Register the click on the whole row
        vh1.binding_less_popular.container1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("movie", Parcels.wrap(movie));


                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context, vh1.binding_less_popular.imgPoster, "profile");
                // start the new activity
                context.startActivity(i, options.toBundle());
            }
        });

    }

    // Configure the second ViewHolder (popular movie)
    private void configureViewHolder2(Viewholder2 vh2, int position) {
        Movie movie = (Movie) movies.get(position);

        vh2.binding_popular.setMovie(movie);
        vh2.binding_popular.executePendingBindings();

        // Register the click on the whole row
        vh2.binding_popular.container2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("movie", Parcels.wrap(movie));


                ActivityOptions options = ActivityOptions
                        .makeSceneTransitionAnimation((Activity) context, vh2.binding_popular.imgBackdrop, "profile");
                // start the new activity
                context.startActivity(i, options.toBundle());

            }
        });

    }


    @Override
    public int getItemCount() {return movies.size();}


    @Override
    public int getItemViewType(int position) {
        // Check the vote_average
        int type;
        if (movies.get(position).getVoteAverage() <= 7) {
            type = lessPopular;
        } else{
            type = popular;
        }
        return type;
    }


    // ViewHolder layout files for less popular movies
    public static class ViewHolder1 extends RecyclerView.ViewHolder {
        ViewholderLesspopularBinding binding_less_popular;

        public ViewHolder1(@NonNull ViewholderLesspopularBinding binding) {
            super(binding.getRoot());
            binding_less_popular = binding;
        }
    }

    // ViewHolder layout files for popular movies
    public static class Viewholder2 extends RecyclerView.ViewHolder {
        ViewholderPopularBinding binding_popular;

        public Viewholder2(@NonNull ViewholderPopularBinding binding_popular) {
            super(binding_popular.getRoot());
            this.binding_popular = binding_popular;
        }
    }


    // Helper class to load image (DataBinding)
    public static class BindingAdapterUtils {
        @BindingAdapter({"imageUrlPopular"})
        public static void loadImage(ImageView view, String imgUrl) {
            Glide.with(context).load(imgUrl)
                    .centerCrop()
                    .transform(new RoundedCorners(80))
                    .placeholder(R.drawable.iconmonstr)
                    .error(R.drawable.ic_play23)
                    .into(view);
        }


        @BindingAdapter({"imageUrlLessPopular"})
        public static void loadImage1(ImageView view, String imgUrl) {
            Glide.with(context).load(imgUrl)
                    .centerCrop()
                    .transform(new RoundedCorners(80))
                    .placeholder(R.drawable.iconmonstr)
                    .error(R.drawable.ic_play23)
                    .into(view);
        }
    }

}
