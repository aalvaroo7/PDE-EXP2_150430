package com.example.pde_exp2_150430.ui;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pde_exp2_150430.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch switchLayout = findViewById(R.id.switchLayout);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        switchLayout.setChecked(prefs.getBoolean("grid_mode", false));

        switchLayout.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("grid_mode", isChecked).apply();
        });
    }
}
