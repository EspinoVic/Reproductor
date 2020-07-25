package com.example.reproductor.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reproductor.IO.SongsByDirectoryIO;
import com.example.reproductor.Models.Song;
import com.example.reproductor.R;
import com.example.reproductor.adapters.recyclers.FolderAdapter;
import com.example.reproductor.main.CurrentPlayListViewModel;
import com.example.reproductor.IO.DirectoriesMusicAvailableScan;

import java.util.ArrayList;
import java.util.List;


public class Directorios extends Fragment implements FolderAdapter.ViewHolderFolder.ClickListener{

    RecyclerView recycler_pathsSongs;

    public Directorios() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_directorios, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.recycler_pathsSongs =  view.findViewById(R.id.recycler_pathsSongs);
        this.recycler_pathsSongs.setAdapter(new FolderAdapter(this));
        recycler_pathsSongs.setItemAnimator(new DefaultItemAnimator());
        //recycler_songsCurrentlyPlaying.setLayoutManager(layoutManager);
        recycler_pathsSongs.setLayoutManager( new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onItemClicked(int position,String pathItemClicked) {
        CurrentPlayListViewModel currentPlayListViewModel = new ViewModelProvider(requireActivity()).get(CurrentPlayListViewModel.class);



        //DirectoriesMusicAvailableScan.getListSongOfDirectory(pathItemClicked,getContext())
        //Check if the path clicked is in the cache
        ArrayList<Song> songsOfThePathClicked = currentPlayListViewModel.getHashMapSongsByDirectory().getValue().get(pathItemClicked);
      //  if(songsOfThePathClicked== null){//if it's not in cache, let's load it from the file
         //   songsOfThePathClicked= new SongsByDirectoryIO().getSongsByDirectory().get(pathItemClicked);
        //FIX, the file is loaded at the start of application. So, it will just call to MediaStore Provider.
            if(songsOfThePathClicked == null){//if doesn't be in the file, then call MediaStore provider.

                songsOfThePathClicked = (ArrayList<Song>) DirectoriesMusicAvailableScan.getListSongOfDirectory(pathItemClicked, getContext());
                //now that provider gets the list, it should to be put on the viewmodel.
                currentPlayListViewModel.getHashMapSongsByDirectory().getValue().put(pathItemClicked,songsOfThePathClicked);
            }
       // }

        currentPlayListViewModel.getDirectoryPlayListCurrentObservedMutableLiveData().setValue(songsOfThePathClicked);
        Bundle bundle = new Bundle();
        bundle.putString("tipo_carga","directorio_play_list");
        Navigation.findNavController(recycler_pathsSongs).navigate(R.id.action_musicLists_to_currentPlayList,
                bundle,
                /*para hacer el clear al backstack */null,
                null
        );
    }

    @Override
    public boolean onItemLongClicked(int position) {
        return false;
    }
}