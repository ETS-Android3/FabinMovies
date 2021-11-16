package com.fmoreno.fabinmovies.interfaces;

import android.view.View;

import com.fmoreno.fabinmovies.db.Entity.Movie;
import com.fmoreno.fabinmovies.model.MovieList;

public interface RecyclerViewInterface {
    void onItemClick(Movie result, View view);
}
