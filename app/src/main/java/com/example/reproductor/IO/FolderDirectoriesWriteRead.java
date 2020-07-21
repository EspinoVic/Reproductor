package com.example.reproductor.IO;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * this class is in charge of scan or saver the routes of folders availables.
 * And can save the directories availables in a file.
 * And also read that file and return it in arratList way.
 */
public class FolderDirectoriesWriteRead {

    File directorySaveMusicList = new File(Environment.getExternalStorageDirectory(),"PlayerVic");
    File fileListDirectoriesMusicaAvailable;

    public FolderDirectoriesWriteRead() {
        fileListDirectoriesMusicaAvailable =  new File( directorySaveMusicList,"test.txt");
    }

    /**
     * This method save the direcctories where is music available inside a txt file.
     * But first call
     * @see DirectoriesMusicAvailableScan to scan de storage, an then de list of directories are written.
     * @return
     */
    public ArrayList<String> saveAvailableDirectories(){
        // File nuevaCarpeta = new File("/storage/emulated/0","micarpeta");
        boolean mkdirs = directorySaveMusicList.mkdirs();
        String path = Environment.getExternalStorageDirectory().toString();

        //pathFile.createNewFile();
        //  }
        ArrayList<String> foldersAvailable = null;
        //SI no existe el archivo , entonces hará un escaneo.
        if(!fileListDirectoriesMusicaAvailable.exists()) {
            try {
                fileListDirectoriesMusicaAvailable.createNewFile();
                DirectoriesMusicAvailableScan directoriesMusicAvailableScan = new DirectoriesMusicAvailableScan();
                 foldersAvailable =  directoriesMusicAvailableScan.startScan();;

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

        return foldersAvailable;
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
