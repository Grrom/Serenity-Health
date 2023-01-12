package com.example.serenityhealth.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.serenityhealth.models.HistoryModel;
import com.example.serenityhealth.models.UserModel;

import java.text.ParseException;
import java.util.ArrayList;

public class HistoryDbHelper extends SQLiteOpenHelper {

    private static final String TAG ="UserDbHelper" ;

    static final class HistoryContract {
        private HistoryContract() {}

        public static class FeedEntry implements BaseColumns {
            public static final String TABLE_NAME = "history";
            public static final String USER_ID = "userId";
            public static final String DATE = "date";
            public static final String TIMESLOT = "timeslot";
            public static final String BLOOD_PRESSURE_SYS = "blood_pressure_sys";
            public static final String BLOOD_PRESSURE_DIAS = "blood_pressure_dias";
            public static final String TEMPERATURE = "temperature";
            public static final String SYMPTOMS = "symptoms";
            public static final String DIAGNOSIS = "diagnosis";
        }
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "histories.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + HistoryContract.FeedEntry.TABLE_NAME + " (" +
                    HistoryContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    HistoryContract.FeedEntry.USER_ID + " TEXT," +
                    HistoryContract.FeedEntry.DATE + " TEXT," +
                    HistoryContract.FeedEntry.TIMESLOT + " TEXT," +
                    HistoryContract.FeedEntry.BLOOD_PRESSURE_SYS + " TEXT," +
                    HistoryContract.FeedEntry.BLOOD_PRESSURE_DIAS + " TEXT," +
                    HistoryContract.FeedEntry.TEMPERATURE + " TEXT," +
                    HistoryContract.FeedEntry.SYMPTOMS + " TEXT," +
                    HistoryContract.FeedEntry.DIAGNOSIS + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HistoryContract.FeedEntry.TABLE_NAME;

    public HistoryDbHelper(Context context) {
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

    public static boolean createHistory(Context context, HistoryModel history){

        HistoryDbHelper dbHelper = new HistoryDbHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HistoryContract.FeedEntry.USER_ID, history.getUser().getId());
        values.put(HistoryContract.FeedEntry.DATE, Constants.dateFormatter.format(history.getDate()));
        values.put(HistoryContract.FeedEntry.TIMESLOT, history.getTime().value);
        values.put(HistoryContract.FeedEntry.BLOOD_PRESSURE_SYS, history.getBloodPressureDiastolic());
        values.put(HistoryContract.FeedEntry.BLOOD_PRESSURE_DIAS, history.getBloodPressureSystolic());
        values.put(HistoryContract.FeedEntry.TEMPERATURE, history.getTemperature());
        values.put(HistoryContract.FeedEntry.SYMPTOMS, history.getSymptoms());
        values.put(HistoryContract.FeedEntry.DIAGNOSIS, history.getDiagnosis());


        return db.insert(HistoryContract.FeedEntry.TABLE_NAME, null, values) >= 0;
    }

    public static ArrayList<HistoryModel> getHistoryByUserUserId(Context context, UserModel user)  {
        HistoryDbHelper dbHelper = new HistoryDbHelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                HistoryContract.FeedEntry.USER_ID,
                HistoryContract.FeedEntry.DATE,
                HistoryContract.FeedEntry.TIMESLOT,
                HistoryContract.FeedEntry.BLOOD_PRESSURE_SYS,
                HistoryContract.FeedEntry.BLOOD_PRESSURE_DIAS,
                HistoryContract.FeedEntry.TEMPERATURE,
                HistoryContract.FeedEntry.SYMPTOMS,
                HistoryContract.FeedEntry.DIAGNOSIS,
        };

        String selection = HistoryContract.FeedEntry.USER_ID + " = ?";
        String[] selectionArgs = { String.valueOf(user.getId()) };

        Cursor cursor = db.query(
                HistoryContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );


        ArrayList<HistoryModel> histories = new ArrayList<>();

        while(cursor.moveToNext()) {
            String _id= cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.FeedEntry._ID));
            String _date= cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.FeedEntry.DATE));
            String _timeslot= cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.FeedEntry.TIMESLOT));
            String _bloodPressureSys= cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.FeedEntry.BLOOD_PRESSURE_SYS));
            String _bloodPressureDias= cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.FeedEntry.BLOOD_PRESSURE_DIAS));
            String _temperature= cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.FeedEntry.TEMPERATURE));
            String _symptoms= cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.FeedEntry.SYMPTOMS));
            String _diagnosis= cursor.getString(cursor.getColumnIndexOrThrow(HistoryContract.FeedEntry.DIAGNOSIS));

            try {
                histories.add(new HistoryModel(_id,Constants.dateFormatter.parse(_date), TimeSlot.toTimeSlot(_timeslot), user, _bloodPressureSys, _bloodPressureDias, _temperature, _symptoms, _diagnosis));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();

        return histories;
    }
}