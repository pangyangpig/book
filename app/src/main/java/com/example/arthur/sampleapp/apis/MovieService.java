package com.example.arthur.sampleapp.apis;

import com.example.arthur.sampleapp.bean.Movies;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by arthur on 16/11/15.
 */
public interface MovieService {
    @GET("top250")
    Observable<Movies> getTopService(@Query("start") int start, @Query("count") int count);
}
