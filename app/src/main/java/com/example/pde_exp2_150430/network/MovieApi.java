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
    // Base URL para las imágenes (asumiendo que están en la misma carpeta relativa)
    private static final String BASE_IMAGE_URL = "https://uax.tionazo.com/pelis/";

    public static void getMovies(Context context, MovieCallback callback) {
        List<Movie> list = new ArrayList<>();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, URL, null,
                response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("peliculas");
                        
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            Movie m = new Movie();
                            m.titulo = obj.optString("titulo");
                            m.descripcion = obj.optString("descripcion");
                            
                            String imgPath = obj.optString("imagen");
                            // CORRECCIÓN: Si la ruta no es absoluta (no empieza por http), añadimos la base
                            if (!imgPath.startsWith("http")) {
                                m.imagen = BASE_IMAGE_URL + imgPath;
                            } else {
                                m.imagen = imgPath;
                            }
                            
                            list.add(m);
                        }
                        callback.onResult(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Error procesando datos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
                    callback.onResult(new ArrayList<>());
                }
        );

        Volley.newRequestQueue(context).add(request);
    }

    public interface MovieCallback {
        void onResult(List<Movie> movies);
    }
}