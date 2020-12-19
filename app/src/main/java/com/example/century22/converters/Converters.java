package com.example.century22.converters;

import androidx.room.TypeConverter;

import java.util.Date;
//Converter to store Date type as Long in Property table
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}