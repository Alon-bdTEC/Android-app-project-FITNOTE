package com.example.fitnote13022021;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class MusicAndTimerService extends Service {

    private DataBaseHelper dataBaseHelper;

    private MediaPlayer mediaPlayer;

    private CountDownTimer countDownTimer;

    public static int count = 0;

    //this method does binds the service
    //with and activity
    //קושרת את השירות עם הפעילות
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        dataBaseHelper = new DataBaseHelper(this);
        ArrayList<Song> songs = dataBaseHelper.getSongs();

        int arrayLength = songs.size();

        Random random = new Random();
        //This gives a random integer number between 0 (inclusive) and (length of array-1)
        int randomNumber = random.nextInt(arrayLength);
        Song chosenSong = songs.get(randomNumber);

        Toast.makeText(this, "chosen song: " + chosenSong.getSongName() + "N: " + randomNumber, Toast.LENGTH_SHORT).show();

        //create the mediaPlayer
        mediaPlayer = MediaPlayer.create(this, chosenSong.getSongMP3());

        mediaPlayer.setLooping(true);

        if(countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer = null;
        }

        //setting up the timer
        //max: 2147483347millis = 35791.389116667minutes = 596.523151944449978 hours, sec to change: 1000
        countDownTimer = new CountDownTimer(Integer.MAX_VALUE - 300, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                count++;
                //setting text in play to be time count from timer in MusicAndTimerService
                ExecuteExerciseActivity.txtTime.setText(getDurationString(count));
            }

            @Override
            public void onFinish() {
                count = 0;
                ExecuteExerciseActivity.txtTime.setText(getDurationString(0));
            }
        };

    }

    //when we start the service the onStartCommand method will be called
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String action = intent.getStringExtra("ACTION");
        if(action.contentEquals("PLAY")){
            Toast.makeText(this, "PLAY", Toast.LENGTH_SHORT).show();
            //play music and timer
            mediaPlayer.start();
            countDownTimer.start();
        }else if (action.contentEquals("PAUSE")){
            Toast.makeText(this, "PAUSE", Toast.LENGTH_SHORT).show();
            //pause music and timer
            mediaPlayer.pause();
            countDownTimer.cancel();
        }

        // this means this service will be explicitly started and stopped
        //פירוש הדבר ששירות זה יופעל ויפסיק במפורש
        return START_STICKY;
    }


    //when the service is stopped onDestroy method will be called
    @Override
    public void onDestroy() {
        super.onDestroy();

        //stop music and timer
        mediaPlayer.stop();
        countDownTimer.cancel();
        count = 0;
        ExecuteExerciseActivity.txtTime.setText(getDurationString(0));

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

}
