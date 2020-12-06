package com.example.century22.bo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Type implements Serializable {

    //This class is used in order to sort a user
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Type")
    public final String type;

    public Type(@NonNull String type)
    {
        this.type = type;
    }
}
