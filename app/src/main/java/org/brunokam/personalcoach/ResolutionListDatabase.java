package org.brunokam.personalcoach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;

public class ResolutionListDatabase extends Observable {
    private static String LOG_TAG = "ResolutionListDatabase";

    private static String mDbName = "ResolutionList";
    private static int mDbVersion = 2;
    private static String mTableName = "ResolutionList";

    // Label names
    private static String mLabelID = "_id";
    private static String mLabelTitle = "Title";
    private static String mLabelDescription = "Description";
    private static String mLabelDifficulty = "Difficulty";
    private static String mLabelInterval = "Interval";
    private static String mLabelStartTime = "StartTime";
    private static String mLabelLastSummaryTime = "LastSummaryTime";

    private static String mDbCreationQuery = "CREATE TABLE " + mTableName
        + "("
        + mLabelID + " INTEGER PRIMARY KEY,"
        + mLabelTitle + " TEXT,"
        + mLabelDescription + " TEXT,"
        + mLabelDifficulty + " INTEGER,"
        + mLabelInterval + " INTEGER,"
        + mLabelStartTime + " INTEGER,"
        + mLabelLastSummaryTime + " INTEGER"
        + ")";
    private static String mDbDroppingQuery = "DROP TABLE IF EXISTS " + mTableName;

    private DatabaseOpenHelper mDbHelper;
    private SQLiteDatabase mDb;

    private class DatabaseOpenHelper extends SQLiteOpenHelper {

        public DatabaseOpenHelper(Context context) {
            super(context, mDbName, null, mDbVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(mDbCreationQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(mDbDroppingQuery);
            db.execSQL(mDbCreationQuery);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(mDbDroppingQuery);
            db.execSQL(mDbCreationQuery);
        }

    }

    private static ResolutionListDatabase mInstance = null;

    private ResolutionListDatabase(Context context) {
        this.mDbHelper = new DatabaseOpenHelper(context);
        this.mDb = this.mDbHelper.getWritableDatabase();
    }

    public static ResolutionListDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ResolutionListDatabase(context.getApplicationContext());
        }
        return mInstance;
    }

    // Gets all resolutions from the database
    public ArrayList<Resolution> all() {
        String query = "SELECT * FROM " + mTableName;
        Cursor result = this.mDb.rawQuery(query, null);

        ArrayList<Resolution> resolutionList = new ArrayList<Resolution>();
        String title, description;
        Integer ID, difficulty, interval, startTime, lastSummaryTime;

        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            ID = result.getInt(result.getColumnIndex(mLabelID));
            title = result.getString(result.getColumnIndex(mLabelTitle));
            description = result.getString(result.getColumnIndex(mLabelDescription));
            difficulty = result.getInt(result.getColumnIndex(mLabelDifficulty));
            interval = result.getInt(result.getColumnIndex(mLabelInterval));
            startTime = result.getInt(result.getColumnIndex(mLabelStartTime));
            lastSummaryTime = result.getInt(result.getColumnIndex(mLabelLastSummaryTime));

            if (startTime == 0) {
                startTime = null;
            }

            if (lastSummaryTime == 0) {
                lastSummaryTime = null;
            }

            resolutionList.add(new Resolution(ID, title, description, difficulty, interval, startTime, lastSummaryTime));
        }

        return resolutionList;
    }

    // Gets resolution from the database
    public Resolution get(Integer ID) {
        String query = "SELECT * FROM " + mTableName + " WHERE _id = ?";
        Cursor result = this.mDb.rawQuery(query, new String[] { ID.toString() });
        result.moveToFirst();

        if (!result.isAfterLast()) {
            String title, description;
            Integer difficulty, interval, startTime, lastSummaryTime;

            title = result.getString(result.getColumnIndex(mLabelTitle));
            description = result.getString(result.getColumnIndex(mLabelDescription));
            difficulty = result.getInt(result.getColumnIndex(mLabelDifficulty));
            interval = result.getInt(result.getColumnIndex(mLabelInterval));
            startTime = result.getInt(result.getColumnIndex(mLabelStartTime));
            lastSummaryTime = result.getInt(result.getColumnIndex(mLabelLastSummaryTime));

            if (startTime == 0) {
                startTime = null;
            }

            if (lastSummaryTime == 0) {
                lastSummaryTime = null;
            }

            return new Resolution(ID, title, description, difficulty, interval, startTime, lastSummaryTime);
        }

        return null;
    }

    // Adds resolution to the database
    public Resolution add(ResolutionEntry resolutionEntry) {
        ContentValues values = new ContentValues();
        values.put(mLabelTitle, resolutionEntry.getTitle());
        values.put(mLabelDescription, resolutionEntry.getDescription());
        values.put(mLabelDifficulty, resolutionEntry.getDifficulty());
        values.put(mLabelInterval, resolutionEntry.getInterval());
        values.put(mLabelStartTime, resolutionEntry.getStartTime());
        values.put(mLabelLastSummaryTime, resolutionEntry.getLastSummaryTime());

        long result = this.mDb.insert(mTableName, null, values);

        if (result != -1) {
            this.notifyObservers();
            return new Resolution((int) result, resolutionEntry);
        } else {
            return null;
        }
    }

    // Updates resolution which exists in the database
    public boolean update(Resolution resolution) {
        ContentValues values = new ContentValues();
        values.put(mLabelTitle, resolution.getTitle());
        values.put(mLabelDescription, resolution.getDescription());
        values.put(mLabelDifficulty, resolution.getDifficulty());
        values.put(mLabelInterval, resolution.getInterval());
        values.put(mLabelStartTime, resolution.getStartTime());
        values.put(mLabelLastSummaryTime, resolution.getLastSummaryTime());

        long result = this.mDb.update(mTableName, values, "_id = ?", new String[] { resolution.getID().toString() });

        if (result != -1) {
            this.notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    // Deletes resolution from the database by given ID
    public void delete(Integer ID) {
        this.mDb.delete(mTableName, "_id = ?", new String[]{ID.toString()});
        this.notifyObservers();
    }

    // Deletes resolution from the database
    public void delete(Resolution resolution) {
        this.delete(resolution.getID());
    }

    // Deletes all resolutions from the database
    public void clear() {
        this.mDb.delete(mTableName, null, null);
        this.notifyObservers();
    }

    @Override
    public void notifyObservers() {
        this.setChanged();
        super.notifyObservers();
    }

}
