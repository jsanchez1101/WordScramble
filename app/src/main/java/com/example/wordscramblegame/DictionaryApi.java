package com.example.wordscramblegame;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DictionaryApi {
    @GET("{word}")
    Call<List<DictionaryResponse>> getWordDefinition(@Path("word") String word);
}