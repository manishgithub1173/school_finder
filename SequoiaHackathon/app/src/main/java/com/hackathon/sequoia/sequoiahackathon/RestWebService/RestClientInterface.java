package com.hackathon.sequoia.sequoiahackathon.RestWebService;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by manishkumar on 28/08/15.
 */
public interface RestClientInterface {

    /*
    @GET(CUSTOMER_TESTIMONIALS)
    public void getCustomerTestimonialsByTaxonId(
            @Header(SPREE_TOKEN) String token,
            @Query("taxon_id") String taxonId,
            @Query("per_page")int perPage,
            @Query("page")int pageNumber,
            Callback<TestimonialResponse> response
    );

    @FormUrlEncoded
    @POST(STORE_GCM_TOKEN)
    public void storeGcmToken(
            @Header(SPREE_TOKEN) String token,
            @Field("third_party_token") String gcmToken,
            @Field("device_os") String deviceOS,
            @Field("third_party_source") String source,
            @Field("device_id") String deviceId,
            Callback<ULResponse> responseCallback);
            */
}
