package com.hackathon.sequoia.sequoiahackathon.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by manishkumar on 30/08/15.
 */
public class Review implements Parcelable{

    @SerializedName("user_name")
    private String name;

    private String comment;

    public String getComment() {
        return comment;
    }

    protected Review(Parcel in) {
        name = in.readString();
        comment = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(comment);
    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public String getName() {
        return name;
    }
}
