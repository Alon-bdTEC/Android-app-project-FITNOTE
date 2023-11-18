package com.example.fitnote13022021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ManagerUsersActivity extends AppCompatActivity {

    //Initialize variables
    DataBaseHelper dataBaseHelper;

    String activeUserName;

    User activeUser;

    TextView txtWelcomeManageUsers, txtExplainManageUser;

    ListView listViewManagerUsers;

    SearchView searchViewManageUsersList;

    ArrayList<User> users;

    ArrayList<User> filteredUsers;

    UsersAdapter usersAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_users);

        //setting database
        dataBaseHelper = new DataBaseHelper(this);

        Intent intent = getIntent();

        activeUserName = intent.getStringExtra("activeAdminName");

        //getting user from database
        activeUser = dataBaseHelper.getUserByName(activeUserName);

        //Assign variables
        txtWelcomeManageUsers = findViewById(R.id.txtWelcomeManageUsers);
        txtExplainManageUser = findViewById(R.id.txtExplainManageUser);

        listViewManagerUsers = findViewById(R.id.listViewManagerUsers);

        searchViewManageUsersList = findViewById(R.id.searchViewManageUsersList);

        users = dataBaseHelper.getUsersAndAdmins();

        filteredUsers = users;

        usersAdaptor = new UsersAdapter(users, this, activeUserName, activeUser.getUserLevel());

        listViewManagerUsers.setAdapter(usersAdaptor);

        //initialize the searchWidget in order to filter exercises
        initSearchWidgets();

        String userLevel = "Admin";

        if (activeUser.getUserLevel() == 2){
            userLevel = "Super-Admin";
        }

        Toast.makeText(this, activeUserName + " Level: " + activeUser.getUserLevel(), Toast.LENGTH_LONG).show();

        //settings text to say hello userName
        txtWelcomeManageUsers.setText("Welcome " + activeUserName + " a known " + userLevel);

        //settings click listener to list
        listViewManagerUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String userNameChosen = users.get(position).getUserName();

                //avoid user changing his own info and prevent non SUPER ADMIN people to do it
                if(!userNameChosen.equals(activeUserName) && activeUser.getUserLevel() == 2){
                    //going to EditUserActivity
                    Intent intent = new Intent(ManagerUsersActivity.this, EditUserActivity.class);
                    intent.putExtra("activeUserName", activeUserName);
                    intent.putExtra("activeUserLevel",activeUser.getUserLevel());
                    intent.putExtra("userNameChosen", users.get(position).getUserName());
                    startActivity(intent);
                }else {
                    if(userNameChosen.equals(activeUserName))
                        Toast.makeText(ManagerUsersActivity.this, "YOU CANT EDIT YOURSELF!!!!!!", Toast.LENGTH_SHORT).show();
                    else if (activeUser.getUserLevel() != 2)
                        Toast.makeText(ManagerUsersActivity.this, "Your'e level doesn't allow you to edit users", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //initialize the searchWidget in order to filter exercises
    public void initSearchWidgets(){

        SearchView searchView = (SearchView)searchViewManageUsersList;

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

                filteredUsers = new ArrayList<User>();

                //same as a regular for lop
                for (User user: users)
                {

                    int userLevel = user.getUserLevel();
                    String txtLevel = "Normal";
                    if (userLevel == 1){
                        txtLevel = "Admin";
                    }else if(userLevel == 2){
                        txtLevel = "Super-Admin";
                    }

                    //if the user's name has one of the letters in the written text
                    if(user.getUserName().toLowerCase().contains(newText.toLowerCase(Locale.ROOT))){
                        filteredUsers.add(user);
                    }

                    //if the user's password has one of the letters in the written text
                    else if(user.getUserPassword().toLowerCase().contains(newText.toLowerCase(Locale.ROOT))){
                        filteredUsers.add(user);
                    }

                    //if the user's weight has one of the letters in the written text
                    else if(Integer.toString(user.getUserWeight()).contains(newText.toLowerCase(Locale.ROOT))){
                        filteredUsers.add(user);
                    }

                    //if the user's height has one of the letters in the written text
                    else if(Integer.toString(user.getUserHeight()).contains(newText.toLowerCase(Locale.ROOT))){
                        filteredUsers.add(user);
                    }

                    //if the user's birth date has one of the letters in the written text
                    else if(user.getUserBirthDate().toLowerCase().contains(newText.toLowerCase(Locale.ROOT))){
                        filteredUsers.add(user);
                    }

                    //if the user's gender has one of the letters in the written text
                    else if(user.getUserGender().toLowerCase().contains(newText.toLowerCase(Locale.ROOT))){
                        filteredUsers.add(user);
                    }

                    //if the user's level has one of the letters in the written text
                    else if(txtLevel.toLowerCase().contains(newText.toLowerCase(Locale.ROOT))){
                        filteredUsers.add(user);
                    }

                }

                UsersAdapter usersAdaptorFiltered = new UsersAdapter(filteredUsers, ManagerUsersActivity.this, activeUserName, activeUser.getUserLevel());

                listViewManagerUsers.setAdapter(usersAdaptorFiltered);

                return false;
            };
        });

    }

    //a method to go back to SettingsActivity when clicking back arrow in bottom
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, SettingsActivity.class);

        intent.putExtra("activeUserName", activeUserName);

        startActivity(intent);

        finish();

    }

}