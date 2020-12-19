package com.example.century22.viewmodel;

import android.app.Application;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.century22.preferences.AppPreferences;

public class LaunchActivityViewModel extends AndroidViewModel {


    public enum ActivityState
    {
        Agents, Properties

    }

    public MutableLiveData<ActivityState> activity = new MutableLiveData<>();

    public LaunchActivityViewModel(@NonNull Application application)
    {
        super(application);
        startActivity();
    }


    /* Start the proper activity after 500 ms */
    public void startActivity()
    {
        new Handler().postDelayed(() -> {
            if(AppPreferences.getAgentName(getApplication()) != null)
                activity.postValue(ActivityState.Properties);
            else
                activity.postValue(ActivityState.Agents);
        }, 500);
    }

}
