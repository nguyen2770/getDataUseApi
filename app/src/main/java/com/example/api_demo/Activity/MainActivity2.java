package com.example.api_demo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api_demo.Interface.IClickISongListener;
import com.example.api_demo.R;
import com.example.api_demo.Adapter.SongsAdapter;
import com.example.api_demo.api.ApiService;
import com.example.api_demo.modal.ListSongs;
import com.example.api_demo.modal.Song;
import com.example.api_demo.modal.SongManager;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {

    RecyclerView rcv_songs;
    SongManager songManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rcv_songs = findViewById(R.id.rcv_songs);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_songs.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcv_songs.addItemDecoration(itemDecoration);


        callApi();



    }

    private void callApi(){
        ApiService.apiService.getSongs().enqueue(new Callback<ListSongs>() {
            @Override
            public void onResponse(Call<ListSongs> call, Response<ListSongs> response) {
                Toast.makeText(MainActivity2.this, "Call Api succes", Toast.LENGTH_SHORT).show();
                ListSongs listSongs = response.body();

                if(listSongs!=null) {
                    List<Song> songs = listSongs.getSongs();
                    songManager = new SongManager();
                    songManager.buildSongMap(songs);

                    SongsAdapter songsAdapter = new SongsAdapter(listSongs, new IClickISongListener() {
                        @Override
                        public void onClickSong(Song song) {

                            List<Song> suggestSong = songManager.getSongsByAlbumAndArtist(song.getAlbum(),song.getArtist());
//
//                            for(Song i : suggestSong){
//                                System.out.println(i.getTitle());
//
//                            }
                            onClickGoToDetail(song,suggestSong);
                        }
                    });
                    rcv_songs.setAdapter(songsAdapter);

                }
            }

            @Override
            public void onFailure(Call<ListSongs> call, Throwable t) {
                Toast.makeText(MainActivity2.this, "Call Api error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void onClickGoToDetail(Song song, List<Song> songList) {
        Intent intent = new Intent(this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_song", song);
        bundle.putSerializable("song_list",(Serializable) songList);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}