package com.example.rupali.dishconnect;

import android.net.Uri;

public class UserData {

    String email,password,name;
    Uri imageurl;
    String title;
    String adhar_no;

    public  UserData()
    {

    }



    public UserData(String email, String password, String adhar_no) {
        this.email = email;
        this.password = password;
        this.adhar_no = adhar_no;
    }

    public UserData(Uri imageurl ,String title ) {
        this.imageurl = imageurl;
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Uri getImageurl() {
        return imageurl;
    }

    public void setImageurl(Uri imageurl) {
        this.imageurl = imageurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
