package com.example.reproductor.IO;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.example.reproductor.Models.Song;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class scans the storage in order to found directories where would there be songs in.
 * For write and read this directories see
 * @see FolderDirectoriesWriteRead
 */
public class DirectoriesMusicAvailableScan {

    /**
     * The key "string" will be where the route available will be stored. -> extSdcard/metallica/kill_em_all
     * The value "ArrayList<String>" will be the list of nameFiles inside the Route given. -> (route)/The FourHorseman.flac, (route)/No Remorse.flac... etc
     */
    private ArrayList<String> listFolerMusicAvailable;


    public DirectoriesMusicAvailableScan() {
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
        String[] projection = {
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.Albums.ALBUM_ID,
        };
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
                Long albumID = c.getLong(4);

              String coverArtPath = getCoverArtPath(context, albumID);
              Drawable img = Drawable.createFromPath(coverArtPath);

             /*   String coverPath = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                Drawable img = Drawable.createFromPath(coverPath);
                ImageView coverAlbum=(ImageView)view.findViewById(R.id.album_cover);
                coverAlbum.setImageDrawable(img);
*/
                Song song = new Song(name,artist,pathSong,album,albumID);
                song.setPathCoverArt(coverArtPath);
                song.setDrawable(img);
                listSongFolder.add(song);
            }

        }
        return listSongFolder;
    }

    public static String getCoverArtPath(Context context, long androidAlbumId) {
        String path = null;
        Cursor c = context.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?",
                new String[]{Long.toString(androidAlbumId)},
                null);
        if (c != null) {
            if (c.moveToFirst()) {
                path = c.getString(0);
            }
            c.close();
        }
        return path;
    }

/*
    private void initLayout() {
        final Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final String[] cursor_cols = { MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION
        };
        final String where = MediaStore.Audio.Media.IS_MUSIC + "=1";
        final Cursor cursor = context.getContentResolver().query(uri,
                cursor_cols, where, null, null);

        while (cursor.moveToNext()) {
            String artist = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String album = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            String track = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String data = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            Long albumId = cursor.getLong(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

            int duration = cursor.getInt(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

 Cursor cursor =context.getContentResolver().query(
                        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID+ "=?",
                        new String[] {String.valueOf(albumPic)},
                        null);

                Uri sArtworkUri = Uri
                        .parse("content://media/external/audio/albumart");
                Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, Long.parseLong(albumPic));

                //  Logger.debug(albumArtUri.toString());
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(
                            context.getContentResolver(), albumArtUri);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 30, 30, true);

                } catch (FileNotFoundException exception) {

                } catch (IOException e) {

                    e.printStackTrace();
                }
            AudioListModel audioListModel = new AudioListModel();
            audioListModel.setArtist(artist);
            audioListModel.setBitmap(bitmap);
            audioListModel.setAlbum(album);
            audioListModel.setTrack(track);
            audioListModel.setData(data);
            audioListModel.setAlbumId(albumId);
            audioListModel.setDuration(duration);
            audioListModel.setAlbumArtUri(albumArtUri);

            audioArrayList.add(audioListModel);

        }

    }*/

/*
    public void getSonglist(){
        Songs song;
        ContentResolver songResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri artUri;
        Cursor songCursor = songResolver.query(songUri, null, null, null, null);
        final Uri ART_CONTENT_URI = Uri.parse("content://media/external/audio/albumart");
        if(songCursor.moveToFirst()){
            do{ song = new Songs("","",0);
                albumID = songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                song.setTitle(songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                song.setArtist(songCursor.getString(songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                song.set_Id(songCursor.getLong(songCursor.getColumnIndex(MediaStore.Audio.Media._ID)));
                artUri = ContentUris.withAppendedId(ART_CONTENT_URI, albumID);
                AlbumArt = null;
                try {
                    AlbumArt = (MediaStore.Images.Media.getBitmap(getContentResolver(), artUri));
                } catch (Exception exception) {
// log error
                }
                song.setAlbumArt(AlbumArt);
                songlist.add(song);
            }while(songCursor.moveToNext());
        }
    }*/
}
