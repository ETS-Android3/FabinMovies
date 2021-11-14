package com.fmoreno.fabinmovies.ui;

import static android.content.ContentValues.TAG;
import static com.fmoreno.fabinmovies.internet.WebServicesConstant.API_KEY;
import static com.fmoreno.fabinmovies.internet.WebServicesConstant.BASE_URL_APPLICATION;
import static com.fmoreno.fabinmovies.internet.WebServicesConstant.BASE_URL_DETAIL_MOVIE;
import static com.fmoreno.fabinmovies.internet.WebServicesConstant.MOVIE;
import static com.fmoreno.fabinmovies.internet.WebServicesConstant.POPULAR;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.fmoreno.fabinmovies.R;
import com.fmoreno.fabinmovies.internet.WebApiRequest;
import com.fmoreno.fabinmovies.model.DetatilMovie;
import com.fmoreno.fabinmovies.model.MovieList;
import com.fmoreno.fabinmovies.utils.Utils;

public class DetailMovieActivity extends AppCompatActivity {
    MovieList.Result movie;

    private ProgressBar progressBar;

    TextView textViewTitle,textViewVotes,textViewStars,textViewDate,textViewDescription,textViewTagline;

    ImageView imageViewPoster, imageViewBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        movie = (MovieList.Result) getIntent().getSerializableExtra("movie");
        initView();
        setImage();
        callGetTopRatedMoviesApi();
        setText();
        setAnimation();
    }

    private void initView(){
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewVotes = findViewById(R.id.textViewVotes);
        textViewStars = findViewById(R.id.textViewStars);
        textViewDate = findViewById(R.id.textViewDate);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewTagline = findViewById(R.id.textViewTagline);

        imageViewPoster = findViewById(R.id.imageViewPoster);
        imageViewBanner = findViewById(R.id.imageViewBanner);

    }

    private void setImage(){
        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                //.load(moviesList.get(position).getPosterPath())
                .into(imageViewPoster);

        Glide.with(this)
                .load("http://image.tmdb.org/t/p/w500" + movie.getBackdropPath())
                //.load(moviesList.get(position).getPosterPath())
                .into(imageViewBanner);
    }

    private void setText(){
        textViewTitle.setText(movie.getTitle());
        textViewVotes.setText(movie.getLikes());
        textViewStars.setText(movie.getStars());
        textViewDate.setText(Utils.getYear(movie.getReleaseDate()));
        textViewDescription.setText(movie.getOverview());
        //textViewTagline.setText(movie.getOverview());
    }

    private void setAnimation(){
        final Animation atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        final Animation packageimg = AnimationUtils.loadAnimation(this, R.anim.packageimg);
        final Animation right_in = AnimationUtils.loadAnimation(this, R.anim.right_in);
        final Animation right_out = AnimationUtils.loadAnimation(this, R.anim.right_out);
        final Animation slide_up = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        final Animation slide_bottom = AnimationUtils.loadAnimation(this, R.anim.slide_bottom);

        imageViewPoster.startAnimation(atg);
        imageViewBanner.startAnimation(packageimg);

        textViewTitle.startAnimation(right_in);
        textViewVotes.startAnimation(slide_bottom);
        textViewStars.startAnimation(slide_bottom);
        textViewDate.startAnimation(slide_bottom);

        textViewDescription.startAnimation(slide_up);
        //textViewTagline.startAnimation(right_out);
    }

    /**
     * Display Progress bar
     */

    private void showProgress() {
        try{
            //progressBar.setVisibility(View.VISIBLE);
        }catch (Exception ex){
            Log.d(TAG, "showProgress: ex:"+ ex.toString());
        }

    }

    /**
     * Hide Progress bar
     */

    private void hideProgress() {
        //progressBar.setVisibility(View.GONE);
    }

    /**
     * Call the api to fetch the TopRatedMovies list
     */

    private void callGetTopRatedMoviesApi() {

        /**
         * Checking internet connection before api call.
         * Very important always take care.
         */

        if (!Utils.isNetworkAvailable(DetailMovieActivity.this)) {
            Toast.makeText(DetailMovieActivity.this,
                    "No internet ..Please connect to internet and start app again",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        showProgress();


        //constructing api url
        String ws_url = BASE_URL_APPLICATION + MOVIE + movie.getId() +
                "?api_key=" + API_KEY +  BASE_URL_DETAIL_MOVIE;


        //Using Volley to call api

        WebApiRequest webApiRequest = new WebApiRequest(Request.Method.GET,
                ws_url, ReqSuccessListener(), ReqErrorListener());
        Volley.newRequestQueue(DetailMovieActivity.this).add(webApiRequest);
    }

    /**
     * Success listener to handle the movie listing
     * process after api returns the movie list
     *
     * @return
     */

    private Response.Listener<String> ReqSuccessListener() {
        return new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("movie list_response", response);
                try {
                    hideProgress();


                    DetatilMovie movieListModel = (DetatilMovie) Utils.jsonToPojo(response, DetatilMovie.class);

                    Log.d("Detail", movieListModel.title);
                   /* if (movieListModel.getResults() != null &&
                            movieListModel.getResults().size() > 0) {
                        recyclerViewAdapter.addMovies(movieListModel.getResults());
                    } else {
                        Log.e(TAG, "list empty==");
                    }*/

                } catch (Exception e) {
                    Log.e(TAG,"Exception=="+e.getLocalizedMessage());
                    hideProgress();
                }
            }
        };
    }

    /**
     * To Handle the error
     *
     * @return
     */

    private Response.ErrorListener ReqErrorListener() {
        return new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                hideProgress();
                Log.e("volley error", "volley error");
                Toast.makeText(DetailMovieActivity.this, "" +
                        "Server Error..Please try again after sometime", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
