package com.example.century22.viewmodel;

import java.util.List;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.century22.bo.Property;
import com.example.century22.repository.AppRepository;

public final class PropertiesActivityViewModel
        extends AndroidViewModel
{

    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();

    public PropertiesActivityViewModel(@NonNull Application application)
    {
        super(application);
    }

    public void loadProperties()
    {
        properties.postValue(AppRepository.getInstance(getApplication()).getProperties());
    }

}
