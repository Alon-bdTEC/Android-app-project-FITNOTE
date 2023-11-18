package com.example.fitnote13022021;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainScreenActivity extends AppCompatActivity implements View.OnClickListener {

    //Initialize variables
    DataBaseHelper dataBaseHelper;

    EditText etUsername, etPassword;

    Button btRegister, btLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting database
        dataBaseHelper = new DataBaseHelper(this);

        //toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //https://www.youtube.com/watch?v=DMkzIOLppf4
        //3:50
        //** https://www.youtube.com/watch?v=IrJ8Hzuz2LU

        //finding XML parts
        //Assign variables
        etUsername = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btLogIn = findViewById(R.id.btLogIn);
        btRegister = findViewById(R.id.btRegister);

        //setting click listener
        btLogIn.setOnClickListener(this);
        btRegister.setOnClickListener(this);

        //adding exercises (one time)
        //dataBaseHelper.deleteDatabase(this);
        //dataBaseHelper.addExercises();

    }

    @Override
    public void onClick(View v) {
        //  User Table:
        // (userName TEXT PRIMARY KEY, userPassword TEXT,
        // userWeight INTEGER, userHeight INTEGER, userBirthDate TEXT,
        // userGender TEXT)
        String userName = etUsername.getText().toString();
        String userPassword = etPassword.getText().toString();

        if (v == btRegister) {

            //clear text on editTexts
            etUsername.setText("");
            etPassword.setText("");

            //going to SignIn activity
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        }

        if (v == btLogIn) {

            if (dataBaseHelper.searchUserByNameAndPass(userName, userPassword)) {
                Toast.makeText(this, "Welcome: " + userName, Toast.LENGTH_SHORT).show();

                //clear text on editTexts
                etUsername.setText("");
                etPassword.setText("");

                //going to programs activity
                Intent intent = new Intent(this, ProgramUserActivity.class);
                intent.putExtra("activeUserName", userName);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "No user with that info", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //a method that sets an AlertDialog if the user wants to leave
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Do you want to leave? ");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                MainScreenActivity.super.onBackPressed();
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

    //on create menu method (inflating) the layout in menu)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    //on item click of menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.aboutItem){
            //going to InformationActivity activity
            Intent intent = new Intent(this, InformationActivity.class);

            startActivity(intent);

            finish();

        }
        //shows dialog if user wants to exit
        if (id == R.id.exitItem){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Do you want to leave? ");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    MainScreenActivity.super.onBackPressed();
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

        return true;
    }

}