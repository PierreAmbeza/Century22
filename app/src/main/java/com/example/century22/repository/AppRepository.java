package com.example.century22.repository;

import android.content.ContentValues;
import android.content.Context;

import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.century22.Database.AppDatabase;
import com.example.century22.bo.Agent;
import com.example.century22.bo.Property;
import com.example.century22.bo.Status;
import com.example.century22.bo.Type;

import java.util.List;

public class AppRepository {

    private static volatile AppRepository instance;

    private final String[][] agentsNames = {{"Bob", "Dufour"}, {"Denis", "McQuaid"}};

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

    //Create database
    private AppRepository(Context context)
    {
        RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
            public void onCreate (SupportSQLiteDatabase db) {

                ContentValues contentValues = new ContentValues();
                for(int i = 0; i < agentsNames.length; i++) {
                    contentValues.put("name", agentsNames[i][0]);
                    contentValues.put("lastname", agentsNames[i][1]);
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

    public void addAgent(Agent agent) { appdatabase.agentDao().addAgent(agent); }

    public Agent getAgentByName(String name, String lastname){ return appdatabase.agentDao().getAgentByName(name, lastname); }

    public void deleteAgents(){ appdatabase.agentDao().del(); }

    public List<Property> getProperties() { return appdatabase.propertyDao().getAllProperties(); }

    public void addProperty(Property property)
    {
        appdatabase.propertyDao().addProperty(property);
    }

    public void deleteProperties(){ appdatabase.propertyDao().del(); }

    public List<Type> getTypes() { return appdatabase.typeDAO().getAllTypes(); }

    public Type getType(int id){ return appdatabase.typeDAO().getType(id); }

    public Type getTypeByName(String type){ return appdatabase.typeDAO().getTypeByName(type);}

    public void addType(Type type) { appdatabase.typeDAO().addType(type); }

    public void deleteTypes(){ appdatabase.typeDAO().del(); }

    public List<Status> getStatus() { return appdatabase.statusDAO().getAllStatus(); }

    public Status getStatus(int id){ return appdatabase.statusDAO().getStatus(id); }

    public Status getStatusByName(String status){ return appdatabase.statusDAO().getStatusByName(status);}

    public void addStatus(Status status) { appdatabase.statusDAO().addStatus(status); }

    public void deleteStatus(){ appdatabase.statusDAO().del(); }
}
