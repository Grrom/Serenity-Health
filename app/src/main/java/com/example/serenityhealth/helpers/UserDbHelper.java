package com.example.serenityhealth.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.serenityhealth.models.UserModel;


public class UserDbHelper extends SQLiteOpenHelper {

    private static final String TAG ="UserDbHelper" ;

    static final class UserContract {
        private UserContract() {}

        public class FeedEntry implements BaseColumns {
            public static final String TABLE_NAME = "users";
            public static final String FIRST_NAME = "firstname";
            public static final String LAST_NAME = "lastname";
            public static final String WEIGHT = "weight";
            public static final String HEIGHT = "height";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String IMAGE_URI = "image_uri";
        }
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "serenity.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + UserContract.FeedEntry.TABLE_NAME + " (" +
                    UserContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    UserContract.FeedEntry.FIRST_NAME + " TEXT," +
                    UserContract.FeedEntry.LAST_NAME + " TEXT," +
                    UserContract.FeedEntry.WEIGHT + " TEXT," +
                    UserContract.FeedEntry.HEIGHT + " TEXT," +
                    UserContract.FeedEntry.USERNAME + " TEXT," +
                    UserContract.FeedEntry.PASSWORD + " TEXT," +
                    UserContract.FeedEntry.IMAGE_URI + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + UserContract.FeedEntry.TABLE_NAME;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static double createUser(Context context,UserModel user){

        UserDbHelper dbHelper = new UserDbHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserContract.FeedEntry.FIRST_NAME, user.getFirstName());
        values.put(UserContract.FeedEntry.LAST_NAME, user.getLastName());
        values.put(UserContract.FeedEntry.WEIGHT, user.getWeight());
        values.put(UserContract.FeedEntry.HEIGHT, user.getHeight());
        values.put(UserContract.FeedEntry.USERNAME, user.getUserName());
        values.put(UserContract.FeedEntry.PASSWORD, user.getPassword());
        values.put(UserContract.FeedEntry.IMAGE_URI, user.getImageUri());


        return db.insert(UserContract.FeedEntry.TABLE_NAME, null, values);
    }

    public static UserModel getUserById(Context context,  String userId  ) {
        UserDbHelper dbHelper = new UserDbHelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UserContract.FeedEntry.FIRST_NAME,
                UserContract.FeedEntry.LAST_NAME,
                UserContract.FeedEntry.WEIGHT,
                UserContract.FeedEntry.HEIGHT,
                UserContract.FeedEntry.USERNAME,
                UserContract.FeedEntry.PASSWORD,
                UserContract.FeedEntry.IMAGE_URI,
        };

        String selection = UserContract.FeedEntry._ID + " = ?";
        String[] selectionArgs = { userId };

        Cursor cursor = db.query(
                UserContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        UserModel user = new UserModel("","", 0, 0, "","","",-1);

        while(cursor.moveToNext()) {
            Log.e(TAG, cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.USERNAME)));

            String _password= cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.PASSWORD));
            String _firstname= cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.FIRST_NAME));
            String _lastname= cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.LAST_NAME));
            double _weight= cursor.getDouble(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.WEIGHT));
            double _height= cursor.getDouble(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.HEIGHT));
            String _username= cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.USERNAME));
            String _image_uri= cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.IMAGE_URI));
            double _user_id= cursor.getDouble(cursor.getColumnIndexOrThrow(UserContract.FeedEntry._ID));

            user= new UserModel(_firstname, _lastname, _weight, _height, _username, _password,_image_uri,_user_id);

        }
        cursor.close();

        return user;
    }


    public static UserModel loginUser(Context context, String username, String password  ) throws Exception {
        UserDbHelper dbHelper = new UserDbHelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UserContract.FeedEntry._ID,
                UserContract.FeedEntry.FIRST_NAME,
                UserContract.FeedEntry.LAST_NAME,
                UserContract.FeedEntry.WEIGHT,
                UserContract.FeedEntry.HEIGHT,
                UserContract.FeedEntry.USERNAME,
                UserContract.FeedEntry.PASSWORD,
                UserContract.FeedEntry.IMAGE_URI,
        };

        String selection = UserContract.FeedEntry.USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                UserContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );


        if(cursor.getCount() ==0){
            throw new Exception("User not found!");
        }

        UserModel user = new UserModel("","", 0, 0, "","","",-1);

        while(cursor.moveToNext()) {
            Log.e(TAG, cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.USERNAME)));

            String _password= cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.PASSWORD));

            if(!password.equals(_password)){
                throw new Exception("Incorrect password!");
            }

            double _id= cursor.getDouble(cursor.getColumnIndexOrThrow(UserContract.FeedEntry._ID));
            String _firstname= cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.FIRST_NAME));
            String _lastname= cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.LAST_NAME));
            double _weight= cursor.getDouble(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.WEIGHT));
            double _height= cursor.getDouble(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.HEIGHT));
            String _username= cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.USERNAME));
            String _image_uri= cursor.getString(cursor.getColumnIndexOrThrow(UserContract.FeedEntry.IMAGE_URI));

            user= new UserModel(_firstname, _lastname, _weight, _height, _username, _password,_image_uri, _id);

        }
        cursor.close();

        return user;
    }
}