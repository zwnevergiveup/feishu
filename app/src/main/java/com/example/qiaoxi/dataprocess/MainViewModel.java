package com.example.qiaoxi.dataprocess;

import com.hyphenate.push.EMPushConfig;
import com.hyphenate.push.EMPushHelper;
import com.hyphenate.push.EMPushType;
import com.hyphenate.push.PushListener;

public final class MainViewModel extends BaseViewModel {
    public MainViewModel() {
        EMPushHelper.getInstance().setPushListener(new PushListener() {
            @Override
            public void onError(EMPushType emPushType, long l) {

            }

            @Override
            public boolean isSupportPush(EMPushType emPushType, EMPushConfig emPushConfig) {
                return super.isSupportPush(emPushType, emPushConfig);
            }
        });
    }
}
