package com.example.api_demo.modal;

import java.util.List;

public class ListSongs {
    private List<Song> songs;

    public ListSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
