package com.br.arley.spotifyclone;

import android.graphics.Bitmap;

public class Song {
    String imgUrl;
    String name;
    String author;
    String playlistName;
    int song;
    Bitmap cover;

    boolean favorite;

    public Song() {

    }

    public Song(String imgUrl, String name, String author, String playlistName, int song) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.author = author;
        this.playlistName = playlistName;
        this.song = song;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public int getSong() {
        return song;
    }

    public void setSong(int song) {
        this.song = song;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Bitmap getCover() {
        return cover;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }
}
