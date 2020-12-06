package com.example.century22.bo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Agent implements Serializable
{

    //This class is used in order to sort a user
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    public final String name;

    public final String lastname;


    public Agent(@NonNull String name, @NonNull String lastname)
    {
        this.name = name;
        this.lastname = lastname;
    }

 }