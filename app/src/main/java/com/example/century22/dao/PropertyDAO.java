package com.example.century22.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.century22.bo.Property;

import java.util.Date;
import java.util.List;

@Dao
public interface PropertyDAO {

    @Query("SELECT * FROM Property")
    List<Property> getAllProperties();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addProperty(Property property);

    @Query("UPDATE Property SET price = :price , rooms = :rooms, address = :address, " +
            "last_edit_date = :edit, latitude = :latitude, longitude = :longitude, surface = :surface, " +
            "status = :status , description = :description WHERE id = :id")
    void updateProperty(String price, String rooms,String surface, String address, String edit, String description,
                        double latitude, double longitude, String status, int id);

    @Query("SELECT * FROM Property WHERE id = :id")
    Property getProperty(int id);

    @Delete
    void del(Property property);

}