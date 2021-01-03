package com.example.century22.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.century22.R;
import com.example.century22.adapter.AgentAdapter;

import androidx.lifecycle.ViewModelProvider;

import com.example.century22.viewmodel.AgentsActivityViewModel;

final public class AgentsActivity
        extends AppCompatActivity
{

    //The tag used into this screen for the logs
    public static final String TAG = AgentsActivity.class.getSimpleName();

    private RecyclerView recyclerView;

    private AgentsActivityViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AgentsActivityViewModel.class);
        //We first set up the layout linked to the activity
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        viewModel.loadAgents();
        observeAgents();
    }

    //Observe method to load agents into the recycler view
    private void observeAgents()
    {
        viewModel.agents.observe(this, agents -> {
            final AgentAdapter agentAdapter = new AgentAdapter(agents);
            recyclerView.setAdapter(agentAdapter);
        });
    }

}