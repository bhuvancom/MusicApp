package com.example.musicapp;

/**
 * Author  : Bhuvaneshvar
 * Project : MusicApp
 * Date    : 10:29 PM
 **/

public class Song {
    public int id;
    public String name;
    public String icon;
    public int rate;
    public String url;

    public Song(int id, String name, String icon, int rate,String url) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.rate = rate;
        this.url = url;
    }

    public Song() {
    }

    public Song(String name, String icon, int rate, String url) {
        this.name = name;
        this.icon = icon;
        this.rate = rate;
        this.url = url;
    }
}