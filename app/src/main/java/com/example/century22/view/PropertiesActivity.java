package com.example.century22.view;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.century22.R;
import com.example.century22.adapter.PropertyAdapter;
import com.example.century22.bo.Property;
import com.example.century22.viewmodel.PropertiesActivityViewModel;

final public class PropertiesActivity
        extends MenuActivity
        implements OnClickListener, CompoundButton.OnCheckedChangeListener {

    //The tag used into this screen for the logs
    public static final String TAG = PropertiesActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    private PropertiesActivityViewModel viewModel;

    public static final String PROPERTIES_EXTRA = "propertiesExtra";

    private View search_view;

    private EditText min_rooms;

    private EditText max_rooms;

    private EditText min_price;

    private EditText max_price;

    private EditText min_area;

    private EditText max_area;

    private EditText add_date;

    private EditText edit_date;

    private CheckBox house;

    private CheckBox office;

    private CheckBox flat;

    private CheckBox sold;

    private CheckBox not_sold;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //We first set up the layout linked to the activity
        setContentView(R.layout.properties_activity);

        recyclerView = findViewById(R.id.recyclerView_properties);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        search_view = findViewById(R.id.search_view);
        min_rooms = findViewById(R.id.rooms_min);
        max_rooms = findViewById(R.id.rooms_max);

        house = findViewById(R.id.houseBox);
        office = findViewById(R.id.officeBox);
        flat = findViewById(R.id.flatBox);
        sold = findViewById(R.id.soldBox);
        not_sold = findViewById(R.id.notSoldBox);

        house.setOnCheckedChangeListener(this);
        office.setOnCheckedChangeListener(this);
        flat.setOnCheckedChangeListener(this);
        sold.setOnCheckedChangeListener(this);
        not_sold.setOnCheckedChangeListener(this);

        viewModel = new ViewModelProvider(this).get(PropertiesActivityViewModel.class);
        //We configure the click on the fab
        findViewById(R.id.property_add_button).setOnClickListener(this);

        findViewById(R.id.search_button).setOnClickListener(this);

        observeProperties();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.gmaps).setVisible(true);
        menu.findItem(R.id.search).setVisible(true);
        return true;
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Handle the click on the maps icon to see all properties on google maps
        if(item.getItemId() == R.id.gmaps)
        {
            final Intent intent = new Intent(this, GoogleMapsActivity.class);
            intent.putExtra(PropertiesActivity.PROPERTIES_EXTRA, (Serializable) viewModel.properties.getValue());
            startActivity(intent);//We start the edit activity with the current property as an extra
        }
        else if(item.getItemId() == R.id.search)
        {
                recyclerView.setVisibility(View.INVISIBLE);
                search_view.setVisibility(View.VISIBLE);
                findViewById(R.id.property_add_button).setVisibility(View.INVISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //We init the list into the onResume method
        //so the list is updated each time the screen goes to foreground
        viewModel.loadProperties();
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId()){
            case R.id.property_add_button:
                //We open the AddUserActivity screen when the user clicks on the FAB
                final Intent intent = new Intent(this, AddPropertyActivity.class);
                startActivity(intent);
            case R.id.search_button:
                final int _min_rooms = Integer.parseInt(min_rooms.getText().toString());

                final int _max_rooms = Integer.parseInt(max_rooms.getText().toString());

                final int _min_price = Integer.parseInt(min_price.getText().toString());

                final int _max_price = Integer.parseInt(min_price.getText().toString());

                final int _min_area = Integer.parseInt(min_area.getText().toString());

                final int _max_area = Integer.parseInt(max_area.getText().toString());

                final String _last_add_date = add_date.getText().toString();

                final String _last_edit_date = edit_date.getText().toString();

                viewModel.searchProperties(_min_price, _max_price, _min_area, _max_area, _min_rooms, _max_rooms, _last_add_date, _last_edit_date);
        }
    }

    private void observeProperties() {
        viewModel.properties.observe(this, properties -> {
            final PropertyAdapter propertyAdapter = new PropertyAdapter(properties);
            recyclerView.setAdapter(propertyAdapter);
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked && (buttonView.getId() == R.id.houseBox || buttonView.getId() == R.id.officeBox || buttonView.getId() == R.id.flatBox)){
            viewModel.addType(buttonView.getText().toString());
        }
        else {
            viewModel.types.remove(buttonView.getText().toString());
        }
        if (isChecked && (buttonView.getId() == R.id.soldBox || buttonView.getId() == R.id.notSoldBox)){
            viewModel.addStatus(buttonView.getText().toString());
        }
        else {
            viewModel.status.remove(buttonView.getText().toString());
        }
    }
}
