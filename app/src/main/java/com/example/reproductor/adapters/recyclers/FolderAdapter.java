package com.example.reproductor.adapters.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reproductor.IO.FolderDirectoriesWriteRead;
import com.example.reproductor.Models.Folder;
import com.example.reproductor.R;
import com.example.reproductor.main.MainActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolderFolder> {

    private List<Folder> folderList;
    private ViewHolderFolder.ClickListener clickListener;
  //  private static MusicScan musicScan = new MusicScan();
    //public static HashMap<String, ArrayList<String>> availableDirectories;
    public static  ArrayList<String> availableDirectories;

    public FolderAdapter(ViewHolderFolder.ClickListener clickListener) {
        this.clickListener = clickListener;
        folderList = new ArrayList<>();

        availableDirectories = (ArrayList<String>) MainActivity.currentPlayListViewModel.getDirectoriesAvailablesList().getValue();

        for(String rutaActual:availableDirectories){
            File file = new File(rutaActual);
            String name = file.getName();
            folderList.add(new Folder(name,rutaActual));
        }

    }

    public FolderAdapter(List<Folder> folderList) {
        this.folderList = folderList;
    }

    @NonNull
    @Override
    public ViewHolderFolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.folder_item , parent, false);

        return new ViewHolderFolder(v,this.clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFolder holder, int position) {
        final Folder folder = folderList.get(position);

        holder.getTxtNameFolder().setText(folder.getNameFolder());
        holder.getTxtPath().setText(folder.getPathFolder());
    }

    @Override
    public int getItemCount() {
        return folderList.size();
    }

    public static class ViewHolderFolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private ClickListener listener;
        private TextView txtPath;
        private TextView txtNameFolder;

        public ViewHolderFolder(@NonNull View itemView,ClickListener listener) {
            super(itemView);

            txtNameFolder = itemView.findViewById(R.id.txt_folder);
            txtPath = itemView.findViewById(R.id.txt_path);
            this.listener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public TextView getTxtPath() {
            return txtPath;
        }

        public TextView getTxtNameFolder() {
            return txtNameFolder;
        }

        @Override
        public void onClick(View view) {
            if(listener!=null){
                listener.onItemClicked(getAdapterPosition(),this.getTxtPath().getText().toString());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            if(listener!=null){
                return listener.onItemLongClicked(getAdapterPosition());
            }
            return false;
        }

        public interface ClickListener {
            public void onItemClicked(int position,String pathItemClicked);
            public boolean onItemLongClicked(int position);
        }
    }
}
