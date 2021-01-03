package com.example.century22.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import java.util.List;
import android.content.Intent;
import android.view.View.OnClickListener;
import com.example.century22.R;
import com.example.century22.bo.Agent;
import com.example.century22.preferences.AppPreferences;
import com.example.century22.view.PropertiesActivity;


public final class AgentAdapter
        extends Adapter<AgentAdapter.AgentViewHolder>
{

    //The ViewHolder class
    //Each Widget is created as an attribut in order to update the UI
    public static final class AgentViewHolder
            extends ViewHolder
    {

        private final TextView name;

        public AgentViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.name);
        }

        public void update(final Agent agent)
        {
            //We update the UI binding the current agent to the current item
            name.setText(agent.name);

            //We handle the click on the current item in order to display a new activity
            itemView.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    //We create the intent that display the PropertiesActivity.
                    AppPreferences.saveAgentLogin(itemView.getContext(), agent.name);
                    final Intent intent = new Intent(itemView.getContext(), PropertiesActivity.class);
                    itemView.getContext().startActivity(intent);
                }

            });
        }

    }

    private final List<Agent> agents;

    public AgentAdapter(List<Agent> agents)
    {
        this.agents = agents;
    }

    @NonNull
    @Override
    public AgentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //We create the ViewHolder
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_viewholder, parent, false);
        return new AgentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgentViewHolder holder, int position)
    {
        //We update the ViewHolder
        holder.update(agents.get(position));
    }

    @Override
    public int getItemCount()
    {
        return agents.size();
    }

}
