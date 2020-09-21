package com.example.qiaoxi.dataprocess;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.qiaoxi.data.model.ContactModel;
import com.example.qiaoxi.helper.json.JsonHelper;
import com.example.qiaoxi.widget.QXApplication;
import com.google.gson.reflect.TypeToken;
import com.example.qiaoxi.network.retrofit.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactViewModel extends BaseViewModel {

    public MutableLiveData<List<ContactModel>> mContactList;

    public ContactViewModel() {
        mContactList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());
        getContactList();
    }

    public void getContactList() {
        RetrofitHelper.getInstance().getServerApi()
            .getContactList(QXApplication.currentUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<Map<String, Object>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Map<String, Object> map) {
                    if ( (Integer) JsonHelper.getInstance().getObject(JsonHelper.getInstance().toJson(map.get("status")),new TypeToken<Integer>(){}.getType()) == 200) {
                        List<ContactModel> contactModels = JsonHelper.getInstance().getObject(JsonHelper.getInstance().toJson(map.get("payload")),new TypeToken<List<ContactModel>>(){}.getType());
                        mContactList.setValue(contactModels);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("qiaoxi",e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
    }

}