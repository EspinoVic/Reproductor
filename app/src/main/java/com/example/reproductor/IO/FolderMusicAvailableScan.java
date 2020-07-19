package com.example.reproductor.IO;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class is in charge of scan or saver the routes of folders availables.
 * And can save the directories availables in a file.
 * And also read that file and return it in arratList way.
 */
public class FolderMusicAvailableScan {

    File directorySaveMusicList = new File(Environment.getExternalStorageDirectory(),"PlayerVic");
    File fileListDirectoriesMusicaAvailable;

    public FolderMusicAvailableScan() {
        fileListDirectoriesMusicaAvailable =  new File( directorySaveMusicList,"test.txt");
    }

    public void saveAvailableDirectories(){
        // File nuevaCarpeta = new File("/storage/emulated/0","micarpeta");
        boolean mkdirs = directorySaveMusicList.mkdirs();
        String path = Environment.getExternalStorageDirectory().toString();

        //pathFile.createNewFile();
        //  }
        if(!fileListDirectoriesMusicaAvailable.exists()) {
            try {
                fileListDirectoriesMusicaAvailable.createNewFile();
                ListMusicFiles listMusicFiles = new ListMusicFiles();
                listMusicFiles.getFolder();
                ArrayList<String> foldersAvailable =  listMusicFiles.getListFolerMusicAvailable();

                FileOutputStream fileOut =
                        new FileOutputStream(fileListDirectoriesMusicaAvailable);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(foldersAvailable);
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public ArrayList<String> getAvailableDirectories(){
        ArrayList<String> foldersAvailable =  null;
        try {
           // FileInputStream fileIn = new FileInputStream("/tmp/employee.ser");
            FileInputStream fileIn = new FileInputStream(fileListDirectoriesMusicaAvailable);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            foldersAvailable = ( ArrayList<String>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
        }
        return foldersAvailable;

    }

}
