package com.example.reproductor.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.reproductor.fragments.CurrentPlayList;


public class PlayListFragmentAdapter extends FragmentStateAdapter {

    private int tabsNum;

    public PlayListFragmentAdapter(@NonNull Fragment fragment, int tabsNum) {
        super(fragment);
        this.tabsNum = tabsNum;
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new CurrentPlayList();
            case 1:
                return null;
            default:
                return null;
        }

    }

    @Override
    public int getItemCount() {
        return tabsNum;
    }
}
