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
import com.example.reproductor.main.MainActivity;

import java.util.List;

/**
 * Adapter for temporary list of the player,
 * the one list that is playing currently.
 */
public class SongBigAdapter extends RecyclerView.Adapter<SongBigAdapter.ViewHolderSong> {
    @SuppressWarnings("unused")
    private static final String TAG = SongBigAdapter.class.getSimpleName();

    private List<Song> songList;

    public SongBigAdapter(List<Song> songList) {
        this.songList = songList;
    }

    @NonNull
    @Override
    public ViewHolderSong onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item_,
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
        if(song.getPathCoverArt()==null)
//            holder.img.setImageBitmap(MainActivity.ALBUM_ICON_BITMAP);
            holder.img.setImageDrawable(MainActivity.ALBUM_ICON_BITMAP_DRAWABLE_RED);
        else
            if(song.getBitmap()!=null)
                holder.img.setImageBitmap(song.getBitmap());



    }

    @Override
    public int getItemCount() {
        return songList.size();
    }


    public static class ViewHolderSong extends RecyclerView.ViewHolder{

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
