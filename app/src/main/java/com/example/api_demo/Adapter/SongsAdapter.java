package com.example.api_demo.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.api_demo.Interface.IClickISongListener;
import com.example.api_demo.R;
import com.example.api_demo.modal.ListSongs;
import com.example.api_demo.modal.Song;

import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SongViewHolder>{

    ListSongs listSongs;
    IClickISongListener songListener;
    public SongsAdapter(ListSongs listSongs, IClickISongListener songListener) {
        this.listSongs = listSongs;
        this.songListener = songListener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song,parent,false);

        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        List<Song> songs = listSongs.getSongs();
        Song song = songs.get(position);
        if(song == null){
            return;
        }
        String imageUrl = song.getImage();
        Glide.with(holder.imgv.getContext())
                .load(imageUrl)
                .into(holder.imgv);

        holder.txt_nameSongs.setText(String.valueOf(song.getTitle()));
        holder.txt_nameTG.setText(String.valueOf(song.getArtist()));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                songListener.onClickSong(song);
            }
        });


    }

    @Override
    public int getItemCount() {
        if(listSongs != null){
            return listSongs.getSongs().size();
        }
        return 0;
    }

    public class SongViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_nameSongs, txt_nameTG;
        private ImageView imgv;
        private LinearLayout layout;
        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nameSongs = itemView.findViewById(R.id.txt_nameSongs);
            txt_nameTG = itemView.findViewById(R.id.txt_nameTG);
            imgv = itemView.findViewById(R.id.imgv);
            layout = itemView.findViewById(R.id.layout_item);
        }
    }
}
