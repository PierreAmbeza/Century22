package com.example.century22.viewmodel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Application;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.appcompat.app.AlertDialog.Builder;

import com.example.century22.bo.Property;
import com.example.century22.repository.AppRepository;
import com.example.century22.view.GoogleMapsActivity;
import com.example.century22.view.PropertyDetailActivity;
import com.google.android.gms.common.api.internal.GoogleServices;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class GoogleMapsActivityViewModel extends AndroidViewModel {

    public MutableLiveData<List<Property>> properties = new MutableLiveData<>();

    private final List<Property> propertiesExtra;


    private static final String TAG = GoogleMapsActivityViewModel.class.getSimpleName();

    public GoogleMapsActivityViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);
        propertiesExtra = savedStateHandle.get(GoogleMapsActivity.PROPERTIES_EXTRA);
        properties.postValue(propertiesExtra);
    }
}
