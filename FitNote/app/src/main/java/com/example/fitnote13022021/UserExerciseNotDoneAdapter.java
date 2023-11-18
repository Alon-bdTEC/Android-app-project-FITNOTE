package com.example.fitnote13022021;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserExerciseNotDoneAdapter extends BaseAdapter implements ListAdapter {

    private ArrayList<UserExercisesInnerJoinEx> arrayList;
    public static Context context;
    private DataBaseHelper dataBaseHelper;
    private String activeUserName;

    public UserExerciseNotDoneAdapter(ArrayList<UserExercisesInnerJoinEx> arrayList, Context context, String userName) {
        this.arrayList = arrayList;
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context);
        this.activeUserName=userName;
    }
    public UserExerciseNotDoneAdapter(ArrayList<UserExercisesInnerJoinEx> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UserExercisesInnerJoinEx userExercisesInnerJoinEx = arrayList.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.user_exercise_layout, null);

        TextView txtMainTitle = convertView.findViewById(R.id.txtMainTitle);
        ImageView imageView = convertView.findViewById(R.id.imageView);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        txtMainTitle.setText(userExercisesInnerJoinEx.getExerciseName());
        imageView.setImageResource(userExercisesInnerJoinEx.getExercisePic());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, txtMainTitle.getText().toString(),Toast.LENGTH_SHORT).show();

                //creating the dialog to delete the userExercise
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("delete exercise");

                builder.setMessage("Do you really want to delete?");

                //delete
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dataBaseHelper.deleteUserExercise(activeUserName,userExercisesInnerJoinEx.getExerciseName(), userExercisesInnerJoinEx.getUserExerciseID());
                        arrayList.remove(position);
                        notifyDataSetChanged();
                    }
                });

                //cancel
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                //Creating and showing the dialog
                AlertDialog  dialog = builder.create();

                dialog.show();

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, userExercisesInnerJoinEx.getExerciseName(), Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(context, ExecuteExerciseActivity.class);

                intent1.putExtra("ExecuteUserExerciseID", userExercisesInnerJoinEx.getUserExerciseID());
                intent1.putExtra("ExecuteExerciseID", userExercisesInnerJoinEx.getExerciseID());
                intent1.putExtra("activeUserName",activeUserName);
                context.startActivity(intent1);
                ((Activity)context).finish();
            }
        });

        txtMainTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, userExercisesInnerJoinEx.getExerciseName(), Toast.LENGTH_SHORT).show();

                Intent intent1 = new Intent(context, ExecuteExerciseActivity.class);

                intent1.putExtra("ExecuteUserExerciseID", userExercisesInnerJoinEx.getUserExerciseID());
                intent1.putExtra("ExecuteExerciseID", userExercisesInnerJoinEx.getExerciseID());
                intent1.putExtra("activeUserName",activeUserName);
                context.startActivity(intent1);
                ((Activity)context).finish();
            }
        });

        return convertView;
    }
}
