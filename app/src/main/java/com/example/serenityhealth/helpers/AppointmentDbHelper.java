package com.example.serenityhealth.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.serenityhealth.models.ConsultationModel;
import com.example.serenityhealth.models.UserModel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class AppointmentDbHelper extends SQLiteOpenHelper {

    private static final String TAG ="UserDbHelper" ;

    static final class AppointmentContract {
        private AppointmentContract() {}

        public class FeedEntry implements BaseColumns {
            public static final String TABLE_NAME = "appointments";
            public static final String USER_ID = "userId";
            public static final String DATE = "date";
            public static final String TIMESLOT = "timeslot";
        }
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "appointments.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AppointmentContract.FeedEntry.TABLE_NAME + " (" +
                    AppointmentContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    AppointmentContract.FeedEntry.USER_ID + " TEXT," +
                    AppointmentContract.FeedEntry.DATE + " TEXT," +
                    AppointmentContract.FeedEntry.TIMESLOT + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AppointmentContract.FeedEntry.TABLE_NAME;

    public AppointmentDbHelper(Context context) {
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

    public static boolean createAppointment(Context context, ConsultationModel consultation){

        AppointmentDbHelper dbHelper = new AppointmentDbHelper(context);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AppointmentContract.FeedEntry.USER_ID, consultation.getUser().getId());
        values.put(AppointmentContract.FeedEntry.DATE, Constants.dateFormatter.format(consultation.getDate()));
        values.put(AppointmentContract.FeedEntry.TIMESLOT, consultation.getTime().value);


        return db.insert(AppointmentContract.FeedEntry.TABLE_NAME, null, values) >= 0;
    }

    public static ArrayList<ConsultationModel> getAppointmentsByUserId(Context context, UserModel user)  {
        AppointmentDbHelper dbHelper = new AppointmentDbHelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                AppointmentContract.FeedEntry.DATE,
                AppointmentContract.FeedEntry.TIMESLOT,
        };

        String selection = AppointmentContract.FeedEntry.USER_ID + " = ?";
        String[] selectionArgs = { String.valueOf(user.getId()) };

        Cursor cursor = db.query(
                AppointmentContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );


        ArrayList<ConsultationModel> consultations = new ArrayList<>();
        Log.e(TAG, "getAppointmentsByUserId: "+cursor.getCount());

        while(cursor.moveToNext()) {
            String _id= cursor.getString(cursor.getColumnIndexOrThrow(AppointmentContract.FeedEntry._ID));
            String _date= cursor.getString(cursor.getColumnIndexOrThrow(AppointmentContract.FeedEntry.DATE));
            String _timeslot= cursor.getString(cursor.getColumnIndexOrThrow(AppointmentContract.FeedEntry.TIMESLOT));

            try {
                consultations.add(new ConsultationModel(_id,Constants.dateFormatter.parse( _date), TimeSlot.toTimeSlot(_timeslot), user));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        cursor.close();

        return consultations;
    }

    public static ArrayList<TimeSlot> getTimeSlotsAvailableByDate(Context context, Date dateSelected)  {
        AppointmentDbHelper dbHelper = new AppointmentDbHelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                AppointmentContract.FeedEntry.TIMESLOT,
        };

        String selection = AppointmentContract.FeedEntry.DATE + " = ?";
        String[] selectionArgs = { Constants.dateFormatter.format(dateSelected) };

        Cursor cursor = db.query(
                AppointmentContract.FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );


        ArrayList<TimeSlot> timeSlotsTaken = new ArrayList<>();
        ArrayList<TimeSlot> timeSlotsAvailable = new ArrayList<>();

        while(cursor.moveToNext()) {
            String _timeslot= cursor.getString(cursor.getColumnIndexOrThrow(AppointmentContract.FeedEntry.TIMESLOT));
            if(timeSlotsTaken.contains(_timeslot)){
                continue;
            }
            timeSlotsTaken.add(TimeSlot.toTimeSlot(_timeslot));
        }
        cursor.close();

        for (int i = 0; i < TimeSlot.values().length; i++) {
            if(!timeSlotsTaken.contains(TimeSlot.values()[i])){
                timeSlotsAvailable.add(TimeSlot.values()[i]);
            }
        }

        return timeSlotsAvailable;
    }

    public static void deleteAppointment(Context context,String appointmentId){
        AppointmentDbHelper dbHelper = new AppointmentDbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = AppointmentContract.FeedEntry._ID + " LIKE ?";
        String[] selectionArgs = { appointmentId };
        db.delete(AppointmentContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }
}