package com.univtop.univtop.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.univtop.univtop.models.Question;
import com.univtop.univtop.models.deserializers.QuestionDeserializer;
import com.univtop.univtop.models.paging.PageableList;

import retrofit.RestAdapter;
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
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Question.class, new QuestionDeserializer())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(apiURL)
                .setRequestInterceptor(this)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
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