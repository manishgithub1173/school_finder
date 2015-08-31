package com.hackathon.sequoia.sequoiahackathon.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manishkumar on 29/08/15.
 */
public class School {


    private String name;
    private int id;
    private String latitude;
    private String longitude;
    private String email;
    private String address;
    private String phone;

    @SerializedName("image_url")
    private String image;


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getImage() {
        return image;
    }
}
