package com.example.century22.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.century22.bo.Property;

import java.util.List;

@Dao
public interface PropertyDAO {

    @Query("SELECT * FROM Property")
    List<Property> getAllProperties();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProperty(Property property);

    @Delete
    void del(Property property);

}