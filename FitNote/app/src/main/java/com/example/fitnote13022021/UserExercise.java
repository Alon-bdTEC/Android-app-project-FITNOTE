package com.example.fitnote13022021;

public class UserExercise{

    //table userExercises
    //(userExerciseID INTEGER PRIMARY KEY AUTOINCREMENT,
    // userName TEXT, exerciseID INTEGER, date TEXT, time INTEGER,
    // rating INTEGER, repetition INTEGER)

    private int userExerciseID;
    private  String userName;
    private  int exerciseID;
    private String date;
    private int time;
    private int rating;
    private int repetition;

    //constructors
    //constructor for new UserExercises
    public UserExercise(String userName, int exerciseID) {
        this.userName = userName;
        this.exerciseID = exerciseID;

        this.date = null;
        this.time = 0;
        this.rating = 0;
        this.repetition = 0;
    }

    //constructor to take exsisting newUserExercises
    public UserExercise(int userExerciseID, String userName, int exerciseID, String date, int time, int rating, int repetition) {
        this.userExerciseID = userExerciseID;
        this.userName = userName;
        this.exerciseID = exerciseID;
        this.date = date;
        this.time = time;
        this.rating = rating;
        this.repetition = repetition;
    }

    // toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return "UserExercise{" +
                "userExerciseID=" + userExerciseID +
                ", userName='" + userName + '\'' +
                ", exerciseID=" + exerciseID +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", rating=" + rating +
                ", repetition=" + repetition +
                '}';
    }

    //Getters and Setters
    public int getUserExerciseID() {
        return userExerciseID;
    }

    public void setUserExerciseID(int userExerciseID) {
        this.userExerciseID = userExerciseID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(int exerciseID) {
        this.exerciseID = exerciseID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

}
