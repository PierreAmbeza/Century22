package com.example.century22.bo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Status implements Serializable {

    //This class is used in order to sort a user
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Status")
    public final String status;

    public Status(@NonNull String status)
    {
        this.status = status;
    }

}
