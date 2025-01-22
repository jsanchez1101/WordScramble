package com.example.wordscramblegame;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PixabayApi {
    @GET("/")
    Call<PixabayResponse> getImages(
            @Query("key") String apiKey,
            @Query("q") String query,
            @Query("image_type") String imageType,
            @Query("per_page") int perPage
    );
}