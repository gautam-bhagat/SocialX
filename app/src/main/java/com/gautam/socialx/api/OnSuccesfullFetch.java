package com.gautam.socialx.api;

import com.gautam.socialx.model.Articles;

import java.util.List;

public interface OnSuccesfullFetch<NewsResponse> {
    void fetchData(List<Articles> articles,String message);
    void onError(String message);
}
