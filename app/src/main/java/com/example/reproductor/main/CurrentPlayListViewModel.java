package com.example.reproductor.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.reproductor.Models.Song;

import java.util.List;

public class CurrentPlayListViewModel extends ViewModel {


    private MutableLiveData<List<Song>> listSongMutableLiveData;

    public MutableLiveData<List<Song>> getListSongMutableLiveData() {
        if (listSongMutableLiveData == null) {
            listSongMutableLiveData = new MutableLiveData<>();
        }
        return listSongMutableLiveData;
    }

    private MutableLiveData<Song> currentSongMutableLiveData;

    public MutableLiveData<Song> getCurrentSongMutableLiveData() {
        if(currentSongMutableLiveData==null){
            currentSongMutableLiveData = new MutableLiveData<>();
        }
        return currentSongMutableLiveData;
    }

    private MutableLiveData<Integer> indexCurrentPlayingSong;

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
