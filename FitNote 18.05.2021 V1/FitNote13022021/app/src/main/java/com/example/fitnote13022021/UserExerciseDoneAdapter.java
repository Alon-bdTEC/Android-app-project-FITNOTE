package com.example.fitnote13022021;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserExerciseDoneAdapter extends BaseAdapter {

    private ArrayList<UserExercise> arrayList;
    public static Context context;
    private DataBaseHelper dataBaseHelper;

    public UserExerciseDoneAdapter(ArrayList<UserExercise> arrayList, Context context){
        this.arrayList = arrayList;
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) { return arrayList.get(position); }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        UserExercise userExercise = arrayList.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.user_exercise_done_layout, null);

        ImageView imageViewOfUserExerciseDone = convertView.findViewById(R.id.imageViewOfUserExerciseDone);
        TextView txtExerciseOfUserExerciseDone = convertView.findViewById(R.id.txtExerciseOfUserExerciseDone);
        TextView txtDateOfUserExerciseDone = convertView.findViewById(R.id.txtDateOfUserExerciseDone);
        TextView txtTimeOfUserExerciseDone = convertView.findViewById(R.id.txtTimeOfUserExerciseDone);
        TextView txtRatingOfUserExerciseDone = convertView.findViewById(R.id.txtRatingOfUserExerciseDone);
        TextView txtRepetitionOfUserExerciseDone = convertView.findViewById(R.id.txtRepetitionOfUserExerciseDone);

        //setting textviews to show userExercise info
        String textViewText = txtDateOfUserExerciseDone.getText().toString();

        txtDateOfUserExerciseDone.setText(textViewText + userExercise.getDate());

        textViewText = txtTimeOfUserExerciseDone.getText().toString();

        txtTimeOfUserExerciseDone.setText(textViewText + userExercise.getTime());

        textViewText = txtRatingOfUserExerciseDone.getText().toString();

        txtRatingOfUserExerciseDone.setText(textViewText + userExercise.getRating());

        textViewText = txtRepetitionOfUserExerciseDone.getText().toString();

        txtRepetitionOfUserExerciseDone.setText(textViewText + userExercise.getRepetition());

        //getting the exercise by exerciseID
        Exercise exerciseFromUserExercise = dataBaseHelper.getExerciseByID(userExercise.getExerciseID());

        //setting the image to show exercise image
        imageViewOfUserExerciseDone.setImageResource(exerciseFromUserExercise.getExercisePic());

        //setting the lasy textView to show the exercise's name
        textViewText = txtExerciseOfUserExerciseDone.getText().toString();

        txtExerciseOfUserExerciseDone.setText(textViewText + exerciseFromUserExercise.getExerciseName());


        return convertView;
    }
}
