package com.example.reproductor.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import com.example.reproductor.IO.MusicScan;
import com.example.reproductor.Models.Song;
import com.example.reproductor.R;
import com.example.reproductor.fragments.CurrentPlayList;
import com.example.reproductor.utilities.ListMusicFiles;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private CurrentPlayListViewModel currentPlayListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MusicScan musicScan = new MusicScan();
        HashMap<String, ArrayList<String>> availableDirectories = musicScan.getAvailableDirectories();

        currentPlayListViewModel = new ViewModelProvider(this).get(CurrentPlayListViewModel.class);
        List<Song> songList = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            songList.add(new Song("Seek & destroy " +i,"Metallica","asd"));

        }
        currentPlayListViewModel.getListSongMutableLiveData().setValue(songList);
    }
}
