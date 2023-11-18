package com.example.fitnote13022021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FeedbackActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;

    int userExerciseID;
    int timeCount = 0;
    int repetition = 0;
    String date;

    TextView txtTestInput, txtFirstSentence, txtSecondSentence;

    NumberPicker numberPicker;

    Button btnFinish;

    RadioGroup radioGroup;
    RadioButton radioButton;

    String activeUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        dataBaseHelper = new DataBaseHelper(this);

        //finding XML parts
        txtTestInput = findViewById(R.id.txtTestInput);
        txtFirstSentence = findViewById(R.id.txtFirstSentence);
        txtSecondSentence = findViewById(R.id.txtSecondSentence);
        numberPicker = findViewById(R.id.numberPicker);
        btnFinish = findViewById(R.id.btnFinish);
        radioGroup = findViewById(R.id.radioGroup);

        //setting txtInput
        txtTestInput.setText("Repetition: " + repetition);

        //getting the userExerciseId and seconds count from ExecuteExerciseActivity
        Intent intent = getIntent();

        userExerciseID = intent.getIntExtra("userExerciseID", 0);
        timeCount = intent.getIntExtra("countOfExercise", 0);
        date = intent.getStringExtra("date");
        activeUserName = intent.getStringExtra("activeUserName");

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(150);

        //setting a listener to repetition choose
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                repetition = newVal;
                txtTestInput.setText("Repetition: " + repetition);
            }
        });

        //setting a listener to click on finishing reporting feedback
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //gets the id of the checked radio button in radio group
                int radioId = radioGroup.getCheckedRadioButtonId();

                //sets radioButton variable to whatever is checked
                radioButton = findViewById(radioId);

                //sets String gender to be checked gender
                int rating = Integer.parseInt(radioButton.getText().toString());

                ////method to update userExercise
                //changeUserExercise(int userExerciseID, String date, int time, int rating, int repetition)
                dataBaseHelper.updateUserExercise(userExerciseID, date, timeCount, rating, repetition);

                //setting an intent to go back to the ProgramUserActivity after finishing an exercise
                Intent intent = new Intent(FeedbackActivity.this, ProgramUserActivity.class);

                intent.putExtra("activeUserName", activeUserName);

                startActivity(intent);

                finish();

            }
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