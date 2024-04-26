package com.example.foodorderapp.retrofit;

public class SearchRequest {
    private String keyword;

    public SearchRequest(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
