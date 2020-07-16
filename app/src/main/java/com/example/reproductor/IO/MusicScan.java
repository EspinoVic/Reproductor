package com.example.reproductor.IO;

import android.os.Environment;

import com.example.reproductor.utilities.ListMusicFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MusicScan {

    File directorySaveMusicList = new File(Environment.getExternalStorageDirectory(),"PlayerVic");
    File fileHashMapSerialized;

    public MusicScan() {
        fileHashMapSerialized =  new File( directorySaveMusicList,"test.txt");
    }

    public void saveAvailableDirectories(){
        // File nuevaCarpeta = new File("/storage/emulated/0","micarpeta");
        boolean mkdirs = directorySaveMusicList.mkdirs();
        String path = Environment.getExternalStorageDirectory().toString();

        //pathFile.createNewFile();
        //  }
        if(!fileHashMapSerialized.exists()) {
            try {
                fileHashMapSerialized.createNewFile();
                ListMusicFiles listMusicFiles = new ListMusicFiles();
                listMusicFiles.getFolder();
                 HashMap<String, ArrayList<String>> mappintFoldersAvailable =  listMusicFiles.getMappintFoldersAvailable();

                FileOutputStream fileOut =
                        new FileOutputStream(fileHashMapSerialized);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(mappintFoldersAvailable);
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public HashMap<String, ArrayList<String>> getAvailableDirectories(){
        HashMap<String, ArrayList<String>> mappintFoldersAvailable =  null;
        try {
           // FileInputStream fileIn = new FileInputStream("/tmp/employee.ser");
            FileInputStream fileIn = new FileInputStream(fileHashMapSerialized);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mappintFoldersAvailable = (HashMap<String, ArrayList<String>>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
        }
        return mappintFoldersAvailable;

    }

}
