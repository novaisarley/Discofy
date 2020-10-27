package com.br.arley.spotifyclone;

public class Song {
    String imgUrl;
    String name;
    String author;
    String playlistName;
    int song;
    boolean favorite;

    public Song(){

    }

    public Song(String imgUrl, String name, String author, String playlistName, int song) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.author = author;
        this.playlistName = playlistName;
        this.song = song;
    }

    public Song(String name, String imgUrl, String author, String playlistName, boolean favorite) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.author = author;
        this.playlistName = playlistName;
        this.favorite = favorite;
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
}
