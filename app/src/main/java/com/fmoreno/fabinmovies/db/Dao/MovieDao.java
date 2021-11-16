package com.fmoreno.fabinmovies.db.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.fmoreno.fabinmovies.db.Entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> getAll();

    @Query("SELECT * FROM movie WHERE category = 'popular'")
    LiveData<List<Movie>> getMoviesPopular();

    @Query("SELECT * FROM movie WHERE category = 'top_rated'")
    LiveData<List<Movie>> getMoviesTopRated();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Movie movieList);

    @Query("UPDATE movie SET tagline = :tagline WHERE id = :id")
    void updateMovie(int id, String tagline);
}
