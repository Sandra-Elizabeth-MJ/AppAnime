package com.proyectos.appanimes.entities;

public class Episode {
    private String title;
    private String aired;

    public Episode() {
    }

    public Episode(String title, String aired) {
        this.title = title;
        this.aired = aired;
    }

    public String getTitle() { return title; }
    public String getAired() { return aired; }
}
