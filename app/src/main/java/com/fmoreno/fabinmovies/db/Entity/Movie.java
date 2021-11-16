package com.fmoreno.fabinmovies.db.Entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "movie")
public class Movie implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    public int id = 0;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    public String title = "";

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    public String posterPath = "";

    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    public String backdropPath = "";

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    public String overview = "";

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    public Float popularity = Float.valueOf(0);

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    public Float voteAverage = Float.valueOf(0);

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    public int voteCount = 0;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    public String releaseDate = "";

    public Movie(int id,
                 String title,
                 String posterPath,
                 String backdropPath,
                 String overview,
                 Float popularity,
                 Float voteAverage,
                 int voteCount,
                 String releaseDate) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public Float getPopularity() {
        return popularity;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getLikes(){
        if(voteCount == 1){
            return "1 Like";
        } else if(voteCount > 1){
            return voteCount + " Likes";
        } else {
            return "No votes";
        }
    }

    public String getStars(){
        if(voteAverage != null && voteAverage > 0){
            return voteAverage + " / 10";
        } else {
            return "No Reviews";
        }
    }
}
