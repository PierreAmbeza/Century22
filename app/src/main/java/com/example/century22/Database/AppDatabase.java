package com.example.century22.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.example.century22.bo.Agent;
import com.example.century22.bo.Property;
import com.example.century22.bo.Status;
import com.example.century22.bo.Type;
import com.example.century22.converters.Converters;
import com.example.century22.dao.AgentDao;
import com.example.century22.dao.PropertyDAO;
import com.example.century22.dao.StatusDAO;
import com.example.century22.dao.TypeDAO;

@Database(entities = {Agent.class, Status.class, Type.class, Property.class}, version = 1)
//@TypeConverters({Converters.class})
public abstract class AppDatabase
        extends RoomDatabase
{
    public abstract AgentDao agentDao();
    public abstract PropertyDAO propertyDao();
    public abstract StatusDAO statusDAO();
    public abstract TypeDAO typeDAO();
}
