package com.example.fitnote13022021;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
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

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    //Initialize variables
    DataBaseHelper dataBaseHelper;

    String activeUserName;

    int activeUserLevel;

    User activeUser;

    ImageView imgSettingUserPic;

    TextView txtSettingsUserNameShow, txtSettingsUserGenderShow
            , txtSettingsPassword, txtSettingsWeightHeight
            , txtSettingsGenderEdit, txtSettingsPictureEdit
            ,txtAdminOPTitleSettings;

    View layoutSettingsPassword, layoutSettingsWeightAndHeight
            ,layoutSettingBirthDate, layoutSettingsGender, layoutSettingPic
            ,layoutSettingManagerUsers;

    DatePickerDialog datePickerDialog;
    Button dateButton;

    private static final int REQUEST_IMAGE_CAPTURE = 100;
    Bitmap bitmapToSave;
    ImageView imgSettingsEditProfilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //setting database
        dataBaseHelper = new DataBaseHelper(this);

        Intent intent = getIntent();

        activeUserName = intent.getStringExtra("activeUserName");

        activeUser = dataBaseHelper.getUserByName(activeUserName);

        //Assign variables
        imgSettingUserPic = findViewById(R.id.imgSettingUserPic);

        txtSettingsUserNameShow = findViewById(R.id.txtSettingsUserNameShow);
        txtSettingsUserGenderShow = findViewById(R.id.txtSettingsUserGenderShow);
        txtSettingsPassword = findViewById(R.id.txtSettingsPassword);
        txtSettingsWeightHeight = findViewById(R.id.txtSettingsWeightHeight);
        txtSettingsGenderEdit = findViewById(R.id.txtSettingsGenderEdit);
        txtSettingsPictureEdit = findViewById(R.id.txtSettingsPictureEdit);
        txtAdminOPTitleSettings = findViewById(R.id.txtAdminOPTitleSettings);

        layoutSettingsPassword = findViewById(R.id.layoutSettingsPassword);
        layoutSettingsWeightAndHeight = findViewById(R.id.layoutSettingsWeightAndHeight);
        layoutSettingBirthDate = findViewById(R.id.layoutSettingBirthDate);
        layoutSettingsGender = findViewById(R.id.layoutSettingsGender);
        layoutSettingPic = findViewById(R.id.layoutSettingPic);
        layoutSettingManagerUsers = findViewById(R.id.layoutSettingManagerUsers);

        //Setting the userPic to be the activeUser's pic
        //changing the userPicture to show the user's picture
        Bitmap userPic = PictureFileHelper.getPic(this, activeUserName);
        if(userPic != null && imgSettingUserPic != null){
            imgSettingUserPic.setImageBitmap(userPic);
        }

        //changing layoutSettingManagerUsers to show if user is admin at least (1 or 2 in userLevel)
        activeUserLevel = dataBaseHelper.getUserLevelByUserName(activeUserName);

        //if user is admin at least, show layoutSettingManagerUsers:
        if (activeUserLevel == 1 || activeUserLevel == 2){
            layoutSettingManagerUsers.setVisibility(View.VISIBLE);

            //txt: option: manage users
            String textFromAdminTitle = txtAdminOPTitleSettings.getText().toString();

            if(activeUserLevel == 1){
                txtAdminOPTitleSettings.setText("Admin " + textFromAdminTitle);
            }else {
                txtAdminOPTitleSettings.setText("Super-Admin " + textFromAdminTitle);
            }

        }

        //Setting text to be user's info from database
        txtSettingsUserNameShow.setText(activeUser.getUserName());
        txtSettingsUserGenderShow.setText(activeUser.getUserGender());
        txtSettingsPassword.setText(activeUser.getUserPassword());
        txtSettingsWeightHeight.setText("Weight: " + activeUser.getUserWeight() + " Height: " + activeUser.getUserHeight());
        txtSettingsGenderEdit.setText(activeUser.getUserGender());

        layoutSettingsPassword.setOnClickListener(this);
        layoutSettingsWeightAndHeight.setOnClickListener(this);
        layoutSettingBirthDate.setOnClickListener(this);
        layoutSettingsGender.setOnClickListener(this);
        layoutSettingPic.setOnClickListener(this);
        layoutSettingManagerUsers.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        //If user wants to change his password
        if (v == layoutSettingsPassword){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Do you want to change the password? ");

            //Setting the builder's editTexts
            final EditText passwordInput = new EditText(SettingsActivity.this);
            passwordInput.setText(activeUser.getUserPassword());

            //Setting the builder's editTexts in the builder
            builder.setView(passwordInput);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    //Setting the updatedUser
                    User updatedUser = activeUser;

                    //Setting the password to be the new one
                    updatedUser.setUserPassword(passwordInput.getText().toString());

                    dataBaseHelper.updateUser(updatedUser);

                    activeUser = updatedUser;

                    txtSettingsPassword.setText(activeUser.getUserPassword());

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

        //If user wants to change his weight and/or height
        if (v == layoutSettingsWeightAndHeight){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Do you want to change the weight and/or height? ");

            //Setting the builder's layout inflater
            View alertView = getLayoutInflater().inflate(R.layout.weight_height_settings_alert, null);

            //Setting the view to be in builder of alert dialog
            builder.setView(alertView);

            //Getting the xml parts from alert view
            SeekBar seekBarSettingsWeight, seekBarSettingsHeight;
            TextView txtSeekBarSettingsWeight, txtSeekBarSettingsHeight;

            seekBarSettingsWeight = alertView.findViewById(R.id.seekBarSettingsWeight);
            seekBarSettingsHeight = alertView.findViewById(R.id.seekBarSettingsHeight);
            txtSeekBarSettingsWeight = alertView.findViewById(R.id.txtSeekBarSettingsWeight);
            txtSeekBarSettingsHeight = alertView.findViewById(R.id.txtSeekBarSettingsHeight);

            //Setting for both seekers change listeners
            seekBarSettingsWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    txtSeekBarSettingsWeight.setText(""+progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            seekBarSettingsHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    txtSeekBarSettingsHeight.setText(""+progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });


            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    //Setting the updatedUser
                    User updatedUser = activeUser;

                    //Setting the weight and/or height to be the new one
                    updatedUser.setUserWeight(Integer.parseInt(txtSeekBarSettingsWeight.getText().toString()));
                    updatedUser.setUserHeight(Integer.parseInt(txtSeekBarSettingsHeight.getText().toString()));

                    dataBaseHelper.updateUser(updatedUser);

                    activeUser = updatedUser;

                    txtSettingsWeightHeight.setText("Weight: " + activeUser.getUserWeight() + " Height: " + activeUser.getUserHeight());

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

        //If user wants to change his birth date
        if (v == layoutSettingBirthDate){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Do you want to change your birth date? ");

            //Setting the builder's datePickerDialog
            //initialize date picker
            dateButton = new Button(SettingsActivity.this);

            dateButton.setWidth(250);
            dateButton.setHeight(200);
            dateButton.setText("JAN 01 2020");
            dateButton.setTextColor(Color.BLACK);
            dateButton.setTextSize(30);
            //dateButton.setScrollBarStyle(Integer.parseInt("?android:spinnerStyle"));
            dateButton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  datePickerDialog.show();
                                              }
                                          });

            initDatePicker();
            dateButton.setText(getTodaysDate());

            //Setting the builder's editTexts in the builder
            builder.setView(dateButton);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    //Setting the updatedUser
                    User updatedUser = activeUser;

                    //Setting the birth date to be the new one
                    updatedUser.setUserBirthDate(dateButton.getText().toString());

                    dataBaseHelper.updateUser(updatedUser);

                    activeUser = updatedUser;

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

        //If user wants to change his gender
        if (v == layoutSettingsGender){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Do you want to change your gender? ");

            //Setting the builder's layout inflater
            View alertView = getLayoutInflater().inflate(R.layout.gender_settings_alert, null);

            //Setting the view to be in builder of alert dialog
            builder.setView(alertView);

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    // Setting the updatedUser
                    User updatedUser = activeUser;

                    // Setting the gender to be the new one
                    RadioButton radioButton;

                    final RadioGroup radioGroupSettingsGender = (RadioGroup)alertView.findViewById(R.id.radioGroupSettingsGender);

                    // get selected radioButton from radioGroup // radioGroupSettingsGender.getCheckedRadioButtonId()
                    int selectedId = radioGroupSettingsGender.getCheckedRadioButtonId();

                    // find the radioButton by returned id
                    radioButton = (RadioButton) alertView.findViewById(selectedId);

                    // radioButton text
                    String radiovalue = String.valueOf(radioButton.getText());

                    updatedUser.setUserGender(radiovalue);

                    dataBaseHelper.updateUser(updatedUser);

                    activeUser = updatedUser;

                    txtSettingsGenderEdit.setText(activeUser.getUserGender());

                    txtSettingsUserGenderShow.setText(activeUser.getUserGender());

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

        //If user wants to change his profile pic
        if (v == layoutSettingPic) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Do you want to change your profile pic? ");

            //Setting the builder's layout inflater
            View alertView = getLayoutInflater().inflate(R.layout.profilepic_settings_alert, null);

            //Setting the view to be in builder of alert dialog
            builder.setView(alertView);

            //Setting the user picture to be the new one
            imgSettingsEditProfilePic = alertView.findViewById(R.id.imgSettingsEditProfilePic);
            final ImageView imgSettingsEditCemraButton = alertView.findViewById(R.id.imgSettingsEditCemraButton);

            imgSettingsEditCemraButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    takePicture(imgSettingsEditCemraButton);
                }
            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    // Setting the updatedUser
                    User updatedUser = activeUser;

                    updatedUser.setProfilePic(activeUser.getUserName());

                    dataBaseHelper.updateUser(updatedUser);

                    activeUser = updatedUser;

                    // Setting the imageView of user pic
                    // from the settings layout to show new pic
                    imgSettingUserPic.setImageBitmap(bitmapToSave);

                    //save image to internal storage
                    saveToInternalStorage(activeUser.getUserName());

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

        //if the user(admin/superAdmin) wants to go to manager users
        if (v == layoutSettingManagerUsers){

            //going to ManagerUsersActivity
            Intent intent = new Intent(this, ManagerUsersActivity.class);
            intent.putExtra("activeAdminName", activeUser.getUserName());
            intent.putExtra("activeAdminLevel",activeUserLevel);
            startActivity(intent);
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

    //method to start another application using and intent object
    public void takePicture(View view){

        //the intents intention is to capture an image
        //we have to specify the action for the indent
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
    //have to override a method called
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
            //imgSettingsEditProfilePic is the profile pic on the alert
            //and on the imageView from the settings layout
            imgSettingsEditProfilePic.setImageBitmap(imageBitmap);

            //getting the image to save
            bitmapToSave = imageBitmap;
        }


    }
    //save image to phone
    private void saveToInternalStorage(String userName){
        //bitmapToSave

        if(bitmapToSave != null){
            PictureFileHelper.writeFileToInternalStorage(this, bitmapToSave, userName);
        }else {
            //default pic
            Bitmap bitmap_default_user = BitmapFactory.decodeResource(getResources(),R.drawable.default_user);
            PictureFileHelper.writeFileToInternalStorage(this, bitmap_default_user, userName);
        }


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