package com.example.pde_exp2_150430.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pde_exp2_150430.R;
import com.example.pde_exp2_150430.model.Movie;
import com.example.pde_exp2_150430.ui.DetailActivity;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Movie> movies;
    private final Context context;
    private final boolean isGrid;

    public MovieAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        this.isGrid = prefs.getBoolean("grid_mode", false);
    }

    @Override
    public int getItemViewType(int position) {
        return isGrid ? 1 : 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) { // Grid
            View view = LayoutInflater.from(context).inflate(R.layout.item_movie_grid, parent, false);
            return new GridViewHolder(view);
        } else { // List
            View view = LayoutInflater.from(context).inflate(R.layout.item_movie_list, parent, false);
            return new ListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Movie movie = movies.get(position);

        if (holder instanceof ListViewHolder) {
            ListViewHolder listHolder = (ListViewHolder) holder;
            listHolder.tvTitle.setText(movie.titulo);
            Glide.with(context).load(movie.imagen).into(listHolder.ivImage);
            
            listHolder.itemView.setOnClickListener(v -> goToDetail(movie));

        } else if (holder instanceof GridViewHolder) {
            GridViewHolder gridHolder = (GridViewHolder) holder;
            Glide.with(context).load(movie.imagen).into(gridHolder.ivImage);

            gridHolder.itemView.setOnClickListener(v -> goToDetail(movie));
        }
    }

    private void goToDetail(Movie movie) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("movie", movie);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ListViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle;

        ListViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }

    static class GridViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;

        GridViewHolder(View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }
}