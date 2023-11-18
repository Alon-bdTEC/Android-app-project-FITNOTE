package com.example.fitnote13022021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    DataBaseHelper dataBaseHelper;

    ImageView imgProfilePic, imgCemraButton;

    Bitmap bitmapToSave;

    private static final int REQUEST_IMAGE_CAPTURE = 100;

    EditText etUsername, etPassword;

    TextView txtSeekBarWeight, txtSeekBarHeight;
    SeekBar seekBarWeight, seekBarHeight;

    DatePickerDialog datePickerDialog;
    Button dateButton;

    Button btFinishReg, btCancel;

    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialize DataBaseHelper
        dataBaseHelper = new DataBaseHelper(this);

        //initialize date picker
        initDatePicker();
        dateButton = findViewById(R.id.btDatePickerButton);
        dateButton.setText(getTodaysDate());

        //finding XML parts
        imgProfilePic = findViewById(R.id.imgProfilePic);
        imgCemraButton = findViewById(R.id.imgCemraButton);
        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);

        txtSeekBarWeight = findViewById(R.id.txtSeekBarWeight);
        txtSeekBarHeight = findViewById(R.id.txtSeekBarHeight);

        seekBarWeight = findViewById(R.id.seekBarWeight);
        seekBarHeight = findViewById(R.id.seekBarHeight);

        radioGroup = findViewById(R.id.radioGroup);

        btFinishReg = findViewById(R.id.btFinishReg);
        btCancel = findViewById(R.id.btCancel);

        //setting a tag to the imgProfilePic to be the default image:
        imgProfilePic.setTag(R.drawable.default_user);

        seekBarWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtSeekBarWeight.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtSeekBarHeight.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //setting textChange listener
        etUsername.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);

        //setting click listener
        btFinishReg.setOnClickListener(this);
        btCancel.setOnClickListener(this);
        imgCemraButton.setOnClickListener(this);

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //gets editTexts inputs, trim() -> removing spaces between strings
        String usernameInput = etUsername.getText().toString().trim();
        String passwordInput = etPassword.getText().toString().trim();

        boolean allWritten = !usernameInput.isEmpty() && !passwordInput.isEmpty();

        // true - only if all buttons have text
        btFinishReg.setEnabled(allWritten);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        if(v == btFinishReg)
        {
            //gets the id of the checked radio button in radio group
            int radioId = radioGroup.getCheckedRadioButtonId();

            //sets radioButton variable to whatever is checked
            radioButton = findViewById(radioId);

            //sets String gender to be checked gender
            String gender = radioButton.getText().toString();

            ///  User (username TEXT PRIMARY KEY, pass TEXT, birthYear INT, gender TEXT)

            //  User Table:
            // (userName TEXT PRIMARY KEY, userPassword TEXT, userWeight INTEGER,
            // userHeight INTEGER, userBirthDate TEXT, userGender TEXT, profilePic INTEGER)
            String userName = etUsername.getText().toString();
            String userPassword = etPassword.getText().toString();
            Integer userWeight = Integer.parseInt(txtSeekBarWeight.getText().toString());
            Integer userHeight= Integer.parseInt(txtSeekBarHeight.getText().toString());
            String userBirthDate = dateButton.getText().toString();
            String userGender = gender;
            String profilePic = userName;

            //putting user info in User table
            User user = new User(userName, userPassword, userWeight, userHeight, userBirthDate, userGender,profilePic);

            //method to search for user with the same userName (true - user exists | false - user doesn't exist)
            if(dataBaseHelper.searchUserByName(user.getUserName()) == false) {
                dataBaseHelper.insertUser(user);

                saveToInternalStorage(userName);


                Toast.makeText(this, user + " is inserted", Toast.LENGTH_SHORT).show();

                //going to MainScreenActivity
                Intent intent = new Intent(this, MainScreenActivity.class);
                startActivity(intent);
                finish();

            } else{
                Toast.makeText(this, "user with the name " + user.getUserName() + " already exists", Toast.LENGTH_SHORT).show();
            }


        }

        if(v == btCancel)
        {
            Toast.makeText(this, "Canceling...", Toast.LENGTH_SHORT).show();

            //going to MainScreenActivity
            Intent intent = new Intent(this, MainScreenActivity.class);
            startActivity(intent);
            finish();
        }

        if(v == imgCemraButton)
        {
            takePicture(imgCemraButton);
        }

    }

    //method to start another application using and intent object
    public void takePicture(View view){

        //the intents intention is to capture an image
        //we have to specify the action for the intent
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //now we are able to check if there is any application
        //capable of handling this intent
        //otherwise your'e application will crush
        if(imageTakeIntent.resolveActivity(getPackageManager())  != null)
        {
            //we have to user startActivityForResult method
            //second parameter is the requestCode
            startActivityForResult(imageTakeIntent, REQUEST_IMAGE_CAPTURE);
        }



    }

    //in order to receive the result from the other application we
    //have to override a method called onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //we have to check if the requestCode is the same
        //AND if the requestCode is ok
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //here we have the data available on this intent
            //now we can retrieve the data
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //now we can display the image on the image view
            imgProfilePic.setImageBitmap(imageBitmap);

            //getting the image to save
            bitmapToSave = imageBitmap;
        }


    }
    //save image to phone
    private void saveToInternalStorage(String userName){
        //bitmapToSave

        if(bitmapToSave != null && userName != null){
            PictureFileHelper.writeFileToInternalStorage(this, bitmapToSave, userName);
        }else {
            //default pic
            Bitmap bitmap_default_user = BitmapFactory.decodeResource(getResources(),R.drawable.default_user);
            PictureFileHelper.writeFileToInternalStorage(this, bitmap_default_user, userName);
        }


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

    //initialize date picker
    private void initDatePicker() {
        //setting date listener
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                //adding 1 to month because its default is 0
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        //setting default date to be current date
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        // (context to datePickerDialog, style, date listener, year, month, day)
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //sets max date to be today
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

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

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    @Override
    public void onBackPressed() {
        //going to MainScreenActivity
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
        finish();
    }
}