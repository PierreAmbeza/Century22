package com.example.century22.view;

import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
        implements OnClickListener
{

    //The tag used into this screen for the logs
    public static final String TAG = PropertiesActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    private PropertiesActivityViewModel viewModel;

    public static final String PROPERTIES_EXTRA = "propertiesExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //We first set up the layout linked to the activity
        setContentView(R.layout.properties_activity);

        recyclerView = findViewById(R.id.recyclerView_properties);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        viewModel = new ViewModelProvider(this).get(PropertiesActivityViewModel.class);
        //We configure the click on the fab
        findViewById(R.id.property_add_button).setOnClickListener(this);
        observeProperties();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.gmaps).setVisible(true);
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
        //We open the AddUserActivity screen when the user clicks on the FAB
        final Intent intent = new Intent(this, AddPropertyActivity.class);
        startActivity(intent);
    }

    private void observeProperties()
    {
        viewModel.properties.observe(this, properties -> {
            final PropertyAdapter propertyAdapter = new PropertyAdapter(properties);
            recyclerView.setAdapter(propertyAdapter);
        });
    }

}
