package com.example.pde_exp2_150430.network;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.pde_exp2_150430.model.Movie;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieApi {

    // URL original proporcionada en el enunciado
    private static final String URL = "https://uax.tionazo.com/pelis/peliculas";

    public static void getMovies(Context context, MovieCallback callback) {
        List<Movie> list = new ArrayList<>();

        // JsonArrayRequest asume que la respuesta raíz es un array [ {..}, {..} ]
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET, URL, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            Movie m = new Movie();
                            // Nos aseguramos de que los nombres de los campos coincidan con el JSON de la API
                            m.titulo = obj.optString("titulo");
                            m.descripcion = obj.optString("descripcion");
                            m.imagen = obj.optString("imagen");
                            
                            // Si el objeto JSON tiene más campos, mapearlos aquí
                            // m.director = obj.optString("director");
                            // m.year = obj.optString("year");
                            
                            list.add(m);
                        }
                        callback.onResult(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error parseando datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(context, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    // En caso de error, devolvemos una lista vacía o manejamos el error
                    callback.onResult(new ArrayList<>());
                }
        );

        Volley.newRequestQueue(context).add(request);
    }

    public interface MovieCallback {
        void onResult(List<Movie> movies);
    }
}