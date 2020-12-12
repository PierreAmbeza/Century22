package com.example.century22.view;

import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.century22.R;
import com.example.century22.bo.Property;
import com.example.century22.preferences.AppPreferences;
import com.example.century22.repository.AppRepository;

public class MenuActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //We add the "menu_order" to the current activity
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        //We handle the click on a menu item
        if (item.getItemId() == R.id.logout)
        {
            AppPreferences.removeLogin(MenuActivity.this);
            final Intent intent = new Intent(this, AgentsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
