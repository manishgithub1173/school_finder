package com.hackathon.sequoia.sequoiahackathon.RestWebService;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hackathon.sequoia.sequoiahackathon.api.NearbySchoolResponse;
import com.hackathon.sequoia.sequoiahackathon.api.SchoolDetailResponse;
import com.hackathon.sequoia.sequoiahackathon.api.SignUpResponse;
import com.hackathon.sequoia.sequoiahackathon.api.SubmitResponse;
import com.hackathon.sequoia.sequoiahackathon.global.UrlConstants;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by manishkumar on 28/08/15.
 */
public class RestClient {

    private static RestClient sInstance;

    private static String AUTH_TOKEN = "Basic aGFja2F0aG9uOnVyYmFubGFkZGVy";

    private RestClientInterface mService;
    private Context mContext;

    private RestClient(Context context) {
        mContext = context.getApplicationContext();
        init();
    }

    public static RestClient getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RestClient(context);
        }
        return sInstance;
    }

    private void init() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(60, TimeUnit.SECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(UrlConstants.BASE_URL)
                .setClient(new OkClient(client))
                .setConverter(new GsonConverter(gson))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        //String userAgent = SharedInfo.getUserAgent(mContext);
                        //request.addHeader("User-Agent", userAgent);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.HEADERS_AND_ARGS)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String message) {
                        Log.d("ultron", message);
                    }
                });

        RestAdapter restAdapter = builder.build();
        mService = restAdapter.create(RestClientInterface.class);
    }

    public void createAccount(String name, String email, String phone, String password,
                              Callback<SignUpResponse> responseListener) {
        mService.createAccount(AUTH_TOKEN, name, email, phone, password, responseListener);
    }

    public void login(String email, String password,
                              Callback<SignUpResponse> responseListener) {
        mService.login(AUTH_TOKEN, email, password, responseListener);
    }


    public void getNearbySchool(double latitude, double longitude,
                      Callback<NearbySchoolResponse> responseListener) {
        mService.getNearbySchool(AUTH_TOKEN, latitude, longitude, responseListener);
    }

    public void getSchoolDetail(int id, Callback<SchoolDetailResponse> responseListener) {
        mService.getSchoolDetail(AUTH_TOKEN, id, responseListener);
    }

    public void submitReview(int id, int user_id, String comment, String names, String rating, Callback<SubmitResponse> responseListener) {
        mService.submitReview(AUTH_TOKEN, id,user_id, comment, "Infrastructure,Teaching", rating,  responseListener);
    }

}
