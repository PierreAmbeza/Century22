package com.example.century22.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModel;

import com.example.century22.bo.Property;
import com.example.century22.preferences.AppPreferences;
import com.example.century22.repository.AppRepository;
import com.example.century22.view.PropertyDetailActivity;

import java.util.Objects;

public class PropertyDetailActivityViewModel extends AndroidViewModel{

    public enum Event{
        Ok, Ko
    }

    public MutableLiveData<Event> event = new MutableLiveData<>();

    public MutableLiveData<Property> property = new MutableLiveData<>();

    private final Property propertyExtra;


    public PropertyDetailActivityViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);
        propertyExtra = savedStateHandle.get(PropertyDetailActivity.PROPERTY_EXTRA);
        property.postValue(propertyExtra);
    }

    public void deleteProperty()
    {
        if(AppPreferences.getAgentName(getApplication()).equals(propertyExtra.agent))
        {
            AppRepository.getInstance(getApplication()).deleteProperty(propertyExtra);
            event.postValue(Event.Ok);
        }
        else
        {
            event.postValue(Event.Ko);
        }
    }
}
