package com.fmoreno.fabinmovies.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fmoreno.fabinmovies.R;
import com.fmoreno.fabinmovies.interfaces.RecyclerViewInterface;
import com.fmoreno.fabinmovies.model.MovieList;
import com.fmoreno.fabinmovies.ui.DetailMovieActivity;
import com.fmoreno.fabinmovies.utils.MyFilter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
implements Filterable {
    public static final String TAG = "RecyclerViewAdapter";
    public MyFilter mFilter;
    RecyclerViewInterface recyclerViewInterface;

    public static List<MovieList.Result> moviesList = new ArrayList<>();
    public static List<MovieList.Result> mFilteredMoviesList = new ArrayList<>();
    private Context context;


    public RecyclerViewAdapter(Context context, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @Override
    public Filter getFilter() {

        if (mFilter == null){
            mFilteredMoviesList.clear();
            mFilteredMoviesList.addAll(this.moviesList);
            mFilter = new MyFilter(RecyclerViewAdapter.this, this.mFilteredMoviesList);
        }
        return mFilter;

    }

    // method for filtering our recyclerview items.
    public void filterList(ArrayList<MovieList.Result> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        moviesList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
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
                recyclerViewInterface.onItemClick(moviesList.get(position));
            }
        });

       holder.itemView.startAnimation(startAnimation);
    }

    /**
     * Add movies list when calling apis
     * @param movies
     */

    public void addMovies(List<MovieList.Result> movies) {
        moviesList.addAll(movies);
        Log.e(TAG, "size of movie list==" + moviesList.size());
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
