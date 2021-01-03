package com.example.century22.dao;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface TypeDAO {

    @Query("SELECT Type FROM Type")
    String[] getAllTypes();

}