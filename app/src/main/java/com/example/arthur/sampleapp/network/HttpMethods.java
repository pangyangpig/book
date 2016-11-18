package com.example.arthur.sampleapp.network;

import android.util.Log;

import com.example.arthur.sampleapp.BuildConfig;
import com.example.arthur.sampleapp.bean.Movies;
import com.example.arthur.sampleapp.apis.MovieService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by arthur on 16/11/15.
 */
public class HttpMethods {


    public static void getMovies(ProgressDialogSubscriber<Movies> subscriber, int start, int count){
        WrapperRetrofit.doSubscribe(WrapperRetrofit.getInstantce().retrofit.create(MovieService.class)
                .getTopService(start, count), subscriber);

    }

    static class WrapperRetrofit{
        static final String BASE_URL = "https://api.douban.com/v2/movie/";

        Retrofit retrofit;

        private WrapperRetrofit(){
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.connectTimeout(5, TimeUnit.SECONDS);

            if (BuildConfig.LOG_DEBUG){
                clientBuilder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.d("<<<<<<<<request",request.toString());

                        Response response = chain.proceed(request);

                        Log.d("response>>>>>>>", response.toString());

                        return response;
                    }
                });

            }

            retrofit = new Retrofit.Builder().client(clientBuilder.build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }

        private static class SingletonHolder{
            private static final HttpMethods Instance = new HttpMethods();
        }

        private static HttpMethods getInstantce(){
            return SingletonHolder.Instance;
        }

        private static <T> void doSubscribe(Observable<T> observable, Subscriber<T> subscriber){
            observable.subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(subscriber);
        }

        static <T> Observable<T> createService(T t){
            return getInstantce().re
        }
    }
}
