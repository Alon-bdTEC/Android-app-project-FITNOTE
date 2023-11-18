package com.example.fitnote13022021;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

public class UsersAdapter extends BaseAdapter {

    private ArrayList<User> arrayList;
    private User user;

    //get activeUserName to not delete and change him
    String activeUserName;
    int activeUserLevel;

    public static Context context;

    public UsersAdapter(ArrayList<User> arrayList, Context context, String activeUserName, int activeUserLevel){
        this.arrayList = arrayList;
        this.context = context;
        this.activeUserName = activeUserName;
        this.activeUserLevel = activeUserLevel;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class MyHolder {
        ImageView imgUserPicUserLayout;
        TextView txtUserNameShowUserLayout, txtUserGenderShowUserLayout;

        public MyHolder(View v) {
            imgUserPicUserLayout = (ImageView) v.findViewById(R.id.imgUserPicUserLayout);

            txtUserNameShowUserLayout = (TextView) v.findViewById(R.id.txtUserNameShowUserLayout);
            txtUserGenderShowUserLayout = (TextView) v.findViewById(R.id.txtUserGenderShowUserLayout);
        }

    }

    // This is a very important method:
    // You can write your code in this function
    // You can set your vies methods:
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        MyHolder holder;

        // using polymorphism on users to get Admin info as well
        user = arrayList.get(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.user_layout, null);
            holder = new MyHolder(view);
            view.setTag(holder);
        }else {
            holder = (MyHolder) view.getTag();
        }

        //Setting the userPic to be the user's pic
        //changing the userPicture to show the user's picture
        Bitmap userPic = PictureFileHelper.getPic(context, user.getUserName());
        if(userPic != null && holder.imgUserPicUserLayout != null){
            holder.imgUserPicUserLayout.setImageBitmap(userPic);
        }

        //setting textviews to show userExercise info
        holder.txtUserNameShowUserLayout.setText(user.getUserName());
        holder.txtUserGenderShowUserLayout.setText(user.getUserGender());

        return view;
    }

}
