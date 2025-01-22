package com.example.scrabblegameapp;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit randomWordRetrofit = null;
    private static Retrofit dictionaryRetrofit = null;

    // Random Words API Base URL
    public static Retrofit getRandomWordClient() {
        if (randomWordRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            randomWordRetrofit = new Retrofit.Builder()
                    .baseUrl("https://random-word-api.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return randomWordRetrofit;
    }

    // Dictionary API Base URL
    public static Retrofit getDictionaryClient() {
        if (dictionaryRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            dictionaryRetrofit = new Retrofit.Builder()
                    .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/en/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return dictionaryRetrofit;
    }
}