package com.proyectos.appanimes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.proyectos.appanimes.R;
import com.proyectos.appanimes.entities.Anime;

import java.util.ArrayList;
import java.util.List;

public class AnimeAdapter  extends RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>{

    private List<Anime> animeList;
    private Context context;
    private OnAnimeClickListener listener;

    public interface OnAnimeClickListener {
        void onAnimeClick(Anime anime);
    }

    public AnimeAdapter(Context context, OnAnimeClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.animeList = new ArrayList<>();
    }

    public void setAnimeList(List<Anime> animeList) {
        this.animeList = animeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_anime, parent, false);
        return new AnimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimeViewHolder holder, int position) {
        Anime anime = animeList.get(position);
        holder.titleTextView.setText(anime.getTitle());
        holder.episodesTextView.setText("Episodios: " + anime.getEpisodes());

        Glide.with(context)
                .load(anime.getImages().getJpg().getImage_url())
                .into(holder.animeImageView);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAnimeClick(anime);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    static class AnimeViewHolder extends RecyclerView.ViewHolder {
        ImageView animeImageView;
        TextView titleTextView;
        TextView episodesTextView;

        public AnimeViewHolder(@NonNull View itemView) {
            super(itemView);
            animeImageView = itemView.findViewById(R.id.image_anime);
            titleTextView = itemView.findViewById(R.id.title_anime);
            episodesTextView = itemView.findViewById(R.id.episodes_count_anime);
        }
    }
}
