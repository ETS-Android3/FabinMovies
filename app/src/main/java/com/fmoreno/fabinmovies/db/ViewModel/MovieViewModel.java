package com.fmoreno.fabinmovies.db.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fmoreno.fabinmovies.db.Entity.Movie;
import com.fmoreno.fabinmovies.db.Repository.MovieRepository;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private final MovieRepository mRepository;

    private final LiveData<List<Movie>> mMovieList;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mMovieList = mRepository.getAllMovieList();
    }

    public LiveData<List<Movie>> getMovieList() {
        return mMovieList;
    }

    public void insert(Movie movieList) {
        mRepository.insert(movieList);
    }
}
