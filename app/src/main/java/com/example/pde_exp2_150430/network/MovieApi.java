package com.example.pde_exp2_150430.network;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pde_exp2_150430.model.Movie;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieApi {

    private static final String URL =
            "https://uax.tionazo.com/pelis/peliculas";

    public static void getMovies(Context context, MovieCallback callback) {
        List<Movie> list = new ArrayList<>();

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, URL, null,
                response -> {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Movie m = new Movie();
                            m.titulo = obj.getString("titulo");
                            m.descripcion = obj.getString("descripcion");
                            m.imagen = obj.getString("imagen");
                            list.add(m);
                        } catch (Exception ignored) {}
                    }
                    callback.onResult(list);
                },
                error -> {}
        );

        Volley.newRequestQueue(context).add(request);
    }

    public interface MovieCallback {
        void onResult(List<Movie> movies);
    }
}

