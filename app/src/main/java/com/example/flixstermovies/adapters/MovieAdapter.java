package com.example.flixstermovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixstermovies.DetailActivity;
import com.example.flixstermovies.R;
import com.example.flixstermovies.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context c, List<Movie> m){
        context = c;
        movies = m;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //movie at the position and bind the movie data infor VIEW HOLDER
        Movie mov = movies.get(position);
        holder.bind(mov);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTitle, tvOverview;
        private ImageView ivPoster;
        private RelativeLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie m){
            tvTitle.setText(m.getTitle());
            tvOverview.setText(m.getOverview());
            //to set the image with use GLIDE
            Glide.with(context)
                    .load(m.getPosterPath())
                    .into(ivPoster);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // make new activity
                    Intent i = new Intent(context, DetailActivity.class);
                    // put "extras" into the bundle for access in the second activity
                    i.putExtra("movieObject", Parcels.wrap(m));
                    i.putExtra("title", m.getTitle());
                    // brings up the second activity
                    context.startActivity(i);
                }
            });
        }
    }
}
