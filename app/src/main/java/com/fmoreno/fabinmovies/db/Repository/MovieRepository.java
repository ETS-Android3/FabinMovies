package com.fmoreno.fabinmovies.db.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.fmoreno.fabinmovies.db.Dao.MovieDao;
import com.fmoreno.fabinmovies.db.DataBase;
import com.fmoreno.fabinmovies.db.Entity.Movie;

import java.util.List;

public class MovieRepository {
    private final LiveData<List<Movie>> mMovieList;
    private final MovieDao mMovieListDao;
    private DataBase db;
    private Context mContext;

    public MovieRepository(Context context) {
        mContext = context;
        db = DataBase.getInstance(context);
        mMovieListDao = db.movieDao();
        mMovieList = mMovieListDao.getAll();
    }

    public LiveData<List<Movie>> getAllMovieList() {
        return mMovieList;
    }

    public void insert(Movie personList) {
        DataBase.dbExecutor.execute(
                () -> mMovieListDao.insert(personList)
        );
    }
}
