package com.example.api_demo.Activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.api_demo.R;
import com.example.api_demo.api.ApiService;
import com.example.api_demo.modal.ListSongs;
import com.example.api_demo.modal.Song;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText edt1,edt2,edt3;
    Button btn,btn2;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edt1 = findViewById(R.id.editTextText);
        edt2 = findViewById(R.id.editTextText2);
        edt3 = findViewById(R.id.editTextText3);
        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallApi();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });
    }

    private void clickCallApi() {
        ApiService.apiService.getSongs().enqueue(new Callback<ListSongs>() {
            @Override
            public void onResponse(Call<ListSongs> call, Response<ListSongs> response) {
                Toast.makeText(MainActivity.this, "Call Api suss", Toast.LENGTH_SHORT).show();
                    ListSongs listSongs = response.body();
                    if(listSongs!=null){
                       List<Song> songs = listSongs.getSongs();
                       Song firstSong = songs.get(1);
                       edt1.setText(firstSong.getArtist());
                       edt2.setText(firstSong.getTitle());
                       edt3.setText(firstSong.getSource());
                        mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(firstSong.getSource()); // Đặt URL của file MP3
                            mediaPlayer.prepareAsync(); // Chuẩn bị phát nhạc
                            mediaPlayer.setOnPreparedListener(mp -> {
                                // Khi MediaPlayer đã sẵn sàng, bắt đầu phát
                                mp.start();
                                Log.d("MediaPlayer", "Playback started");
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("MediaPlayer", "Error setting data source", e);
                        }
                    }
            }

            @Override
            public void onFailure(Call<ListSongs> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call api err", Toast.LENGTH_SHORT).show();
            }
        });
        
    }
}