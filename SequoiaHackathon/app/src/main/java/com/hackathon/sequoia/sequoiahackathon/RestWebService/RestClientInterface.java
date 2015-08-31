package com.hackathon.sequoia.sequoiahackathon.RestWebService;

import com.hackathon.sequoia.sequoiahackathon.api.NearbySchoolResponse;
import com.hackathon.sequoia.sequoiahackathon.api.SchoolDetailResponse;
import com.hackathon.sequoia.sequoiahackathon.api.SignUpResponse;
import com.hackathon.sequoia.sequoiahackathon.api.SubmitResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by manishkumar on 28/08/15.
 */
public interface RestClientInterface {

    public static final String CREATE_ACCOUNT = "/user/create";
    public static final String SIGN_IN = "/user/login";
    public static final String NEARBY_SCHOOL = "/school/nearby";
    public static final String SCHOOL_DETAIL = "/school/{id}";
    public static final String SUBMIT_REVIEW = "/review/create";

    @FormUrlEncoded
    @POST(CREATE_ACCOUNT)
    public void createAccount(
            @Header("Authorization") String authToken,
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("encrypted_password") String password,
            Callback<SignUpResponse> responseCallback);

    @FormUrlEncoded
    @POST(SIGN_IN)
    public void login(
            @Header("Authorization") String authToken,
            @Field("email") String email,
            @Field("encrypted_password") String password,
            Callback<SignUpResponse> responseCallback);

    @GET(NEARBY_SCHOOL)
    public void getNearbySchool(
            @Header("Authorization") String authToken,
            @Query("latitude") double latitude,
            @Query("longitude") double longitude,
            Callback<NearbySchoolResponse> responseCallback);

    @GET(SCHOOL_DETAIL)
    public void getSchoolDetail(
            @Header("Authorization") String authToken,
            @Path("id") int id,
            Callback<SchoolDetailResponse> responseCallback);

    @FormUrlEncoded
    @POST(SUBMIT_REVIEW)
    public void submitReview(
            @Header("Authorization") String authToken,
            @Field("school_id") int id,
            @Field("user_id") int userId,
            @Field("comment") String comment,
            @Field("names") String names,
            @Field("ratings") String ratings,
            Callback<SubmitResponse> responseCallback);
}
