package com.example.century22.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.century22.bo.Status;

import java.util.List;

@Dao
public interface StatusDAO {

    @Query("SELECT * FROM Status")
    List<Status> getAllStatus();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addStatus(Status status);

    @Query("SELECT * FROM Status WHERE id = :id")
    Status getStatus(int id);

    @Query("SELECT * FROM Status WHERE status = :status")
    Status getStatusByName(String status);

    @Query("Delete FROM Status")
    void del();

}
