package com.example.pde_exp2_150430.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pde_exp2_150430.R;
import com.example.pde_exp2_150430.adapter.MovieAdapter;
import com.example.pde_exp2_150430.model.Movie;
import com.example.pde_exp2_150430.network.MovieApi;
import com.google.firebase.auth.FirebaseAuth;
import com.example.pde_exp2_150430.auth.LoginActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        applyLayout();
        loadMovies();
    }

    private void applyLayout() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean grid = prefs.getBoolean("grid_mode", false);

        if (grid) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void loadMovies() {
        MovieApi.getMovies(this, movies -> {
            MovieAdapter adapter = new MovieAdapter(movies, this);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        if (item.getItemId() == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return true;
    }
}
