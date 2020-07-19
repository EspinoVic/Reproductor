package com.example.reproductor.IO;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.reproductor.Models.Song;
import com.example.reproductor.adapters.recyclers.FolderAdapter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class scans the storage in order to found directories where would there be songs in.
 * For write and read this directories see
 * @see FolderMusicAvailableScan
 */
public class ListMusicFiles {

    /**
     * The key "string" will be where the route available will be stored. -> extSdcard/metallica/kill_em_all
     * The value "ArrayList<String>" will be the list of nameFiles inside the Route given. -> (route)/The FourHorseman.flac, (route)/No Remorse.flac... etc
     */
    private ArrayList<String> listFolerMusicAvailable;


    public ListMusicFiles() {
        this.listFolerMusicAvailable = new ArrayList<>();
    }

    public ArrayList<String> getListFolerMusicAvailable() {
        return listFolerMusicAvailable;
    }

    public void getFolder(){
        String path = Environment.getExternalStorageDirectory().toString();
        // File f = new File("/storage/emulated/0");
        File f = new File("/storage/extSdCard");
        //File[] files = f.listFiles();//got all directories and files in /storage/extSdCard

        File[] directoriesWithOutSystem = f.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                //boolean matches = Pattern.matches("^\\.", ".android");
                // boolean matches2 = Pattern.matches("^\\.[a-z]*||", ".dfaaaasd"); work
                if (s.toLowerCase().equals("android")
                        ||s.toLowerCase().equals("dcim")
                        ||s.toLowerCase().equals("whatsapp")
                        ||s.toLowerCase().equals("telegram")
                        ||s.toLowerCase().equals("pictures")
                        ||s.toLowerCase().equals("backups")
                        ||s.toLowerCase().equals("mega")
                        ||s.contains(".")
                ) {
                    return false;
                }
                return true;
            }
        });

        getFilesAvailable(f);//para root
        getFoldersAvailable(directoriesWithOutSystem);

    }

    /**
     *
     * @param directories
     * Directories group where it's gonna check each sub directory contained.
     */
    private void getFoldersAvailable(File[] directories) {
        if (directories != null) {
            for(File file : directories){
                if(file.isDirectory()){
                    getFilesAvailable(file);
                    getFoldersAvailable(file.listFiles());
                }
                /*else if(file.isFile()){
                    getFilesAvailable(file);
                }*/
            }
        }
    }

    /**
     * Gonna search files music available in the root directory given.
     * @param fileDirectory
     * Directories grout where it's gonna search if can find mp3|flac files.
     * If it find at least one file, then, the directory given will be add that directory to the list of
     * directories available,
     *  and the files will be attached to that directtion.
     */
    private void getFilesAvailable(File fileDirectory){

        //fileDirectory.getName()//nombre de la carpeta
       // fileDirectory.getPath()// /storage/extSdCard
        String pathActual = fileDirectory.getPath();
        ArrayList<String> listRouteFilesAvailable = new ArrayList<>();

        File[] musicFilerInDirectory = fileDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return s.toLowerCase().endsWith(".mp3")||s.toLowerCase().endsWith(".flac");
            }
        });
        if(musicFilerInDirectory!=null)
            if(musicFilerInDirectory.length>0){
               /* for(File file:musicFilerInDirectory){
                    listRouteFilesAvailable.add(file.getPath());
                }
                */
               // mappintFoldersAvailable.put(fileDirectory.getPath(),listRouteFilesAvailable);
                this.listFolerMusicAvailable.add(fileDirectory.getPath());
            }
        System.out.println("fin");
    }

    /**
     * Search all song inside the directory given and return them inside a list.
     * @param path
     * Route directory where is gonna search.
     * @param context
     * Need the context to get them.
     * @return
     * Return list with elements founded.
     * I the other hand, is found nothing, then the list returned will be empty.
     */
    public static List<Song> getListSongOfDirectory(String path, Context context){

        List<Song> listSongFolder = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Audio.AudioColumns.DATA, MediaStore.Audio.AudioColumns.TITLE, MediaStore.Audio.AudioColumns.ALBUM, MediaStore.Audio.ArtistColumns.ARTIST,};
        Cursor c = context.getContentResolver().query(uri,
                projection,
                MediaStore.Audio.Media.DATA + " like ? ",
                new String[]{"%"+path+"%"},
                "title ASC");

        if (c != null) {
            while (c.moveToNext()) {

                String pathSong = c.getString(0);   // Retrieve path.
                String name = c.getString(1);   // Retrieve name.
                String album = c.getString(2);  // Retrieve album name.
                String artist = c.getString(3); // Retrieve artist name.

                Song song = new Song(name,artist,pathSong,album);
                listSongFolder.add(song);
            }

        }
        return listSongFolder;
    }




}
