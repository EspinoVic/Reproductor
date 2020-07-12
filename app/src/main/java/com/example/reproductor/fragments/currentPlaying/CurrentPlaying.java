package com.example.reproductor.fragments.currentPlaying;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reproductor.R;
import com.example.reproductor.adapters.viewpagers.CurrentPlayingAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentPlaying#newInstance} factory method to
 * create an instance of this fragment.
 *
 * This fragment contains a ViewPager in order to have the main view with controls of current playing
 * and the list current playing, to swipe between them.
 *
 */
public class CurrentPlaying extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ViewPager2 viewPager2;
    CurrentPlayingAdapter currentPlayingAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CurrentPlaying() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentPlaying.
     */
    /*// TODO: Rename and change types and number of parameters
    public static CurrentPlaying newInstance() {
        CurrentPlaying fragment = new CurrentPlaying();

        return fragment;
    }
*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_playing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentPlayingAdapter = new CurrentPlayingAdapter(this,2);//se le manda this porque necesita saber quien contendrá el viewpager

        viewPager2 = view.findViewById(R.id.vp2_CurrentPlaying);
        viewPager2.setAdapter(currentPlayingAdapter);
    }
}