package com.zone_bussiness.app.utils;

public class EventDetails {

    private int id;
    private String imgPath;
    private String name;
    private String date;
    private String hour;
    private String type;

    public EventDetails(int id, String imgPath, String name, String date, String hour, String type) {
        this.id = id;
        this.imgPath = imgPath;
        this.name = name;
        this.date = date;
        this.hour = hour;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgPath() {
        return imgPath;
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

    public String getType() {
        return type;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public void setType(String type) {
        this.type = type;
    }
}
