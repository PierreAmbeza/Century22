package com.example.century22.view;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.example.century22.R;
import com.example.century22.adapter.PropertyAdapter;
import com.example.century22.bo.Property;
import com.example.century22.repository.AppRepository;
import com.example.century22.viewmodel.GoogleMapsActivityViewModel;
import com.example.century22.viewmodel.PropertyDetailActivityViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    public static final String PROPERTIES_EXTRA = "propertiesExtra";

    private GoogleMapsActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_maps_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        viewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(GoogleMapsActivityViewModel.class);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        observeProperties();

    }

    private void addProperty(double latitude, double longitude, String status)
    {
        LatLng place = new LatLng(latitude, longitude);
        if(status.equals("Sold"))
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(0)).position(place));
        else
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(200)).position(place));
    }

    private void observeProperties()
    {
        viewModel.properties.observe(this, properties -> {
            for(int i = 0; i < properties.size(); i++)
            {
                addProperty(properties.get(i).latitude, properties.get(i).longitude, properties.get(i).status);
            }
        });
    }


}
