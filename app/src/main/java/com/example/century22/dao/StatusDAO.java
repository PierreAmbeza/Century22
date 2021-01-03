package com.example.century22.dao;

import androidx.room.Dao;
import androidx.room.Query;
import java.util.List;

@Dao
public interface StatusDAO {

    @Query("SELECT Status FROM Status")
    String[] getAllStatus();

}
