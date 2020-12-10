package com.example.century22.viewmodel;

import android.app.Application;
import android.location.Address;
import android.location.Geocoder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.century22.bo.Agent;
import com.example.century22.bo.Property;
import com.example.century22.preferences.AppPreferences;
import com.example.century22.repository.AppRepository;
import com.example.century22.view.AddPropertyActivity;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class AddPropertyActivityViewModel
        extends AndroidViewModel
{

    public enum Event
    {
        ResetForm, DisplayError
    }

    public MutableLiveData<Event> event = new MutableLiveData<>();

    public AddPropertyActivityViewModel(@NonNull Application application)
    {
        super(application);
    }

    public void saveProperty(int price, int surface, int rooms, int type, String description, String address)
    {
        //We display the properties into the logcat
        displayUserEntries(price, surface, rooms, type, description, address);

        //We check if all entries are valid (not null and not empty)
        boolean canAddProperty = true ;//checkFormEntries(price, surface, rooms, type, description, address);
                //|| checkIfExists(address);
        if (canAddProperty == true)
        {
            //We add the property to the list and we reset the form
            persistUser(price, surface, rooms, type, description, address);
            event.postValue(Event.ResetForm);
        }
        else
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Cannot add the property");
            event.postValue(Event.DisplayError);
        }
    }

    private void persistUser(int price, int surface, int rooms, int type, String description, String address)
    {
        Geocoder geocoder = new Geocoder(getApplication());
        List<Address> l ;
        String name = AppPreferences.getAgentName(getApplication());
        String lastname = AppPreferences.getAgentLastName(getApplication());
        Log.d(AddPropertyActivityViewModel.class.getSimpleName(), name + " "+ lastname);
        try {
            l = geocoder.getFromLocationName(address, 1);
            Double latitude = l.get(0).getLatitude();
            Double longitude = l.get(0).getLongitude();
            Date d = Calendar.getInstance().getTime();
            Agent agent = AppRepository.getInstance(getApplication()).getAgentByName(name, lastname);
            Toast.makeText(getApplication(), agent.name+agent.lastname, Toast.LENGTH_SHORT);
            AppRepository.getInstance(getApplication()).addProperty(new Property(price, surface,
                    rooms, type, description, address, latitude, longitude, 1, agent.id, d, d));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkFormEntries(int price, int surface, int rooms, int type, String description, String address)
    {
        String _price = Integer.toString(price);
        String _surface = Integer.toString(surface);
        String _rooms = Integer.toString(rooms);
        String _type = Integer.toString(type);
        return TextUtils.isEmpty(_price) && TextUtils.isEmpty(_surface) == false  && TextUtils.isEmpty(_rooms) == false
                && TextUtils.isEmpty(_type) == false && TextUtils.isEmpty(description) == false
                && TextUtils.isEmpty(address) == false;
    }

    private void displayUserEntries(int price, int surface, int rooms, int type, String description, String address)
    {
        Log.d(AddPropertyActivity.TAG, "price : '" + price + "'");
        Log.d(AddPropertyActivity.TAG, "surface : '" + surface + "'");
        Log.d(AddPropertyActivity.TAG, "rooms : '" + rooms + "'");
        Log.d(AddPropertyActivity.TAG, "type : '" + type + "'");
        Log.d(AddPropertyActivity.TAG, "description : '" + description + "'");
        Log.d(AddPropertyActivity.TAG, "address : '" + address + "'");

    }

    private boolean checkIfExists(String address)
    {
        List<Property> properties = AppRepository.getInstance(getApplication()).getProperties();
        for(int i = 0; i < properties.size(); i++)
        {
            if(properties.get(i).address.equalsIgnoreCase(address))
                return false;
        }
        return true;
    }

}