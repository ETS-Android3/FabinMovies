package com.fmoreno.fabinmovies.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fmoreno.fabinmovies.model.Movie;

import java.util.ArrayList;

public class PopularViewModel extends ViewModel {
    MutableLiveData<ArrayList<Movie>> moviesPopularLiveData;
    ArrayList<Movie> moviesArrayList;
    public PopularViewModel() {

        moviesPopularLiveData = new MutableLiveData<>();

        // call your Rest API in init method
        init();
    }

    public MutableLiveData<ArrayList<Movie>> getUserMutableLiveData() {
        return moviesPopularLiveData;
    }

    public void init(){
        populateList();
        moviesPopularLiveData.setValue(moviesArrayList);
    }

    public void populateList(){

        Movie user = new Movie();
        user.setTitle("Darknight");
        user.setDescription("Best rating movie");

        moviesArrayList = new ArrayList<>();
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
        moviesArrayList.add(user);
    }
}
