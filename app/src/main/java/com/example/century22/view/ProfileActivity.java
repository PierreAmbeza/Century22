package com.example.century22.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.text.TextUtils;
import android.view.View.OnClickListener;
import com.example.century22.R;
import com.example.century22.preferences.AppPreferences;

public class ProfileActivity extends AppCompatActivity implements OnClickListener {

    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        findViewById(R.id.fab).setOnClickListener(this);
        name = findViewById(R.id.name);
        retrieveUser();
    }

    @Override
    public void onClick(View v)
    {
        //We open the AddUserActivity screen when the user clicks on the FAB
        final Intent intent = new Intent(this, GoogleMapsActivity.class);
        startActivity(intent);
    }

    public void retrieveUser()
    {
        final String userLogin = AppPreferences.getAgentName(this);
        if(!TextUtils.isEmpty(userLogin))
            name.setText(userLogin);
        Log.d(ProfileActivity.class.getSimpleName(), userLogin);
    }
}
