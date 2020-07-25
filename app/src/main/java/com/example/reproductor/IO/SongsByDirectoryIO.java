package com.example.reproductor.IO;

import com.example.reproductor.Models.Song;

import java.io.File;
import java.io.FileInputStream;
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
        File folderPlayerVic = FolderDirectoriesWriteRead.directorySaveMusicList;
        File fileSongsByDirectory = new File(folderPlayerVic,"SongsByDirectory.vic");

        boolean mkdirs = fileSongsByDirectory.mkdirs();

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
        HashMap<String, ArrayList<Song>> songsByDirectory =  null;
        try {
            //Folder PlayerVic.
            File folderPlayerVic = FolderDirectoriesWriteRead.directorySaveMusicList;
            File fileSongsByDirectory = new File(folderPlayerVic,"SongsByDirectory.vic");

            FileInputStream fileIn = new FileInputStream(fileSongsByDirectory);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            songsByDirectory = ( HashMap<String, ArrayList<Song>>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("SongsByDirectory.vic not found.");
            c.printStackTrace();
        }
        return songsByDirectory;

    }
}
