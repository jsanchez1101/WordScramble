package com.example.wordscramblegame;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WordApi {
    @GET("word")
    Call<List<String>> getRandomWord(
            @Query("number") int number
    );
}