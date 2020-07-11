package com.example.reproductor.adapters;

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
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    @SuppressWarnings("unused")
    private static final String TAG = Adapter.class.getSimpleName();

    private static final int ITEM_COUNT = 20;
    private List<Song> songList;

    public Adapter() {
        super();

        // Create some items
        songList = new ArrayList<>();
        for (int i = 0; i < ITEM_COUNT; ++i) {
            songList.add(new Song("The Four Horseman" +i,"Metallica","asd"));

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item_, parent, false);
        v.findViewById(R.id.img_song).setTransitionName("transition_imgCurrentSong");
        v.findViewById(R.id.txt_songName).setTransitionName("transition_songName");
        v.findViewById(R.id.txt_authorName).setTransitionName("transition_authorName");


        return new ViewHolder(v);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);

        holder.songName.setTransitionName("transition_songName");
        holder.authorName.setTransitionName("transition_authorName");
        holder.img.setTransitionName("transition_imgCurrentSong");
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Song song = songList.get(position);


        holder.songName.setText(song.getSongName());
        holder.authorName.setText(song.getAuthor());
        holder.img.setImageResource(R.drawable.kill_em_all);



    }

    @Override
    public int getItemCount() {
        return songList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView songName;
        private TextView authorName;
        private ImageView img;
        public ViewHolder(View itemView) {
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
