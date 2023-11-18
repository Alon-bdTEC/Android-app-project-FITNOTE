package com.example.fitnote13022021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class AddExerciseActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    DataBaseHelper dataBaseHelper;

    ListView listViewExercises;

    ArrayList<Exercise> exercises;

    ArrayList<Exercise> filteredExercises;

    ExerciseAdaptor exerciseAdaptor;

    Button btCancel;

    SearchView searchViewExerciseList;

    String activeUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);

        dataBaseHelper = new DataBaseHelper(this);

        listViewExercises = findViewById(R.id.listViewExercises);
        btCancel = findViewById(R.id.btCancel);
        searchViewExerciseList = findViewById(R.id.searchViewExerciseList);

        exercises = dataBaseHelper.getExercises();

        //setting filteredExercises to be exercises
        filteredExercises = exercises;

        exerciseAdaptor = new ExerciseAdaptor(exercises, this);

        listViewExercises.setAdapter(exerciseAdaptor);

        //initialize the searchWidget in order to filter exercises
        initSearchWidgets();

        //settings onClick Listeners
        listViewExercises.setOnItemClickListener(this);

        btCancel.setOnClickListener(this);

        //getting userName from intent
        Intent intent = getIntent();

        activeUserName = intent.getStringExtra("activeUserName");

    }

    //method to go back to ProgramUserActivity
    @Override
    public void onClick(View v) {

        if (v == btCancel){

            Intent intent = new Intent(this, ProgramUserActivity.class);

            intent.putExtra("activeUserName", activeUserName);

            startActivity(intent);

            finish();

        }

    }

    //method to add this specific exercise
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //gets the clicked exercise
        Exercise chosenExercise = filteredExercises.get(position);

        int exerciseID = chosenExercise.getExerciseID();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Do you want to add this exercise?");

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

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                UserExercise newUserExercise = new UserExercise(activeUserName, exerciseID);

                //method to insert UserExercise
                //public boolean insertUserExercise(UserExercise userExercise)
                //so we wiil turn this chosen exercise into a new userExercise
                //the builder - (String userName, int exerciseID)
                dataBaseHelper.insertUserExercise(newUserExercise);

                Intent intent = new Intent(AddExerciseActivity.this, ProgramUserActivity.class);

                intent.putExtra("activeUserName", activeUserName);

                startActivity(intent);

                finish();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        //Creating and showing the dialog
        AlertDialog dialog = builder.create();

        dialog.show();

    }

    //initialize the searchWidget in order to filter exercises
    public void initSearchWidgets(){

        SearchView searchView = (SearchView)searchViewExerciseList;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //this method is called every time a user
            //puts in any character into the search view
            //literally any chang :D
            @Override
            public boolean onQueryTextChange(String newText) {

                filteredExercises = new ArrayList<Exercise>();

                //same as a regular for lop
                for (Exercise exercise: exercises)
                {
                    //if an exercise has one of the letter in the written text
                    if(exercise.getExerciseName().toLowerCase().contains(newText.toLowerCase(Locale.ROOT))){
                        filteredExercises.add(exercise);
                    }
                }

                ExerciseAdaptor exerciseAdaptor = new ExerciseAdaptor(filteredExercises, AddExerciseActivity.this);

                listViewExercises.setAdapter(exerciseAdaptor);

                return false;
            };
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);

    }

    //a method to go back to ProgramUserActivity when clicking back arrow in bottom
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, ProgramUserActivity.class);

        intent.putExtra("activeUserName", activeUserName);

        startActivity(intent);

        finish();

    }
}