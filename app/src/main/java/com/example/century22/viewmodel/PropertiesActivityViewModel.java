package com.example.century22.viewmodel;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.example.century22.R;
import com.example.century22.bo.Property;
import com.example.century22.repository.AppRepository;
import com.example.century22.view.PropertiesActivity;

public final class PropertiesActivityViewModel
        extends AndroidViewModel
{

    public enum Event{
        Search, CannotSearch
    }

    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();

    public MutableLiveData<Event> event = new MutableLiveData<>();

    public List<String> types = new ArrayList<>();

    public List<String> status = new ArrayList<>();

    // Query string
    String queryString ;


    public PropertiesActivityViewModel(@NonNull Application application)
    {
        super(application);
    }

    public void loadProperties()
    {
        properties.postValue(AppRepository.getInstance(getApplication()).getProperties());
        event.postValue(Event.Search);
    }

    //Adding type or status to corresponding array when checkbox is checked or remove when unchecked
    public void checkBoxManager(int id, boolean isChecked, String value)
    {
        if (isChecked && (id == R.id.houseBox || id == R.id.officeBox || id == R.id.flatBox)) {
            if (!types.contains(value))
                types.add(value);
        }
        else if (!isChecked && (id == R.id.houseBox || id == R.id.officeBox || id == R.id.flatBox)) {
            types.remove(value);
        }
        if (isChecked && (id == R.id.soldBox || id == R.id.notSoldBox)) {
            if (!status.contains(value))
                status.add(value);
        }
        else if(!isChecked && (id == R.id.soldBox || id == R.id.notSoldBox)) {
            status.remove(value);
        }
    }

    public void searchProperties(String min_price, String max_price, String min_area, String max_area, String min_rooms, String max_rooms, String min_add_date, String max_add_date, String min_edit_date, String max_edit_date)
    {
        queryString = "SELECT * FROM Property ";
        if(max_add_date.isEmpty())
            max_add_date = "1970-01-01";//default date for searching into db
        if(max_edit_date.isEmpty())
            max_edit_date = max_add_date;//default date for searching into db
        boolean canSearch = checkSearchEntriesNumbers(min_price, max_price, min_area, max_area, min_rooms, max_rooms);
        if(canSearch)
        {
            minMaxPriceCheck(min_price, max_price);
            minMaxAreaCheck(min_area, max_area);
            minMaxRoomsCheck(min_rooms, max_rooms);
            if(types.size() != 0)
                typesFilter();
            if(status.size() != 0)
                statusFilter();
            addDateFilter(min_add_date, max_add_date);
            editDateFilter(min_edit_date, max_edit_date);
            queryString += ";";
            Log.d(PropertiesActivity.TAG, queryString);
            SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString);
            Log.d(PropertiesActivity.TAG, query.getSql());
            properties.postValue(AppRepository.getInstance(getApplication()).searchProperties(query));
            event.postValue(Event.Search);

        }
        else{
            queryString += ";";
            Log.d(PropertiesActivity.TAG, queryString);
            event.postValue(Event.CannotSearch);
        }
    }

    /* Adding string to the querystring for filter search */

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

    private void typesFilter()
    {
        String _types = "";
        for(int i = 0; i < types.size(); i++)
        {
            _types += "'" + types.get(i) + "'";
            if(i != types.size() - 1)
                _types += ",";
        }
        queryString += " AND Type IN (" + _types + ")";
    }

    private void addDateFilter(String min_add_date, String max_add_date)
    {
        Pattern date_pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");//check input format from user
        if(date_pattern.matcher(min_add_date).matches())
        {
            queryString += " AND add_date BETWEEN " + "'"+ max_add_date + "'" + " AND " + "'" + min_add_date + "'";
        }
        else if(min_add_date.isEmpty())
        {
            queryString += " AND add_date >= " + "'" + max_add_date + "'";
        }
    }

    private void editDateFilter(String min_edit_date, String max_edit_date)
    {
        Pattern date_pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");//check input format from user
        if(date_pattern.matcher(min_edit_date).matches())
        {
            queryString += " AND last_edit_date BETWEEN " + "'"+ max_edit_date + "'" + " AND " + "'" + min_edit_date + "'";
        }
        else if(min_edit_date.isEmpty())
        {
            queryString += " AND last_edit_date >= " + "'" + max_edit_date + "'";
        }
    }

    private void statusFilter()
    {
        String _status = "";
        for(int i = 0; i < status.size(); i++)
        {
            _status += "'" + status.get(i) + "'";
            if(i != status.size() - 1)
                _status += ",";
        }
        queryString += " AND Status IN (" + _status + ")";
    }

    private boolean checkSearchEntriesNumbers(String min_price, String max_price, String min_area, String max_area, String min_rooms, String max_rooms)
    {
        return TextUtils.isEmpty(min_price) == false && TextUtils.isEmpty(max_price) == false && TextUtils.isEmpty(min_area) == false
                && TextUtils.isEmpty(max_area) == false && TextUtils.isEmpty(min_rooms) == false
                && TextUtils.isEmpty(max_rooms) == false && Integer.parseInt(min_price) < Integer.parseInt(max_price) && Integer.parseInt(min_area) < Integer.parseInt(max_area) && Integer.parseInt(min_rooms) < Integer.parseInt(max_rooms);
    }

}
