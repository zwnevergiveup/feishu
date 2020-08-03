package com.example.qiaoxi.helper.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private volatile  static RetrofitHelper sInstance;
    private Retrofit mRetrofit;
    private ServerApi mServerApi;
    private RetrofitHelper() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.119:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mServerApi = mRetrofit.create(ServerApi.class);
    }
    public static RetrofitHelper getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitHelper.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitHelper();
                }
            }
        }
        return sInstance;
    }

    public ServerApi getServerApi() {
        return mServerApi;
    }
}
