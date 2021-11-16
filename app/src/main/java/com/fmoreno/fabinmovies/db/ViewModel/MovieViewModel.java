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

    private final LiveData<List<Movie>> mMovieListPopular;
    private final LiveData<List<Movie>> mMovieListTopRated;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mMovieListPopular = mRepository.getMovieListPopular();
        mMovieListTopRated = mRepository.getMovieListTopRated();
    }

    public LiveData<List<Movie>> getMovieListPopular() {
        return mMovieListPopular;
    }
    public LiveData<List<Movie>> getMovieListTopRated() {
        return mMovieListTopRated;
    }

    public void insert(Movie movieList) {
        mRepository.insert(movieList);
    }

    public void updateTagline(int id, String tagline) {
        mRepository.updateTagline(id, tagline);
    }
}
