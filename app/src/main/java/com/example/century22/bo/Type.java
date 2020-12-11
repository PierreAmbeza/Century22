package com.example.century22.bo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Type implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "Type")
    public final @NonNull String type;

    public Type(@NonNull String type)
    {
        this.type = type;
    }
}
