package com.example.century22.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.example.century22.R;
import com.example.century22.bo.Agent;
import com.example.century22.bo.Property;
import com.example.century22.repository.AppRepository;
import com.example.century22.viewmodel.PropertyDetailActivityViewModel;

import java.util.Date;

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

        observeProperty();
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
            }
        });
    }
}
