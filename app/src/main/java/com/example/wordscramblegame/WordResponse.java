package com.example.wordscramblegame;

import com.google.gson.annotations.SerializedName;

public class WordResponse {
    @SerializedName("word")
    private String word;

    @SerializedName("hasDictionaryDef")
    private boolean hasDictionaryDef;

    public String getWord() {
        return word;
    }

    public boolean isHasDictionaryDef() {
        return hasDictionaryDef;
    }
}