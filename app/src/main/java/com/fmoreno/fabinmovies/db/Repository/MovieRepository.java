package com.fmoreno.fabinmovies.db.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.fmoreno.fabinmovies.db.Dao.MovieDao;
import com.fmoreno.fabinmovies.db.DataBase;
import com.fmoreno.fabinmovies.db.Entity.Movie;

import java.util.List;

public class MovieRepository {
    private final LiveData<List<Movie>> mMovieListPopular;
    private final LiveData<List<Movie>> mMovieListTopRated;
    private final MovieDao mMovieListDao;
    private DataBase db;
    private Context mContext;

    public MovieRepository(Context context) {
        mContext = context;
        db = DataBase.getInstance(context);
        mMovieListDao = db.movieDao();
        mMovieListPopular = mMovieListDao.getMoviesPopular();
        mMovieListTopRated = mMovieListDao.getMoviesTopRated();
    }

    public LiveData<List<Movie>> getMovieListPopular() {
        return mMovieListPopular;
    }

    public LiveData<List<Movie>> getMovieListTopRated() {
        return mMovieListTopRated;
    }

    public void insert(Movie personList) {
        DataBase.dbExecutor.execute(
                () -> mMovieListDao.insert(personList)
        );
    }

    public void updateTagline(int id, String tagline) {
        DataBase.dbExecutor.execute(
                () -> mMovieListDao.updateMovie(id, tagline)
        );
    }
}
