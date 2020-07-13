package com.example.reproductor.adapters.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reproductor.Models.Song;
import com.example.reproductor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for temporary list of the player,
 * the one list that is playing currently.
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolderSong> {
    @SuppressWarnings("unused")
    private static final String TAG = PlayListAdapter.class.getSimpleName();

    private static final int ITEM_COUNT = 20;
    private String kindView = "lista"; //default type
    private List<Song> songList;


    public PlayListAdapter(String kindView) {
        super();

        this.kindView = kindView;

    }

    public PlayListAdapter(List<Song> songList) {
        super();
        if(songList==null){
            // Create some items
            this.songList = new ArrayList<>();
            for (int i = 0; i < ITEM_COUNT; ++i) {
                this.songList.add(new Song("The Four Horseman" +i,"Metallica","asd"));

            }
        }else
            this.songList = songList;
    }

    public PlayListAdapter(String kindView, List<Song> songList) {
        this(songList);
        this.kindView = kindView;
    //    this.songList = songList;
    }

    @NonNull
    @Override
    public ViewHolderSong onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(this.kindView.equals("lista")?R.layout.song_item_lista : R.layout.song_item_,
                parent, false);

        v.findViewById(R.id.img_song).setTransitionName("transition_imgCurrentSong");
        v.findViewById(R.id.txt_songName).setTransitionName("transition_songName");
        v.findViewById(R.id.txt_authorName).setTransitionName("transition_authorName");


        return new ViewHolderSong(v);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolderSong holder) {
        super.onViewAttachedToWindow(holder);
/*
        holder.songName.setTransitionName("transition_songName");
        holder.authorName.setTransitionName("transition_authorName");
        holder.img.setTransitionName("transition_imgCurrentSong");
        */
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSong holder, int position) {
        final Song song = songList.get(position);


        holder.songName.setText(song.getSongName());
        holder.authorName.setText(song.getAuthor());
        holder.img.setImageResource(R.drawable.kill_em_all);



    }

    @Override
    public int getItemCount() {
        return songList.size();
    }


    public static class ViewHolderSong extends RecyclerView.ViewHolder {

        private TextView songName;
        private TextView authorName;
        private ImageView img;
        public ViewHolderSong(View itemView) {
            super(itemView);
            songName = itemView.findViewById(R.id.txt_songName);


            authorName =  itemView.findViewById(R.id.txt_authorName);

            img=  itemView.findViewById(R.id.img_song);

        }

        public TextView getSongName() {
            return songName;
        }

        public TextView getAuthorName() {
            return authorName;
        }

        public ImageView getImg() {
            return img;
        }
    }

}
