package com.example.reproductor.IO;

import com.example.reproductor.Models.Song;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Save and load the HashMap of the routes with its songs of the binary file of the app.
 */
public class SongsByDirectoryIO {

    /**
     * Save the HashMap of the routes with its songs.
     * @param routesSongsByDirectory
     * The routes to save.
     */
    public void saveSonsgsByDirectory(HashMap<String, ArrayList<Song>> routesSongsByDirectory){

        //Folder PlayerVic.
        File fileSongsByDirectory = new File(FolderDirectoriesWriteRead.directorySaveMusicList,"songsbydirectory.txt");

        //this function create directories, not files xd.
        //boolean mkdirs = fileSongsByDirectory.mkdirs();

        if(fileSongsByDirectory.exists()){
            FileOutputStream fileOut =
                    null;
            try {
                fileOut = new FileOutputStream(fileSongsByDirectory);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(routesSongsByDirectory);
                out.close();
                fileOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else
        if(!fileSongsByDirectory.exists()) {
            try {
                fileSongsByDirectory.createNewFile();

                FileOutputStream fileOut =
                        new FileOutputStream(fileSongsByDirectory);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(routesSongsByDirectory);
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public HashMap<String, ArrayList<Song>> getSongsByDirectory(){
        HashMap<String, ArrayList<Song>> songsByDirectory =  new HashMap<>();
        try {
            //Folder PlayerVic.
            File folderPlayerVic = FolderDirectoriesWriteRead.directorySaveMusicList;
            File fileSongsByDirectory = new File(folderPlayerVic,"songsbydirectory.txt");

            FileInputStream fileIn = new FileInputStream(fileSongsByDirectory);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            songsByDirectory = ( HashMap<String, ArrayList<Song>>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            System.out.println("songsbydirectory.txt not found.");
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("songsbydirectory.txt not found.");
            c.printStackTrace();
        }
        //if cannot read, then return a hashmap empty.
        return songsByDirectory;

    }
}
