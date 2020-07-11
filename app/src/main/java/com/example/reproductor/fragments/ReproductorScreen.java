package com.example.reproductor.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.reproductor.R;
import com.example.reproductor.adapters.Adapter;
import com.example.reproductor.transitions.DetailsTransition;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReproductorScreen extends Fragment {


    ImageButton btnNext;
    ImageButton btnPreviouse;
    ImageView btnPause;
    RecyclerView recyclerPlayer;
    Adapter adapterSongsCurrentPlaying;

    LinearLayoutManager layoutManager =
            new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) ;

    //cambio por pagina, aun cuando se haga un scroll rapido, cambiara 1 vez (el lineal, la cantidad cambia por velocidad)
    SnapHelper snapHelper = new PagerSnapHelper();

    //for knowing the  current position that is been playing.
    int indexSongCurrent = 0;

    public ReproductorScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setSharedElementEnterTransition(new DetailsTransition());

        View playerView = inflater.inflate(R.layout.fragment_reproductor_screen, container, false);
        recyclerPlayer = playerView.findViewById(R.id.recycler_songsCurrentlyPlaying);
        adapterSongsCurrentPlaying = new Adapter();
        recyclerPlayer.setAdapter(adapterSongsCurrentPlaying);

       // snapHelper.findTargetSnapPosition(layoutManager,10,0);
       // snapHelper.onFling(10,10);
        snapHelper.attachToRecyclerView(recyclerPlayer);


        recyclerPlayer.setItemAnimator(new DefaultItemAnimator());
        recyclerPlayer.setLayoutManager(layoutManager);

        btnNext = playerView.findViewById(R.id.btn_nextSong);
        btnPreviouse = playerView.findViewById(R.id.btn_previousSong);
        btnPause = playerView.findViewById(R.id.btnPause);



        //make validation for index in range of itemCount
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(indexSongCurrent<adapterSongsCurrentPlaying.getItemCount()){
                    recyclerPlayer.smoothScrollToPosition(++indexSongCurrent);
                }
            }
        });

        btnPreviouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(indexSongCurrent>0){
                    recyclerPlayer.smoothScrollToPosition(--indexSongCurrent);
                }
            }
        });


        recyclerPlayer.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //System.out.println("onscrolled");
                //Here it's got the targetPosition in base of the scroll,
                // but, this function is called many times,
                // implementar function sincrona de acceso a la variable para cambiar la cancion
               /* int targetPos = snapHelper.findTargetSnapPosition(layoutManager,dx,dy);
                System.out.println("Target position: "+targetPos);
                indexSongCurrent=targetPos;
                */
               //this makes the same than the code above.
                //but still have the same problem of calling
                View findSnapView = snapHelper.findSnapView(layoutManager);
                int position = layoutManager.getPosition(findSnapView);
                indexSongCurrent=position;
                System.out.println("Last position: " +position );
            }

            @Override
            @SuppressWarnings("unused")
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                //super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        System.out.println("The RecyclerView is not scrolling");
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        System.out.println("Scrolling now");
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        System.out.println("Scroll Settling");
                        //it gives the last position
                       /*
                        View findSnapView = snapHelper.findSnapView(layoutManager);
                        int position = layoutManager.getPosition(findSnapView);
                        System.out.println("Last position: " +position );
*/
                        /*int findFirstCompletelyVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
                       // Adapter.ViewHolder vieiwH = snapHelper.findSnapView(layoutManager);
                        System.out.println("firstCompletelyVisibleItemPosition: " + findFirstCompletelyVisibleItemPosition);
*/

                        break;
                }
            }
        });

        return playerView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //View viewByPosition = this.layoutManager.findViewByPosition(indexSongCurrent).;
      //  Adapter.ViewHolder viewHolderForItemId = (Adapter.ViewHolder) recyclerPlayer.findViewHolderForItemId(indexSongCurrent);
       // viewHolderForItemId.getImg().setTransitionName("transition_imgCurrentSong");
        //View itemView = recyclerPlayer.findViewHolderForAdapterPosition(0).itemView;
        //itemView.findViewById(R.id.img_song).setTransitionName("transition_imgCurrentSong");
    }
}
