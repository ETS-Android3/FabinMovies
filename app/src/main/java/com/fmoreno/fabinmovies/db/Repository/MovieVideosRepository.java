package com.fmoreno.fabinmovies.db.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.fmoreno.fabinmovies.db.Dao.MovieDao;
import com.fmoreno.fabinmovies.db.Dao.MovieVideosDao;
import com.fmoreno.fabinmovies.db.DataBase;
import com.fmoreno.fabinmovies.db.Entity.Movie;
import com.fmoreno.fabinmovies.db.Entity.MovieVideos;

import java.util.List;

public class MovieVideosRepository {
    private LiveData<List<MovieVideos>> mMovieVideoList;
    private final MovieVideosDao movieVideosDao;
    private DataBase db;
    private Context mContext;

    public MovieVideosRepository(Context context) {
        mContext = context;
        db = DataBase.getInstance(context);
        movieVideosDao = db.movieVideosDao();

    }

    public LiveData<List<MovieVideos>> getMovieVideoList(int id_movie) {
        return movieVideosDao.getMovieVideos(id_movie);
    }

    public void insert(MovieVideos movieVideo) {
        DataBase.dbExecutor.execute(
                () -> movieVideosDao.insert(movieVideo)
        );
    }

}
