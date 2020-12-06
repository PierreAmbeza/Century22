package com.example.century22.viewmodel;

import java.util.List;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.century22.bo.Agent;
import com.example.century22.bo.Property;
import com.example.century22.bo.Status;
import com.example.century22.bo.Type;
import com.example.century22.repository.AppRepository;

public class AgentsActivityViewModel extends AndroidViewModel {

    public AgentsActivityViewModel(@NonNull Application application) {

        super(application);
    }

    public List<Agent> loadAgents()
    {
        return AppRepository.getInstance(getApplication()).getAgents();
    }

}