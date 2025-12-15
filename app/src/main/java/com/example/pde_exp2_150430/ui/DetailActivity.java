package com.example.pde_exp2_150430.ui;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pde_exp2_150430.R;
import com.example.pde_exp2_150430.model.Movie;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        ImageView ivImage = findViewById(R.id.ivImage);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvDesc = findViewById(R.id.tvDesc);

        tvTitle.setText(movie.titulo);
        tvDesc.setText(movie.descripcion);

        Glide.with(this)
                .load(movie.imagen)
                .into(ivImage);
    }
}
