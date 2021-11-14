package com.fmoreno.fabinmovies.internet;

import androidx.lifecycle.LiveData;

/**
 * Handle all web service or api related constants from here.
 *
 */
public class WebServicesConstant {
    //Main Host Url to access apis
    public static final String BASE_URL_APPLICATION="https://api.themoviedb.org/3/";

    //Image urls
    public static final String BASE_URL_IMAGE_ORIGINAL="https://image.tmdb.org/t/p/original";
    public static final String BASE_URL_IMAGE_W_500="https://image.tmdb.org/t/p/w500";

    //Details Movie
    public static final String BASE_URL_DETAIL_MOVIE = "&append_to_response=videos,credits,reviews";

    /*@GET("movie/{id}?append_to_response=videos,credits,reviews")
    LiveData<ApiResponse<Movie>> getMovieDetails(@Path("id") long id);*/

    //api key (Its my personal) you have to crate your own to avoid suspension of your account

    //23fc7389372548c592b1f08cb8a0dffe
    //3f4ccf9c8108bb8d03e86f9123add311
    public static final String API_KEY="3f4ccf9c8108bb8d03e86f9123add311";



    //api connections
    public static final String MOVIE="movie/";
    public static final String TOP_RATED="top_rated";
    public static final String POPULAR="popular";
}
