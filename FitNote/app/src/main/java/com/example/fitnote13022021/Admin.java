package com.example.fitnote13022021;

public class Admin extends User{

    private boolean isSuperAdmin;

    //constructors
    //constructor for all info
    public Admin(String userName, String userPassword,boolean isSuperAdmin , int userWeight, int userHeight, String userBirthDate, String userGender, String profilePic) {

        super(userName, userPassword, userWeight, userHeight, userBirthDate, userGender, profilePic);

        this.isSuperAdmin = isSuperAdmin;

    }

    //constructor for user
    public Admin(User user, boolean isSuperAdmin ) {

        super(user.getUserName(), user.getUserPassword(), user.getUserWeight(), user.getUserHeight(), user.getUserBirthDate(), user.getUserGender(), user.getProfilePic());

        this.isSuperAdmin = isSuperAdmin;

    }

    // toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return super.toString()+
                "\n" +
                "Admin{" +
                "isSuperAdmin=" + isSuperAdmin +
                '}';
    }

    //Getters and Setters
    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    public int getUserLevel(){
        if (isSuperAdmin())
            return 2;
        else
            return 1;
    }

}
