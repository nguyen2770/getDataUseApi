package com.example.api_demo.modal;

public class Song {
    private String id;
    private String title;
    private String album;
    private String artist;
    private String source;
    private String image;
    private int duration;
    private boolean favorite;
    private int counter;
    private int replay;

    public Song(String id, String title, String album, String artist, String source, String image, int duration, boolean favorite, int counter, int replay) {
        this.id = id;
        this.title = title;
        this.album = album;
        this.artist = artist;
        this.source = source;
        this.image = image;
        this.duration = duration;
        this.favorite = favorite;
        this.counter = counter;
        this.replay = replay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getReplay() {
        return replay;
    }

    public void setReplay(int replay) {
        this.replay = replay;
    }
}
