package com.example.century22.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.example.century22.R;
import com.example.century22.bo.Agent;
import com.example.century22.bo.Property;
import com.example.century22.viewmodel.AddPropertyActivityViewModel;
import com.example.century22.viewmodel.PropertyDetailActivityViewModel;
import com.example.century22.viewmodel.PropertyDetailActivityViewModel.Event;

public class PropertyDetailActivity extends MenuActivity{

    public static final String PROPERTY_EXTRA = "propertyExtra";

    public static final String TAG = PropertyDetailActivity.class.getSimpleName();

    private TextView price;

    private TextView rooms;

    private TextView area;

    private TextView description;

    private TextView type;

    private TextView status;

    private TextView agent;

    private TextView creation;

    private TextView update;

    private TextView address;

    private PropertyDetailActivityViewModel viewModel;

    private String _address;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.properties_details_view);

        //Then we retrieved the widget we will need to manipulate into the
        price = findViewById(R.id.price);
        rooms = findViewById(R.id.rooms);
        area = findViewById(R.id.area);
        description = findViewById(R.id.description);
        type = findViewById(R.id.type);
        status = findViewById(R.id.status);
        agent = findViewById(R.id.agent);
        creation = findViewById(R.id.creation);
        update = findViewById(R.id.update);
        address = findViewById(R.id.address);
        viewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(PropertyDetailActivityViewModel.class);
        observeEvent();
        observeProperty();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.delete).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete)
        {
            viewModel.deleteProperty();
        }
        return super.onOptionsItemSelected(item);
    }

    private void displayNotification()
    {
        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        final String notificationChannelId = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ? "MyChannel" : null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            final NotificationChannel notificationChannel = new NotificationChannel(notificationChannelId, "My Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }

        final Intent intent = new Intent(PropertyDetailActivity.this, AddPropertyActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity( PropertyDetailActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(PropertyDetailActivity.this, notificationChannelId);
        notificationBuilder.setContentTitle("Deleted property:");
        notificationBuilder.setContentText("Location:" +_address);
        notificationBuilder.setSmallIcon(R.drawable.ic_house);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setChannelId(notificationChannelId);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationManagerCompat.notify(2, notificationBuilder.build());
    }

    private void observeProperty()
    {
        viewModel.property.observe(this, new Observer<Property>()
        {
            @Override
            public void onChanged(Property property)
            {
                //Then we bind the User and the UI
                price.setText(property.price);
                rooms.setText(property.rooms);
                area.setText(property.surface);
                description.setText(property.description);
                type.setText(property.type);
                status.setText(property.status);
                agent.setText(property.agent);
                creation.setText(property.add_date.toString());
                update.setText(property.last_edit_date.toString());
                address.setText(property.address);
                _address = property.address;
            }
        });
    }

    private void displayError()
    {
        Toast.makeText(this, "Only property's agent can delete it", Toast.LENGTH_SHORT).show();
    }

    private void observeEvent()
    {
        viewModel.event.observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {

                if (event == Event.Ok)
                {
                    displayNotification();
                    onBackPressed();
                }
                else if (event == Event.Ko)
                {
                    displayError();
                }
            }
        });
    }
}
