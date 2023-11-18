package com.example.fitnote13022021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ExecuteExerciseActivity extends AppCompatActivity implements View.OnClickListener {

    DataBaseHelper dataBaseHelper;

    ImageView btnFinish, btnPlay;

    public static TextView txtTime;

    ImageView imgExercise;

    public static boolean playTheTimer = true;

    int userExerciseID;

    String activeUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_exercise);

        dataBaseHelper = new DataBaseHelper(this);

        //finding XML parts
        btnFinish = findViewById(R.id.btnFinish);
        btnPlay = findViewById(R.id.btnPlay);
        txtTime = findViewById(R.id.txtTime);
        imgExercise = findViewById(R.id.imgExercise);

        //refresh boolean for new screen
        playTheTimer = true;

        Intent intent = getIntent();

        userExerciseID = intent.getIntExtra("ExecuteUserExerciseID", 0);
        int exerciseID = intent.getIntExtra("ExecuteExerciseID", 0);
        activeUserName = intent.getStringExtra("activeUserName");

        Exercise exercise = dataBaseHelper.getExerciseByID(exerciseID);

        imgExercise.setImageResource(exercise.getExercisePic());

        btnPlay.setOnClickListener(this);
        btnFinish.setOnClickListener(this);

    }

    //on button click
    @Override
    public void onClick(View v) {

        if (v == btnPlay) {

            //Playing the clock count and starting music
            if (playTheTimer == true) {

                Intent startService = new Intent(this, MusicAndTimerService.class);
                startService.putExtra("ACTION", "PLAY");
                startService(startService);

                //setting the image to be pause button
                btnPlay.setImageResource(R.drawable.pause_button);

            }

            //Pausing the clock count and the music
            if (playTheTimer == false) {

                Intent startService = new Intent(this, MusicAndTimerService.class);
                startService.putExtra("ACTION", "PAUSE");
                startService(startService);

                //setting the image to be play button
                btnPlay.setImageResource(R.drawable.play_button);

            }

            //Switching modes (Play/Pause) to switch between them
            if (playTheTimer == true) {
                playTheTimer = false;
            } else {
                playTheTimer = true;
            }

        }

        if (v == btnFinish){

            //Pausing the clock count
            if(!playTheTimer){
                btnPlay.setImageResource(R.drawable.play_button);
            }

            int countFromService = MusicAndTimerService.count;

            //stop button to stop music service
            Intent startService = new Intent(this, MusicAndTimerService.class);
            stopService(startService);

            //going to SignIn activity
            Intent intent = new Intent(this, FeedbackActivity.class);

            String date = getTodaysDate();

            //adding extra in order to update the userExercise later
            intent.putExtra("userExerciseID", userExerciseID);
            intent.putExtra("countOfExercise", countFromService);
            intent.putExtra("date", date);
            intent.putExtra("activeUserName", activeUserName);

            startActivity(intent);

            //finishing the activity to go to onCreate when coming back
            finish();
        }

    }

    //a method to go back to ProgramUserActivity
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //stop button to stop music service
        Intent startService = new Intent(this, MusicAndTimerService.class);
        stopService(startService);

        //setting an intent to go back to the ProgramUserActivity after finishing an exercise
        Intent intent = new Intent(ExecuteExerciseActivity.this, ProgramUserActivity.class);

        intent.putExtra("activeUserName", activeUserName);

        startActivity(intent);

        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //stop button to stop music service
        Intent startService = new Intent(this, MusicAndTimerService.class);
        stopService(startService);

        finish();
    }

    //gets today's date
    private String getTodaysDate() {

        Calendar cal = Calendar.getInstance();
        //setting default date to be current date
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        //adding 1 to month because its defualt is 0
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    //creats date as a string
    private String makeDateString(int day, int month, int year) {
        return  getMonthFormat(month) + " " + day + " " + year;
    }

    //makes the number month a real month (1 - JAN)
    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }


}