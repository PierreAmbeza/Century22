package com.example.century22.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.century22.R;
import com.example.century22.viewmodel.LaunchActivityViewModel;

import java.util.List;


//Launch Activity, seen on first launch and acts like welcome screen before launching the good activity if user was already logged or not
public class LaunchActivity extends AppCompatActivity {


    private LaunchActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(LaunchActivityViewModel.class);
        setContentView(R.layout.launch_activity);
        observeActivity();
    }

    private void startAgents()
    {
        final Intent intent = new Intent(this, AgentsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }

    private void startProperties()
    {
        final Intent intent = new Intent(this, PropertiesActivity.class);
        startActivity(intent);
    }

    private void observeActivity()
    {
        viewModel.activity.observe(this, as -> {
            if (as == LaunchActivityViewModel.ActivityState.Agents)//If user was not logged before, then start agents activity
            {
                startAgents();
            }
            else if (as == LaunchActivityViewModel.ActivityState.Properties)//else, start directly the properties activity
            {
                startProperties();
            }
        });
    }
}
