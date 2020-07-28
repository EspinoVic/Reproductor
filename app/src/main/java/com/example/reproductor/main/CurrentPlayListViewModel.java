package com.example.reproductor.main;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reproductor.IO.FolderDirectoriesWriteRead;
import com.example.reproductor.IO.SongsByDirectoryIO;
import com.example.reproductor.Models.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CurrentPlayListViewModel extends ViewModel {


    /**
     * The current playlist that is being played.
     */
    private MutableLiveData<List<Song>> currentPlayingSongListMutableLiveData;

    /**
     * @return
     * The current playlist that is being played.
     */
    public MutableLiveData<List<Song>> getCurrentPlayingSongListMutableLiveData() {
        if (currentPlayingSongListMutableLiveData == null) {
            currentPlayingSongListMutableLiveData = new MutableLiveData<>();
        }
        return currentPlayingSongListMutableLiveData;
    }

    /**
     * The song that currently is being played.
     */
    private MutableLiveData<Song> currentSongMutableLiveData;

    /**
     *
     * @return
     * The Song that is currently playing.
     *
     */
    public MutableLiveData<Song> getCurrentSongMutableLiveData() {
        if(currentSongMutableLiveData==null){
            currentSongMutableLiveData = new MutableLiveData<>();
        }
        return currentSongMutableLiveData;
    }

    /**
     * Will store the directory which is being watched, when click in a directory is opened a new fragment
     * that will show the song in that directory.
     */
    private MutableLiveData<List<Song>> directoryPlayListCurrentObservedMutableLiveData;

    /**
     * Get the Song List which is clicked in the directories fragment and gonna be displayed in the fragment.
     * @return
     */
    public MutableLiveData<List<Song>> getDirectoryPlayListCurrentObservedMutableLiveData() {
        if(directoryPlayListCurrentObservedMutableLiveData==null){
            directoryPlayListCurrentObservedMutableLiveData = new MutableLiveData<>();
            loadUsers();//asyncronous operation.

        }
        return directoryPlayListCurrentObservedMutableLiveData;
    }
    
    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
    }

    /**
     * Will store the directorie where at least is a song in there.
     */
    private MutableLiveData<List<String>> directoriesAvailablesList;

    /**
     * Get -> the directories where at least is a song in there.
     */
    public MutableLiveData<List<String>> getDirectoriesAvailablesList() {
        if(directoriesAvailablesList == null){
            directoriesAvailablesList = new MutableLiveData<>();
            directoriesAvailablesList.setValue(new FolderDirectoriesWriteRead().saveAvailableDirectories());
        }
        return directoriesAvailablesList;
    }

    /**
     * Will store the route(String) with its "Songs"(ArrayList) inside.
     */
    private MutableLiveData<HashMap<String, ArrayList<Song>>> hashMapSongsByDirectory;
    /**
     * Get -> the route(String) with its "Songs"(ArrayList) inside.
     */
    public MutableLiveData<HashMap<String, ArrayList<Song>>> getHashMapSongsByDirectory() {
        if(hashMapSongsByDirectory == null){
            hashMapSongsByDirectory = new MutableLiveData<>();

            //hashMapSongsByDirectory.setValue(new SongsByDirectoryIO().getSongsByDirectory());
        }
        return hashMapSongsByDirectory;
    }

    /**
     * Will store the route(String) of the album-art with its "BitMap" created.
     */
    private MutableLiveData<HashMap<String, Bitmap>> routeALbumArthashMapMutableLiveData;
    /**
     * Get -> the route(String) of the album-art with its "BitMap" created.
     */
    public MutableLiveData<HashMap<String, Bitmap>> getRouteALbumArthashMapMutableLiveData() {
        if(routeALbumArthashMapMutableLiveData==null){
            routeALbumArthashMapMutableLiveData = new MutableLiveData<>();
            routeALbumArthashMapMutableLiveData.setValue(new HashMap<String, Bitmap>());
        }
        return routeALbumArthashMapMutableLiveData;
    }


}
