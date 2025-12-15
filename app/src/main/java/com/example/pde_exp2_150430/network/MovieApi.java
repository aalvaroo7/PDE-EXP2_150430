package com.example.pde_exp2_150430.network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pde_exp2_150430.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieApi {

    private static final String URL = "https://uax.tionazo.com/pelis/peliculas";

    public static void getMovies(Context context, MovieCallback callback) {
        List<Movie> list = new ArrayList<>();

        // Cambiado a JsonObjectRequest porque la respuesta empieza con { "peliculas": ... }
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, URL, null,
                response -> {
                    try {
                        // Extraemos el array del campo "peliculas"
                        JSONArray jsonArray = response.getJSONArray("peliculas");
                        
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Movie m = new Movie();
                            m.titulo = obj.optString("titulo");
                            m.descripcion = obj.optString("descripcion");
                            m.imagen = obj.optString("imagen");
                            
                            // Si hay más campos como director o año, añadirlos aquí
                            // m.director = obj.optString("director");
                            
                            list.add(m);
                        }
                        callback.onResult(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error parseando JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(context, "Error de red: " + error.toString(), Toast.LENGTH_LONG).show();
                    callback.onResult(new ArrayList<>());
                }
        );

        Volley.newRequestQueue(context).add(request);
    }

    public interface MovieCallback {
        void onResult(List<Movie> movies);
    }
}