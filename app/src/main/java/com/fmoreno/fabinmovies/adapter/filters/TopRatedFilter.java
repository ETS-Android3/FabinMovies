package com.fmoreno.fabinmovies.adapter.filters;

import android.widget.Filter;

import com.fmoreno.fabinmovies.adapter.RecyclerViewTopRatedAdapter;
import com.fmoreno.fabinmovies.db.Entity.Movie;
import com.fmoreno.fabinmovies.model.MovieList;

import java.util.ArrayList;
import java.util.List;

public class TopRatedFilter extends Filter {
    private final RecyclerViewTopRatedAdapter recyclerViewTopRatedAdapter;
    private final List<Movie> originalList;
    private final List<Movie> filteredList;

    public TopRatedFilter(RecyclerViewTopRatedAdapter myAdapter, List<Movie> originalList) {
        this.recyclerViewTopRatedAdapter = myAdapter;
        this.originalList = originalList;
        this.filteredList = new ArrayList<Movie>();
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        filteredList.clear();
        final FilterResults results = new FilterResults();
        if (charSequence.length() == 0){
            filteredList.addAll(originalList);
        }else {
            final String filterPattern = charSequence.toString().toLowerCase().trim();
            for (Movie movie : originalList){
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

        recyclerViewTopRatedAdapter.moviesList.clear();
        recyclerViewTopRatedAdapter.moviesList.addAll((ArrayList<Movie>)filterResults.values);
        recyclerViewTopRatedAdapter.notifyDataSetChanged();

    }
}
