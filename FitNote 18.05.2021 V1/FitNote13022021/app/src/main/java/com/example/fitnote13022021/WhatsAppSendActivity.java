package com.example.fitnote13022021;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class WhatsAppSendActivity extends AppCompatActivity {

    //Initialize variables
    DataBaseHelper dataBaseHelper;

    TextView tvName, tvPhone;

    EditText etWhatsAppMessage;

    Button btSendWhatsAppMessage;

    String userExercisesSummaryText = "Here is my data on all the exercises I did in the last week successfully:";

    String activeUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app_send);

        dataBaseHelper = new DataBaseHelper(this);

        //Assign variables
        tvName = findViewById(R.id.tvName);
        tvPhone = findViewById(R.id.tvPhone);
        etWhatsAppMessage = findViewById(R.id.etWhatsAppMessage);
        btSendWhatsAppMessage = findViewById(R.id.btSendWhatsAppMessage);

        Intent intent = getIntent();

        activeUserName = intent.getStringExtra("activeUserName");

        //getting information from database to send on WhatsApp
        ArrayList<UserExercise> userExercisesDoneByUser = dataBaseHelper.getUserExercisesDoneByUserName(activeUserName);

        //Takes only the UserExercises done in the last week
        ArrayList<UserExercise> userExercisesDoneByUserLastWeek = new ArrayList<UserExercise>();

        int arrayListUserExercisesSize= userExercisesDoneByUser.size();

        Calendar dayOfExercise = Calendar.getInstance();


        //go over userExercisesDoneByUser to get exercises done in last week
        for (int i=0; i<arrayListUserExercisesSize; i++) {

            UserExercise userExercise = userExercisesDoneByUser.get(i);

            String date = userExercise.getDate();

            //EXAMPLE DATE: "FEB 28 2021" 11 (year - 7-10, day - 5-6, month 0-2)

            String[] splited = date.split("\\s+");

            int year = Integer.parseInt(splited[2]);
            int day = Integer.parseInt(splited[1]);
            int month = getMonthNumber(date);

            dayOfExercise.set(Calendar.YEAR, year);
            //month starts at 0 in Calendar
            dayOfExercise.set(Calendar.MONTH, month-1);
            dayOfExercise.set(Calendar.DAY_OF_MONTH, day);
            dayOfExercise.set(Calendar.HOUR_OF_DAY, 0);
            dayOfExercise.set(Calendar.MINUTE, 0);
            dayOfExercise.set(Calendar.SECOND, 0);
            dayOfExercise.set(Calendar.MILLISECOND, 0);

            Calendar currentDate = Calendar.getInstance();

            int dayDifference = 0;
            //get the day difference between today and the date of the exercise
            //if the two dates are not null
            if (dayOfExercise != null && currentDate != null){
                dayDifference = (int)( (currentDate.getTime().getTime() - dayOfExercise.getTime().getTime()) / (1000 * 60 * 60 * 24));
            }

            if (dayDifference >=0 &&  dayDifference <= 7){
                userExercisesDoneByUserLastWeek.add(userExercise);
            }

        }

        //We need the exercises in order to send the name of the exercise with the data
        ArrayList<Exercise> exercises = dataBaseHelper.getExercises();

        int  arrayListExercisesSize = exercises.size();

        //go over userExercisesDoneByUserLastWeek to take data
        int sizeExercisesLastWeek = userExercisesDoneByUserLastWeek.size();
        for (int i=0; i<sizeExercisesLastWeek; i++) {

            //(userExerciseID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT,
            // exerciseID INTEGER, date TEXT, time(SEC) INTEGER, rating INTEGER, repetition INTEGER)
            UserExercise userExercise = userExercisesDoneByUserLastWeek.get(i);

            int exerciseID = userExercise.getExerciseID();

            String exerciseName ="";

            //getting the exercise name
            for (int j=0; j<arrayListExercisesSize; j++){
                Exercise exercise = exercises.get(j);
                if(exerciseID == exercise.getExerciseID()){
                    exerciseName = exercise.getExerciseName();
                }
            }

            String date = userExercise.getDate();
            int time = userExercise.getTime();
            int rating = userExercise.getRating();
            int repetition = userExercise.getRepetition();

            userExercisesSummaryText += "\n" + "<---->" + "\n";

            userExercisesSummaryText += " - The exercise: "
                    + exerciseName + "\n";

            userExercisesSummaryText += " - The date in which the exercise was done:"
                    + date + "\n";

            userExercisesSummaryText += " - The time it took to perform the exercise: "
                    + getDurationString(time) + "\n";

            userExercisesSummaryText += " - The rating " + activeUserName + " gave to the exercise: "
                    + getRating(rating) + "\n";

            userExercisesSummaryText += " - " + activeUserName + " did the exercise "
                    + repetition + "times" + "\n";


        }

        //get contactName
        String contactName = intent.getStringExtra("contactName");
        //get contactNumber
        String contactNumber = intent.getStringExtra("contactNumber");

        String tvNameTxt = tvName.getText().toString();
        String tvPhoneTxt = tvPhone.getText().toString();

        tvName.setText(tvNameTxt + contactName);
        tvPhone.setText(tvPhoneTxt + contactNumber);

        btSendWhatsAppMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text to send from etWhatsAppMessage
                String textMessage = etWhatsAppMessage.getText().toString() + "\n";
                textMessage += userExercisesSummaryText;
                sendMessage(contactNumber, textMessage);
            }
        });

    }

    private void sendMessage(String phoneNumber, String text){
        //boolean to check if WhatsApp exists
        boolean installed = isAppInstalled("com.whatsapp");

        if (installed) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //WhatsApp message requires phone number & text(the message itself)
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+phoneNumber+"&text="+text));
            startActivity(intent);
        }else{
            Toast.makeText(this, "WhatsApp is not installed!", Toast.LENGTH_SHORT).show();
        }
    }

    // A method to check if WhatsApp exists on the users phone
    private boolean isAppInstalled(String s) {

        PackageManager packageManager = getPackageManager();
        boolean is_installed;

        try {
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            is_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            is_installed = false;
        }

        return is_installed;

    }

    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    private String getRating(int rating){

        String ratingString = "Easy";

        switch (rating){
            case 1:
                //Hard
                ratingString =  "Hard";
                break;

            case 2:
                //Hard-Medium
                ratingString =  "Hard-Medium";
                break;

            case 3:
                //Medium
                ratingString =  "Medium";
                break;

            case 4:
                //Easy-Medium
                ratingString =  "Easy-Medium";
                break;

            case 5:
                //Easy
                ratingString =  "Easy";
                break;

        }

        return ratingString;


    }

    //makes the real month a number month (JAN -> 1)
    private int getMonthNumber(String date) {
        if(date.contains("JAN"))
            return 1;
        if(date.contains("FEB"))
            return 2;
        if(date.contains("MAR"))
            return 3;
        if(date.contains("APR"))
            return 4;
        if(date.contains("MAY"))
            return 5;
        if(date.contains("JUN"))
            return 6;
        if(date.contains("JUL"))
            return 7;
        if(date.contains("AUG"))
            return 8;
        if(date.contains("SEP"))
            return 9;
        if(date.contains("OCT"))
            return 10;
        if(date.contains("NOV"))
            return 11;
        if(date.contains("DEC"))
            return 12;

        //default should never happen
        return 1;
    }

    //a method to go back to ProgramUserActivity when clicking back arrow in bottom
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, ShareActivity.class);

        intent.putExtra("activeUserName", activeUserName);

        startActivity(intent);

        finish();

    }

}