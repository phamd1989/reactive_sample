package com.univtop.univtop;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by duncapham on 8/9/15.
 */
public interface API {
    @GET("/question?format=json")
    public void getTrendingFeedData(@Query("limit") int limit,
                                    @Query("offset") int offset,
                                    Callback<Response> response);
}
