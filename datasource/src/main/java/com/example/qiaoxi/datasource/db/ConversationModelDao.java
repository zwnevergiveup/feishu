package com.example.qiaoxi.datasource.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.qiaoxi.datasource.model.ConversationModel;

import java.util.List;

@Dao
public interface ConversationModelDao {
    @Query("SELECT * FROM conversations WHERE current_name LIKE :currentUser")
    List<ConversationModel> getCurrentUserConversations(String currentUser);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ConversationModel conversationModel);

    @Update
    void update(ConversationModel model);

    @Delete
    void delete(ConversationModel model);
}
