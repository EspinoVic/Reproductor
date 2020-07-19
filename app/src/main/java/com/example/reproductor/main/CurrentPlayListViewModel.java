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
        }
        return directoryPlayListCurrentObservedMutableLiveData;
    }

    @Deprecated
    private MutableLiveData<Integer> indexCurrentPlayingSong;
    @Deprecated
    public MutableLiveData<Integer> getIndexCurrentPlayingSong() {
        if (indexCurrentPlayingSong == null) {
            indexCurrentPlayingSong = new MutableLiveData<>();
            loadUsers();//asyncronous operation.
        }
        return indexCurrentPlayingSong;

    }
    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
    }




   // public static List<Song> currentPlayingList;
    //public static int indexCurrentPlayingSong;


}
