package com.hackathon.sequoia.sequoiahackathon.api;

import java.util.ArrayList;

/**
 * Created by manishkumar on 30/08/15.
 */
public class SchoolDetailResponse {

    private School school;

    private Rating rating;

    private ArrayList<Review> reviews = new ArrayList<>();

    public School getSchool() {
        return school;
    }

    public Rating getRating() {
        return rating;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }
}
