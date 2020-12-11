package com.example.century22.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.century22.bo.Property;
import com.example.century22.view.PropertyDetailActivity;

public class PropertyDetailActivityViewModel extends ViewModel{

    public MutableLiveData<Property> property = new MutableLiveData<>();

    public PropertyDetailActivityViewModel(SavedStateHandle savedStateHandle) {
        final Property propertyExtra = savedStateHandle.get(PropertyDetailActivity.PROPERTY_EXTRA);
        property.postValue(propertyExtra);
    }
}
