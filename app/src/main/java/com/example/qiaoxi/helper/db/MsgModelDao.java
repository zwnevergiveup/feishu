package com.example.qiaoxi.helper.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.qiaoxi.model.MsgModel;

import java.util.List;

@Dao
public interface MsgModelDao {
    @Query("SELECT * FROM msgmodel")
    List<MsgModel> getAll();

    @Query("SELECT * FROM msgmodel WHERE id IN (:msgIds)")
    List<MsgModel> loadAllByIds(String[] msgIds);

    @Query("SELECT * FROM msgmodel WHERE send_name LIKE :name ")
    List<MsgModel> loadMsgByName(String name);

    @Insert
    void insertAll(MsgModel... msgModels);

    @Update
    void update(MsgModel msgModel);

    @Delete
    void delete(MsgModel msgModel);
}
