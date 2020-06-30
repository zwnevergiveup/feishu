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
    @Query("SELECT * FROM msgmodels")
    List<MsgModel> getAll();

    @Query("SELECT * FROM msgmodels WHERE id IN (:msgIds)")
    List<MsgModel> loadAllByIds(String[] msgIds);

    @Query("SELECT * FROM msgmodels WHERE (send_name LIKE :name OR receive_name LIKE :name) AND (send_name LIKE :currentName OR receive_name LIKE :currentName ) ")
    List<MsgModel> loadMsgByName(String name,String currentName);

    @Insert
    void insertAll(MsgModel... msgModels);

    @Update
    void update(MsgModel msgModel);

    @Delete
    void delete(MsgModel msgModel);
}
