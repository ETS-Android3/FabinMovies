package com.fmoreno.fabinmovies.adapter;

import static com.fmoreno.fabinmovies.internet.WebServicesConstant.YOUTUBE_WEB_URL;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fmoreno.fabinmovies.R;
import com.fmoreno.fabinmovies.db.Entity.Movie;
import com.fmoreno.fabinmovies.db.Entity.MovieVideos;
import com.fmoreno.fabinmovies.model.DetailMovie;
import com.fmoreno.fabinmovies.model.MovieList;

import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends
        RecyclerView.Adapter<TrailersAdapter.ViewHolder>{
    Context mContext;

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public CardView card_trailer;
        public ImageView image_trailer;
        public TextView trailer_name;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            card_trailer = itemView.findViewById(R.id.card_trailer);
            image_trailer = itemView.findViewById(R.id.image_trailer);
            trailer_name = itemView.findViewById(R.id.trailer_name);

        }
    }

    // Store a member variable for the contacts
    private List<MovieVideos> mVideos;

    // Pass in the contact array into the constructor
    public TrailersAdapter(List<MovieVideos> videos) {
        mVideos = videos;
    }

    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_trailer, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(TrailersAdapter.ViewHolder holder, int position) {
        Animation startAnimation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.slide_up);
        // Get the data model based on position
        MovieVideos video = mVideos.get(position);

        CardView card_trailer = holder.card_trailer;
        card_trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickYoutube(video, holder.itemView.getContext());
            }
        });

        // Set item views based on your views and data model
        ImageView image_trailer = holder.image_trailer;
        TextView trailer_name = holder.trailer_name;

        String thumbnail =
                "https://img.youtube.com/vi/" + video.key + "/hqdefault.jpg";
        Glide.with(holder.itemView.getContext())
                .load(thumbnail)
                .into(holder.image_trailer);

        trailer_name.setText(video.name);
        holder.itemView.startAnimation(startAnimation);
    }

    public void addMovies(List<MovieVideos> videos) {
        mVideos.addAll(videos);
        //Log.e(TAG, "size of movie list==" + moviesList.size());
        notifyDataSetChanged();
    }

    public void submitList(List<MovieVideos> videos) {
        mVideos = videos;
        notifyDataSetChanged();
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mVideos.size();
    }

    private void onClickYoutube(MovieVideos video, Context context){
        Intent appIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("vnd.youtube:" + video.key));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(YOUTUBE_WEB_URL + video.key));
        if (appIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(appIntent);
        } else {
            context.startActivity(webIntent);
        }
    }

}
