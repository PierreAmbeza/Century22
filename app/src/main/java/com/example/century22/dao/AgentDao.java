package com.example.century22.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.century22.bo.Agent;

import java.util.List;

@Dao
public interface AgentDao {

    @Query("SELECT * FROM Agent")
    List<Agent> getAllAgents();

    @Query("SELECT * FROM Agent WHERE name = :name ")
    Agent getAgentByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAgent(Agent agent);

    @Query("Delete FROM Agent")
    void del();

}
