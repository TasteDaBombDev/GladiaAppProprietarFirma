package com.zone_bussiness.app.utils;

import android.graphics.Bitmap;

public class Artist {

    private Bitmap img;
    private String name;
    private String descriere;

    public Artist(Bitmap img, String name, String descriere) {
        this.img = img;
        this.name = name;
        this.descriere = descriere;
    }

    public Bitmap getImg() {
        return img;
    }

    public String getDescriere() {
        return descriere;
    }

    public String getName() {
        return name;
    }
}
