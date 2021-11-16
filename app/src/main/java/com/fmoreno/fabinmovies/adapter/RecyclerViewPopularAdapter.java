package com.fmoreno.fabinmovies.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fmoreno.fabinmovies.R;
import com.fmoreno.fabinmovies.adapter.filters.PopularFilter;
import com.fmoreno.fabinmovies.db.Entity.Movie;
import com.fmoreno.fabinmovies.interfaces.RecyclerViewInterface;
import com.fmoreno.fabinmovies.model.MovieList;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewPopularAdapter extends RecyclerView.Adapter<RecyclerViewPopularAdapter.ViewHolder>
implements Filterable {
    public static final String TAG = "RecyclerViewAdapter";
    public PopularFilter mFilter;
    RecyclerViewInterface recyclerViewInterface;

    public static List<Movie> moviesList = new ArrayList<>();
    public static List<Movie> mFilteredMoviesList = new ArrayList<>();
    private Context context;


    public RecyclerViewPopularAdapter(Context context, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @Override
    public Filter getFilter() {

        if (mFilter == null){
            mFilteredMoviesList.clear();
            mFilteredMoviesList.addAll(this.moviesList);
            mFilter = new PopularFilter(RecyclerViewPopularAdapter.this, this.mFilteredMoviesList);
        }
        return mFilter;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Animation startAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide_up);
        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w500" + moviesList.get(position).getPosterPath())
                .into(holder.post);

        holder.post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewInterface.onItemClick(moviesList.get(position), holder.post);
            }
        });

       holder.itemView.startAnimation(startAnimation);
    }

    /**
     * Add movies list when calling apis
     * @param movies
     */

    public void addMovies(List<Movie> movies) {
        moviesList.addAll(movies);
        Log.e(TAG, "size of movie list==" + moviesList.size());
        notifyDataSetChanged();
    }

    public void submitList(List<Movie> movies) {
        moviesList = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }


    /**
     * View Holder for common row of movies
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView post;
        public ViewHolder(View itemView) {
            super(itemView);
            post = itemView.findViewById(R.id.imgView_post);
        }
    }
}
