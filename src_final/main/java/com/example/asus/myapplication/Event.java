package com.example.asus.myapplication;

import android.graphics.Bitmap;

/**
 * Created by Bruno Brito on 30/03/2019.
 */

public class Event {

    private String name;
    private String date;
    private String hour;
    private String duration;
    private String location;
    private String description;
    private byte[] picture;
    private String link;

    public Event(String name, String date, String hour, String duration, String location, String description, byte[] picture, String link)  {
        initialize(name, date, hour, duration, location, description, picture);

        this.link = link;
    }

    public Event(String name, String date, String hour, String duration, String location, String description, byte[] picture)  {
        initialize(name, date, hour, duration, location, description, picture);
        this.link = null;
    }

    private void initialize(String name,String date, String hour, String duration, String location, String description, byte[] picture){
        this.name = name;
        this.date = date;
        this.hour = hour;
        this.duration = duration;
        this.location = location;
        this.description = description;
        this.picture = picture;
    }


    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getDuration() {
        return duration;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getPicture() {
        return picture;
    }

    public String getLink() {
        return link;
    }
}

