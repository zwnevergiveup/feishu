package com.example.qiaoxi.dataprocess;

import androidx.lifecycle.MutableLiveData;

import com.example.qiaoxi.datasource.ContactModel;
import com.example.qiaoxi.datasource.DataSourceHelper;
import com.example.qiaoxi.helper.json.JsonHelper;
import com.example.qiaoxi.network.NetworkCallBacker;
import com.example.qiaoxi.network.NetworkHelper;
import com.example.qiaoxi.network.ResponseModel;
import com.example.qiaoxi.widget.QXApplication;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ContactViewModel extends BaseViewModel {

    public MutableLiveData<List<ContactModel>> mContactList;

    public ContactViewModel() {
        mContactList = new MutableLiveData<>();
        mContactList.setValue(new ArrayList<>());
        getContactList();
    }

    public void getContactList() {
        NetworkHelper.getInstance().getContactList(DataSourceHelper.getInstance().getCurrentUserName(), new NetworkCallBacker() {
            @Override
            public void onSuccess(ResponseModel model) {
                if (model.status.equals("200")) {
                    List<ContactModel> contactModels = JsonHelper.getInstance().getObject(JsonHelper.getInstance().toJson(model.extraInfo),new TypeToken<List<ContactModel>>(){}.getType());
                    mContactList.setValue(contactModels);
                }
            }

            @Override
            public void onFail(ResponseModel model) {

            }
        });

    }

}