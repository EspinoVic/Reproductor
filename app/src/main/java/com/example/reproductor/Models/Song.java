package com.example.reproductor.Models;

public class Song {

    private String songName = "Unknown name song";
    private String author = "Unkown author";

    private String path;

    public Song(String songName, String author, String path) {
        this.songName = songName;
        this.author = author;
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
}
