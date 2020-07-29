package com.example.reproductor.adapters.recyclers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
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

import java.util.HashMap;
import java.util.List;

/**
 * Adapter for temporary list of the player,
 * the one list that is playing currently.
 */
public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.ViewHolderSong> {
    @SuppressWarnings("unused")
    private static final String TAG = PlayListAdapter.class.getSimpleName();

    private static final int ITEM_COUNT = 20;
    private List<Song> songList;

    private ViewHolderSong.ClickListener clickListener;
    private HashMap<String, Bitmap> routeAlbumArtHashMap;


    public PlayListAdapter(ViewHolderSong.ClickListener clickListener, List<Song> songList) {
        this.clickListener = clickListener;
        this.songList = songList;
        routeAlbumArtHashMap= MainActivity.currentPlayListViewModel.getRouteALbumArthashMapMutableLiveData().getValue();
    }

    public List<Song> getSongList() {
        return songList;
    }

    @NonNull
    @Override
    public ViewHolderSong onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item_lista,
                parent, false);

        v.findViewById(R.id.img_song).setTransitionName("transition_imgCurrentSong");
        v.findViewById(R.id.txt_songName).setTransitionName("transition_songName");
        v.findViewById(R.id.txt_authorName).setTransitionName("transition_authorName");


        return new ViewHolderSong(v,clickListener);
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

    /**
     * Gonna search in the hashmap of bitmaps if the key given exists.
     * This function is invoked by many threads, that's why it is synchronized;
     * @param key_pathAlbumArt
     * The key path of the album required.
     * @return
     * @see Bitmap if the path exists.
     * @return NULL if it doesn't.
     */
    public synchronized Bitmap getAlbumArt(String key_pathAlbumArt){
        return this.routeAlbumArtHashMap.get(key_pathAlbumArt);
    }

    public synchronized HashMap<String, Bitmap> getRouteAlbumArtHashMap(){
        return this.routeAlbumArtHashMap;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderSong holder, int position) {
        final Song song = songList.get(position);
        holder.songName.setText(song.getSongName());
        holder.authorName.setText(song.getAuthor());


        if(song.getBitmap()==null){

//            holder.img.setImageResource(R.drawable.ic_baseline_album_24);
           // holder.img.setImageBitmap(MainActivity.ALBUM_ICON_BITMAP);//ya viene por defecto en el layout del item
            //if it's null, then will go an create it from the path,
            if(song.getPathCoverArt() != null)
                new Thread(() -> {
                    String pathCoverArt = song.getPathCoverArt();
                        Bitmap albumArtExists = getAlbumArt(pathCoverArt);
                        if(albumArtExists!=null){
                           song.setBitmap(albumArtExists); //ya tieene el radius corner
                        }else{//si no existe en el hashmap, entonces lo crea y lo añade
                            albumArtExists = getRoundedCornerBitmap(BitmapFactory.decodeFile(pathCoverArt),100);//no tiene el radius corner
                            song.setBitmap(albumArtExists);
                            getRouteAlbumArtHashMap().put(pathCoverArt,albumArtExists);
                        }
                        final Bitmap finalAlbumArtExists = albumArtExists;
                        MainActivity.getInstance().runOnUiThread(() -> holder.img.setImageBitmap(finalAlbumArtExists));

                }).start();
        }
        else{
            holder.img.setImageBitmap(song.getBitmap());
        }
    }
    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    public synchronized static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }


    public static class ViewHolderSong extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private TextView songName;
        private TextView authorName;
        private ImageView img;

        private ClickListener listener;

        public ViewHolderSong(View itemView,ClickListener listener) {
            super(itemView);
            songName = itemView.findViewById(R.id.txt_songName);


            authorName =  itemView.findViewById(R.id.txt_authorName);

            img=  itemView.findViewById(R.id.img_song);
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

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

        @Override
        public void onClick(View view) {
            if (listener != null) {
                //cant acces to the list from here. So the acces should to be in the fragment of the playlist.
                listener.onItemClicked(
                       getAdapterPosition()
                );
            }
        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }

        public interface ClickListener {
            public void onItemClicked(int positionSong);
            public boolean onItemLongClicked(int position);
        }
    }

}
