package com.example.fitnote13022021;

public class User {

    //  User Table:
    // (userName TEXT PRIMARY KEY, userPassword TEXT, userLevel INTEGER,
    // userWeight INTEGER, userHeight INTEGER, userBirthDate TEXT, userGender TEXT, profilePic INTEGER)

    private String userName;
    private String userPassword;
    private int userWeight;
    private int userHeight;
    private String userBirthDate;
    private String userGender;
    private String profilePic;

    //constructors
    //constructor for all info
    public User(String userName, String userPassword, int userWeight, int userHeight, String userBirthDate, String userGender, String profilePic) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userWeight = userWeight;
        this.userHeight = userHeight;
        this.userBirthDate = userBirthDate;
        this.userGender = userGender;
        this.profilePic = profilePic;
    }

    //constructor for user
    public User(User user) {

        this.userName = user.getUserName();
        this.userPassword =user.getUserPassword();
        this.userWeight =user.getUserWeight();
        this.userHeight =user.getUserHeight();
        this.userBirthDate =user.getUserBirthDate();
        this.userGender =user.getUserGender();
        this.profilePic =user.getProfilePic();

    }

    //constructors
    //constructor for no info
    public User() {
    }

    // toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userWeight=" + userWeight +
                ", userHeight=" + userHeight +
                ", userBirthDate='" + userBirthDate + '\'' +
                ", userGender='" + userGender + '\'' +
                ", profilePic=" + profilePic +
                '}';
    }

    //Getters and Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

    public int getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(int userHeight) {
        this.userHeight = userHeight;
    }

    public String getUserBirthDate() {
        return userBirthDate;
    }

    public void setUserBirthDate(String userBirthDate) {
        this.userBirthDate = userBirthDate;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getUserLevel(){
        return 0;
    }

}
