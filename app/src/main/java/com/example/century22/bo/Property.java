package com.example.century22.bo;

import android.location.Address;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Agent.class,
                parentColumns = "id",
                childColumns = "Agent"
        ),
        @ForeignKey(
                entity = Status.class,
                parentColumns = "id",
                childColumns = "Status"
        ),
        @ForeignKey(
                entity = Type.class,
                parentColumns = "id",
                childColumns = "Type"
        )
})
public class Property implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public final int price;

    public final int surface;

    public final int rooms;

    @ColumnInfo(name = "Type")
    public final int type;

    public final String description;

    public final String address;

    public final double latitute;

    public final double longitude;

    @ColumnInfo(name = "Status")
    public final int status;

    @ColumnInfo(name = "Agent")
    public final int agent;

    public final Date add_date;

    public final Date last_edit_date;


    public Property(@NonNull int price, @NonNull int surface, @NonNull int rooms, @NonNull int type, @NonNull String description, @NonNull String address, @NonNull double latitute, @NonNull double longitude, @NonNull int status, @NonNull int agent, @NonNull Date add_date, @NonNull Date last_edit_date) {
        this.price = price;
        this.surface = surface;
        this.rooms = rooms;
        this.type = type;
        this.description = description;
        this.address = address;
        this.latitute = latitute;
        this.longitude = longitude;
        this.status = status;
        this.agent = agent;
        this.add_date = add_date;
        this.last_edit_date = last_edit_date;
    }
}
