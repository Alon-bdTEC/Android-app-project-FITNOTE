package com.example.fitnote13022021;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExerciseAdaptor extends BaseAdapter {

    private ArrayList<Exercise> arrayList;
    private Context context;

    public ExerciseAdaptor(ArrayList<Exercise> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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

        Exercise exercise = arrayList.get(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.exercise_layout, null);

        TextView txtMainTitle = convertView.findViewById(R.id.txtMainTitle);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        txtMainTitle.setText(exercise.getExerciseName());
        imageView.setImageResource(exercise.getExercisePic());

        return convertView;
    }

}
