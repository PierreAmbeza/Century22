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
                parentColumns = "Name",
                childColumns = "Agent"
        ),
        @ForeignKey(
                entity = Status.class,
                parentColumns = "Status",
                childColumns = "Status"
        ),
        @ForeignKey(
                entity = Type.class,
                parentColumns = "Type",
                childColumns = "Type"
        )
})
public class Property implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public final String price;

    public final String surface;

    public final String rooms;

    @ColumnInfo(name = "Type")
    public final String type;

    public final String description;

    public final String address;

    public final double latitute;

    public final double longitude;

    @ColumnInfo(name = "Status")
    public final String status;

    @ColumnInfo(name = "Agent")
    public final String agent;

    public final Date add_date;

    public final Date last_edit_date;


    public Property(@NonNull String price, @NonNull String surface, @NonNull String rooms, @NonNull String type, @NonNull String description, @NonNull String address, double latitute, double longitude, @NonNull String status, @NonNull String agent, @NonNull Date add_date, @NonNull Date last_edit_date) {
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
