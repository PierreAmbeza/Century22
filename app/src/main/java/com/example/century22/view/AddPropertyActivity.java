package com.example.century22.view;/*package com.example.century21.view;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.century21.R;
import com.example.century21.preferences.AppPreferences;
import com.example.century21.viewmodel.AddPropertyActivityViewModel;
import com.example.century21.viewmodel.AddPropertyActivityViewModel.Event;

public class AddPropertyActivity extends AppCompatActivity implements OnClickListener {

    //The tag used into this screen for the logs
    public static final String TAG = AddPropertyActivity.class.getSimpleName();

    private EditText price;

    private EditText surface;

    private EditText rooms;

    private EditText address;

    private EditText description;

    private AddPropertyActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_add_property);

        //Then we retrieved the widget we will need to manipulate into the
        price = findViewById(R.id.price);
        surface = findViewById(R.id.size);
        rooms = findViewById(R.id.rooms);
        address = findViewById(R.id.address);
        description = findViewById(R.id.description);

        //We configure the click on the save button
        findViewById(R.id.save).setOnClickListener(this);

        viewModel = new ViewModelProvider(this).get(AddPropertyActivityViewModel.class);

        observeEvent();
    }

    private void observeEvent()
    {
        viewModel.event.observe(this, new Observer<Event>()
        {
            @Override
            public void onChanged(Event event)
            {
                if (event == Event.ResetForm)
                {
                    resetForm();
                }
                else if (event == Event.DisplayError)
                {
                    displayError();
                }
            }
        });
    }

    private void displayError()
    {
        Toast.makeText(this, R.string.cannot_add_property, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v)
    {
        //we first retrieve user's entries
        final Double price = price.getEditableText().toDouble();
        final Double surface = surface.getEditableText().toDouble();
        final int rooms = rooms.getEditablelText().toInt();
        final String Address = address.getEditableText().toString();
        final String Description = description.getEditableText().toString();

        viewModel.saveProperty(price, surface, Address, Description);
    }

    private void resetForm()
    {
        price.setText(null);
        surface.setText(null);
        address.setText(null);
        description.setText(null);
    }

}*/