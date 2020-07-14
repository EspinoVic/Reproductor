package com.example.reproductor.adapters.viewpagers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.reproductor.Models.Song;
import com.example.reproductor.fragments.Directorios;
import com.example.reproductor.fragments.CurrentPlayList;

import java.util.ArrayList;
import java.util.List;


public class ListSongs extends FragmentStateAdapter {

    private int tabsNum;

    public ListSongs(@NonNull Fragment fragment, int tabsNum) {
        super(fragment);
        this.tabsNum = tabsNum;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        List<Song> songList = new ArrayList<>();

        switch (position){
            case 0:
                return new CurrentPlayList();
            case 1:
                return new Directorios();
            case 2:
                for (int i = 0; i < 20; ++i) {
                    songList.add(new Song("Seek & destroy " +i,"Metallica","asd"));

                }
                return new CurrentPlayList(songList);
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return tabsNum;
    }
}
