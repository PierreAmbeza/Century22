package com.example.century22.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.example.century22.R;
import com.example.century22.bo.Property;
import com.example.century22.viewmodel.EditPropertyActivityViewModel;
import com.example.century22.viewmodel.EditPropertyActivityViewModel.Event;
import com.example.century22.viewmodel.PropertyDetailActivityViewModel;

public class EditPropertyActivity extends MenuActivity
        implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    public static final String PROPERTY_EXTRA = "propertyExtra";

    //The tag used into this screen for the logs
    public static final String TAG = AddPropertyActivity.class.getSimpleName();

    private EditText price;

    private EditText area;

    private EditText rooms;

    private EditText description;

    private EditText address;

    private Spinner spin;

    private String status;

    private EditPropertyActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(EditPropertyActivityViewModel.class);
        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_edit_property);

        //Then we retrieved the widget we will need to manipulate into the
        price = findViewById(R.id.price);
        rooms = findViewById(R.id.rooms);
        address = findViewById(R.id.address);
        description = findViewById(R.id.description);
        area = findViewById(R.id.area);
        spin = findViewById(R.id.status);
        spin.setOnItemSelectedListener(this);

        //We configure the click on the save button
        findViewById(R.id.edit_property).setOnClickListener(this);
        setSpinner();
        observeProperty();
        observeEvent();
    }

    private void setSpinner(){
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,viewModel.getStatus());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    /* Method to choose what to do depending on the Edit event*/
    private void observeEvent()
    {
        viewModel.event.observe(this, event -> {
            if (event == Event.Edited)//If property has been edited, display a Toast and send notification
            {
                displayEdit();
                displayNotification();
            }
            else if (event == Event.Error)//Else, display Toast error
            {
                displayError();
            }
        });
    }

    private void displayEdit()
    {
        Toast.makeText(this, R.string.edited_property, Toast.LENGTH_SHORT).show();
    }

    private void displayError()
    {
        Toast.makeText(this, R.string.cannot_add_property, Toast.LENGTH_SHORT).show();
    }

    /* We observe the property variable to set the property's attributes */
    private void observeProperty()
    {
        viewModel.property.observe(this, property -> {
            //Then we bind the User and the UI
            price.setText(property.price);
            rooms.setText(property.rooms);
            area.setText(property.surface);
            description.setText(property.description);
            spin.setSelection(viewModel.getIndexFromStatus(property.status));
            address.setText(property.address);
        });
    }

    @Override
    public void onClick(View v)
    {
        //we first retrieve user's entries
        final String _price = price.getEditableText().toString();
        final String _rooms = rooms.getEditableText().toString();
        final String _area = area.getEditableText().toString();
        final String _address = address.getEditableText().toString();
        final String _description = description.getEditableText().toString();

        viewModel.editProperty(_price, _area, _rooms, status, _description, _address);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        status = viewModel.getStatus()[position];//Get status selected by user

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

        final Intent intent = new Intent(EditPropertyActivity.this, EditPropertyActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(EditPropertyActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(EditPropertyActivity.this, notificationChannelId);
        notificationBuilder.setContentTitle("Edited Property");
        notificationBuilder.setContentText("Location : " + address.getEditableText().toString());
        notificationBuilder.setSmallIcon(R.drawable.ic_house);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setChannelId(notificationChannelId);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationManagerCompat.notify(1, notificationBuilder.build());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        displayError();
    }
}
