package com.example.century22.viewmodel;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public enum Event{
        Search, CannotSearch
    }

    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();

    public List<String> types = new ArrayList<>();

    public List<String> status = new ArrayList<>();

    public int[] rooms = new int[50];

    public PropertiesActivityViewModel(@NonNull Application application)
    {
        super(application);
        for(int i = 0; i < 50; i++)
        {
            rooms[i] = i+1;
        }
    }

    public void loadProperties()
    {
        properties.postValue(AppRepository.getInstance(getApplication()).getProperties());
    }

    public void addType(String type)
    {
        if(!types.contains(type))
            types.add(type);
    }

    public void addStatus(String status)
    {
        if(!types.contains(status))
            types.add(status);
    }

    public void searchProperties(int min_price, int max_price, int min_area, int max_area, int min_rooms, int max_rooms, String add_date, String edit_date)
    {
        boolean canSearch = checkSearchEntriesNumbers(min_price, max_price, min_area, max_area, min_rooms, max_rooms) && checkDateEntries(add_date, edit_date);
        if(canSearch)
        {

        }
        else{

        }
    }

    private boolean checkSearchEntriesNumbers(int min_price, int max_price, int min_area, int max_area, int min_rooms, int max_rooms)
    {
        return min_price < max_price && min_area < max_area && min_rooms < max_rooms;
    }

    private boolean checkDateEntries(String add_date, String edit_date)
    {
        Format f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if(f.parseObject(add_date) == add_date && f.parseObject(edit_date) == edit_date)
                return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

}
