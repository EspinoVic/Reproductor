package com.example.reproductor.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.example.reproductor.IO.FolderDirectoriesWriteRead;
import com.example.reproductor.Models.Song;
import com.example.reproductor.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static CurrentPlayListViewModel currentPlayListViewModel;
    public static FolderDirectoriesWriteRead folderDirectoriesWriteRead;
    private static AppCompatActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;

        setContentView(R.layout.activity_main);
        currentPlayListViewModel = new ViewModelProvider(this).get(CurrentPlayListViewModel.class);

         folderDirectoriesWriteRead = new FolderDirectoriesWriteRead();
        currentPlayListViewModel.getDirectoriesAvailablesList().setValue( folderDirectoriesWriteRead.saveAvailableDirectories());
        currentPlayListViewModel.getDirectoriesAvailablesList().getValue( );

        List<Song> songList = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            songList.add(new Song("Seek & destroy " +i,"Metallica","asd"));

        }
        currentPlayListViewModel.getCurrentPlayingSongListMutableLiveData().setValue(songList);
    }
    public static Context getContext() {
        return instance.getApplicationContext();
    }

    public static AppCompatActivity getInstance() {
        return instance;
    }
}
