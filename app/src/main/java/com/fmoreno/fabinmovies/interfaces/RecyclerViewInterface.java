package com.fmoreno.fabinmovies.interfaces;

import android.view.View;

import com.fmoreno.fabinmovies.model.MovieList;

public interface RecyclerViewInterface {
    void onItemClick(MovieList.Result result, View view);
}
