package com.example.century22.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import com.example.century22.R;
import com.example.century22.viewmodel.SimulatorActivityViewModel;

public class SimulatorActivity extends MenuActivity implements View.OnClickListener {

    private EditText durationInput;

    private TextView price;

    private  TextView type;

    private  TextView loan_rate;

    private TextView loan_contribution;

    private SimulatorActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //We first set up the layout linked to the activity
        setContentView(R.layout.simulator_activity);

        durationInput = findViewById(R.id.loan_duration);
        price = findViewById(R.id.price);
        type = findViewById(R.id.type);
        loan_rate = findViewById(R.id.loan_rate);
        loan_contribution = findViewById(R.id.contribution);

        findViewById(R.id.start_simulation).setOnClickListener(this);

        viewModel = new ViewModelProvider(this, new SavedStateViewModelFactory(getApplication(), this, getIntent().getExtras())).get(SimulatorActivityViewModel.class);
        observeProperty();
    }

    //Set layout views with property's info
    private void observeProperty()
    {
        viewModel.property.observe(this, property -> {


            //Then we bind the User and the UI
            price.setText(property.price);
            type.setText(property.type);
            loan_rate.setText(Double.toString(viewModel.loan_rate));
            loan_contribution.setText(Integer.toString(viewModel.contribution));
        });
    }

    //Handle click to simulate loan and display toast with result
    @Override
    public void onClick(View v) {
        final String _duration = durationInput.getText().toString();
        viewModel.computeTotalPrice(_duration);
        Toast.makeText(this, "Total price for " + durationInput.getText().toString() + " months is:" + Integer.toString(viewModel.total_price), Toast.LENGTH_SHORT).show();

    }
}
