package com.example.qiaoxi.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NetworkHelper {
    private volatile static NetworkHelper mInstance = null;
    private Gson gson = new Gson();

    private NetworkHelper() { }

    public static NetworkHelper getInstance() {
        if (mInstance == null) {
            synchronized (NetworkHelper.class) {
                if (mInstance == null) {
                    mInstance = new NetworkHelper();
                }
            }
        }
        return mInstance;
    }

    public void getContactList(String user, final NetworkCallBacker callBacker) {
        final ResponseModel model = new ResponseModel();
        RetrofitHelper.getInstance().getServerApi()
        .getContactList(user)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Observer<Map<String, Object>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Map<String, Object> stringObjectMap) {
                model.extraInfo = stringObjectMap.get("payload");
                Integer status = (Integer)gson.fromJson(gson.toJson(stringObjectMap.get("status")),new TypeToken<Integer>(){}.getType());
                model.status = status + "";
                callBacker.onSuccess(model);
            }

            @Override
            public void onError(Throwable e) {
                model.status = "-1";
                model.message = "网络错误";
                callBacker.onFail(model);
            }

            @Override
            public void onComplete() {

            }
        });
    };

    public void login(String userName, String pwd,  final NetworkCallBacker callBacker ) {
        final ResponseModel model = new ResponseModel();
        RetrofitHelper.getInstance().getServerApi()
                .loginWithPassword(userName, pwd)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Map<String, Object>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Map<String, Object> stringObjectMap) {
                        model.extraInfo = stringObjectMap.get("payload");
                        Integer status = (Integer)gson.fromJson(gson.toJson(stringObjectMap.get("status")),new TypeToken<Integer>(){}.getType());
                        model.status = status + "";
                        model.message = (String)stringObjectMap.get("message");
                        callBacker.onSuccess(model);
                    }

                    @Override
                    public void onError(Throwable e) {
                        model.status = "-1";
                        model.message = "网络错误";
                        callBacker.onFail(model);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
