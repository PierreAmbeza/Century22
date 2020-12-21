package com.example.century22.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import android.view.View.OnClickListener;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.example.century22.R;
import com.example.century22.bo.Agent;
import com.example.century22.bo.Property;
import com.example.century22.viewmodel.AddPropertyActivityViewModel;
import com.example.century22.viewmodel.PropertyDetailActivityViewModel;
import com.example.century22.viewmodel.PropertyDetailActivityViewModel.Event;

public class PropertyDetailActivity extends MenuActivity implements OnClickListener {

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

    private String _currency = "€";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.properties_details_view);
        findViewById(R.id.currency).setOnClickListener(this);

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.loadProperty();
        observeProperty();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.findItem(R.id.delete).setVisible(true);
        menu.findItem(R.id.edit).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Handle the click on the delete item
        if(item.getItemId() == R.id.delete)
        {
            viewModel.deleteProperty();
        }
        //Handle the click on edit item
        else if(item.getItemId() == R.id.edit)
        {
            final Intent intent = new Intent(this, EditPropertyActivity.class);
            intent.putExtra(PropertyDetailActivity.PROPERTY_EXTRA, viewModel.property.getValue());
            startActivity(intent);//We start the edit activity with the current property as an extra
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        viewModel.convert(_currency);
        observeCurrency();
    }

    /* Method to set property's attributes in the view */
    private void observeCurrency()
    {
        viewModel.currency.observe(this, currency -> {
            //Then we bind the User and the UI
            price.setText(currency.getConverted_price() + currency.getCurrency());
            _currency = currency.getCurrency();
        });
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
        notificationBuilder.setContentText("Location:" + address.getText().toString());
        notificationBuilder.setSmallIcon(R.drawable.ic_house);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setChannelId(notificationChannelId);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationManagerCompat.notify(2, notificationBuilder.build());
    }

    /* Method to set property's attributes in the view */
    private void observeProperty()
    {
        viewModel.property.observe(this, property -> {
            //Then we bind the User and the UI
            price.setText(property.price + _currency);
            rooms.setText(property.rooms);
            area.setText(property.surface);
            description.setText(property.description);
            type.setText(property.type);
            status.setText(property.status);
            agent.setText(property.agent);
            creation.setText(property.add_date.toString());
            update.setText(property.last_edit_date.toString());
            address.setText(property.address);
        });
    }

    private void displayError()
    {
        Toast.makeText(this, "Only property's agent can delete it", Toast.LENGTH_SHORT).show();
    }

    /* Method to decide what to display if property has been deleted or not */
    private void observeEvent()
    {
        viewModel.event.observe(this, event -> {

            if (event == Event.Ok)
            {
                displayNotification();
                onBackPressed();
            }
            else if (event == Event.Ko)
            {
                displayError();
            }
        });
    }
}
