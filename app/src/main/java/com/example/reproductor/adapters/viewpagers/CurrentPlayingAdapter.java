package com.example.reproductor.adapters.viewpagers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.reproductor.fragments.currentPlaying.CurrentPlayList;
import com.example.reproductor.fragments.currentPlaying.CurrentPlaying;
import com.example.reproductor.fragments.currentPlaying.ReproductorScreen;

public class CurrentPlayingAdapter extends FragmentStateAdapter {

    private int tabsNum;

    public CurrentPlayingAdapter(@NonNull Fragment fragment, int tabsNum) {
        super(fragment);
        this.tabsNum = tabsNum;
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return ReproductorScreen.newInstance();
            case 1:
                return CurrentPlayList.newInstance();
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return tabsNum;
    }
}
