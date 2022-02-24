package com.example.qiaoxi.network;

import java.util.Map;


import io.reactivex.Observable;
import retrofit2.http.*;

interface ServerApi {

    /**
     * 注册
     * @return
     */
    @POST("user/logon")
    Observable<Map<String, Object>> logonWithInfo(@Body LogonBean logonBean);

    /**
     * 登录
     * @return
     */
    @GET("/login")
    Observable<Map<String, Object>> loginWithPassword(@Query("userName") String userName, @Query("pwd") String password);

    /**
     * 获取好友列表
     */
    @GET("user/getContactList")
    Observable<Map<String, Object>> getContactList(@Query("userName") String userName);

}
