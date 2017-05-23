package com.example.makhamwan.myricsapp;
import android.util.Log;

/**
 * Created by makhamwan on 5/23/2017 AD.
 */

public class Song {
    private String url, name, artist, album, lyric;

    public Song(String url, String name, String artist, String album, String lyric){
        this.url = url;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.lyric = lyric;
        Log.d("test","created");
    }

    public Song(){}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }
}