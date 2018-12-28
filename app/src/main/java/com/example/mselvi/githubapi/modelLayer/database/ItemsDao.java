package com.example.mselvi.githubapi.modelLayer.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ItemsDao {

    @Query("SELECT * FROM itemsdto")
    LiveData<List<ItemsDto>> getAll();

    @Insert
    void insert(ItemsDto itemsdto);

    @Delete
    void delete(ItemsDto itemsdto);

    @Update
    void update(ItemsDto itemsdto);

    @Query("SELECT * FROM itemsdto where repoTitle = :title")
    ItemsDto getTitle (String title);

    @Query("SELECT COUNT(*) FROM itemsdto where repoId = :repoId")
    int getCount(Integer repoId);

    @Query("DELETE FROM itemsdto WHERE repoId = :repoId")
    int deleteRow(Integer repoId);
}
