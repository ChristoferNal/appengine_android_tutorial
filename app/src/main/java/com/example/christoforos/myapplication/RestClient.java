package com.example.christoforos.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by christoforos on 12/12/2016.
 */

public class RestClient {
    private static Api REST_CLIENT;
    private static String ROOT = Api.ROOT_URL;

    static {
        setupRestClient();
    }

    private RestClient() {}

    public static Api get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ROOT).build();
        REST_CLIENT = retrofit.create(Api.class);
    }
}
