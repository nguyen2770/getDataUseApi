package com.example.api_demo.modal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongManager {
    private Map<String, Song> songMap = new HashMap<>();

    public void buildSongMap(List<Song> songList) {
        for (Song song : songList) {
            songMap.put(song.getId(), song);
        }
    }

    public Song getSongById(String id) {
        return songMap.get(id); // Trả về null nếu không tìm thấy bài hát với id đó
    }

    // Lấy danh sách bài hát theo tác giả
    public List<Song> getSongsByArtist(String artist) {
        List<Song> result = new ArrayList<>();
        for (Song song : songMap.values()) {
            if (song.getArtist().equalsIgnoreCase(artist)) {
                result.add(song);
            }
        }
        return result;
    }

    // Lấy danh sách bài hát theo album
    public List<Song> getSongsByAlbum(String album) {
        List<Song> result = new ArrayList<>();
        for (Song song : songMap.values()) {
            if (song.getAlbum().equalsIgnoreCase(album)) {
                result.add(song);
            }
        }
        return result;
    }

    // lấy danh sách bài hát theo ablum và tác giả
    public List<Song> getSongsByAlbumAndArtist(String album, String artist) {
        List<Song> result = new ArrayList<>();
        for (Song song : songMap.values()) {
            if (song.getAlbum().equalsIgnoreCase(album) || song.getArtist().equalsIgnoreCase(artist)) {
                result.add(song);
            }
        }
        // lấy thêm danh sách tại ít bài hát quá
        while(result.size() < 10){
            // Chuyển đổi các giá trị của HashMap thành một danh sách
            List<Song> songList = new ArrayList<>(songMap.values());
            // Trộn danh sách
            Collections.shuffle(songList);
            // lấy ra thêm bài hát khác với bài hát đã chọn
            for (Song song : songList) {
                if (result.size() >= 10) {
                    break;
                }
                if (!result.contains(song)) {
                    result.add(song);
                }
            }
        }
        return result;
    }


}
