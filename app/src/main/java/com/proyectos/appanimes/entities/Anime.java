package com.proyectos.appanimes.entities;

import android.provider.MediaStore;

public class Anime {
    private int mal_id;
    private String title;
    private int episodes;
    private Images images;

    public int getMal_id() { return mal_id; }
    public String getTitle() { return title; }
    public int getEpisodes() { return episodes; }
    public Images getImages() { return images; }

    public static class Images {
        public static class Jpg {
            private String image_url;
            public String getImage_url() { return image_url; }
        }
        private Jpg jpg;
        public Jpg getJpg() { return jpg; }
    }
}
