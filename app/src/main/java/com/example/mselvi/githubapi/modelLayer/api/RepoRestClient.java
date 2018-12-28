package com.example.mselvi.githubapi.modelLayer.api;

import com.example.mselvi.githubapi.modelLayer.RepoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RepoRestClient {
    @GET("search/repositories?q=kotlin+pushed:%3E2018-12-16T13:29:00")
    Call<RepoResponse> getBloglar(@Query("api_key") String apiKey);

    @GET("search/repositories?q=language:java+language:kotlin+pushed:>2018-12-16T13:29:00")
    Call<RepoResponse> getKategoriler(@Query("api_key") String apiKey);

    @GET("search/repositories?q=language:kotlin")
    Call<RepoResponse> getLanguage(@Query("api_key") String apiKey);

    @GET("/search/repositories")
    Call<RepoResponse> getMappedLanguage (@Query(value="q",encoded = true)String q,@Query("page")String page);

}
