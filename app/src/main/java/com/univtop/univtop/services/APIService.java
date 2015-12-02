package com.univtop.univtop.services;

import android.util.Log;

import com.facebook.stetho.okhttp.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.univtop.univtop.BuildConfig;
import com.univtop.univtop.models.LoginResponse;
import com.univtop.univtop.models.Question;
import com.univtop.univtop.models.deserializers.QuestionDeserializer;
import com.univtop.univtop.models.paging.PageableList;

import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by dungpham on 11/1/15.
 */
public class APIService extends UnivtopRequestInterceptor {
    public static final String API_URL = BuildConfig.DEBUG ? "http://development.univtop.com" : "http://development.univtop.com";
    private static final String TAG = "__Univtop_service";
    private static volatile APIService instance = null;
    private Api mApi;

    public static APIService getInstance() {
        if (instance == null) {
            synchronized (APIService.class) {
                // Double check
                if (instance == null) {
                    instance = new APIService();
                }
            }
        }
        return instance;
    }

    private APIService() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(30, TimeUnit.SECONDS);
        client.setReadTimeout(3, TimeUnit.MINUTES);
        client.networkInterceptors().add(new StethoInterceptor());

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Question.class, new QuestionDeserializer())
                .create();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLog(new AndroidLog(TAG))
                .setEndpoint(API_URL)
                .setRequestInterceptor(this)
                .setConverter(new GsonConverter(gson))
                .setClient(new OkClient(client))
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .build();

        mApi = restAdapter.create(Api.class);
    }


    public Observable<PageableList<Question>> getPublicQuestions(int limit, int offset) {
        return mApi.getPublicQuestions(limit, offset);
    }

    public Observable<PageableList<Question>> getQueryQuestions(int limit, int offset, String query) {
        return mApi.getQueryQuestions(limit, offset, query);
    }

    public Observable<LoginResponse> login(String email, String password) {
        return mApi.login(email, password);
    }

    public Observable<LoginResponse> signUp(String firstName, String lastName, String email, String password) {
        return mApi.signUp(firstName, lastName, email, password);
    }
    public interface Api {
        @GET("/api/v1/question?format=json")
        Observable<PageableList<Question>> getPublicQuestions(
                @Query("limit") int limit,
                @Query("offset") int offset);

        @GET("/api/v1/question?format=json")
        Observable<PageableList<Question>> getQueryQuestions(
                @Query("limit") int limit,
                @Query("offset") int offset, @Query("q") String query);

        @FormUrlEncoded
        @POST("/api/v1/auth/login/?format=json")
        Observable<LoginResponse> login(@Field("email") String email, @Field("password") String password);

        @FormUrlEncoded
        @POST("/api/v1/auth/signup/?format=json")
        Observable<LoginResponse> signUp(@Field("first_name") String first_name, @Field("last_name") String last_name, @Field("email") String email, @Field("password") String password);
    }
}
