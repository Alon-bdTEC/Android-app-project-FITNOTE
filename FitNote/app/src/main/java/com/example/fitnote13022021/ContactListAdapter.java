package com.example.fitnote13022021;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    //Initialize variable
    Activity activity;
    ArrayList<ContactModel> arrayList;
    RecycleViewClickListener listener;

    //Create constructor
    public ContactListAdapter(Activity activity, ArrayList<ContactModel> arrayList, RecycleViewClickListener listener){
       this.activity = activity;
       this.arrayList = arrayList;
       this.listener = listener;
       notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize variable
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact,parent,false);
        //Return view
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Initialize contact model
        ContactModel model = arrayList.get(position);

        //Set name
        holder.tv_nameContact.setText(model.getName());

        //Set number
        holder.tv_numberContact.setText(model.getNumber());

    }

    @Override
    public int getItemCount() {
        //Return array list size
        return arrayList.size();
    }

    public interface RecycleViewClickListener {
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Initialize variable
        TextView tv_nameContact, tv_numberContact;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            tv_nameContact = itemView.findViewById(R.id.tv_nameContact);
            tv_numberContact = itemView.findViewById(R.id.tv_numberContact);
            itemView.setOnClickListener(this);

        }

        // A method that is triggered when you click an item in the list
        @Override
        public void onClick(View v) {
            // we need to the pass the view and the position in the array
            listener.onClick(v, getAdapterPosition());
        }
    }

}
