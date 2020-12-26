package com.yasin.addproduct;

import android.net.Uri;

public class ModClass
{
    String imagename;
    Uri image;

    public ModClass()
    {

    }


    public ModClass(String imagename, Uri image)
    {
        this.imagename = imagename;
        this.image = image;
    }


    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getImagename() {
        return imagename;
    }

    public void setImagename(String imagename) {
        this.imagename = imagename;
    }
}