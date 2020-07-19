package com.example.reproductor.Models;

import android.graphics.drawable.Drawable;

public class Song {

    private Drawable drawable;
    private String songName = "Unknown name song";
    private String author = "Unkown author";
    private String album = "Unknown Album";
    private String path;

    private boolean stared = false;



    public Song(String songName, String author, String path) {
        this.songName = songName;
        this.author = author;
        this.path = path;
    }

    public Song(String songName, String author, String path, boolean stared) {
        this.songName = songName;
        this.author = author;
        this.path = path;
        this.stared = stared;
    }

    public Song(String songName, String author, Drawable drawable) {
        this.songName = songName;
        this.author = author;
        this.drawable = drawable;
    }

    public Song(String songName, String author, String album, String path) {
        this.songName = songName;
        this.author = author;
        this.album = album;
        this.path = path;
    }

    public String getSongName() {
        return songName;
    }

    public String getAuthor() {
        return author;
    }

    public String getPath() {
        return path;
    }

    public boolean isStared() {
        return stared;
    }

    public void setStared(boolean stared) {
        this.stared = stared;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
