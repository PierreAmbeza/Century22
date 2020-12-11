package com.example.century22.bo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Status implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "Status")
    public final @NonNull String status;

    public Status(@NonNull String status)
    {
        this.status = status;
    }

}
