package com.example.century22.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.example.century22.bo.Property;
import com.example.century22.repository.AppRepository;
import com.example.century22.view.GoogleMapsActivity;
import com.example.century22.view.PropertyDetailActivity;

import java.util.List;

public class GoogleMapsActivityViewModel extends AndroidViewModel {

    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();

    private final List<Property> propertiesExtra;

    public GoogleMapsActivityViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);
        propertiesExtra = savedStateHandle.get(GoogleMapsActivity.PROPERTIES_EXTRA);
        properties.postValue(propertiesExtra);
    }
}
