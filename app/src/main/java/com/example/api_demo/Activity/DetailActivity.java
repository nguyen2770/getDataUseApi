package com.example.api_demo.Activity;

import android.annotation.SuppressLint;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.api_demo.R;
import com.example.api_demo.modal.Song;
import com.example.api_demo.modal.SongManager;

import java.io.Console;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity {

    ImageView  imgv_play, imgv_pre, imgv_next;
    CircleImageView img_DetailImage;
    TextView txt_DetailNameSong, txt_DetailNameTG, txt_playerPosition, txt_playerDuration;
    SeekBar seekBarTime;
    private Song currentSong;
    private List<Song> songList;
    private int currentSongIndex;
    private SongManager songManager;
    private MediaPlayer mediaPlayer;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        img_DetailImage = findViewById(R.id.img_detaiImage);
        txt_DetailNameSong = findViewById(R.id.txt_detalNameSong);
        txt_DetailNameTG = findViewById(R.id.txt_detalNameTG);
        txt_playerDuration = findViewById(R.id.txt_playerDuration);
        txt_playerPosition = findViewById(R.id.txt_playerPoisition);
        imgv_pre = findViewById(R.id.imgv_pre);
        imgv_next = findViewById(R.id.imgv_next);
        imgv_play = findViewById(R.id.imgv_play);
        seekBarTime = findViewById(R.id.seekBarTime);

        // lấy dữ liệu đã truyền qua intent
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            currentSong = (Song) bundle.get("object_song");
            songList = (List<Song>) getIntent().getSerializableExtra("song_list");

            if(songList != null){
                for(Song song : songList){
                    System.out.println(song.getAlbum());
                    System.out.println(song.getDuration());
                    System.out.println(song.getTitle());
                }
                System.out.println("ok");
            }else{
                System.out.println("lisst song null roi");
            }
            songManager = new SongManager();
            songManager.buildSongMap(songList);


            // tìm chỉ số của bài hát hiện tại
            if (songList != null && currentSong != null) {
                currentSongIndex = songList.indexOf(currentSong);
                System.out.println("index" + currentSongIndex);
            }else{
                System.out.println("NULL");
            }

            // thiết lập dữ liệu bài hát
            setSongDetails(currentSong);

        }


        imgv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBarTime.setMax(mediaPlayer.getDuration());
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    imgv_play.setImageResource(R.drawable.baseline_play_arrow_24);
                    stopAnimationMusic();
                } else {
                    mediaPlayer.start();
                    imgv_play.setImageResource(R.drawable.baseline_pause_24);
                    startAnimationMusic();
                }
            }
        });

        //
        imgv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentSongIndex < songList.size() - 1) {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                    currentSongIndex++;
                    System.out.println(currentSongIndex);
//                    Song newSong = songList.;
                    setSongDetails(songList.get(currentSongIndex));

                }
            }
        });
        //
        imgv_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentSongIndex > 0) {
                    if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    }
                    currentSongIndex--;
//                    Song newSong = songManager.getSongById(String.valueOf(currentSongIndex));
//                    if(newSong == null){
//                        Toast.makeText(DetailActivity.this, "ko tt" + currentSongIndex, Toast.LENGTH_SHORT).show();
//                    }
                    setSongDetails(songList.get(currentSongIndex));
                }
            }
        });

        // seekbar duration
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBarTime.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
            }
        });

        seekBarTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b) {
                    mediaPlayer.seekTo(i);
                    seekBarTime.setProgress(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }


        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try {
                        if (mediaPlayer.isPlaying()) {
                            Message message = new Message();

                            message.what = mediaPlayer.getCurrentPosition();
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                        }


                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();


    }

    private void setSongDetails(Song song) {
        String imageUrl = song.getImage();
        Glide.with(img_DetailImage.getContext())
                .load(imageUrl)
                .into(img_DetailImage);
        txt_DetailNameSong.setText(song.getTitle());
        txt_DetailNameTG.setText(song.getArtist());
        String sduration = convertFormat(song.getDuration());
        txt_playerDuration.setText(sduration);
        System.out.println(currentSongIndex);
        playSong(song.getSource());
        img_DetailImage.setRotation(0);
        startAnimationMusic();
    }

//    private void playSong(String musicUrl) {
//
//        mediaPlayer = new MediaPlayer();
//        try {
//
//            mediaPlayer.setDataSource(musicUrl);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//            // Cập nhật SeekBar khi bài hát bắt đầu phát
//            seekBarTime.setMax(mediaPlayer.getDuration());
//            mediaPlayer.setOnPreparedListener(mp -> mediaPlayer.start());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // hàm chạy nhạc
    private void playSong(String musicUrl) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        } else {
            mediaPlayer.reset(); // Dùng lại mediaPlayer thay vì tạo mới
        }
        try {
            mediaPlayer.setDataSource(musicUrl);
            mediaPlayer.prepareAsync(); // Sử dụng prepareAsync để không block UI
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                    seekBarTime.setMax(mp.getDuration());
                    imgv_play.setImageResource(R.drawable.baseline_pause_24);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler  = new Handler() {
        @Override
        public void handleMessage(Message message) {
            seekBarTime.setProgress(message.what);
            String formattedCurrentPosition = convertFormat(message.what / 1000); // Chuyển từ mili giây sang giây
            txt_playerPosition.setText(formattedCurrentPosition);
        }
    };

    //
    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration) {
        return String.format("%02d:%02d",
                TimeUnit.SECONDS.toMinutes(duration),
                duration % 60);
    }

    private void startAnimationMusic(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                img_DetailImage.animate().rotationBy(360).withEndAction(this).setDuration(10000)
                        .setInterpolator(new LinearInterpolator()).start();
            }

        };
        img_DetailImage.animate().rotationBy(360).withEndAction(runnable).setDuration(10000)
                .setInterpolator(new LinearInterpolator()).start();
    }

    private void stopAnimationMusic(){
        img_DetailImage.animate().cancel();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}