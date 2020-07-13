package com.example.reproductor.Models;

public class Folder {

    String nameFolder;
    String pathFolder;

    public Folder(String nameFolder, String pathFolder) {
        this.nameFolder = nameFolder;
        this.pathFolder = pathFolder;
    }

    public String getNameFolder() {
        return nameFolder;
    }

    public void setNameFolder(String nameFolder) {
        this.nameFolder = nameFolder;
    }

    public String getPathFolder() {
        return pathFolder;
    }

    public void setPathFolder(String pathFolder) {
        this.pathFolder = pathFolder;
    }
}
