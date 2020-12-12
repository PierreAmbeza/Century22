package com.example.century22.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

import android.content.Intent;
import android.view.View.OnClickListener;

import com.example.century22.bo.Property;
import com.example.century22.R;
import com.example.century22.view.PropertyDetailActivity;


public final class PropertyAdapter
        extends Adapter<PropertyAdapter.PropertyViewHolder>
{

    //The ViewHolder class
    //Each Widget is created as an attribut in order to update the UI
    public static final class PropertyViewHolder
            extends ViewHolder
    {
        private final ImageView image;

        private final TextView type;

        private final TextView description;

        private final TextView status;

        private final TextView area;

        private final TextView price;

        public PropertyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.avatar);
            type = itemView.findViewById(R.id.type);
            description = itemView.findViewById(R.id.description);
            status = itemView.findViewById(R.id.status);
            area = itemView.findViewById(R.id.area);
            price = itemView.findViewById(R.id.price);
        }

        public void update(final Property property)
        {
            //We update the UI binding the current user to the current item
            int imageResource = itemView.getContext().getResources()
                    .getIdentifier("@drawable/ic_"+ property.type.toLowerCase(), null, itemView.getContext().getPackageName());
            image.setImageResource(imageResource);
            type.setText(property.type);
            description.setText(property.description);
            status.setText(property.status);
            area.setText(property.surface + "m2");
            price.setText(property.price + "€");
            //We handle the click on the current item in order to display a new activity
            itemView.setOnClickListener(new OnClickListener()
            {

                @Override
                public void onClick(View v)
                {
                    //We create the intent that display the UserDetailActivity.
                    //The current user is added as an extra
                    //The User class implement the "Serializable" interface so I can put the whole object as an extra
                    final Intent intent = new Intent(itemView.getContext(), PropertyDetailActivity.class);
                    intent.putExtra(PropertyDetailActivity.PROPERTY_EXTRA, property);
                    itemView.getContext().startActivity(intent);
                }

            });
        }

    }

    private final List<Property> properties;

    public PropertyAdapter(List<Property> properties)
    {
        this.properties = properties;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //We create the ViewHolder
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.property_viewholder, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyViewHolder holder, int position)
    {
        //We update the ViewHolder

        holder.update(properties.get(position));
    }

    @Override
    public int getItemCount() { return properties.size(); }

}
