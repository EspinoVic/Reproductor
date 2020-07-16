package com.example.reproductor.utilities;

import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ListMusicFiles {

    /**
     * The key "string" will be where the route available will be stored. -> extSdcard/metallica/kill_em_all
     * The value "ArrayList<String>" will be the list of nameFiles inside the Route given. -> (route)/The FourHorseman.flac, (route)/No Remorse.flac... etc
     */

    private HashMap<String,ArrayList<String>> mappintFoldersAvailable;

    public ListMusicFiles() {
        this.mappintFoldersAvailable = new HashMap<>();
    }

    public HashMap<String, ArrayList<String>> getMappintFoldersAvailable() {
        return mappintFoldersAvailable;
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
     * directories available, and the files will be attached to that directtion.
     */
    public void getFilesAvailable(File fileDirectory){

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
                for(File file:musicFilerInDirectory){
                    listRouteFilesAvailable.add(file.getPath());
                }

                mappintFoldersAvailable.put(fileDirectory.getPath(),listRouteFilesAvailable);
            }
        System.out.println("fin");
    }

}
