package com.fmoreno.fabinmovies.db.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "movie_videos")
public class MovieVideos implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id_video")
    @SerializedName("id")
    @NonNull
    public String idVideo = "";

    @ColumnInfo(name = "id_movie")
    @SerializedName("id_movie")
    public int idMovie = 0;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    public String name;

    @ColumnInfo(name = "key")
    @SerializedName("key")
    public String key;

    @ColumnInfo(name = "size")
    @SerializedName("size")
    public int size;

    public MovieVideos(String id, int idMovie, String name, String key, int size) {
        this.idVideo = id;
        this.idMovie = idMovie;
        this.name = name;
        this.key = key;
        this.size = size;
    }

    public MovieVideos(){

    }

    public String getId() {
        return idVideo;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public int getSize() {
        return size;
    }

}
