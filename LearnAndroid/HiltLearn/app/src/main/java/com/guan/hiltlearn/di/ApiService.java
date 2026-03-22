package com.guan.hiltlearn.di;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("search/")
    Call<String> request(@Query("q") String keyword);
}
