package com.example.qiaoxi.helper.retrofit;

import java.util.HashMap;
import java.util.Map;


import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServerApi {

    /**
     * 注册
     * @return
     */
    @POST("user/logon")
    Observable<HashMap<String, Object>> logonWithInfo(@Query("userName") String userName, @Query("password") String password, @Query("phoneNumber") String phone);


}
