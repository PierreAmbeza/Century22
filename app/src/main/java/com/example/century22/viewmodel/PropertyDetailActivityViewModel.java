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

    /* Method to check if we can delete property or not */
    public void deleteProperty()
    {
        //If agent is the agent that added the property then it can be deleted
        if(AppPreferences.getAgentName(getApplication()).equals(propertyExtra.agent)){
            AppRepository.getInstance(getApplication()).deleteProperty(propertyExtra);
            event.postValue(Event.Ok);
        }
        //Else we post the value to inform that we cannot delete property
        else
        {
            event.postValue(Event.Ko);
        }
    }

    /* Method to refresh the attributes if property has been edited */
    public void loadProperty()
    {
        if(property.getValue() != null)
            property.postValue(AppRepository.getInstance(getApplication()).getProperty(property.getValue().id));
    }
}
