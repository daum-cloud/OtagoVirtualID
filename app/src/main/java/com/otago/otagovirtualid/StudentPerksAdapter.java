package com.otago.otagovirtualid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentPerksAdapter extends RecyclerView.Adapter<StudentPerksAdapter.ViewHolder>{

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public Button activeButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.perk_name);
            activeButton = (Button) itemView.findViewById(R.id.go_to);
        }
    }

    // Store a member variable for the contacts
    private List<Perk> mPerks;

    // Pass in the contact array into the constructor
    public StudentPerksAdapter(List<Perk> perks) {
        mPerks = perks;
    }


    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public StudentPerksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.perks_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(StudentPerksAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Perk perks = mPerks.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(perks.getName());
        Button button = holder.activeButton;
        button.setText(perks.isOnline() ? "ACTIVE" : "EXPIRED");
        button.setEnabled(perks.isOnline());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mPerks.size();
    }
}
