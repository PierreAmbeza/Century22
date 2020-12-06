package com.example.century22.viewmodel;/*package com.example.century21.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.century21.bo.Property;
import com.example.century21.view.AddPropertyActivity;
import com.example.century21.view.AddPropertyActivity;

public final class AddPropertyActivityViewModel
        extends AndroidViewModel
{

    public enum Event
    {
        ResetForm, DisplayError
    }

    public MutableLiveData<Event> event = new MutableLiveData<>();

    public AddPropertyActivityViewModel(@NonNull Application application)
    {
        super(application);
    }

    public void saveProperty(Double price, Double surface, String Address,
                         String Description)
    {
        //We display the properties into the logcat
        displayUserEntries(price, surface, Address, Description);

        //We check if all entries are valid (not null and not empty)
        final boolean canAddProperty = checkFormEntries(price, surface, Address, Description);

        if (canAddProperty == true)
        {
            //We add the user to the list and we reset the form
            persistProperty(price, surface, Address, Description);
            event.postValue(Event.ResetForm);
        }
        else
        {
            //we display a log error and a Toast
            Log.w(AddPropertyActivity.TAG, "Cannot add the user");
            event.postValue(Event.DisplayError);
        }
    }

    private void persistProperty(Double price, Double surface, String Address,
                                 String Description)
    {
        AppRepository.getInstance(getApplication()).addProperty(new Property(price, surface, Address, Description));
    }

    private boolean checkFormEntries(Double price, Double surface, String Address,
                                     String Description)
    {
        return TextUtils.isEmpty(price) == false && TextUtils.isEmpty(userPhoneNumber) == false && TextUtils.isEmpty(userAddress) == false && TextUtils.isEmpty(aboutUser) == false;
    }

    private void displayUserEntries(Double price, Double surface, String Address,
                                    String Description)
    {
        Log.d(AddPropertyActivity.TAG, "price : '" + price + "'");
        Log.d(AddPropertyActivity.TAG, "surface : '" + surface + "'");
        Log.d(AddPropertyActivity.TAG, "address : '" + Address + "'");
        Log.d(AddPropertyActivity.TAG, "about : '" + Description + "'");
    }

}
*/