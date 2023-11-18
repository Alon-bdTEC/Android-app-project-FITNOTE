package com.example.fitnote13022021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class StatisticsActivity extends AppCompatActivity {

    DataBaseHelper dataBaseHelper;

    ImageView imgUserPicture;

    //Objects to choose exercise in graph

    Spinner exercise_spinner;

    ArrayList<Exercise> exercises;

    ArrayList<String> exerciseNames;

    Spinner year_spinner;

    ArrayList<String> years;

    //Button to change filter of exercise in graph

    Button btnSubmit;

    //Objects to create the BarChart(graph)

    BarChart barChart;

    BarData barData;

    BarDataSet barDataSet;

    ArrayList<BarEntry> barEntries;

    ArrayList<UserExercise> userExercisesDone;

    ArrayList<Integer> colors = new ArrayList<Integer>();

    //Values to save

    String activeUserName;

    //Filter ID of exercise for graph
    int exerciseIDToCheck = 1;
    String yearToCheck = "2021";

    //Object for date format

    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd", Locale.ENGLISH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        dataBaseHelper = new DataBaseHelper(this);

        //finding xml parts
        //finding the BarChart (the graph)
        //finding spinner
        imgUserPicture = findViewById(R.id.imgUserPicture);
        barChart = findViewById(R.id.barChart);
        exercise_spinner = findViewById(R.id.exercise_spinner);
        year_spinner = findViewById(R.id.year_spinner);
        btnSubmit = findViewById(R.id.btnSubmit);


        //setting the exercise_spinner list
        exerciseNames = new ArrayList<String>();
        //getting the exercises
        exercises = dataBaseHelper.getExercises();

        //populating the exercise spinner
        populateExerciseSpinner();

        //setting the year_spinner list
        int yearNow = 2021;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            yearNow = now.getYear();
        }

        //getting the years
        years = yearRange("2020",String.valueOf(yearNow));

        //populating the year spinner
        populateYearSpinner();

        //getting userName from intent
        Intent intent = getIntent();

        activeUserName = intent.getStringExtra("activeUserName");

        //changing the userPicture to show the user's picture
        Bitmap userPic = PictureFileHelper.getPic(this, activeUserName);
        if(userPic != null && imgUserPicture != null){
            imgUserPicture.setImageBitmap(userPic);
        }

        //getting userExercises by the active user name
        userExercisesDone = dataBaseHelper.getUserExercisesDoneByUserName(activeUserName);

        updateDataInBarChart();

        //setting onClickListener to btnSubmit
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //gets the exercise name that was chosen
                String exerciseNameChosen = exercise_spinner.getSelectedItem().toString();

                int size = exercises.size();

                //searching for exercise which has that name
                //and setting the chosen exercise's ID to be
                //the one who is filtering the barChart
                for (int i=0; i<size; i++){
                    Exercise exercise = exercises.get(i);
                    if(exercise.getExerciseName().contentEquals(exerciseNameChosen)){
                        exerciseIDToCheck = exercise.getExerciseID();
                    }
                }

                //gets the chosen year
                String chosenYear = year_spinner.getSelectedItem().toString();

                //setting the chosen year to be
                //the one who is filtering the barChart
                yearToCheck = chosenYear;

                updateDataInBarChart();

            }
        });

    }

    private void updateDataInBarChart(){

        //calling a method to clear the barChart in order to update it properly
        barChart.clear();

        //retrieve data for our barChart
        getData();

        //now we will start our barChart
        String labelDataSet = "Number above column is the repetition." + "\n";

        labelDataSet += " Year: " + yearToCheck;

        barDataSet = new BarDataSet(barEntries, labelDataSet);

        int wow = barEntries.size();

        for (int i=0; i<wow; i++){
            Toast.makeText(this, "barEntries " + barEntries.get(i).getX(), Toast.LENGTH_SHORT).show();
        }

        barData = new BarData(barDataSet);

        //now we have to set our data in our bar chart
        barChart.setData(barData);

        barChart.getDescription().setText("Exercises data");

        //customizing barChart
        //setting the colors to be the colors representing
        //the user's ratings
        barDataSet.setColors(colors);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(18f);

        //formatting XAxis to show date
        XAxis xAxis = barChart.getXAxis();

        //JAN FEB MAR APR MAY JUN JUL AUG SEP OCT NOV DEC
        String[] months = new String[] {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));

        xAxis.setTextSize(3f);
        xAxis.setLabelCount(12);
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        //setting YAxis
        YAxis yAxisLeft = barChart.getAxisLeft();
        YAxis yAxisRight = barChart.getAxisRight();

        yAxisLeft.setAxisMaximum(150);
        yAxisLeft.setAxisMinimum(0);

        yAxisRight.setAxisMaximum(150);
        yAxisRight.setAxisMinimum(0);


        barChart.setDragEnabled(true);
        //barChart.setVisibleXRangeMaximum(20);//maybe 20?

        float barSpace = 0.08f;
        float groupSpace = 0.44f;

        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(12 + 1f);
        //barChart.getAxisLeft().setAxisMinimum(0);


        //barChart.groupBars(0, groupSpace, barSpace);

        barChart.invalidate();

    }

    //adds content to exercise spinner
    private void populateExerciseSpinner() {

        int size = exercises.size();

        for (int i=0; i<size; i++){
            Exercise exercise = exercises.get(i);
            exerciseNames.add(exercise.getExerciseName());
        }

        ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, exerciseNames);

        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        exercise_spinner.setAdapter(exerciseAdapter);

    }

    //adds content to year spinner
    private void populateYearSpinner(){

        ArrayAdapter<String> exerciseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, years);

        exerciseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        year_spinner.setAdapter(exerciseAdapter);
    }

    //a method to get data to barChard (to barEntries)
    private void getData(){

        barEntries = new ArrayList<BarEntry>();

        if(userExercisesDone != null)
        {
            int length = userExercisesDone.size();

            for (int i=0; i<length; i++) {

                UserExercise userExercise = userExercisesDone.get(i);

                //checks the filter to what exercise should the graph show
                if(userExercise.getExerciseID() == exerciseIDToCheck){

                    int rating = userExercise.getRating();
                    int repetition = userExercise.getRepetition();
                    String date = userExercise.getDate();
                    //EXAMPLE DATE: "FEB 28 2021" 11 (year - 7-10, day - 5-6, month 0-2)

                    String[] splited = date.split("\\s+");

                    int year = Integer.parseInt(splited[2]);
                    int day = Integer.parseInt(splited[1]);
                    int month = getMonthNumber(date);

                    //String dateForGraph = day + " " +  month + " " + year;

                    //checks the filter to what year should the graph show
                    if(year == Integer.parseInt(yearToCheck)) {
                        long dateInMillis = getTimeInMillis(day, month, year);

                        Date dateOfExercise = new Date(year, month, day);

                        Toast.makeText(this, "month " + month + " repetition " + repetition + " yearToCheck " + yearToCheck, Toast.LENGTH_SHORT).show();

                        BarEntry entry = new BarEntry((int)month, repetition);

                        //adding a color that represents the difficulty of
                        //doing the exercise
                        //the color is added to a list of colors later adapted
                        //to the barChart

                        colorRating(rating);

                        barEntries.add(entry);

                    }

                }

            }
        }


    }

    private void colorRating(int rating){

        switch(rating) {
            case 1:
                //Hard
                colors.add(getResources().getColor(R.color.Hard));
                break;
            case 2:
                //Hard-Medium
                colors.add(getResources().getColor(R.color.HardMedium));
                break;
            case 3:
                //
                colors.add(getResources().getColor(R.color.Medium));
                break;
            case 4:
                //Easy-Medium
                colors.add(getResources().getColor(R.color.EasyMedium));
                break;
            case 5:
                //Easy
                colors.add(getResources().getColor(R.color.Easy));
                break;
            default:
                colors.add(getResources().getColor(R.color.Easy));
                break;
        }

    }

    //This Method will convert given specific day,month and year into milliseconds.
    // It will be very help when using Timpicker or Datepicker.
    public static long getTimeInMillis(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }

    //makes the real month a number month (JAN -> 1)
    private int getMonthNumber(String date) {
        if(date.contains("JAN"))
            return 1;
        if(date.contains("FEB"))
            return 2;
        if(date.contains("MAR"))
            return 3;
        if(date.contains("APR"))
            return 4;
        if(date.contains("MAY"))
            return 5;
        if(date.contains("JUN"))
            return 6;
        if(date.contains("JUL"))
            return 7;
        if(date.contains("AUG"))
            return 8;
        if(date.contains("SEP"))
            return 9;
        if(date.contains("OCT"))
            return 10;
        if(date.contains("NOV"))
            return 11;
        if(date.contains("DEC"))
            return 12;

        //default should never happen
        return 1;
    }

    public static ArrayList<String> yearRange(String startYear, String endYear) {
        int cur = Integer.parseInt(startYear);
        int stop = Integer.parseInt(endYear);
        ArrayList<String> list = new ArrayList<String>();
        while (cur <= stop) {
            list.add(String.valueOf(cur++));
        }
        return list;
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