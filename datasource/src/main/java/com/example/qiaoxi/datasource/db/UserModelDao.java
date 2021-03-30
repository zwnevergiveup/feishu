package com.example.qiaoxi.datasource.db;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.qiaoxi.datasource.model.UserModel;


@Dao
public interface UserModelDao {
    @Query("SELECT * FROM usermodels WHERE userId LIKE :currentName ")
    UserModel getCurrentUserModel(String currentName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserModel userModel);

    @Update
    void update(UserModel userModel);

    @Delete
    void delete(UserModel userModel);
}
