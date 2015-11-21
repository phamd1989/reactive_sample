package com.univtop.univtop.services;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.univtop.univtop.models.Question;
import com.univtop.univtop.models.deserializers.QuestionDeserializer;
import com.univtop.univtop.models.paging.PageableList;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by dungpham on 11/2/15.
 */
public class PagingApiService extends UnivtopRequestInterceptor {
    private Api mApi;

    public PagingApiService(String apiURL) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(3, TimeUnit.MINUTES);
        client.networkInterceptors().add(new StethoInterceptor());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Question.class, new QuestionDeserializer())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(apiURL)
                .setRequestInterceptor(this)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(client))
                .build();

        mApi = restAdapter.create(Api.class);
    }

    public Observable<PageableList<Question>> getPublicQuestionsPage(String path) {
        return mApi.getPublicQuestionsPage(path);
    }

    public interface Api {
        @GET("/{path}")
        Observable<PageableList<Question>> getPublicQuestionsPage(@Path(value = "path", encode = false) String path);
    }
}