package com.example.century22.dao;

import androidx.room.Dao;
import androidx.room.Query;
import com.example.century22.bo.Agent;

import java.util.List;

@Dao
public interface AgentDao {

    @Query("SELECT * FROM Agent")
    List<Agent> getAllAgents();

}
