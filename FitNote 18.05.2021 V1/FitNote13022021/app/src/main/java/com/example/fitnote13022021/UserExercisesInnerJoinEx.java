package com.example.fitnote13022021;

public class UserExercisesInnerJoinEx {

    //UserExercisesInnerJoinEx (int userExerciseID, int exerciseID, String exerciseName, int exercisePic, String exerciseDetail)

    private int userExerciseID;
    private int exerciseID;
    private String exerciseName;
    private int exercisePic;
    private String exerciseDetail;

    //constructors
    public UserExercisesInnerJoinEx(int userExerciseID, int exerciseID, String exerciseName, int exercisePic, String exerciseDetail) {
        this.userExerciseID = userExerciseID;
        this.exerciseID = exerciseID;
        this.exerciseName = exerciseName;
        this.exercisePic = exercisePic;
        this.exerciseDetail = exerciseDetail;
    }


    // toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return "UserExercisesInerJoinEx{" +
                "userExerciseID=" + userExerciseID +
                ", exerciseID=" + exerciseID +
                ", exerciseName='" + exerciseName + '\'' +
                ", exercisePic=" + exercisePic +
                ", exerciseDetail='" + exerciseDetail + '\'' +
                '}';
    }


    //Getters and Setters
    public int getUserExerciseID() {
        return userExerciseID;
    }

    public void setUserExerciseID(int userExerciseID) {
        this.userExerciseID = userExerciseID;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public void setExerciseID(int exerciseID) {
        this.exerciseID = exerciseID;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getExercisePic() {
        return exercisePic;
    }

    public void setExercisePic(int exercisePic) {
        this.exercisePic = exercisePic;
    }

    public String getExerciseDetail() {
        return exerciseDetail;
    }

    public void setExerciseDetail(String exerciseDetail) {
        this.exerciseDetail = exerciseDetail;
    }

}
