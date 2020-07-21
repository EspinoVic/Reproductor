package com.example.reproductor.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reproductor.Models.Song;

import java.util.List;

public class CurrentPlayListViewModel extends ViewModel {


    private MutableLiveData<List<Song>> currentPlayingSongListMutableLiveData;

    public MutableLiveData<List<Song>> getCurrentPlayingSongListMutableLiveData() {
        if (currentPlayingSongListMutableLiveData == null) {
            currentPlayingSongListMutableLiveData = new MutableLiveData<>();
        }
        return currentPlayingSongListMutableLiveData;
    }

    private MutableLiveData<Song> currentSongMutableLiveData;

    public MutableLiveData<Song> getCurrentSongMutableLiveData() {
        if(currentSongMutableLiveData==null){
            currentSongMutableLiveData = new MutableLiveData<>();
        }
        return currentSongMutableLiveData;
    }


    private MutableLiveData<List<Song>> directoryPlayListCurrentObservedMutableLiveData;

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


    private MutableLiveData<List<String>> directoriesAvailablesList;

    public MutableLiveData<List<String>> getDirectoriesAvailablesList() {
        if(directoriesAvailablesList == null){
            directoriesAvailablesList = new MutableLiveData<>();
        }
        return directoriesAvailablesList;
    }
    // public static List<Song> currentPlayingList;
    //public static int indexCurrentPlayingSong;


}
