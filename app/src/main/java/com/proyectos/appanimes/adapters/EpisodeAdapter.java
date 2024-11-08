package com.proyectos.appanimes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.proyectos.appanimes.R;
import com.proyectos.appanimes.entities.Episode;

import java.util.ArrayList;
import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>{

    private List<Episode> episodeList;
    private Context context;

    public EpisodeAdapter(Context context) {
        this.context = context;
        this.episodeList = new ArrayList<>();
    }

    public void setEpisodeList(List<Episode> episodeList) {
        this.episodeList = episodeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_episodios, parent, false);
        return new EpisodeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode episode = episodeList.get(position);
        holder.titleTextView.setText(episode.getTitle());

        // Formatear la fecha de publicación
        String airedDate = episode.getAired() != null ? episode.getAired() : "Fecha no disponible";
        holder.dateTextView.setText("Fecha de publicación: " + airedDate);
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    static class EpisodeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView dateTextView;

        public EpisodeViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_episodio);
            dateTextView = itemView.findViewById(R.id.episodes_fecha);
        }
    }
}
