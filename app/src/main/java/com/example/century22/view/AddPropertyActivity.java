package com.example.century22.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.century22.R;
import com.example.century22.viewmodel.AddPropertyActivityViewModel;
import com.example.century22.viewmodel.AddPropertyActivityViewModel.Event;

final public class AddPropertyActivity
        extends MenuActivity
        implements OnClickListener, AdapterView.OnItemSelectedListener {

    //The tag used into this screen for the logs
    public static final String TAG = AddPropertyActivity.class.getSimpleName();

    private EditText price;

    private EditText area;

    private EditText rooms;

    private EditText description;

    private EditText address;

    private Spinner spin;

    private String type;

    private AddPropertyActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(AddPropertyActivityViewModel.class);
        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_add_property);

        //Then we retrieved the widget we will need to manipulate into the
        price = findViewById(R.id.price);
        rooms = findViewById(R.id.rooms);
        address = findViewById(R.id.address);
        description = findViewById(R.id.description);
        area = findViewById(R.id.area);
        spin = findViewById(R.id.type);
        spin.setOnItemSelectedListener(this);

        //We configure the click on the save button
        findViewById(R.id.add_property).setOnClickListener(this);
        setSpinner();//spinner to display type possibilities

        observeEvent();
    }

    private void setSpinner(){
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,viewModel.getTypes());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    //Observe if a new property has been added or not */
    private void observeEvent()
    {
        viewModel.event.observe(this, event -> {
            if (event == Event.ResetForm)//If property has been added, then display the notification and reset the form
            {
                displayNotification();
                resetForm();
            }
            else if (event == Event.DisplayError)//Else, display an error
            {
                displayError();
            }
            else if (event == Event.AddressNotFound)
            {
                displayAddressError();
            }
        });
    }

    //Display error when entered address hasn't been found
    private void displayAddressError()
    {
        Toast.makeText(this, R.string.address_error, Toast.LENGTH_SHORT).show();
    }

    //Display error when property has not been added
    private void displayError()
    {
        Toast.makeText(this, R.string.cannot_add_property, Toast.LENGTH_SHORT).show();
    }

    //Click and call to VM to check if we can save the property
    @Override
    public void onClick(View v)
    {
        //we first retrieve user's entries
        final String _price = price.getEditableText().toString();
        final String _rooms = rooms.getEditableText().toString();
        final String _area = area.getEditableText().toString();
        final String _address = address.getEditableText().toString();
        final String _description = description.getEditableText().toString();

        viewModel.saveProperty(_price, _area, _rooms, type, _description, _address);
    }

    //Send notification if property has been added

    private void displayNotification()
    {
        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        final String notificationChannelId = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ? "MyChannel" : null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            final NotificationChannel notificationChannel = new NotificationChannel(notificationChannelId, "My Channel", NotificationManager.IMPORTANCE_HIGH);
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }

        final Intent intent = new Intent(AddPropertyActivity.this, AddPropertyActivity.class);
        final PendingIntent pendingIntent = PendingIntent.getActivity(AddPropertyActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(AddPropertyActivity.this, notificationChannelId);
        notificationBuilder.setContentTitle(getString(R.string.added_property));
        notificationBuilder.setContentText("Price:" + price.getEditableText().toString());//We display the price of the added property in the notification
        notificationBuilder.setSmallIcon(R.drawable.ic_house);//we add an icon to the notification
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setChannelId(notificationChannelId);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationManagerCompat.notify(1, notificationBuilder.build());
    }

    //Reset form once a property has been added
    private void resetForm()
    {
        price.setText(null);
        address.setText(null);
        rooms.setText(null);
        area.setText(null);
        description.setText(null);
    }


    //Get selected item from spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        type = viewModel.getTypes()[position];//get the type selected by the user
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        displayError();
    }
}