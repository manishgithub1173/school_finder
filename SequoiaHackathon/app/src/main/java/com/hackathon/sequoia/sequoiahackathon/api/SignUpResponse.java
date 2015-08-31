package com.hackathon.sequoia.sequoiahackathon.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manishkumar on 29/08/15.
 */
public class SignUpResponse {

    private int id;

    private String name;

    private String email;

    @SerializedName("password")
    private String encrypted_password;

    private String phone;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
