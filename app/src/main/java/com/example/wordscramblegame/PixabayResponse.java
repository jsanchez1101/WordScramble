package com.example.wordscramblegame;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PixabayResponse {
    @SerializedName("total")
    private int total;

    @SerializedName("totalHits")
    private int totalHits;

    @SerializedName("hits")
    private List<Hit> hits;

    // Getters
    public int getTotal() {
        return total;
    }

    public int getTotalHits() {
        return totalHits;
    }

    public List<Hit> getHits() {
        return hits;
    }

    public class Hit {
        @SerializedName("id")
        private int id;

        @SerializedName("pageURL")
        private String pageURL;

        @SerializedName("type")
        private String type;

        @SerializedName("tags")
        private String tags;

        @SerializedName("previewURL")
        private String previewURL;

        @SerializedName("webformatURL")
        private String webformatURL;

        @SerializedName("largeImageURL")
        private String largeImageURL;

        // Getters
        public int getId() {
            return id;
        }

        public String getPageURL() {
            return pageURL;
        }

        public String getType() {
            return type;
        }

        public String getTags() {
            return tags;
        }

        public String getPreviewURL() {
            return previewURL;
        }

        public String getWebformatURL() {
            return webformatURL;
        }

        public String getLargeImageURL() {
            return largeImageURL;
        }
    }
}