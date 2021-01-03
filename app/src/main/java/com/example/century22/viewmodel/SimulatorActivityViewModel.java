package com.example.century22.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.example.century22.bo.Property;
import com.example.century22.view.PropertyDetailActivity;

import java.util.Random;

public class SimulatorActivityViewModel extends AndroidViewModel {

    public MutableLiveData<Property> property = new MutableLiveData<>();

    public int contribution;

    public int total_price;

    private Property propertyExtra;

    public double loan_rate;

    public SimulatorActivityViewModel(@NonNull Application application, SavedStateHandle savedStateHandle) {
        super(application);
        Random r = new Random();
        propertyExtra = savedStateHandle.get(PropertyDetailActivity.PROPERTY_EXTRA);
        property.postValue(propertyExtra);
        //Computing loan rate and contribution
        loan_rate = r.nextInt(40) / 100.0 + 0.1;
        loan_rate = Math.floor(loan_rate * 100) / 100;
        contribution = (int) (loan_rate / 100.0 * Double.parseDouble(propertyExtra.price));
    }

    public void computeTotalPrice(String duration)
    {
        if(!duration.isEmpty())
            total_price = Integer.parseInt(duration)*contribution;//computing total contribution with duration
    }
}
