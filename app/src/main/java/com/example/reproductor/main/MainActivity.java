package com.example.reproductor.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
  //  public static Bitmap ALBUM_ICON_BITMAP;

    public static CurrentPlayListViewModel currentPlayListViewModel;
    public static FolderDirectoriesWriteRead folderDirectoriesWriteRead;
    private static AppCompatActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

      //  FOLDER_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_folder_24);
     //   ALBUM_ICON_BITMAP = BitmapFactory.decodeResource(getResources(), R.drawable.ic_baseline_album_24);

        setContentView(R.layout.activity_main);
        currentPlayListViewModel = new ViewModelProvider(this).get(CurrentPlayListViewModel.class);
        currentPlayListViewModel.getRouteALbumArthashMapMutableLiveData().getValue();//to create the hashmap
         folderDirectoriesWriteRead = new FolderDirectoriesWriteRead();
     /*   currentPlayListViewModel.getDirectoriesAvailablesList().setValue( folderDirectoriesWriteRead.saveAvailableDirectories());
        currentPlayListViewModel.getDirectoriesAvailablesList().getValue( );
        */
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
                        routeALbumArthashMapMutableLiveData.put(
                                pathCurrentSong,
                                PlayListAdapter.getRoundedCornerBitmap(BitmapFactory.decodeFile(pathCurrentSong),100)
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
}
