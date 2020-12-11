package com.example.century22.bo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Agent implements Serializable
{

    @PrimaryKey
    @ColumnInfo(name = "Name")
    public final @NonNull String name;


    public Agent(@NonNull String name)
    {
        this.name = name;
    }

 }