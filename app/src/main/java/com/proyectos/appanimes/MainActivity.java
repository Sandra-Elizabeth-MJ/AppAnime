package com.proyectos.appanimes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.proyectos.appanimes.activities.AnimeDetalleActivity;
import com.proyectos.appanimes.adapters.AnimeAdapter;
import com.proyectos.appanimes.entities.Anime;
import com.proyectos.appanimes.services.ApiResponse;
import com.proyectos.appanimes.services.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnimeAdapter adapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvAnimes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AnimeAdapter(this, anime -> {
            Intent intent = new Intent(MainActivity.this, AnimeDetalleActivity.class);
            intent.putExtra("animeId", anime.getMal_id());
            intent.putExtra("animeTitle", anime.getTitle());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        // Configurar Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loadAnimes();
    }

    private void loadAnimes() {
        ApiService service = retrofit.create(ApiService.class);
        Call<ApiResponse> call = service.getAnimes();

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setAnimeList(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,
                        "Error al cargar los animes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}