package com.fmoreno.fabinmovies.db.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.fmoreno.fabinmovies.db.Entity.Movie;
import com.fmoreno.fabinmovies.db.Entity.MovieVideos;
import com.fmoreno.fabinmovies.db.Repository.MovieRepository;
import com.fmoreno.fabinmovies.db.Repository.MovieVideosRepository;

import java.util.List;

public class MovieVideosViewModel extends AndroidViewModel {
    private final MovieVideosRepository mRepository;

    private LiveData<List<MovieVideos>> mMovieVideosList;

    public MovieVideosViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MovieVideosRepository(application);
        //mMovieVideosList = mRepository.getMovieVideoList();
    }

    public LiveData<List<MovieVideos>> getMovieVideosList(int id_movie) {
        return mRepository.getMovieVideoList(id_movie);
    }

    public void insert(MovieVideos movieVideo) {
        mRepository.insert(movieVideo);
    }

}
