package com.example.reproductor.adapters.recyclers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reproductor.Models.Folder;
import com.example.reproductor.Models.Song;
import com.example.reproductor.R;

import java.util.ArrayList;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolderFolder> {

    private List<Folder> folderList;

    public FolderAdapter() {
        folderList = new ArrayList<>();
        for (int i = 0; i < 20; ++i) {
            folderList.add(new Folder("Folder " +i,"Ruta "+ i));

        }
    }

    public FolderAdapter(List<Folder> folderList) {
        this.folderList = folderList;
    }

    @NonNull
    @Override
    public ViewHolderFolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.folder_item , parent, false);

        return new ViewHolderFolder(v);
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

        private TextView txtPath;
        private TextView txtNameFolder;

        public ViewHolderFolder(@NonNull View itemView) {
            super(itemView);

            txtNameFolder = itemView.findViewById(R.id.txt_folder);
            txtPath = itemView.findViewById(R.id.txt_path);
        }

        public TextView getTxtPath() {
            return txtPath;
        }

        public TextView getTxtNameFolder() {
            return txtNameFolder;
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}
