package com.example.century22.viewmodel;

import android.app.Application;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.century22.bo.Property;
import com.example.century22.preferences.AppPreferences;
import com.example.century22.repository.AppRepository;
import com.example.century22.view.AddPropertyActivity;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class AddPropertyActivityViewModel
        extends AndroidViewModel {

    public enum Event {
        ResetForm, DisplayError
    }

    private final String[] types = {"Office", "Flat", "House"};

    public MutableLiveData<Event> event = new MutableLiveData<>();

    public AddPropertyActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public void saveProperty(String price, String surface, String rooms, String type, String description, String address) {
        //We display the properties into the logcat
        displayUserEntries(price, surface, rooms, type, description, address);

        //We check if all entries are valid (not null and not empty)
        boolean canAddProperty = checkFormEntries(price, surface, rooms, type, description, address) && checkIfExists(address);
        if (canAddProperty) {
            //We add the property to the list and we reset the form
            persistProperty(price, surface, rooms, type, description, address);
            event.postValue(Event.ResetForm);
        } else {
            //we display a log error and a Toast
            event.postValue(Event.DisplayError);
        }
    }

    private void persistProperty(String price, String surface, String rooms, String type, String description, String address) {
        Geocoder geocoder = new Geocoder(getApplication());
        List<Address> l;
        String name = AppPreferences.getAgentName(getApplication());
        Log.d(AddPropertyActivityViewModel.class.getSimpleName(), name);
        try {
            l = geocoder.getFromLocationName(address, 1);
            double latitude = l.get(0).getLatitude();
            double longitude = l.get(0).getLongitude();
            Date d = Calendar.getInstance().getTime();
            AppRepository.getInstance(getApplication()).addProperty(new Property(price, surface,
                    rooms, type, description, address, latitude, longitude, "Not sold", name, d, d));
        } catch (IOException e) {
            event.postValue(Event.DisplayError);
            //e.printStackTrace();
        }
    }

    private boolean checkFormEntries(String price, String surface, String rooms, String type, String description, String address) {
        return TextUtils.isEmpty(price) == false && TextUtils.isEmpty(surface) == false && TextUtils.isEmpty(rooms) == false
                && TextUtils.isEmpty(type) == false && TextUtils.isEmpty(description) == false
                && TextUtils.isEmpty(address) == false;
    }

    private void displayUserEntries(String price, String surface, String rooms, String type, String description, String address) {
        Log.d(AddPropertyActivity.TAG, "price : '" + price + "'");
        Log.d(AddPropertyActivity.TAG, "surface : '" + surface + "'");
        Log.d(AddPropertyActivity.TAG, "rooms : '" + rooms + "'");
        Log.d(AddPropertyActivity.TAG, "type : '" + type + "'");
        Log.d(AddPropertyActivity.TAG, "description : '" + description + "'");
        Log.d(AddPropertyActivity.TAG, "address : '" + address + "'");

    }

    private boolean checkIfExists(String address) {
        List<Property> properties = AppRepository.getInstance(getApplication()).getProperties();
        for (int i = 0; i < properties.size(); i++) {
            if (properties.get(i).address.equalsIgnoreCase(address))
                return false;
        }
        return true;
    }

    public String[] getTypes() {
        return this.types;
    }

}