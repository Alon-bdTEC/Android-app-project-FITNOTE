package com.example.fitnote13022021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ViewExercisesResultsActivity extends AppCompatActivity {

    //Initialize variables
    public static String activeUserName = null;

    DataBaseHelper dataBaseHelper;

    ImageView imgUserPictureExercisesResultsScreen;

    TextView txtBMIDoneExercises;

    ListView listViewUserExercisesDone;

    ArrayList<UserExercise> userExercisesDone;

    ArrayList<UserExercise> filteredUserExercisesDone;

    SearchView searchViewUserExerciseDoneList;

    UserExerciseDoneAdapter userExerciseDoneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_done_exercises);

        dataBaseHelper = new DataBaseHelper(this);

        //finding XML parts / Assign variables
        imgUserPictureExercisesResultsScreen = findViewById(R.id.imgUserPictureExercisesResultsScreen);
        txtBMIDoneExercises = findViewById(R.id.txtBMIDoneExercises);
        searchViewUserExerciseDoneList = findViewById(R.id.searchViewUserExerciseDoneList);
        listViewUserExercisesDone = findViewById(R.id.listViewUserExercisesDone);

        //setting filteredExercises to be exercises
        filteredUserExercisesDone = userExercisesDone;

        Intent intent = getIntent();

        //getting the active userName from getting the user who entered with log in with his name given from the intent
        String userName = intent.getStringExtra("activeUserName");

        activeUserName = userName;

        //changing the userPicture to show the user's picture
        Bitmap userPic = PictureFileHelper.getPic(this, activeUserName);
        if(userPic != null && imgUserPictureExercisesResultsScreen != null){
            imgUserPictureExercisesResultsScreen.setImageBitmap(userPic);
        }

        //getting the exercises done by the user
        userExercisesDone = dataBaseHelper.getUserExercisesDoneByUserName(activeUserName);

        userExerciseDoneAdapter = new UserExerciseDoneAdapter(userExercisesDone, ViewExercisesResultsActivity.this);

        listViewUserExercisesDone.setAdapter(userExerciseDoneAdapter);

        //initialize the searchWidget in order to filter exercises
        initSearchWidgets();

        //changing textView to show user's BMI
        //Getting the user's weight and height
        User activeUser = dataBaseHelper.getUserByName(activeUserName);
        double userWeight = activeUser.getUserWeight();
        double userHeight = activeUser.getUserHeight() * 0.01;//turning cm to m
        //BMI = kg/m2
        double userBMI = userWeight / (userHeight*userHeight);

        String BMIMessageToUser = activeUserName + " BMI is: " + new DecimalFormat("##.##").format(userBMI);

        txtBMIDoneExercises.setText(BMIMessageToUser);
        //A BMI of 25.0 or more is overweight, while the healthy range is 18.5 to 24.9.

    }

    private void initSearchWidgets() {

        SearchView searchView = (SearchView)searchViewUserExerciseDoneList;

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

                filteredUserExercisesDone = new ArrayList<UserExercise>();

                //same as a regular for lop
                for (UserExercise userExercise: userExercisesDone)
                {

                    //getting the exerciseName by exerciseID
                    String exerciseName = dataBaseHelper.getExerciseByID(userExercise.getExerciseID()).getExerciseName();

                    //if a userExercise has one of the letter in the date
                    if(userExercise.getDate().toLowerCase().contains(newText.toLowerCase(Locale.ROOT))){
                        filteredUserExercisesDone.add(userExercise);
                    }

                    //if a userExercise has one of the letter in the time
                    else if(String.valueOf(userExercise.getTime()).contains(newText.toLowerCase(Locale.ROOT))){
                        filteredUserExercisesDone.add(userExercise);
                    }

                    //if a userExercise has one of the letter in the rating
                    else if(String.valueOf(userExercise.getRating()).contains(newText.toLowerCase(Locale.ROOT))){
                        filteredUserExercisesDone.add(userExercise);
                    }

                    //if a userExercise has one of the letter in the repetition
                    else if(String.valueOf(userExercise.getRepetition()).contains(newText.toLowerCase(Locale.ROOT))){
                        filteredUserExercisesDone.add(userExercise);
                    }

                    //if a userExercise has one of the letter in the name
                    else if(exerciseName.toLowerCase().contains(newText.toLowerCase(Locale.ROOT))){
                        filteredUserExercisesDone.add(userExercise);
                    }

                }

                UserExerciseDoneAdapter exerciseAdaptor = new UserExerciseDoneAdapter(filteredUserExercisesDone, ViewExercisesResultsActivity.this);

                listViewUserExercisesDone.setAdapter(exerciseAdaptor);

                return false;
            };
        });

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