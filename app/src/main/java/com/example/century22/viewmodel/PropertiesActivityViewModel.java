package com.example.century22.viewmodel;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.century22.bo.Property;
import com.example.century22.repository.AppRepository;
import com.example.century22.view.PropertiesActivity;

public final class PropertiesActivityViewModel
        extends AndroidViewModel
{

    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();

    public List<String> types = new ArrayList<>();

    public List<String> status = new ArrayList<>();

    // Query string
    String queryString ;


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

    public void searchProperties(String min_price, String max_price, String min_area, String max_area, String min_rooms, String max_rooms, String add_date, String edit_date)
    {
        queryString = "SELECT * FROM Property ";
        Log.d(PropertiesActivity.TAG, max_price + " " + min_price);
        boolean canSearch = checkSearchEntriesNumbers(min_price, max_price, min_area, max_area, min_rooms, max_rooms) ;//&& checkDateEntries(add_date, edit_date);
        Log.d(PropertiesActivity.TAG, Boolean.toString(canSearch));
        if(canSearch)
        {
            minMaxPriceCheck(min_price, max_price);
            minMaxAreaCheck(min_area, max_area);
            minMaxRoomsCheck(min_rooms, max_rooms);
            queryString += ";";
            Log.d(PropertiesActivity.TAG, queryString);
            SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString);
            Log.d(PropertiesActivity.TAG, query.getSql());
            properties.postValue(AppRepository.getInstance(getApplication()).searchProperties(query));
        }
        else{
            queryString += ";";
            Log.d(PropertiesActivity.TAG, queryString);
            SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString);
            properties.postValue(AppRepository.getInstance(getApplication()).searchProperties(query));
        }
    }

    private void minMaxPriceCheck(String min_price, String max_price)
    {
        queryString += "WHERE CAST(price as int) BETWEEN " + min_price + " AND " + max_price;
    }

    private void minMaxAreaCheck(String min_area, String max_area)
    {
        queryString += " AND CAST(surface as int) BETWEEN " + min_area + " AND " + max_area;
    }

    private void minMaxRoomsCheck(String min_rooms, String max_rooms)
    {
        queryString += " AND CAST(rooms as int) BETWEEN " + min_rooms + " AND " + max_rooms;
    }

    private boolean checkSearchEntriesNumbers(String min_price, String max_price, String min_area, String max_area, String min_rooms, String max_rooms)
    {
        return TextUtils.isEmpty(min_price) == false && TextUtils.isEmpty(max_price) == false && TextUtils.isEmpty(min_area) == false
                && TextUtils.isEmpty(max_area) == false && TextUtils.isEmpty(min_rooms) == false
                && TextUtils.isEmpty(max_rooms) == false ;//&& Integer.parseInt(min_price) < Integer.parseInt(max_price) && Integer.parseInt(min_area) < Integer.parseInt(max_area) && Integer.parseInt(min_rooms) < Integer.parseInt(max_rooms);
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
