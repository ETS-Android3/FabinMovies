package com.fmoreno.fabinmovies.utils;

import android.widget.Filter;

import com.fmoreno.fabinmovies.adapter.RecyclerViewAdapter;
import com.fmoreno.fabinmovies.model.MovieList;

import java.util.ArrayList;
import java.util.List;

public class MyFilter extends Filter {
    private final RecyclerViewAdapter recyclerViewAdapter;
    private final List<MovieList.Result> originalList;
    private final List<MovieList.Result> filteredList;

    public MyFilter(RecyclerViewAdapter myAdapter,  List<MovieList.Result> originalList) {
        this.recyclerViewAdapter = myAdapter;
        this.originalList = originalList;
        this.filteredList = new ArrayList<MovieList.Result>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        filteredList.clear();
        final FilterResults results = new FilterResults();
        if (charSequence.length() == 0){
            filteredList.addAll(originalList);
        }else {
            final String filterPattern = charSequence.toString().toLowerCase().trim();
            for (MovieList.Result movie : originalList){
                if (movie.getTitle().toLowerCase().contains(filterPattern)){
                    filteredList.add(movie);
                }
            }
        }

        results.values = filteredList;
        results.count = filteredList.size();
        return results;

    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

        recyclerViewAdapter.moviesList.clear();
        recyclerViewAdapter.moviesList.addAll((ArrayList<MovieList.Result>)filterResults.values);
        recyclerViewAdapter.notifyDataSetChanged();

    }
}
