package com.fmoreno.fabinmovies.ui.fragments;

import static com.fmoreno.fabinmovies.internet.WebServicesConstant.API_KEY;
import static com.fmoreno.fabinmovies.internet.WebServicesConstant.BASE_URL_APPLICATION;
import static com.fmoreno.fabinmovies.internet.WebServicesConstant.MOVIE;
import static com.fmoreno.fabinmovies.internet.WebServicesConstant.POPULAR;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.fmoreno.fabinmovies.R;
import com.fmoreno.fabinmovies.adapter.RecyclerViewPopularAdapter;
import com.fmoreno.fabinmovies.db.Entity.Movie;
import com.fmoreno.fabinmovies.db.ViewModel.MovieViewModel;
import com.fmoreno.fabinmovies.interfaces.RecyclerViewInterface;
import com.fmoreno.fabinmovies.internet.WebApiRequest;
import com.fmoreno.fabinmovies.model.MovieList;
import com.fmoreno.fabinmovies.ui.DetailMovieActivity;
import com.fmoreno.fabinmovies.utils.Utils;
import com.fmoreno.fabinmovies.viewmodel.PopularViewModel;

import java.util.ArrayList;
import java.util.List;


public class PopularFragment extends Fragment implements RecyclerViewInterface {
    public static final String TAG = "PopularFragment";

    private ProgressBar progressBar;

    PopularFragment context;
    View mRootView;
    RelativeLayout rlMoviesList;
    SearchView search_bar;
    RecyclerView rv_popular;

    PopularViewModel viewModel;
    RecyclerViewPopularAdapter recyclerViewPopularAdapter;
    GridLayoutManager layoutManager;

    //For Load more functionality
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 2;
    private int firstVisibleItem = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;

    //current page number
    private int pageNumber = 1;

    MovieList movieListModel;
    List<Movie> mMovieList;

    ViewModelProvider.AndroidViewModelFactory factory;
    public MovieViewModel movieViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_popular, container, false);
        context = this;
        rlMoviesList = mRootView.findViewById(R.id.rlMoviesList);
        rv_popular = mRootView.findViewById(R.id.rv_popular);
        search_bar = mRootView.findViewById(R.id.search_bar);
        progressBar = new ProgressBar(context.getActivity(), null, android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        progressBar.setVisibility(View.GONE);
        rlMoviesList.addView(progressBar, params);

        layoutManager =
                new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.span_count));
        rv_popular.setLayoutManager(layoutManager);
        rv_popular.setHasFixedSize(true);
        rv_popular.clearOnScrollListeners(); //clear scrolllisteners
        recyclerViewPopularAdapter = new RecyclerViewPopularAdapter(context.getActivity(), this);

        init();

        initViewModelRoom();

        callGetTopRatedMoviesApi();
        /*viewModel = ViewModelProviders.of(this).get(PopularViewModel.class);
        viewModel.getUserMutableLiveData().observe(context.getActivity(), userListUpdateObserver);
        //Inflate the layout for this fragment
        viewModel.setContext(context.getActivity());*/
        return mRootView;
    }

    private void initViewModelRoom() {
        try{
            factory = ViewModelProvider.AndroidViewModelFactory.getInstance(context.getActivity().getApplication());

            movieViewModel = new ViewModelProvider(this, factory).get(MovieViewModel.class);
        }catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
    }

    /*Observer<ArrayList<Movie>> userListUpdateObserver = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> userArrayList) {
            //recyclerViewAdapter = new RecyclerViewAdapter(context.getActivity());
            layoutManager =
                    new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.span_count));
            rv_popular.setLayoutManager(layoutManager);
            rv_popular.setHasFixedSize(true);
            rv_popular.clearOnScrollListeners(); //clear scrolllisteners
            rv_popular.setAdapter(recyclerViewPopularAdapter);
        }
    };*/

    private void init(){
        /**
         * For Adding Load more functionality
         *
         */

        rv_popular.setAdapter(recyclerViewPopularAdapter);

        rv_popular.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount = recyclerView.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount)
                            <= (firstVisibleItem + visibleThreshold)) {
                        // End has been reached
                        Log.i("InfiniteScrollListener", "End reached");

                        callGetTopRatedMoviesApi();
                        loading = true;
                    }
                }
            }
        });


        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerViewPopularAdapter.getFilter().filter(query);
                if(query.isEmpty()){
                    previousTotal = 0;
                    firstVisibleItem = 0;
                    visibleItemCount = 0;
                    totalItemCount = 0;
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //filter(newText);
                recyclerViewPopularAdapter.getFilter().filter(newText);
                if(newText.isEmpty()){
                    previousTotal = 0;
                    firstVisibleItem = 0;
                    visibleItemCount = 0;
                    totalItemCount = 0;
                }
                return true;
            }
        });
    }

    /**
     * Display Progress bar
     */

    private void showProgress() {
        try{
            progressBar.setVisibility(View.VISIBLE);
        }catch (Exception ex){
            Log.d(TAG, "showProgress: ex:"+ ex.toString());
        }

    }

    /**
     * Hide Progress bar
     */

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Call the api to fetch the TopRatedMovies list
     */

    private void callGetTopRatedMoviesApi() {

        /**
         * Checking internet connection before api call.
         * Very important always take care.
         */

        if (!Utils.isNetworkAvailable(context.getActivity())) {
            Toast.makeText(context.getActivity(),
                    "No internet ..Please connect to internet and start app again",
                    Toast.LENGTH_SHORT).show();
            //LiveData<List<Movie>> data =  movieViewModel.getMovieList();
            movieViewModel.getMovieList().observe(getViewLifecycleOwner(), new Observer<List<Movie>>() {
                @Override
                public void onChanged(List<Movie> movieList) {
                    if (movieList.isEmpty()) {
                        // TODO: 11/7/2018 optimize this
                        // display empty state since there is no favorites in database
                        Log.d("dataMovie1", movieList.toString());
                    } else {
                        Log.d("dataMovie2", movieList.toString());
                        recyclerViewPopularAdapter.submitList(movieList);
                    }
                }
            });
            //Log.d("dataMovie", data.toString());
            return;
        }

        showProgress();


        //constructing api url
        String ws_url = BASE_URL_APPLICATION + MOVIE + POPULAR +
                "?api_key=" + API_KEY + "&language=es-ES&page=" + pageNumber;

        //Using Volley to call api

        WebApiRequest webApiRequest = new WebApiRequest(Request.Method.GET,
                ws_url, ReqSuccessListener(), ReqErrorListener());
        Volley.newRequestQueue(context.getActivity()).add(webApiRequest);
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
                    pageNumber++;

                    movieListModel = (MovieList) Utils.jsonToPojo(response, MovieList.class);
                    mMovieList = new ArrayList<>();
                    if (movieListModel.getResults() != null &&
                            movieListModel.getResults().size() > 0) {

                        try{

                            for(MovieList.Result tmpMovie: movieListModel.getResults()){
                                Movie movie = new Movie(tmpMovie.getId(),
                                        tmpMovie.getTitle(),
                                        tmpMovie.getPosterPath(),
                                        tmpMovie.getBackdropPath(),
                                        tmpMovie.getOverview(),
                                        tmpMovie.getPopularity(),
                                        tmpMovie.getVoteAverage(),
                                        tmpMovie.getVoteCount(),
                                        tmpMovie.getReleaseDate());
                                if(!mMovieList.contains(movie)){
                                    mMovieList.add(movie);
                                    movieViewModel.insert(movie);
                                }
                            }
                            recyclerViewPopularAdapter.addMovies(mMovieList);
                        }catch (Exception ex){
                            Log.e("Error DATABASE", ex.toString());
                        }
                    } else {
                        Log.e(TAG, "list empty==");
                    }

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
                Toast.makeText(context.getActivity(), "" +
                        "Server Error..Please try again after sometime", Toast.LENGTH_SHORT).show();
            }
        };
    }
    Movie movie;

    @Override
    public void onItemClick(Movie result, View view) {
        movie = result;
        Intent datailActivity = new Intent(context.getActivity(), DetailMovieActivity.class);
        datailActivity.putExtra("movie", movie);
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(context.getActivity(), view, "poster");
        startActivity(datailActivity, options.toBundle());
        context.getActivity().overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out);
        //context.getActivity().finish();
    }
}
