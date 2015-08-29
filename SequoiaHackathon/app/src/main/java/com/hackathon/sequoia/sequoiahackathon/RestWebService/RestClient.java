package com.hackathon.sequoia.sequoiahackathon.RestWebService;

import android.content.Context;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    private int taxonomyVal = 2;

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
                    }
                });

        RestAdapter restAdapter = builder.build();
        mService = restAdapter.create(RestClientInterface.class);
    }

    /*
    // Returns token
    private String getToken() {
        return AppPreferences.getInstance(mContext).getAccessToken();
    }

    // Get list of inspirations
    public void getInspirations(int pageNumber, int perPage, Callback<GetInspirationsResponse> responseListener) {
        mService.getInspirations(getToken(), pageNumber, perPage, null, responseListener);
    }*/
}
