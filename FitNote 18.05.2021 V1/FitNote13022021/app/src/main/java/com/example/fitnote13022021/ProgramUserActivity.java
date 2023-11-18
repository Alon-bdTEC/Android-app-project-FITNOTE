package com.example.fitnote13022021;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ProgramUserActivity extends AppCompatActivity implements View.OnClickListener {

    //Initialize variables
    public static String activeUserName = null;

    DataBaseHelper dataBaseHelper;

    Button btnSetAlarm, btnCancelAlarm;

    ImageView imgProgramUserPic;

    TextView txtMainTitle, txtEmptyListMessage, tv_timer;

    ListView listViewUserExercises;

    ArrayList<UserExercisesInnerJoinEx> activeUsersExercises;

    UserExerciseNotDoneAdapter userExerciseAdaptor;

    ImageView btnAddExercise;

    SearchView searchViewUserExerciseList;

    private int tvHour=0, tvMinute=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_user);

        //creating the notification channel (if needed)
        createNotificationChannel();

        dataBaseHelper = new DataBaseHelper(this);

        //finding XML parts / Assign variables
        imgProgramUserPic = findViewById(R.id.imgProgramUserPic);
        listViewUserExercises = findViewById(R.id.listViewUserExercises);
        txtMainTitle = findViewById(R.id.txtMainTitle);
        txtEmptyListMessage = findViewById(R.id.txtEmptyListMessage);
        tv_timer = findViewById(R.id.tv_timer);
        btnAddExercise = findViewById(R.id.btnAddExercise);
        searchViewUserExerciseList = findViewById(R.id.searchViewUserExerciseList);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);
        btnCancelAlarm = findViewById(R.id.btnCancelAlarm);


        Intent intent = getIntent();

        //getting the active userName from getting the user who entered with log in with his name given from the intent
        String userName = intent.getStringExtra("activeUserName");

        activeUserName = userName;

        //changing textView to show userName's name as well
        String welcomeText = txtMainTitle.getText().toString();
        txtMainTitle.setText(welcomeText + " " + activeUserName);

        //changing the userPicture to show the user's picture

        Bitmap userPic = null;

        if(userName != null)
            userPic = PictureFileHelper.getPic(this, userName);

        if(userPic != null && imgProgramUserPic != null){
            imgProgramUserPic.setImageBitmap(userPic);
        }


        activeUsersExercises = dataBaseHelper.getExercisesByUserNameGivenNotDone(activeUserName);

        //setting up a new adaptor
        userExerciseAdaptor = new UserExerciseNotDoneAdapter(activeUsersExercises, this, activeUserName);

        listViewUserExercises.setAdapter(userExerciseAdaptor);

        //setting the txtEmptyListMessage visible if the listView is empty
        if(activeUsersExercises.isEmpty()){
            txtEmptyListMessage.setVisibility(View.VISIBLE);
        }

        //goes to ExecuteExerciseActivity
        listViewUserExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(ProgramUserActivity.this, "1", Toast.LENGTH_SHORT).show();


            }
        });

        //initialize the searchWidget in order to filter exercises
        initSearchWidgets();

        btnAddExercise.setOnClickListener(this);

        tv_timer.setOnClickListener(this);

        btnSetAlarm.setOnClickListener(this);

        btnCancelAlarm.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        //if the user wants to add an exercise
        if(v == btnAddExercise){

            //going to SignIn activity
            Intent intent = new Intent(this, AddExerciseActivity.class);

            //adding extra
            intent.putExtra("activeUserName", activeUserName);

            startActivity(intent);

            //finishing the activity to go to onCreate when coming back
            finish();

        }

        //if the user want to set himself a timer
        if(v == tv_timer){
            //Initialize time picked dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    ProgramUserActivity.this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            //Initialize hour and minute
                            tvHour = hourOfDay;
                            tvMinute = minute;
                            //Initialize calendar
                            Calendar calendar = Calendar.getInstance();
                            //Set hour and minute
                            calendar.set(0,0,0,tvHour,tvMinute);
                            //Set selected time on text view
                            tv_timer.setText(android.text.format.DateFormat.format("h:mm a", calendar));
                        }
                    },12,0,true
            );
            //Displayed previous selected time
            timePickerDialog.updateTime(tvHour,tvMinute);
            //Show dialog
            timePickerDialog.show();
        }

        if(v == btnSetAlarm || v == btnCancelAlarm){

            //Set notificationID & message
            Intent notificationIntent = new Intent(
                    ProgramUserActivity.this, ReminderBroadcast.class);

            notificationIntent.putExtra("message", "Its time to train " + activeUserName);
            notificationIntent.putExtra("activeUserName", activeUserName);

            //PendingIntent
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    ProgramUserActivity.this, 0, notificationIntent, 0
            );
            //FLAG_CANCEL_CURRENT-if pending already exists, the current one will be canceled.
            //FLAG_UPDATE_CURRENT-indicates that the pendingIntent
            //which we created now can be updated in the future


            //AlarmManager
            //Use with getSystemService to retrieve an AlarmManager
            //for receiving intents at a time of your choosing
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            switch (v.getId()){
                case R.id.btnSetAlarm:
                    // Set Alarm - we got hour and minutes from: tvHour,tvMinute

                    // Create time
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(Calendar.HOUR_OF_DAY, tvHour);
                    startTime.set(Calendar.MINUTE, tvMinute);
                    startTime.set(Calendar.SECOND, 1);
                    long alarmStartTime = startTime.getTimeInMillis();

                    //checking that the time given is not before now
                    if (alarmStartTime <= System.currentTimeMillis()){

                        Toast.makeText(this, "This time is before now! : " + startTime.getTime(), Toast.LENGTH_LONG).show();

                    }else{
                        // Set Alarm
                        //AlarmManager.RTC_WAKEUP - wakes up the device to fire the pending intent
                        //at the specified time
                        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);

                        Toast.makeText(this, "Set Done! : " + startTime.getTime(), Toast.LENGTH_LONG).show();
                    }

                    break;

                case R.id.btnCancelAlarm:
                    // Cancel Alarm
                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "LembuitReminderChannel";
            String description = "Channel for Lembuit Remider";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    //initialize the searchWidget in order to filter exercises
    public void initSearchWidgets(){

        SearchView searchView = (SearchView)searchViewUserExerciseList;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            //this method is called every time a user
            //puts in any character into the search view
            //literally any change :D
            @Override
            public boolean onQueryTextChange(String newText) {

                ArrayList<UserExercisesInnerJoinEx> filteredExercises = new ArrayList<UserExercisesInnerJoinEx>();

                //same as a regular for lop
                for (UserExercisesInnerJoinEx userExercisesInnerJoinEx: activeUsersExercises)
                {
                    //if an exercise as one of the letter in the written text
                    if(userExercisesInnerJoinEx.getExerciseName().toLowerCase().contains(newText.toLowerCase(Locale.ROOT))){
                        filteredExercises.add(userExercisesInnerJoinEx);
                    }
                }

                UserExerciseNotDoneAdapter userExerciseAdaptor = new UserExerciseNotDoneAdapter(filteredExercises, ProgramUserActivity.this);

                listViewUserExercises.setAdapter(userExerciseAdaptor);

                return false;
            };
        });

    }

    //on create menu method (inflating) the layout in menu)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.program_user_menu, menu);

        return true;
    }

    //on item click of menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //if the user wants to see graphs of his performances
        if (id == R.id.graphsItem){

            //going to StatisticsActivity activity
            Intent intent = new Intent(this, StatisticsActivity.class);

            //adding extra
            intent.putExtra("activeUserName", activeUserName);

            startActivity(intent);

            //finishing the activity to go to onCreate when coming back
            finish();
        }

        //if the user wants to change his settings
        if (id == R.id.settingsItem){
            //going to SettingsActivity activity
            Intent intent = new Intent(this, SettingsActivity.class);

            //adding extra
            intent.putExtra("activeUserName", activeUserName);

            startActivity(intent);

            //finishing the activity to go to onCreate when coming back
            finish();
        }

        //if the user wants to share his data with others
        if(id == R.id.shareItem){

            //going to ShareActivity
            Intent intent = new Intent(this, ShareActivity.class);
            intent.putExtra("activeUserName", activeUserName);
            startActivity(intent);
            finish();

        }

        //if the user wants to see his results on a list
        if (id == R.id.exercisesResultsItem){
            //going to ViewExercisesResultsActivity
            Intent intent = new Intent(this, ViewExercisesResultsActivity.class);
            intent.putExtra("activeUserName", activeUserName);
            startActivity(intent);
            finish();
        }


        return true;
    }

    //a method to go back to SettingsActivity when clicking back arrow in bottom
    @Override
    public void onBackPressed() {

        //going to MainScreenActivity
        Intent intent = new Intent(this, MainScreenActivity.class);
        startActivity(intent);
        finish();

    }

}