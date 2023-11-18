package com.example.fitnote13022021;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditUserActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    //Initialize variables
    DataBaseHelper dataBaseHelper;

    User userChosen;

    String activeUserName;

    int activeUserLevel;

    EditText etPasswordEditUser;

    TextView txtTitleEditUser, txtSeekBarWeightEditUser, txtSeekBarHeightEditUser;

    SeekBar seekBarWeightEditUser, seekBarHeightEditUser;

    DatePickerDialog datePickerDialog;
    Button btDatePickerButtonEditUser, btUpdateEditUser, btCancelEditUser, btDeleteEditUser;

    RadioGroup radioGroupGenderEditUser, radioGroupUserLevelEditUser;
    RadioButton radioButtonGender, radioButtonUserLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        //setting database
        dataBaseHelper = new DataBaseHelper(this);

        //Assign btDatePickerButtonEditUser
        btDatePickerButtonEditUser = findViewById(R.id.btDatePickerButtonEditUser);
        //initialize date picker
        initDatePicker();
        btDatePickerButtonEditUser.setText(getTodaysDate());

        Intent intent = getIntent();

        String userNameChosen = intent.getStringExtra("userNameChosen");
        activeUserName = intent.getStringExtra("activeUserName");
        activeUserLevel = intent.getIntExtra("activeUserLevel", 0);

        //getting chosen user from database
        userChosen = dataBaseHelper.getUserByName(userNameChosen);

        //Assign variables
        etPasswordEditUser = findViewById(R.id.etPasswordEditUser);

        txtTitleEditUser = findViewById(R.id.txtTitleEditUser);
        txtSeekBarWeightEditUser = findViewById(R.id.txtSeekBarWeightEditUser);
        txtSeekBarHeightEditUser = findViewById(R.id.txtSeekBarHeightEditUser);

        seekBarWeightEditUser = findViewById(R.id.seekBarWeightEditUser);
        seekBarHeightEditUser = findViewById(R.id.seekBarHeightEditUser);

        btUpdateEditUser = findViewById(R.id.btUpdateEditUser);
        btCancelEditUser = findViewById(R.id.btCancelEditUser);
        btDeleteEditUser = findViewById(R.id.btDeleteEditUser);

        radioGroupGenderEditUser = findViewById(R.id.radioGroupGenderEditUser);
        radioGroupUserLevelEditUser = findViewById(R.id.radioGroupUserLevelEditUser);

        //setting title to show userNameChosen:
        txtTitleEditUser.setText("Edit " + userChosen.getUserName() + " :");

        //setting seekBar and txt to show progress from user chosen
        seekBarWeightEditUser.setProgress(userChosen.getUserWeight());
        txtSeekBarWeightEditUser.setText(""+userChosen.getUserWeight());
        seekBarHeightEditUser.setProgress(userChosen.getUserHeight());
        txtSeekBarHeightEditUser.setText(""+userChosen.getUserHeight());

        seekBarWeightEditUser.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtSeekBarWeightEditUser.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarHeightEditUser.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtSeekBarHeightEditUser.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //setting the edit text to show password
        etPasswordEditUser.setText(userChosen.getUserPassword());

        //setting textChange listener
        etPasswordEditUser.addTextChangedListener(this);

        //setting click listener
        btUpdateEditUser.setOnClickListener(this);
        btCancelEditUser.setOnClickListener(this);
        btDeleteEditUser.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == btUpdateEditUser)
        {
            //gets the id of the checked radio button in radio group
            int radioIdGender = radioGroupGenderEditUser.getCheckedRadioButtonId();

            //sets radioButton variable to whatever is checked
            radioButtonGender = findViewById(radioIdGender);

            //sets String gender to be checked gender
            String gender = radioButtonGender.getText().toString();

            //gets the id of the checked radio button in radio group
            int radioIdUserLevel = radioGroupUserLevelEditUser.getCheckedRadioButtonId();

            //sets radioButton variable to whatever is checked
            radioButtonUserLevel = findViewById(radioIdUserLevel);

            //sets String gender to be checked gender
            String userLevelTXT = radioButtonUserLevel.getText().toString();

            int chosenUserLevel = 0;

            if (userLevelTXT.equals("Normal")){
                chosenUserLevel = 0;
            }else if (userLevelTXT.equals("Admin")){
                chosenUserLevel = 1;
            }else if (userLevelTXT.equals("Super Admin")){
                chosenUserLevel = 2;
            }

            ///  User (username TEXT PRIMARY KEY, pass TEXT, birthYear INT, gender TEXT)

            //  User Table:
            // (userName TEXT PRIMARY KEY, userPassword TEXT, userWeight INTEGER,
            // userHeight INTEGER, userBirthDate TEXT, userGender TEXT, profilePic INTEGER)
            String userName = userChosen.getUserName();
            String userPassword = etPasswordEditUser.getText().toString();
            Integer userWeight = Integer.parseInt(txtSeekBarWeightEditUser.getText().toString());
            Integer userHeight= Integer.parseInt(txtSeekBarHeightEditUser.getText().toString());
            String userBirthDate = btDatePickerButtonEditUser.getText().toString();
            String userGender = gender;
            String profilePic = userName;

            User user = new User(userName, userPassword, userWeight, userHeight, userBirthDate, userGender,profilePic);

            //putting user info in User table
            if(chosenUserLevel == 0){
                User user1 = new User(userName, userPassword, userWeight, userHeight, userBirthDate, userGender,profilePic);
                //update chosen user info
                dataBaseHelper.updateUser(user1);
            }


            //if user is not noraml
            if (chosenUserLevel == 1 || chosenUserLevel == 2){
                Admin user2;

                if (chosenUserLevel == 1)
                    user2 = new Admin(userName, userPassword, false,userWeight, userHeight, userBirthDate, userGender,profilePic);
                else
                    user2 = new Admin(userName, userPassword, true,userWeight, userHeight, userBirthDate, userGender,profilePic);

                //update chosen user info
                dataBaseHelper.updateUserAsAdmin(user2);

            }



            //going to ManagerUsersActivity
            Intent intent = new Intent(this, ManagerUsersActivity.class);
            intent.putExtra("activeAdminName", activeUserName);
            intent.putExtra("activeAdminLevel",activeUserLevel);
            startActivity(intent);
            finish();

        }

        if(v == btCancelEditUser)
        {
            //going to ManagerUsersActivity
            Intent intent = new Intent(this, ManagerUsersActivity.class);
            intent.putExtra("activeAdminName", activeUserName);
            intent.putExtra("activeAdminLevel",activeUserLevel);
            startActivity(intent);
            finish();
        }

        if (v == btDeleteEditUser){

            dataBaseHelper.deleteUser(userChosen);

            //going to ManagerUsersActivity
            Intent intent = new Intent(this, ManagerUsersActivity.class);
            intent.putExtra("activeAdminName", activeUserName);
            intent.putExtra("activeAdminLevel",activeUserLevel);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //gets editTexts inputs, trim() -> removing spaces between strings
        String passwordInput = etPasswordEditUser.getText().toString().trim();

        boolean allWritten = !passwordInput.isEmpty();

        // true - only if all buttons have text
        btUpdateEditUser.setEnabled(allWritten);
    }

    @Override
    public void afterTextChanged(Editable s) {

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
                btDatePickerButtonEditUser.setText(date);

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

    //a method to go back to SettingsActivity when clicking back arrow in bottom
    @Override
    public void onBackPressed() {

        //going to ManagerUsersActivity
        Intent intent = new Intent(this, ManagerUsersActivity.class);
        intent.putExtra("activeAdminName", activeUserName);
        intent.putExtra("activeAdminLevel",activeUserLevel);
        startActivity(intent);
        finish();

    }

}