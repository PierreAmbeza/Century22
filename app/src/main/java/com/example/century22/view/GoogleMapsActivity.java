package com.example.century22.view;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.example.century22.R;
import com.example.century22.viewmodel.GoogleMapsActivityViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    private static final String TAG = GoogleMapsActivity.class.getSimpleName();

    public static final String PROPERTIES_EXTRA = "propertiesExtra";

    private GoogleMapsActivityViewModel viewModel;

    private List<Marker> markers = new ArrayList<Marker>();

    private static final int PERMISSION_REQUEST_CODE = 1000;

    private FusedLocationProviderClient client;

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Log.d(PropertyDetailActivity.TAG, "New position found");

            final Location location = locationResult.getLastLocation();
            LatLng place = new LatLng(location.getLatitude(), location.getLongitude());
            Log.d(TAG, "Latitude" + location.getLatitude());
            mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(50)).position(place));
        }

        @Override
        public void onLocationAvailability(LocationAvailability locationAvailability)
        {
            Log.d(GoogleMapsActivity.TAG, locationAvailability.isLocationAvailable() + "?");
            super.onLocationAvailability(locationAvailability);
        }
    };

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        Log.d(TAG, String.valueOf(requestCode));
        if (requestCode == GoogleMapsActivity.PERMISSION_REQUEST_CODE)
        {
            Log.d(GoogleMapsActivity.TAG, "Checking runtime permission result");

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.d(GoogleMapsActivity.TAG, "Runtime permission has been granted");
                trackLocation();
            }
            else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]) == false)
            {
                Log.d(GoogleMapsActivity.TAG, "Runtime permission has been disabled for ever");
                displayAlertDialog();
            }
            else
            {
                Log.d(GoogleMapsActivity.TAG, "Runtime permission has been disabled");
                trackPermissionCheck();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.google_maps_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        client = LocationServices.getFusedLocationProviderClient(this);
        viewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(GoogleMapsActivityViewModel.class);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        trackPermissionCheck();
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
        mMap.setOnMarkerClickListener(this);
        observeProperties();
    }

    private void addProperty(double latitude, double longitude, String status)
    {
        LatLng place = new LatLng(latitude, longitude);
        if(status.equals("Sold"))
             markers.add(mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(0)).position(place)));
        else
            markers.add(mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(200)).position(place)));
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

    private void displayAlertDialog()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());
        builder.setTitle(android.R.string.dialog_alert_title);
        builder.setMessage(R.string.permissions);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }

    private void trackPermissionCheck()
    {
        Log.d(GoogleMapsActivity.TAG, "Checking for permission");
        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            Log.d(GoogleMapsActivity.TAG, "Permission granted");
            trackLocation();
        }
        else
        {
            Log.d(GoogleMapsActivity.TAG, "Permission not granted");
            ActivityCompat.requestPermissions(this, new String[] { permission.ACCESS_FINE_LOCATION }, GoogleMapsActivity.PERMISSION_REQUEST_CODE);
        }
    }

    @RequiresPermission(permission.ACCESS_FINE_LOCATION)
    private void trackLocation()
    {
        final LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1_000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        Log.d(GoogleMapsActivity.TAG, "Request location updates");
        client.flushLocations();
        Log.d(GoogleMapsActivity.TAG, "Request location updates");
        client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }


    @Override
    public boolean onMarkerClick(Marker marker) {
        if(markers.indexOf(marker) != -1) {
            final Intent intent = new Intent(this, PropertyDetailActivity.class);
            intent.putExtra(PropertyDetailActivity.PROPERTY_EXTRA, viewModel.properties.getValue().get(markers.indexOf(marker)));
            startActivity(intent);
        }
        return false;
    }
}
