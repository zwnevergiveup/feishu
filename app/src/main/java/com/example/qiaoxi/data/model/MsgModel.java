package com.example.qiaoxi.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.util.UUID;

@Entity(tableName = "msgModels")
public class MsgModel implements Parcelable {
    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "send_time")
    public Long sendTime;

    @ColumnInfo(name = "send_name")
    public String send;

    @ColumnInfo(name = "receive_name")
    public String receive;

    @ColumnInfo(name = "content")
    public String content;

    public MsgModel(){ }

    public MsgModel(EMMessage emMessage) {
        EMTextMessageBody body = (EMTextMessageBody) emMessage.getBody();
        content = body.getMessage();
        send = emMessage.getFrom();
        sendTime = emMessage.getMsgTime();
        receive = emMessage.getTo();
        id = UUID.randomUUID().toString().replace("-","").substring(0,12);
    }

    public MsgModel(Parcel in) {
        id = in.readString();
        sendTime = in.readLong();
        send = in.readString();
        receive = in.readString();
        content = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeLong(sendTime);
        dest.writeString(send);
        dest.writeString(receive);
        dest.writeString(content);
    }

    public static final Parcelable.Creator<MsgModel> CREATOR = new Creator<MsgModel>() {
        @Override
        public MsgModel createFromParcel(Parcel source) {
            return new MsgModel(source);
        }

        @Override
        public MsgModel[] newArray(int size) {
            return new MsgModel[size];
        }
    };
}
