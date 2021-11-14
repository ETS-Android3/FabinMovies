package com.fmoreno.fabinmovies.viewmodel;

import static com.fmoreno.fabinmovies.internet.WebServicesConstant.API_KEY;
import static com.fmoreno.fabinmovies.internet.WebServicesConstant.BASE_URL_APPLICATION;
import static com.fmoreno.fabinmovies.internet.WebServicesConstant.MOVIE;
import static com.fmoreno.fabinmovies.internet.WebServicesConstant.POPULAR;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fmoreno.fabinmovies.internet.WebApiRequest;
import com.fmoreno.fabinmovies.model.Movie;
import com.fmoreno.fabinmovies.model.MovieList;
import com.fmoreno.fabinmovies.ui.fragments.PopularFragment;
import com.fmoreno.fabinmovies.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PopularViewModel extends ViewModel {
    Context mContext;
    MutableLiveData<List<MovieList.Result>> moviesPopularLiveData;
    //ArrayList<Movie> moviesArrayList;

    MovieList movieListModel;
    public static List<MovieList.Result> moviesList = new ArrayList<>();

    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 2;
    private int firstVisibleItem = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;

    //current page number
    private int pageNumber = 1;

    public PopularViewModel() {

        moviesPopularLiveData = new MutableLiveData<>();

        // call your Rest API in init method
        init();
        callGetTopRatedMoviesApi();
    }

    public MutableLiveData<List<MovieList.Result>> getUserMutableLiveData() {
        return moviesPopularLiveData;
    }

    public void init(){
        //populateList();
        moviesPopularLiveData.setValue(moviesList);
    }

    public void populateList(){

        /*Movie user = new Movie();
        user.setTitle("Darknight");
        user.setDescription("Best rating movie");*/

        /*moviesArrayList = new ArrayList<>();
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);*/
    }

    private void callGetTopRatedMoviesApi() {

        /**
         * Checking internet connection before api call.
         * Very important always take care.
         */

        if (!Utils.isNetworkAvailable(mContext)) {
            Toast.makeText(mContext,
                    "No internet ..Please connect to internet and start app again",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        //showProgress();


        //constructing api url
        String ws_url = BASE_URL_APPLICATION + MOVIE + POPULAR +
                "?api_key=" + API_KEY + "&language=es-ES&page=" + pageNumber;

        //Using Volley to call api

        WebApiRequest webApiRequest = new WebApiRequest(Request.Method.GET,
                ws_url, ReqSuccessListener(), ReqErrorListener());
        Volley.newRequestQueue(mContext).add(webApiRequest);
    }

    private Response.Listener<String> ReqSuccessListener() {
        return new Response.Listener<String>() {
            public void onResponse(String response) {
                Log.e("movie list_response", response);
                try {
                    //hideProgress();
                    pageNumber++;

                    movieListModel = (MovieList) Utils.jsonToPojo(response, MovieList.class);

                    if (movieListModel.getResults() != null &&
                            movieListModel.getResults().size() > 0) {
                        moviesList.addAll(movieListModel.getResults());
                    } else {
                        Log.e("TAG", "list empty==");
                    }

                } catch (Exception e) {
                    Log.e("TAG","Exception=="+e.getLocalizedMessage());
                    //hideProgress();
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
                //hideProgress();
                Log.e("volley error", "volley error");
                Toast.makeText(mContext, "" +
                        "Server Error..Please try again after sometime", Toast.LENGTH_SHORT).show();
            }
        };
    }

    public void setContext(Context context) {
        mContext = context;
    }
}
