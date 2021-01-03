package com.example.century22.viewmodel;

import java.util.List;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.century22.bo.Agent;
import com.example.century22.repository.AppRepository;

public class AgentsActivityViewModel extends AndroidViewModel {

    public MutableLiveData<List<Agent>> agents = new MutableLiveData<>();

    public AgentsActivityViewModel(@NonNull Application application) {

        super(application);
    }

    public void loadAgents()
    {
        agents.postValue(AppRepository.getInstance(getApplication()).getAgents());
    }

}