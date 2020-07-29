package com.example.reproductor.adapters.viewpagers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.reproductor.Models.Song;
import com.example.reproductor.R;
import com.example.reproductor.fragments.PlayListFragment;
import com.example.reproductor.fragments.Directorios;
import com.example.reproductor.main.MainActivity;

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
                PlayListFragment currentPlayListFragment = new PlayListFragment();

                Bundle args = new Bundle();
                args.putString(MainActivity.TIPO_CARGA,MainActivity.CURRENT_PLAY_LIST);
                currentPlayListFragment.setArguments(args);
                return currentPlayListFragment;
            case 1:
                return new Directorios();
            case 2:
                for (int i = 0; i < 20; ++i) {
                    songList.add(new Song("Seek & destroy Star" +i,"Metallica","asd"));
                }
                PlayListFragment playListFragmentStared = new PlayListFragment(songList);
                Bundle argsStared = new Bundle();
                argsStared.putString(MainActivity.TIPO_CARGA,MainActivity.ANY_PLAY_LIST);
                playListFragmentStared.setArguments(argsStared);
                return playListFragmentStared;
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return tabsNum;
    }
}
