package com.fmoreno.fabinmovies.db.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.fmoreno.fabinmovies.db.Entity.Movie;
import com.fmoreno.fabinmovies.db.Entity.MovieVideos;

import java.util.List;

@Dao
public interface MovieVideosDao {

    @Query("SELECT * FROM movie_videos WHERE id_movie = :id_movie")
    LiveData<List<MovieVideos>> getMovieVideos(int id_movie);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MovieVideos movieVideo);
}
