package com.proyectos.appanimes.services;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("anime")
    Call<ApiResponse> getAnimes();

    @GET("anime/{id}/episodes")
    Call<EpisodeResponse> getEpisodes(@Path("id") int animeId);
}
