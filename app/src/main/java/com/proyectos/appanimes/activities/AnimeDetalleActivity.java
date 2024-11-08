package com.proyectos.appanimes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectos.appanimes.R;
import com.proyectos.appanimes.adapters.EpisodeAdapter;
import com.proyectos.appanimes.entities.Episode;
import com.proyectos.appanimes.services.ApiService;
import com.proyectos.appanimes.services.EpisodeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimeDetalleActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EpisodeAdapter adapter;
    private Retrofit retrofit;
    private TextView titleTextView;
    private int animeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_detalle);

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.rvEpisodios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EpisodeAdapter(this);
        recyclerView.setAdapter(adapter);

        // Obtener datos del intent
        Intent intent = getIntent();
        animeId = intent.getIntExtra("animeId", 0);
        String title = intent.getStringExtra("animeTitle");

        // Configurar título en el ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Configurar Retrofit
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/v4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loadEpisodes();
    }

    private void loadEpisodes() {
        ApiService service = retrofit.create(ApiService.class);
        Call<EpisodeResponse> call = service.getEpisodes(animeId);

        call.enqueue(new Callback<EpisodeResponse>() {
            @Override
            public void onResponse(Call<EpisodeResponse> call, Response<EpisodeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Episode> episodes = response.body().getData();
                    if (episodes != null && !episodes.isEmpty()) {
                        adapter.setEpisodeList(episodes);
                    } else {
                        showError("No hay episodios disponibles");
                    }
                } else {
                    showError("Error al cargar los episodios");
                }
            }

            @Override
            public void onFailure(Call<EpisodeResponse> call, Throwable t) {
                showError("Error de conexión: " + t.getMessage());
            }
        });
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}