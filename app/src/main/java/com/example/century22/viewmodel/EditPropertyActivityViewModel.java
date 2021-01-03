package com.example.century22.viewmodel;

import android.app.Application;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.example.century22.bo.Property;
import com.example.century22.preferences.AppPreferences;
import com.example.century22.repository.AppRepository;
import com.example.century22.view.EditPropertyActivity;
import com.example.century22.view.PropertyDetailActivity;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditPropertyActivityViewModel extends AndroidViewModel {

    public enum Event{
        Edited, Error, AddressNotFound
    }

    public MutableLiveData<Property> property = new MutableLiveData<>();

    public MutableLiveData<Event> event = new MutableLiveData<>();

    private final Property propertyExtra;

    public EditPropertyActivityViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);
        propertyExtra = savedStateHandle.get(EditPropertyActivity.PROPERTY_EXTRA);
        property.postValue(propertyExtra);
    }

    /* Check if the property can be edited or not */
    public void editProperty(String price, String surface, String rooms, String type, String description, String address) {

        //We check if all entries are valid (not null and not empty)
        boolean canAddProperty = checkFormEntries(price, surface, rooms, description, address);
        if (canAddProperty) {
            //We add the property to the list and we reset the form
            updateProperty(price, surface, rooms, type, description, address);
        } else {
            //we display a log error and a Toast
            event.postValue(Event.Error);
        }
    }

    /* Update the property */
    private void updateProperty(String price, String surface, String rooms, String status, String description, String address) {
        Geocoder geocoder = new Geocoder(getApplication());
        List<Address> l;
        try {
            l = geocoder.getFromLocationName(address, 1);
            if(l.size() != 0) {
                double latitude = l.get(0).getLatitude();
                double longitude = l.get(0).getLongitude();
                Format f = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = f.format(new Date());
                AppRepository.getInstance(getApplication()).updateProperty(price, rooms, address, surface, strDate,
                        description, latitude, longitude, status, property.getValue().id);
                event.postValue(Event.Edited);
            }
            else{
                event.postValue(Event.AddressNotFound);
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    private boolean checkFormEntries(String price, String surface, String rooms, String description, String address) {
        return TextUtils.isEmpty(price) == false && TextUtils.isEmpty(surface) == false && TextUtils.isEmpty(rooms) == false
                && TextUtils.isEmpty(description) == false
                && TextUtils.isEmpty(address) == false;
    }


    public String[] getStatus(){
        return AppRepository.getInstance(getApplication()).getStatus();
    }

    /* Get index of current status of the property in the spinner*/
    public int getIndexFromStatus(String s){
        String [] status = AppRepository.getInstance(getApplication()).getStatus();
        for(int i = 0; i < status.length; i++)
        {
            if(status[i].equals(s))
                return i;
        }
        return -1;
    }
}
