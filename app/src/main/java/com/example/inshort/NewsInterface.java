package com.example.inshort;

import com.example.inshort.dtos.NewsApiRespDto;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsInterface {

    @GET("top-headlines?category=general&apiKey=e1d2194d001540cd903f61c8f8966390&pageSize=10&country=IN")
    Call<NewsApiRespDto> getNewsList();
}
