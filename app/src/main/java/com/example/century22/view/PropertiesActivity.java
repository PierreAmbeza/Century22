package com.example.century22.view;

import java.io.Serializable;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.century22.R;
import com.example.century22.adapter.PropertyAdapter;
import com.example.century22.viewmodel.PropertiesActivityViewModel;

final public class PropertiesActivity
        extends MenuActivity
        implements OnClickListener, CompoundButton.OnCheckedChangeListener {

    //The tag used into this screen for the logs
    public static final String TAG = PropertiesActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    private PropertiesActivityViewModel viewModel;

    public static final String PROPERTIES_EXTRA = "propertiesExtra";

    //From here, all variables used for the search form

    private View search_view;

    private EditText min_rooms;

    private EditText max_rooms;

    private EditText min_price;

    private EditText max_price;

    private EditText min_area;

    private EditText max_area;

    private EditText max_add_date;

    private EditText max_edit_date;

    private EditText min_add_date;

    private EditText min_edit_date;

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
        min_price = findViewById(R.id.price_min);
        max_price = findViewById(R.id.price_max);
        min_area = findViewById(R.id.area_min);
        max_area = findViewById(R.id.area_max);
        max_add_date = findViewById(R.id.MaxAddDate);
        min_add_date = findViewById(R.id.MinAddDate);
        max_edit_date = findViewById(R.id.MaxEditDate);
        min_edit_date = findViewById(R.id.MinEditDate);

        min_rooms.setText("1");//minimum number of rooms
        max_rooms.setText("20");//maximum number of rooms
        min_price.setText("0");//minimum price
        max_price.setText("10000000");//maximum price
        min_area.setText("0");//minimum area
        max_area.setText("20000");//maximum area


        house = findViewById(R.id.houseBox);
        office = findViewById(R.id.officeBox);
        flat = findViewById(R.id.flatBox);
        sold = findViewById(R.id.soldBox);
        not_sold = findViewById(R.id.notSoldBox);

        //Checkboxes definition
        house.setOnCheckedChangeListener(this);
        office.setOnCheckedChangeListener(this);
        flat.setOnCheckedChangeListener(this);
        sold.setOnCheckedChangeListener(this);
        not_sold.setOnCheckedChangeListener(this);

        viewModel = new ViewModelProvider(this).get(PropertiesActivityViewModel.class);
        //We configure the click on the fab
        findViewById(R.id.property_add_button).setOnClickListener(this);
        findViewById(R.id.view_all).setOnClickListener(this);
        findViewById(R.id.search_button).setOnClickListener(this);

        observeProperties();
    }

    //Add gmaps and search options into the menu for this view
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
        else if(item.getItemId() == R.id.search)//Make the search layout visible and hide property recycler when click on search item
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

    //Handle click depending on the button

    @Override
    public void onClick(View v)
    {
        switch(v.getId()){
            case R.id.view_all://View all properties
                viewModel.loadProperties();
                //observeProperties();
                break;
            case R.id.property_add_button:
                //We open the AddPropertyActivity screen when the user clicks on the FAB
                final Intent intent = new Intent(this, AddPropertyActivity.class);
                startActivity(intent);
                break;
            case R.id.search_button://search properties with user entries
                final String _min_rooms = min_rooms.getText().toString();
                final String _max_rooms = max_rooms.getText().toString();

                final String _min_price = min_price.getText().toString();
                final String _max_price = max_price.getText().toString();

                final String _min_area = min_area.getText().toString();
                final String _max_area = max_area.getText().toString();

                final String _max_add_date = max_add_date.getText().toString();
                final String _min_add_date = min_add_date.getText().toString();
                final String _max_edit_date = max_edit_date.getText().toString();
                final String _min_edit_date = min_edit_date.getText().toString();

                viewModel.searchProperties(_min_price, _max_price, _min_area, _max_area, _min_rooms, _max_rooms, _min_add_date, _max_add_date,_min_edit_date, _max_edit_date);
                observeEvent();
                break;
        }
    }

    //Make recycler visible and hide search form when search has been done or display message on search layout if error
    private void observeEvent(){
        viewModel.event.observe(this, event -> {
            if(event == PropertiesActivityViewModel.Event.CannotSearch)
            {
                Toast.makeText(this, "Invalid search", Toast.LENGTH_SHORT).show();
            }
            else
            {
                search_view.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                findViewById(R.id.property_add_button).setVisibility(View.VISIBLE);
                resetSearch();
            }
        });
    }

    //Reset search form
    private void resetSearch()
    {
        min_add_date.setText(null);
        max_add_date.setText(null);
        min_edit_date.setText(null);
        max_edit_date.setText(null);
    }

    //Set recycler view adapter when properties have been loaded
    private void observeProperties() {
        viewModel.properties.observe(this, properties -> {
            final PropertyAdapter propertyAdapter = new PropertyAdapter(properties);
            recyclerView.setAdapter(propertyAdapter);
        });
    }

    //Checkboxes state handler
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        viewModel.checkBoxManager(buttonView.getId(), isChecked, buttonView.getText().toString());
    }
}
