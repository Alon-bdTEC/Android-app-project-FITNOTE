package com.example.fitnote13022021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShareActivity extends AppCompatActivity {

    //Initialize variable
    String activeUserName;

    TextView txtShareWithContactScreen;

    RecyclerView recyclerViewContacts;

    ArrayList<ContactModel> contactList = new ArrayList<ContactModel>();

    ArrayList<ContactModel> filteredContacts;

    ContactListAdapter adapter;

    ContactListAdapter.RecycleViewClickListener listener;

    SearchView searchViewContactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Intent intent = getIntent();

        activeUserName = intent.getStringExtra("activeUserName");

        //Assign variables
        txtShareWithContactScreen = findViewById(R.id.txtShareWithContactScreen);
        recyclerViewContacts = findViewById(R.id.recyclerViewContacts);
        searchViewContactList = findViewById(R.id.searchViewContactList);

        //edit TextView
        String text = txtShareWithContactScreen.getText().toString();
        txtShareWithContactScreen.setText(text + " " + activeUserName);

        //Check permission
        checkPermission();

        //initialize the searchWidget in order to filter exercises
        initSearchWidgets();


    }

    private void checkPermission() {
        //Check condition
        if (ContextCompat.checkSelfPermission(ShareActivity.this
                , Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED){
            //When permission is not granted
            //Request permission
            ActivityCompat.requestPermissions(ShareActivity.this
            , new String[]{Manifest.permission.READ_CONTACTS}, 100);
        }else {
            //When permission is granted
            //Create method
            getContactList();
        }
    }

    private void getContactList() {
        //Initialize uri
        //uniform resource identifier
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        //Sort by ascending (ASC)
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" ASC";

        //Initialize cursos
        Cursor cursor = getContentResolver().query(
                uri, null, null, null, sort
        );

        //Check condition
        if(cursor.getCount() > 0){
            //When count is greater than 0
            //Use while loop
            while (cursor.moveToNext()){
                //Cursor move to next
                //Get contact id
                String id = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts._ID
                ));
                //Get contact name
                String name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                ));
                //Initialize phone uri
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                //Initialize selection
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                        +" =?";
                //Initialize phone cursor
                Cursor phoneCursor = getContentResolver().query(
                        uriPhone, null, selection
                        , new String[]{id}, null
                );
                //Check condition
                if (phoneCursor.moveToNext()) {
                    //When phone cursor move to next
                    String number = phoneCursor.getString(phoneCursor.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    ));
                    //Initialize contact model
                    ContactModel model = new ContactModel();
                    //Set name
                    model.setName(name);
                    //Set number
                    if(number.charAt(0) == '0')
                    {
                        number = "+972 "+number.substring(1);
                    }
                    model.setNumber(number);
                    //Add model in array list
                    contactList.add(model);
                    //Close number cursor
                    phoneCursor.close();
                }
            }
            //Close cursor
            cursor.close();
        }

        //setting filtered list to be contactList
        filteredContacts = contactList;

        //Set layout manager
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));

        //setOnClickListener
        setOnClickListener();

        //Initialize adapter
        adapter = new ContactListAdapter(this, contactList, listener);
        //Set adapter
        recyclerViewContacts.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listener = new ContactListAdapter.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                //now we are creating an intent to go to the WhatsAppSendActivity
                Intent intent = new Intent(getApplicationContext(), WhatsAppSendActivity.class);

                //now we want to get the contact ID, name and number:
                ContactModel contactChosen = filteredContacts.get(position);
                String name = contactChosen.getName();
                String number = contactChosen.getNumber();

                intent.putExtra("contactName", name);
                intent.putExtra("contactNumber", number);
                intent.putExtra("activeUserName", activeUserName);

                startActivity(intent);

                finish();

            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Check condition
        if (requestCode == 100 & grantResults.length > 0 &&grantResults[0]
        == PackageManager.PERMISSION_GRANTED){
            //When permission is granted
            //Call method
            getContactList();
        }else {
            //When permission is denied
            //Display toast
            Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
            //Call check permission method
            checkPermission();
        }
    }

    //initialize the searchWidget in order to filter contacts
    public void initSearchWidgets(){

        SearchView searchView = (SearchView)searchViewContactList;

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

                filteredContacts = new ArrayList<ContactModel>();

                //same as a regular for lop
                for (ContactModel contactModel: contactList)
                {
                    //if a contact has one of the letter in the written text
                    //OR a contact has one of the numbers in the written number
                    if(contactModel.getName().toLowerCase().contains(newText.toLowerCase())
                    || contactModel.getNumber().contains(newText)){
                        filteredContacts.add(contactModel);
                    }
                }

                ContactListAdapter contactListAdapter = new ContactListAdapter(ShareActivity.this, filteredContacts,  listener);

                recyclerViewContacts.setAdapter(contactListAdapter);

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