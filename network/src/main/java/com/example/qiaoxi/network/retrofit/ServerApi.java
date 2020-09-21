package com.example.qiaoxi.network.retrofit;

import com.example.qiaoxi.network.model.LoginBean;
import com.example.qiaoxi.network.model.LogonBean;

import java.util.Map;


import io.reactivex.Observable;
import retrofit2.http.*;

public interface ServerApi {

    /**
     * 注册
     * @return
     */
    @POST("user/logon")
    Observable<Map<String, Object>> logonWithInfo(@Body LogonBean logonBean);

    /**
     * 登陆
     * @return
     */
    @POST("user/login")
    Observable<Map<String, Object>> loginWithPassword(@Body LoginBean loginBean);

    /**
     * 获取好友列表
     */
    @GET("user/getContactList")
    Observable<Map<String, Object>> getContactList(@Query("userName") String userName);

}
