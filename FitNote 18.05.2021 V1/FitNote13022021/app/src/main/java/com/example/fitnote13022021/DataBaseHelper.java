package com.example.fitnote13022021;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    //Initialize variables
    private static final String DATABASE_NAME = "fitnote_database12345678";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "Users";
    private static final String TABLE_EXERCISES = "Exercises";
    private static final String TABLE_USEREXERCISES = "UserExercises";
    private static final String TABLE_SONGS = "Songs";

    DataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create tables
        String tableUser = "CREATE TABLE " + TABLE_USER + " (userName TEXT PRIMARY KEY, userPassword TEXT, userLevel INTEGER, userWeight INTEGER, userHeight INTEGER, userBirthDate TEXT, userGender TEXT, profilePic INTEGER)";
        //userLevel 0-normal, 1-admin, 2-superAdmin
        String tableExercise = "CREATE TABLE " + TABLE_EXERCISES + " (exerciseID INTEGER PRIMARY KEY, exerciseName TEXT, exercisePic INTEGER, exerciseDetail TEXT)";
        String tableUserExercises = "CREATE TABLE " + TABLE_USEREXERCISES + " (userExerciseID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT, exerciseID INTEGER, date TEXT, time INTEGER, rating INTEGER, repetition INTEGER)";
        String tableSongs = "CREATE TABLE " + TABLE_SONGS + " (songID INTEGER PRIMARY KEY, songName TEXT, songMP3 INTEGER)";


        db.execSQL(tableUser);
        db.execSQL(tableExercise);
        db.execSQL(tableUserExercises);
        db.execSQL(tableSongs);

        // adding exercises
        // Exercise Table;
        //  (exerciseID INT PRIMARY KEY, exerciseName TEXT,
        //  exercisePic INT, exerciseDetail TEXT)
        db.execSQL("INSERT INTO " + TABLE_EXERCISES + " VALUES (1, 'Push Up', "+R.drawable.pushup+", '1. Get down on all fours, placing your hands slightly wider than your shoulders. " +
                "\n" +
                "2. Straighten your arms and legs. " +
                "\n" +
                "3. Lower your body until your chest nearly touches the floor. " +
                "\n" +
                "4. Pause, then push yourself back up. " +
                "\n" +
                "5. Repeat.  ')");

        db.execSQL("INSERT INTO " + TABLE_EXERCISES + " VALUES (2, 'Squat', "+R.drawable.squat+", '1. Find a foot stance that feels best for you. Pointing your toes slightly outwards helps some, but keeping them parallel is fine, too. If you’re not sure what’s best, start by putting your feet shoulder-width apart and pointed about 15 degrees outwards. " +
                "\n" +
                "2. Tense your abs like someone is about to punch you. " +
                "\n" +
                "3. Look straight ahead and stand tall!')");

        db.execSQL("INSERT INTO " + TABLE_EXERCISES + " VALUES (3, 'Running', "+R.drawable.running+", 'TIPS: When you first start out, try alternating between running and walking during your session. As time goes on, make the running intervals longer until you no longer feel the need to walk. " +
                "Give yourself a few minutes to cool down after each run by walking and a doing few stretches. Try our post-run stretch routine.')");

        db.execSQL("INSERT INTO " + TABLE_EXERCISES + " VALUES (4, 'RopeJump', "+R.drawable.ropejump+", 'Get a large rope that you can pass under your feet. Move the rope under your feet while you jump and repeat.')");

        db.execSQL("INSERT INTO " + TABLE_EXERCISES + " VALUES (5, 'JumpingJacks', "+R.drawable.jumping_jacks+", 'JumpingJacks: Stand upright with your legs together, arms at your sides. "+
                "\n" +
                "Bend your knees slightly, and jump into the air. "+
                "\n" +
                "As you jump, spread your legs to be about shoulder-width apart. Stretch your arms out and over your head. " +
                "\n" +
                "Jump back to starting position. "+
                "\n" +
                "Repeat.')");

        db.execSQL("INSERT INTO " + TABLE_EXERCISES + " VALUES (6, 'TricepsDips', "+R.drawable.triceps_dips+", '1. You can do it from a chair or a bench, "+
                "you can even do it on the floor. "+
                "\n" +
                "2. So just getting yourself into position to start by rolling your shoulders down. "+
                "You want to start from good posture. Sometimes we tend to slump forward and then were gonna potentially cause an extra strain on the shoulder joint. "+
                "\n" +
                "3. So set yourself up by rolling the shoulders back, opening up the chest. Bringing your hands directly underneath your shoulders onto the bench or onto the ground. "+
                "And youre gonna take your legs out keeping your knees bent." +
                "\n" +
                "4. If you wanted to make it harder you could extend the legs. "+
                " So start with the easier option, see how you feel first. "+
                "\n" +
                "5. Youre gonna bend the elbows, lowering the hips down, and then exhale to extend the elbows and lift your body up. "+
                "Inhale down, exhale up.')");

        db.execSQL("INSERT INTO " + TABLE_EXERCISES + " VALUES (7, 'InclinePushUp', "+R.drawable.incline_pushup+", '1. Place your hands on the edge of the elevated surface. " +
                "\n" +
                "2. Step your feet back so your legs are straight and your arms are perpendicular to your body. "+
                "\n" +
                "3. Inhale as you slowly lower your chest to the edge of your platform. "+
                "\n" +
                "4. Pause for a second. "+
                "\n" +
                "5. Exhale as you push back to your starting position with your arms fully extended.')");

        db.execSQL("INSERT INTO " + TABLE_EXERCISES + " VALUES (8, 'JumpingSquat', "+R.drawable.jumping_squat+", 'Stand tall with your feet hip-width apart. "+
                "\n" +
                "Hinge at the hips to push your butt back and lower down until your thighs are parallel to the floor. "+
                "\n" +
                "Allow your knees to bend 45 degrees when you land, and then immediately drop back down into a squat, and jump again.')");

        db.execSQL("INSERT INTO " + TABLE_EXERCISES + " VALUES (9, 'HammerCurls', "+R.drawable.hammer_curls+", 'Step 1 "+
                "Stand up straight with a dumbbell in each hand, holding them alongside you. Your palms should face your body. Keep your feet hip-width apart and engage your core to stabilize the body. "+
                "\n" +
                "Step 2 "+
                "Keep your biceps stationary and start bending at your elbows, lifting both dumbbells. "+
                "\n" +
                "Step 3 " +
                "Lift until the dumbbells reach shoulder-level, but don’t actually touch your shoulders. Hold this contraction briefly, then lower back to the starting position and repeat.')");

        db.execSQL("INSERT INTO " + TABLE_EXERCISES + " VALUES (10, 'ShoulderPress', "+R.drawable.shoulder_press+", 'Stand with feet shoulder-width apart and hold the dumbbells at shoulder height with your elbows at a 90-degree angle. "+
                "\n" +
                "Slowly lift the dumbbells above your head without fully straightening your arms. Pause at the top. "+
                "\n" +
                "Slowly return to the start position.')");

        db.execSQL("INSERT INTO " + TABLE_EXERCISES + " VALUES (11, 'Swimming', "+R.drawable.swimming+", '1. Float with your face in the water, your body straight and horizontal. Stack your hands and keep your arms and legs long. " +
                "Point your thumbs down. " +
                "\n" +
                "2. Press your hands out and back in a circle, elbows high. Lift your head slightly and inhale. " +
                "\n" +
                "3. Bring your hands together in front of your shoulders, thumbs pointing up. Keep your elbows close to your body. Simultaneously bend your knees, bringing your feet toward your butt and pointing your feet outward. " +
                "\n" +
                "4. Reach your arms forward. Kick out and back in a circle then snap your feet together. Drop your head underwater and exhale. " +
                "\n" +
                "5. Glide forward and repeat.')");

        // adding songs
        db.execSQL("INSERT INTO " + TABLE_SONGS + " VALUES (1, 'speedrun', "+R.raw.speedrun+")");
        db.execSQL("INSERT INTO " + TABLE_SONGS + " VALUES (2, 'speedrun2', "+R.raw.speedrun2+")");
        db.execSQL("INSERT INTO " + TABLE_SONGS + " VALUES (3, 'speedrun3', "+R.raw.speedrun3+")");
        db.execSQL("INSERT INTO " + TABLE_SONGS + " VALUES (4, 'phantom', "+R.raw.phantom+")");
        db.execSQL("INSERT INTO " + TABLE_SONGS + " VALUES (5, 'shoping', "+R.raw.shoping+")");



        //Table Users:
        //(userName TEXT PRIMARY KEY, userPassword TEXT, userLevel INTEGER,
        // userWeight INTEGER, userHeight INTEGER, userBirthDate TEXT, userGender TEXT, profilePic INTEGER)
        //adding user alon - me the SUPER ADMIN
        db.execSQL("INSERT INTO " + TABLE_USER + " VALUES('alon', 123, 2, 90, 90, 'APR 2 2003', 'male', 'alon')");



        //adding userExercises
        //JAN FEB MAR APR MAY JUN JUL AUG SEP OCT NOV DEC
        //(userExerciseID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT,
        // exerciseID INTEGER, date TEXT, time INTEGER, rating INTEGER, repetition INTEGER)


        db.execSQL("INSERT INTO " + TABLE_USEREXERCISES + " VALUES(1, 'alon', 1, 'MAR 7 2021', 100, 1, 100)");
        db.execSQL("INSERT INTO " + TABLE_USEREXERCISES + " VALUES(2, 'alon', 2, 'JAN 8 2021', 10, 2, 10)");
        db.execSQL("INSERT INTO " + TABLE_USEREXERCISES + " VALUES(3, 'alon', 3, 'FEB 9 2021', 60, 3, 60)");
        db.execSQL("INSERT INTO " + TABLE_USEREXERCISES + " VALUES(4, 'alon', 4, 'APR 10 2020', 30, 4, 30)");
        db.execSQL("INSERT INTO " + TABLE_USEREXERCISES + " VALUES(5, 'alon', 5, 'JUN 11 2020', 50, 5, 50)");
        db.execSQL("INSERT INTO " + TABLE_USEREXERCISES + " VALUES(6, 'alon', 6, 'SEP 25 2020', 100, 1, 100)");
        db.execSQL("INSERT INTO " + TABLE_USEREXERCISES + " VALUES(7, 'alon', 7, 'MAR 7 2021', 20, 2, 20)");
        db.execSQL("INSERT INTO " + TABLE_USEREXERCISES + " VALUES(8, 'alon', 8, 'JAN 8 2021', 56, 3, 56)");
        db.execSQL("INSERT INTO " + TABLE_USEREXERCISES + " VALUES(9, 'alon', 9, 'FEB 9 2021', 34, 4, 34)");
        db.execSQL("INSERT INTO " + TABLE_USEREXERCISES + " VALUES(10, 'alon', 10, 'APR 10 2020', 70, 5, 70)");
        db.execSQL("INSERT INTO " + TABLE_USEREXERCISES + " VALUES(11, 'alon', 11, 'MAR 11 2021', 120, 1, 110)");






    }

    //Delete database
    public static void deleteDatabase(Context mContext) {
        mContext.deleteDatabase(DATABASE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop Existing Table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXERCISES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USEREXERCISES);
        onCreate(db);
    }

    //method to add user
    public boolean insertUser(User user) {
        //getWritableDatabase - method from the default properties of SQLiteOpenHelper
        //getWritableDatabase for insert actions...getReadableDatabase for select (read) actions.
        SQLiteDatabase db = this.getWritableDatabase();

        //  User Table:
        // (userName TEXT PRIMARY KEY, userPassword TEXT, userLevel INTEGER,
        // userWeight INTEGER, userHeight INTEGER, userBirthDate TEXT, userGender TEXT, profilePic INTEGER)
        ContentValues cv = new ContentValues();
        cv.put("userName", user.getUserName());
        cv.put("userPassword", user.getUserPassword());
        cv.put("userLevel", 0);
        cv.put("userWeight", user.getUserWeight());
        cv.put("userHeight", user.getUserHeight());
        cv.put("userBirthDate", user.getUserBirthDate());
        cv.put("userGender", user.getUserGender());
        cv.put("profilePic", user.getProfilePic());

        // insert - success variable... 1)positive -> it was inserted...2) -1 -> it was a fail
        long insert = db.insert(TABLE_USER, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    //method to add user
    public boolean insertAdmin(Admin admin) {
        //getWritableDatabase - method from the default properties of SQLiteOpenHelper
        //getWritableDatabase for insert actions...getReadableDatabase for select (read) actions.
        SQLiteDatabase db = this.getWritableDatabase();

        int userLevel = 1;

        if (admin.isSuperAdmin() == false){
            userLevel = 1;
        }
        else {
            userLevel = 2;
        }



        //  User Table:
        // (userName TEXT PRIMARY KEY, userPassword TEXT, userLevel INTEGER,
        // userWeight INTEGER, userHeight INTEGER, userBirthDate TEXT, userGender TEXT, profilePic INTEGER)
        ContentValues cv = new ContentValues();
        cv.put("userName", admin.getUserName());
        cv.put("userPassword", admin.getUserPassword());
        cv.put("userLevel", userLevel);
        cv.put("userWeight", admin.getUserWeight());
        cv.put("userHeight", admin.getUserHeight());
        cv.put("userBirthDate", admin.getUserBirthDate());
        cv.put("userGender", admin.getUserGender());
        cv.put("profilePic", admin.getProfilePic());

        // insert - success variable... 1)positive -> it was inserted...2) -1 -> it was a fail
        long insert = db.insert(TABLE_USER, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    //method to update User
    public boolean updateUser(User user){

        //getWritableDatabase - method from the default properties of SQLiteOpenHelper
        //getWritableDatabase for insert actions...getReadableDatabase for select (read) actions.
        SQLiteDatabase db = this.getWritableDatabase();

        // ContentValues - a special class that works like a associative  array (PHP)
        // can take pairs of values and associate with them (like the bundle in intent)
        // The ID column is auto increment
        //  User Table:
        // (userName TEXT PRIMARY KEY, userPassword TEXT, userLevel INTEGER,
        // userWeight INTEGER, userHeight INTEGER, userBirthDate TEXT, userGender TEXT, profilePic INTEGER)
        ContentValues cv = new ContentValues();
        cv.put("userName", user.getUserName());
        cv.put("userPassword", user.getUserPassword());
        cv.put("userLevel", user.getUserLevel());
        cv.put("userWeight", user.getUserWeight());
        cv.put("userHeight", user.getUserHeight());
        cv.put("userBirthDate", user.getUserBirthDate());
        cv.put("userGender", user.getUserGender());
        cv.put("profilePic", user.getProfilePic());

        // insert - success variable... 1)positive -> it was inserted...2) -1 -> it was a fail
        long result = db.update(TABLE_USER, cv, "userName=?", new String []{user.getUserName()});

        if(result == -1){
            return false;
        }else{
            return true;
        }

    }

    //method to update User
    public boolean updateUserAsAdmin(Admin user){

        //getWritableDatabase - method from the default properties of SQLiteOpenHelper
        //getWritableDatabase for insert actions...getReadableDatabase for select (read) actions.
        SQLiteDatabase db = this.getWritableDatabase();

        int userLevel = 1;

        if (user.isSuperAdmin())
            userLevel = 2;

        // ContentValues - a special class that works like a associative  array (PHP)
        // can take pairs of values and associate with them (like the bundle in intent)
        // The ID column is auto increment
        //  User Table:
        // (userName TEXT PRIMARY KEY, userPassword TEXT, userLevel INTEGER,
        // userWeight INTEGER, userHeight INTEGER, userBirthDate TEXT, userGender TEXT, profilePic INTEGER)
        ContentValues cv = new ContentValues();
        cv.put("userName", user.getUserName());
        cv.put("userPassword", user.getUserPassword());
        cv.put("userLevel", userLevel);
        cv.put("userWeight", user.getUserWeight());
        cv.put("userHeight", user.getUserHeight());
        cv.put("userBirthDate", user.getUserBirthDate());
        cv.put("userGender", user.getUserGender());
        cv.put("profilePic", user.getProfilePic());

        // insert - success variable... 1)positive -> it was inserted...2) -1 -> it was a fail
        long result = db.update(TABLE_USER, cv, "userName=?", new String []{user.getUserName()});

        if(result == -1){
            return false;
        }else{
            return true;
        }

    }

    //method to delete user (by ID because its the primary key)
    public void deleteUser(User user) {
        // find user in the database. if its found, delete it and return true.
        // if its not found, return false.

        // getWritableDatabase - we are going to delete from it
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, "userName=?", new String[]{user.getUserName()});

    }

    //method to get user's level
    public int getUserLevelByUserName(String givenUserName){
        ArrayList<User> returnList = new ArrayList<>();

        int userLevel = 0;

        //  User Table:
        // (userName TEXT PRIMARY KEY, userPassword TEXT, userLevel INTEGER,
        // userWeight INTEGER, userHeight INTEGER, userBirthDate TEXT, userGender TEXT, profilePic INTEGER)
        // get data from the database
        String  queryString = ("SELECT " + "*" + " FROM " + TABLE_USER + " WHERE " + "userName" + " = '" + givenUserName + "'");

        // get a reference to the active database
        // getWritableDatabase - insert, update or delete records
        // getReadableDatabase - SELECT items from the database
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor is the result set from a SQL statement
        Cursor cursor = db.rawQuery(queryString, null);

        // moveToFirst returns a true if there were items selected
        if(cursor.moveToFirst()){

           userLevel = cursor.getInt(2);

        }else{
            // failure. do not add anything to the list.
        }

        // always clean up after yourself
        // lets close the connection to the database so others can use it
        // close the cursor when done.
        cursor.close();

        return userLevel;
    }

    //method to get all users
    public ArrayList<User> getUsers(){

        ArrayList<User> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM " + TABLE_USER;

        // get a reference to the active database
        // getWritableDatabase - insert, update or delete records
        // getReadableDatabase - SELECT items from the database
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor is the result set from a SQL statement
        Cursor cursor = db.rawQuery(queryString, null);

        // moveToFirst returns a true if there were items selected
        if(cursor.moveToFirst()){

            // loop through the cursor (result set) and create new customer objects. Put them into the return list
            do{
                //  User Table:
                // (userName TEXT PRIMARY KEY, userPassword TEXT, userLevel INTEGER,
                // userWeight INTEGER, userHeight INTEGER, userBirthDate TEXT, userGender TEXT, profilePic INTEGER)
                String userName = cursor.getString(0);
                String userPassword = cursor.getString(1);
                int userLevel = cursor.getInt(2);
                int userWeight = cursor.getInt(3);
                int userHeight = cursor.getInt(4);
                String userBirthDate = cursor.getString(5);
                String userGender = cursor.getString(6);
                String profilePic = cursor.getString(7);

                User newUser = new User(userName, userPassword, userWeight, userHeight, userBirthDate, userGender, profilePic);
                returnList.add(newUser);

            } while (cursor.moveToNext());

        }else{
            // failure. do not add anything to the list.
        }

        // always clean up after yourself
        // lets close the connection to the database so others can use it
        // close the cursor when done.
        cursor.close();

        return returnList;

    }

    //method to get all users
    public ArrayList<User> getUsersAndAdmins(){

        ArrayList<User> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM " + TABLE_USER;

        // get a reference to the active database
        // getWritableDatabase - insert, update or delete records
        // getReadableDatabase - SELECT items from the database
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor is the result set from a SQL statement
        Cursor cursor = db.rawQuery(queryString, null);

        // moveToFirst returns a true if there were items selected
        if(cursor.moveToFirst()){

            // loop through the cursor (result set) and create new customer objects. Put them into the return list
            do{
                //  User Table:
                // (userName TEXT PRIMARY KEY, userPassword TEXT, userLevel INTEGER,
                // userWeight INTEGER, userHeight INTEGER, userBirthDate TEXT, userGender TEXT, profilePic INTEGER)
                String userName = cursor.getString(0);
                String userPassword = cursor.getString(1);
                int userLevel = cursor.getInt(2);
                int userWeight = cursor.getInt(3);
                int userHeight = cursor.getInt(4);
                String userBirthDate = cursor.getString(5);
                String userGender = cursor.getString(6);
                String profilePic = cursor.getString(7);

                //user is Admin at minimum
                if (userLevel == 1 || userLevel == 2){
                    //settings boolean of isSuperAdmin
                    boolean isSuperAdmin = false;
                    if (userLevel == 2)
                        isSuperAdmin = true;

                    User newUser = new Admin(userName, userPassword, isSuperAdmin,  userWeight, userHeight, userBirthDate, userGender, profilePic);
                    returnList.add(newUser);
                }
                //user is normal
                else {
                    User newUser = new User(userName, userPassword,  userWeight, userHeight, userBirthDate, userGender, profilePic);
                    returnList.add(newUser);
                }


            } while (cursor.moveToNext());

        }else{
            // failure. do not add anything to the list.
        }

        // always clean up after yourself
        // lets close the connection to the database so others can use it
        // close the cursor when done.
        cursor.close();

        return returnList;

    }

    //method to get user with specific name and password
    //method to search for user (true - user exists | false - user doesn't exist)
    public boolean searchUserByNameAndPass(String userName, String userPassword){
        //get all users from user table
        List<User> Users = this.getUsers();

        // if there are no users
        if(Users.isEmpty() || (userName.isEmpty() && userPassword.isEmpty()) )
            return false;

        for (int i=0; i<Users.size(); i++) {
            User user = Users.get(i);
            if(user.getUserName().contentEquals(userName) && user.getUserPassword() .contentEquals(userPassword)){
                return true;
            }
        }

        return false;

    }

    //method to search for user (true - user exists | false - user doesn't exist)
    public boolean searchUserByName(String userName){
        //get all users from user table
        List<User> Users = this.getUsersAndAdmins();

        // if there are no users
        if(Users.isEmpty())
            return false;

        for (int i=0; i<Users.size(); i++) {
            User user = Users.get(i);
            if(user.getUserName().contentEquals(userName)){
                return true;
            }
        }

        return false;

    }

    //method to get for user (true - user exists | false - user doesn't exist)
    public User getUserByName(String userName){
        //get all users from user table
        ArrayList<User> Users = this.getUsersAndAdmins();

        // if there are no users
        if(Users.isEmpty() || userName == null)
            return null;

        for (int i=0; i<Users.size(); i++) {
            User user = Users.get(i);
            if(user.getUserName().contentEquals(userName)){
                return user;
            }
        }

        return null;

    }

    //method to insert Exercise
    public boolean insertExercise(Exercise exercise){

        //getWritableDatabase - method from the default properties of SQLiteOpenHelper
        //getWritableDatabase for insert actions...getReadableDatabase for select (read) actions.
        SQLiteDatabase db = this.getWritableDatabase();

        // ContentValues - a special class that works like a associative  array (PHP)
        // can take pairs of values and associate with them (like the bundle in intent)
        // The ID column is auto increment
        // Exercise Table;
        //  (exerciseID INTEGER PRIMARY KEY, exerciseName TEXT,
        //  exercisePic INTEGER, exerciseDetail TEXT)
        ContentValues cv = new ContentValues();
        cv.put("exerciseID", exercise.getExerciseID());
        cv.put("exerciseName", exercise.getExerciseName());
        cv.put("exercisePic", exercise.getExercisePic());
        cv.put("exerciseDetail", exercise.getExerciseDetail());

        // insert - success variable... 1)positive -> it was inserted...2) -1 -> it was a fail
        long insert = db.insert(TABLE_EXERCISES, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }

    }

    //method to get all exercises
    public ArrayList<Exercise> getExercises(){

        ArrayList<Exercise> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM " + TABLE_EXERCISES;

        // get a reference to the active database
        // getWritableDatabase - insert, update or delete records
        // getReadableDatabase - SELECT items from the database
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor is the result set from a SQL statement
        Cursor cursor = db.rawQuery(queryString, null);

        // moveToFirst returns a true if there were items selected
        if(cursor.moveToFirst()){

            // loop through the cursor (result set) and create new customer objects. Put them into the return list
            do{
                // Exercise Table;
                //  (exerciseID INTEGER PRIMARY KEY, exerciseName TEXT,
                //  exercisePic INTEGER, exerciseDetail TEXT)
                int exerciseID = cursor.getInt(0);
                String exerciseName = cursor.getString(1);
                int exercisePic = cursor.getInt(2);
                String exerciseDetail = cursor.getString(3);

               Exercise newExercise = new Exercise(exerciseID, exerciseName, exercisePic, exerciseDetail);
                returnList.add(newExercise);

            } while (cursor.moveToNext());

        }else{
            // failure. do not add anything to the list.
        }

        // always clean up after yourself
        // lets close the connection to the database so others can use it
        // close the cursor when done.
        cursor.close();

        return returnList;

    }

    //method to get all exercises by userNameGiven
    public ArrayList<UserExercisesInnerJoinEx> getExercisesByUserNameGiven(String userNameGiven){

        ArrayList<UserExercisesInnerJoinEx> returnList = new ArrayList<>();

        // get data from the database
        // First table: Exercises
        // Second table: UserExercises
        // Exercise Table;
        //  (exerciseID INTEGER PRIMARY KEY, exerciseName TEXT,
        //  exercisePic INTEGER, exerciseDetail TEXT)
        //table UserExercises
        //(userExerciseID INTEGER PRIMARY KEY AUTOINCREMENT,
        // userName TEXT, exerciseID INTEGER, date TEXT, time INTEGER,
        // rating INTEGER, repetition INTEGER)
        String queryString = "SELECT Exercises.exerciseID, exerciseName, exercisePic, exerciseDetail, userExerciseID FROM Exercises INNER JOIN UserExercises ON Exercises.exerciseID = UserExercises.exerciseID WHERE UserExercises.userName = '"+userNameGiven+"'";
        //String queryString = "SELECT Exercises.exerciseID, UserExercises.exerciseID, exerciseName, exercisePic, exerciseDetail FROM Exercises INNER JOIN UserExercises ON Exercises.exerciseID = UserExercises.exerciseID WHERE UserExercises.userName = " + userNameGiven;
        // get a reference to the active database
        // getWritableDatabase - insert, update or delete records
        // getReadableDatabase - SELECT items from the database
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor is the result set from a SQL statement
        Cursor cursor = db.rawQuery(queryString, null);

        // moveToFirst returns a true if there were items selected
        if(cursor.moveToFirst()){

            // loop through the cursor (result set) and create new customer objects. Put them into the return list
            do{
                //UserExercisesInnerJoinEx (int userExerciseID, int exerciseID, String exerciseName, int exercisePic, String exerciseDetail)
                int exerciseID = cursor.getInt(0);
                String exerciseName = cursor.getString(1);
                int exercisePic = cursor.getInt(2);
                String exerciseDetail = cursor.getString(3);
                int userExerciseID = cursor.getInt(4);

                UserExercisesInnerJoinEx newUserExercisesInnerJoinEx = new UserExercisesInnerJoinEx(userExerciseID, exerciseID, exerciseName, exercisePic, exerciseDetail);
                returnList.add(newUserExercisesInnerJoinEx);

            } while (cursor.moveToNext());

        }else{
            // failure. do not add anything to the list.
        }

        // always clean up after yourself
        // lets close the connection to the database so others can use it
        // close the cursor when done.
        cursor.close();

        return returnList;

    }

    //method to get all exercises by userNameGiven
    //that have not been done by the user yet (by knowing that the date of doing the exercise is null)
    public ArrayList<UserExercisesInnerJoinEx> getExercisesByUserNameGivenNotDone(String userNameGiven){

        ArrayList<UserExercisesInnerJoinEx> returnList = new ArrayList<>();

        // get data from the database
        // First table: Exercises
        // Second table: UserExercises
        // Exercise Table;
        //  (exerciseID INTEGER PRIMARY KEY, exerciseName TEXT,
        //  exercisePic INTEGER, exerciseDetail TEXT)
        //table UserExercises
        //(userExerciseID INTEGER PRIMARY KEY AUTOINCREMENT,
        // userName TEXT, exerciseID INTEGER, date TEXT, time INTEGER,
        // rating INTEGER, repetition INTEGER)
        String queryString = "SELECT Exercises.exerciseID, exerciseName, exercisePic, exerciseDetail, userExerciseID FROM Exercises INNER JOIN UserExercises ON Exercises.exerciseID = UserExercises.exerciseID WHERE UserExercises.userName = '"+userNameGiven+"' AND UserExercises.date IS NULL";
        //String queryString = "SELECT Exercises.exerciseID, UserExercises.exerciseID, exerciseName, exercisePic, exerciseDetail FROM Exercises INNER JOIN UserExercises ON Exercises.exerciseID = UserExercises.exerciseID WHERE UserExercises.userName = " + userNameGiven;
        // get a reference to the active database
        // getWritableDatabase - insert, update or delete records
        // getReadableDatabase - SELECT items from the database
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor is the result set from a SQL statement
        Cursor cursor = db.rawQuery(queryString, null);

        // moveToFirst returns a true if there were items selected
        if(cursor.moveToFirst()){

            // loop through the cursor (result set) and create new customer objects. Put them into the return list
            do{
                //UserExercisesInnerJoinEx (int userExerciseID, int exerciseID, String exerciseName, int exercisePic, String exerciseDetail)
                int exerciseID = cursor.getInt(0);
                String exerciseName = cursor.getString(1);
                int exercisePic = cursor.getInt(2);
                String exerciseDetail = cursor.getString(3);
                int userExerciseID = cursor.getInt(4);

                UserExercisesInnerJoinEx newUserExercisesInnerJoinEx = new UserExercisesInnerJoinEx(userExerciseID, exerciseID, exerciseName, exercisePic, exerciseDetail);
                returnList.add(newUserExercisesInnerJoinEx);

            } while (cursor.moveToNext());

        }else{
            // failure. do not add anything to the list.
        }

        // always clean up after yourself
        // lets close the connection to the database so others can use it
        // close the cursor when done.
        cursor.close();

        return returnList;

    }

    //method to search for exercise by ID
    public ArrayList<Exercise> getExercisesByID(int exerciseID){
        //get all users from user table
        ArrayList<Exercise> exercises = this.getExercises();
        ArrayList<Exercise> returnList = new ArrayList<>();

        // if there are no users
        if(exercises.isEmpty())
            return null;

        for (int i=0; i<exercises.size(); i++) {
            Exercise exercise = exercises.get(i);
            if(exercise.getExerciseID() == exerciseID){
                returnList.add(exercise);
            }
        }

        return returnList;

    }

    //method to get one exercise by its ID
    public Exercise getExerciseByID(int id){

        // get a reference to the active database
        // getWritableDatabase - insert, update or delete records
        // getReadableDatabase - SELECT items from the database
        SQLiteDatabase db = getReadableDatabase();

        // Table: Exercises
        //(exerciseID INTEGER PRIMARY KEY, exerciseName TEXT,
        // exercisePic INTEGER, exerciseDetail TEXT)
        Cursor cursor = db.rawQuery("SELECT * FROM Exercises WHERE exerciseID = " + id, null);

        cursor.moveToFirst();

        int exerciseID = cursor.getInt(0);
        String exerciseName = cursor.getString(1);
        int exercisePic = cursor.getInt(2);
        String exerciseDetail = cursor.getString(3);

        Exercise exercise = new Exercise(exerciseID, exerciseName, exercisePic, exerciseDetail);

        // always clean up after yourself
        // lets close the connection to the database so others can use it
        // close the cursor when done.
        cursor.close();

        return exercise;

    }

    //method to insert UserExercise
    public boolean insertUserExercise(UserExercise userExercise){

        //getWritableDatabase - method from the default properties of SQLiteOpenHelper
        //getWritableDatabase for insert actions...getReadableDatabase for select (read) actions.
        SQLiteDatabase db = this.getWritableDatabase();

        // ContentValues - a special class that works like a associative  array (PHP)
        // can take pairs of values and associate with them (like the bundle in intent)
        // The ID column is auto increment
        // UserExercises Table
        //(userExerciseID INTEGER PRIMARY KEY, userName TEXT,
        // exerciseID INTEGER, date TEXT, time INTEGER, rating INTEGER, repetition INTEGER)
        ContentValues cv = new ContentValues();
        cv.put("userName", userExercise.getUserName());
        cv.put("exerciseID", userExercise.getExerciseID());
        cv.put("date", userExercise.getDate());
        cv.put("time", userExercise.getTime());
        cv.put("rating", userExercise.getRating());
        cv.put("repetition", userExercise.getRepetition());


        // insert - success variable... 1)positive -> it was inserted...2) -1 -> it was a fail
        long insert = db.insert(TABLE_USEREXERCISES, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }

    }

    //method to delete userExercise
    //using it to delete userExercise when in dialog to delete an userExercise
    public void deleteUserExercise(String userName,String exerciseName, int userExerciseID){

        // get a reference to the active database
        // getWritableDatabase - insert, update or delete records
        // getReadableDatabase - SELECT items from the database
        SQLiteDatabase db = this.getWritableDatabase();

        //table: Exercises
        //(exerciseID INTEGER PRIMARY KEY, exerciseName TEXT, exercisePic INTEGER, exerciseDetail TEXT)
        Cursor c = db.rawQuery("SELECT * FROM "+ TABLE_EXERCISES +" WHERE exerciseName = '"+exerciseName+"'", null);

        c.moveToFirst();

        //taking the exerciseID from the selected exercise
        //there is supposed to be only one exercise with the name it has
        int exerciseID = c.getInt(0);
        //Log.d("exerciseID", String.valueOf(exerciseID));
        //table UserExercises
        //(userExerciseID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT,
        // exerciseID INTEGER, date TEXT, time INTEGER, rating INTEGER, repetition INTEGER)
        String queryString = "DELETE FROM " + TABLE_USEREXERCISES + " WHERE userName = '"+userName+"' AND exerciseID = " + exerciseID + " AND userExerciseID = " + userExerciseID;

        Log.d("Delete filter", String.valueOf(userExerciseID));
        //Log.d("Delete filter2", String.valueOf(exerciseID));

        db.execSQL(queryString);

    }

    //method to update userExercise
    //(userExerciseID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT, exerciseID INTEGER, date TEXT, time INTEGER, rating INTEGER, repetition INTEGER)
    public void updateUserExercise(int userExerciseID, String date, int time, int rating, int repetition){

        // get a reference to the active database
        // getWritableDatabase - insert, update or delete records
        // getReadableDatabase - SELECT items from the database
        SQLiteDatabase db = this.getWritableDatabase();

        // UserExercises Table
        //(userExerciseID INTEGER PRIMARY KEY, userName TEXT,
        // exerciseID INTEGER, date TEXT, time INTEGER, rating INTEGER, repetition INTEGER)
        ContentValues cv = new ContentValues();
        cv.put("date", date);
        cv.put("time", time);
        cv.put("rating", rating);
        cv.put("repetition", repetition);

        db.update(TABLE_USEREXERCISES, cv, "userExerciseID = " + userExerciseID,null);

    }

    //method to get all UserExercises
    public ArrayList<UserExercise> getUserExercises(){

        ArrayList<UserExercise> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM " + TABLE_USEREXERCISES;

        // get a reference to the active database
        // getWritableDatabase - insert, update or delete records
        // getReadableDatabase - SELECT items from the database
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor is the result set from a SQL statement
        Cursor cursor = db.rawQuery(queryString, null);

        // moveToFirst returns a true if there were items selected
        if(cursor.moveToFirst()){

            // loop through the cursor (result set) and create new customer objects. Put them into the return list
            do{
                // UserExercises Table
                //(userExerciseID INTEGER PRIMARY KEY AUTOINCREMENT, userName TEXT,
                // exerciseID INTEGER, date TEXT, time INTEGER, rating INTEGER, repetition INTEGER)
                int userExerciseID = cursor.getInt(0);
                String userName = cursor.getString(1);
                int exerciseID = cursor.getInt(2);
                String date = cursor.getString(3);
                int time = cursor.getInt(4);
                int rating = cursor.getInt(5);
                int repetition = cursor.getInt(6);

                UserExercise newUserExercise = new UserExercise(userExerciseID, userName, exerciseID, date, time, rating, repetition);
                returnList.add(newUserExercise);

            } while (cursor.moveToNext());

        }else{
            // failure. do not add anything to the list.
        }

        // always clean up after yourself
        // lets close the connection to the database so others can use it
        // close the cursor when done.
        cursor.close();

        return returnList;

    }

    //method to search for userExercise by User ID
    public ArrayList<UserExercise> getUserExercisesByUserName(String userName){
        //get all users from user table
        ArrayList<UserExercise> userExercises = this.getUserExercises();
        ArrayList<UserExercise> returnList = new ArrayList<>();

        // if there are no users
        if(userExercises.isEmpty())
            return null;

        for (int i=0; i<userExercises.size(); i++) {

            UserExercise userExercise = userExercises.get(i);

            if(userExercise.getUserName() == userName){
                returnList.add(userExercise);
            }
        }

        return returnList;

    }

    //method to search for userExercise by User ID
    //that have been done by the user (by knowing that the date of doing the exercise is not null)
    public ArrayList<UserExercise> getUserExercisesDoneByUserName(String userName){
        //get all users from user table
        ArrayList<UserExercise> userExercises = this.getUserExercises();
        ArrayList<UserExercise> returnList = new ArrayList<>();

        // if there are no users
        if(userExercises.isEmpty())
            return null;

        for (int i=0; i<userExercises.size(); i++) {

            UserExercise userExercise = userExercises.get(i);

            if(userExercise.getUserName().contentEquals(userName) && userExercise.getDate() != null){
                returnList.add(userExercise);
            }
        }

        if(returnList.isEmpty() == false){
            Log.d("Error","List is empty");
        }

        return returnList;

    }

    //method to search for userExercise by User ID
    //that have not been done by the user yet (by knowing that the date of doing the exercise is null)
    public ArrayList<UserExercise> getUserExercisesNotDoneByUserName(String userName){
        //get all users from user table
        ArrayList<UserExercise> userExercises = this.getUserExercises();
        ArrayList<UserExercise> returnList = new ArrayList<>();

        // if there are no users
        if(userExercises.isEmpty())
            return null;

        for (int i=0; i<userExercises.size(); i++) {

            UserExercise userExercise = userExercises.get(i);

            if(userExercise.getUserName() == userName && userExercise.getDate() == null){
                returnList.add(userExercise);
            }
        }

        return returnList;

    }

    //method to get all songs
    public ArrayList<Song> getSongs() {

        ArrayList<Song> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM " + TABLE_SONGS;

        // get a reference to the active database
        // getWritableDatabase - insert, update or delete records
        // getReadableDatabase - SELECT items from the database
        SQLiteDatabase db = this.getReadableDatabase();

        // Cursor is the result set from a SQL statement
        Cursor cursor = db.rawQuery(queryString, null);

        // moveToFirst returns a true if there were items selected
        if (cursor.moveToFirst()) {

            // loop through the cursor (result set) and create new customer objects. Put them into the return list
            do {
                // Song Table
                //(songID INTEGER PRIMARY KEY, songName TEXT, songMP3 INTEGER)
                int songID = cursor.getInt(0);
                String songName = cursor.getString(1);
                int songMP3 = cursor.getInt(2);

                Song newSong = new Song(songID, songName, songMP3);
                returnList.add(newSong);

            } while (cursor.moveToNext());

        } else {
            // failure. do not add anything to the list.
        }

        // always clean up after yourself
        // lets close the connection to the database so others can use it
        // close the cursor when done.
        cursor.close();

        return returnList;
    }
}

