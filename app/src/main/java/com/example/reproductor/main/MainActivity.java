package com.example.reproductor.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.reproductor.IO.FolderDirectoriesWriteRead;
import com.example.reproductor.IO.SongsByDirectoryIO;
import com.example.reproductor.Models.Song;
import com.example.reproductor.R;
import com.example.reproductor.adapters.recyclers.PlayListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * Value for fragment directories.
     */
    public final static String DIRECTORY_PLAY_LIST = "directory_play_list";
    public final static String CURRENT_PLAY_LIST = "current_play_list";
    public final static String ANY_PLAY_LIST = "any_play_list";

    public final static String TIPO_CARGA = "tipo_carga";

   // public static Bitmap FOLDER_BITMAP;
    public static Bitmap ALBUM_ICON_BITMAP;
    public static Drawable ALBUM_ICON_BITMAP_DRAWABLE_RED;
    public static Drawable ALBUM_ICON_BITMAP_DRAWABLE_BLUE;

    public static Bitmap ALBUM_ICON_BITMAP_BITMAP_RED;
    public static Bitmap ALBUM_ICON_BITMAP_BITMAP_BLUE;

    public static CurrentPlayListViewModel currentPlayListViewModel;
    public static FolderDirectoriesWriteRead folderDirectoriesWriteRead;
    private static AppCompatActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

         ALBUM_ICON_BITMAP_DRAWABLE_RED = getDrawable(R.drawable.ic_baseline_album_80);
        ALBUM_ICON_BITMAP_DRAWABLE_BLUE = getDrawable(R.drawable.ic_baseline_album_24);

        ALBUM_ICON_BITMAP_BITMAP_RED = drawableToBitmap(ALBUM_ICON_BITMAP_DRAWABLE_RED);
        ALBUM_ICON_BITMAP_BITMAP_BLUE = drawableToBitmap(ALBUM_ICON_BITMAP_DRAWABLE_BLUE);

        setContentView(R.layout.activity_main);
        currentPlayListViewModel = new ViewModelProvider(this).get(CurrentPlayListViewModel.class);
        currentPlayListViewModel.getRouteALbumArthashMapMutableLiveData().getValue();//to create the hashmap
        folderDirectoriesWriteRead = new FolderDirectoriesWriteRead();


        HashMap<String, ArrayList<Song>> songsByDirectory = new SongsByDirectoryIO().getSongsByDirectory();
        currentPlayListViewModel.getHashMapSongsByDirectory().setValue(songsByDirectory);
        //load the album-art bitmap in a background thread and still keeping them cached, so the playlist adapter hasn't to create them.
        new Thread(()->{
            Collection<ArrayList<Song>> values = currentPlayListViewModel.getHashMapSongsByDirectory().getValue().values();
            HashMap<String, Bitmap> routeALbumArthashMapMutableLiveData = currentPlayListViewModel.getRouteALbumArthashMapMutableLiveData().getValue();
            for(ArrayList<Song> currentList:values){
                for(Song currentSong:currentList){
                    String pathCurrentSong = currentSong.getPathCoverArt();
                    if(pathCurrentSong==null)
                        continue;
                    if(!routeALbumArthashMapMutableLiveData.containsKey(pathCurrentSong)){
                        Bitmap bitmapGot = BitmapFactory.decodeFile(pathCurrentSong);

                        routeALbumArthashMapMutableLiveData.put(
                                pathCurrentSong,
                                PlayListAdapter.getRoundedCornerBitmap(bitmapGot == null?MainActivity.ALBUM_ICON_BITMAP_BITMAP_BLUE:bitmapGot,100)
                        );//no tiene el radius corner)
                    }
                }
                System.out.println("Directory playlist albums art created");

            }
            System.out.println("Fin creation album art");
        }).start();

        List<Song> songList = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            songList.add(new Song("Seek & destroy " +i,"Metallica","asd"));

        }
        currentPlayListViewModel.getCurrentPlayingSongListMutableLiveData().setValue(songList);
    }
    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public synchronized static AppCompatActivity getInstance() {
        return instance;
    }

    @Override
    protected void onDestroy() {

        new SongsByDirectoryIO().
                saveSonsgsByDirectory(currentPlayListViewModel.getHashMapSongsByDirectory().getValue());

        super.onDestroy();


    }

    public synchronized static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
