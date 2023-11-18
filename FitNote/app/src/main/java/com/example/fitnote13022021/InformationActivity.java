package com.example.fitnote13022021;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class InformationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    //Initialize variables
    DataBaseHelper dataBaseHelper;

    ListView listViewExercisesInfo;

    ArrayList<Exercise> exercises;

    ExerciseAdaptor exerciseAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        //setting database
        dataBaseHelper = new DataBaseHelper(this);

        //finding XML parts
        //Assign variables
        listViewExercisesInfo = findViewById(R.id.listViewExercisesInfo);

        exercises = dataBaseHelper.getExercises();

        exerciseAdaptor = new ExerciseAdaptor(exercises, this);

        listViewExercisesInfo.setAdapter(exerciseAdaptor);

        //settings onClick Listener
        listViewExercisesInfo.setOnItemClickListener(this);

    }

    //method to show a specific exercise
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //gets the clicked exercise
        Exercise chosenExercise = exercises.get(position);

        int exerciseID = chosenExercise.getExerciseID();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(chosenExercise.getExerciseName() + ":");

        //Setting the builder's layout inflater
        View alertView = getLayoutInflater().inflate(R.layout.exercise_with_description_layout, null);

        //getting views from layout
        ImageView exerciseImage = alertView.findViewById(R.id.imageViewOfExerciseInDescription);
        TextView txtExerciseNameInDescription = alertView.findViewById(R.id.txtExerciseNameInDescription);
        TextView txtExerciseDescription = alertView.findViewById(R.id.txtExerciseDescription);

        //getting the whole chosen exercise information
        Exercise exerciseChosen = null;

        for (int i=0; i<exercises.size(); i++) {
            Exercise exercise = exercises.get(i);
            if(exercise.getExerciseID() == exerciseID){
                exerciseChosen = exercise;
            }
        }

        //Setting the imageView to show exercise image
        exerciseImage.setImageResource(exerciseChosen.getExercisePic());

        //Setting textViews to show exercise details
        txtExerciseNameInDescription.setText(exerciseChosen.getExerciseName());
        txtExerciseDescription.setText(exerciseChosen.getExerciseDetail());


        //Setting the view to be in builder of alert dialog
        builder.setView(alertView);

        builder.setPositiveButton("Got it!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dialog.cancel();

            }
        });
        //builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
           // public void onClick(DialogInterface dialog, int id) {
             //   dialog.cancel();
           // }
       // });

        //Creating and showing the dialog
        AlertDialog dialog = builder.create();

        dialog.show();

    }

    //a method to go back to MainScreenActivity when clicking back arrow in bottom
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, MainScreenActivity.class);

        startActivity(intent);

        finish();

    }

}