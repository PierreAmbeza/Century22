package com.example.century22.repository;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.century22.Database.AppDatabase;
import com.example.century22.bo.Agent;
import com.example.century22.bo.Property;
import com.example.century22.bo.Status;
import com.example.century22.bo.Type;

import java.util.Date;
import java.util.List;

public class AppRepository {

    private static volatile AppRepository instance;

    private final String[] agentsNames = {"Bob Dufour", "Denis McQuaid"};

    private final String[] status = { "Not sold", "Sold"};

    private final String[] types = { "Office", "Flat", "House"};


    // We accept the "out-of-order writes" case
    public static AppRepository getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (AppRepository.class)
            {
                if (instance == null)
                {
                    instance = new AppRepository(context);
                }
            }
        }

        return instance;
    }

    private final AppDatabase appdatabase;

    //Create database with embedded values in Agent, Status and Type tables
    private AppRepository(Context context)
    {
        RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
            public void onCreate (SupportSQLiteDatabase db) {

                ContentValues contentValues = new ContentValues();
                for(int i = 0; i < agentsNames.length; i++) {
                    contentValues.put("Name", agentsNames[i]);
                    db.insert("Agent", OnConflictStrategy.REPLACE, contentValues);
                }
                ContentValues cv = new ContentValues();
                for(int i = 0; i < status.length; i++) {
                    cv.put("Status", status[i]);
                    db.insert("Status", OnConflictStrategy.REPLACE, cv);
                }
                ContentValues cv2 = new ContentValues();
                for(int i = 0; i < types.length; i++) {
                    cv2.put("Type", types[i]);
                    db.insert("Type", OnConflictStrategy.REPLACE, cv2);
                }
            }
        };
        appdatabase = Room.databaseBuilder(context, AppDatabase.class, "app-database").addCallback(rdc).allowMainThreadQueries().build();
        //context.deleteDatabase("app-database");
    }



    public List<Agent> getAgents() { return appdatabase.agentDao().getAllAgents(); }

    public List<Property> getProperties() { return appdatabase.propertyDao().getAllProperties(); }

    public void addProperty(Property property)
    {
        appdatabase.propertyDao().addProperty(property);
    }

    public void updateProperty(String price, String rooms, String surface, String address, String edit, String description,
                               double latitude, double longitude, String status, int id)
    {
        appdatabase.propertyDao().updateProperty(price,rooms, address, surface, edit, description, latitude, longitude, status, id);
    }

    public List<Property> searchProperties(SimpleSQLiteQuery query){
        return appdatabase.propertyDao().searchProperties(query);
    }

    public Property getProperty(int id){ return appdatabase.propertyDao().getProperty(id);}

    public void deleteProperty(Property property){ appdatabase.propertyDao().del(property); }

    public String[] getTypes() { return appdatabase.typeDAO().getAllTypes(); }

    public String[] getStatus() { return appdatabase.statusDAO().getAllStatus(); }

}
