package com.example.fitnote13022021;

public class Song {

    //Song (songID INTEGER PRIMARY KEY, songName TEXT, songMP3 INTEGER)

    private int songID;
    private String songName;
    private int songMP3;

    //constructors
    public Song(int songID, String songName, int songMP3) {
        this.songID = songID;
        this.songName = songName;
        this.songMP3 = songMP3;
    }

    // toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return "Song{" +
                "songID=" + songID +
                ", songName='" + songName + '\'' +
                ", songMP3=" + songMP3 +
                '}';
    }

    //Getters and Setters
    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public int getSongMP3() {
        return songMP3;
    }

    public void setSongMP3(int songMP3) {
        this.songMP3 = songMP3;
    }
}
