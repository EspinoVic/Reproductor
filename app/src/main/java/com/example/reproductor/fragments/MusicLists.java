package com.example.reproductor.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reproductor.IO.SongsByDirectoryIO;
import com.example.reproductor.Models.Song;
import com.example.reproductor.R;
import com.example.reproductor.adapters.viewpagers.ListSongs;
import com.example.reproductor.main.CurrentPlayListViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicLists extends Fragment {

    ViewPager2 vp2_musicList;

    private CurrentPlayListViewModel currentPlayListViewModel;

    View containerMiniPlayer;

    public MusicLists() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View containerMusicLists = inflater.inflate(R.layout.fragment_music_lists, container, false);
        return containerMusicLists;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        containerMiniPlayer = view.findViewById(R.id.container_mini_player);
        final View btnPause = containerMiniPlayer.findViewById(R.id.btnPause);
      // btnPause.setTransitionName("transitionN_btnPause");

        final View btnNextSong = containerMiniPlayer.findViewById(R.id.btn_nextSong);
        //btnPause.setTransitionName("transitionN_btnNextSong");

        final View imgCurrentSong = containerMiniPlayer.findViewById(R.id.img_song);
        final View txtSongName = containerMiniPlayer.findViewById(R.id.txt_songName);
        final View txtAuthorNAme = containerMiniPlayer.findViewById(R.id.txt_authorName);

         vp2_musicList = view.findViewById(R.id.vp2_musicLists);


        containerMiniPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                        .addSharedElement(btnPause, "transition_btnPause")
                      //  .addSharedElement(btnNextSong,"transition_btnNext")
                        .addSharedElement(imgCurrentSong,"transition_imgCurrentSong")
                        .addSharedElement(txtSongName,"transition_songName")
                        .addSharedElement(txtAuthorNAme,"transition_authorName")
                        .build();
                Navigation.findNavController(view).navigate(R.id.action_musicLists_to_reproductorScreen,
                        null,
                        /*para hacer el clear al backstack */null,
                        extras
                );
            }
        });

        this.tabsListPager = view.findViewById(R.id.tabLayout_listas);

        this.vp2_musicList.setAdapter(new ListSongs(this,3));

        new TabLayoutMediator(tabsListPager, vp2_musicList,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                        switch (position){
                            case 0:
                                tab.setText("Reproduciendo");
                                break;
                            case 1:
                                tab.setText("Folders");
                                break;
                            case 2:
                                tab.setText("Stared");
                                break;
                        }
                    }
                }
        ).attach();

        //cambios en la cancion actual reproduciendo, desde cualquier contexto.
        currentPlayListViewModel = new ViewModelProvider(requireActivity()).get(CurrentPlayListViewModel.class);
        currentPlayListViewModel.getCurrentSongMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Song>() {
            @Override
            public void onChanged(Song currentSong) {
                ((TextView)containerMiniPlayer.findViewById(R.id.txt_songName)).setText(currentSong.getSongName());
                ((TextView)containerMiniPlayer.findViewById(R.id.txt_authorName)).setText(currentSong.getAuthor());
                ((ImageView)containerMiniPlayer.findViewById(R.id.img_song)).setImageBitmap(currentSong.getBitmap());
            }
        });



    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    TabLayout tabsListPager;
}
