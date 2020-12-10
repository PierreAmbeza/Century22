package com.example.century22.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.century22.R;
import com.example.century22.adapter.AgentAdapter;
import com.example.century22.bo.Agent;
import com.example.century22.bo.Property;
import com.example.century22.bo.Status;
import com.example.century22.bo.Type;
import com.example.century22.repository.AppRepository;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.century22.viewmodel.AgentsActivityViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

final public class AgentsActivity
        extends AppCompatActivity
        //implements View.OnClickListener
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
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        initList();
        //addProperty();
        //We init the list into the onResume method
        //so the list is updated each time the screen goes to foreground
        //if(AppPreferences.getAgentLogin(this) == null)
            //observeUsers();
        /*else
        {
            final Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }*/
    }

    private void initList()
    {
        final List<Agent> agents = viewModel.loadAgents();
        final AgentAdapter agentsAdapter = new AgentAdapter(agents);
        recyclerView.setAdapter(agentsAdapter);

    }

    /*
    @Override
    public void onClick(View v)
    {
        //We open the AddUserActivity screen when the user clicks on the FAB
        final Intent intent = new Intent(this, AddUserActivity.class);
        startActivity(intent);

    }*/

}