package com.example.century22.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.century22.bo.Status;
import com.example.century22.bo.Type;

import java.util.List;

@Dao
public interface TypeDAO {

    @Query("SELECT * FROM Type")
    List<Type> getAllTypes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addType(Type type);

    @Query("SELECT * FROM Type WHERE type = :type")
    Type getTypeByName(String type);

    @Query("Delete FROM Type")
    void del();

}