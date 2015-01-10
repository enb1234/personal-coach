package org.brunokam.personalcoach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ResolutionListDatabaseHelper {
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

    String mDbCreationQuery = "CREATE TABLE " + mTableName
        + "("
        + mLabelID + " INTEGER PRIMARY KEY,"
        + mLabelTitle + " TEXT,"
        + mLabelDescription + " TEXT,"
        + mLabelDifficulty + " INTEGER,"
        + mLabelInterval + " INTEGER,"
        + mLabelStartTime + " INTEGER,"
        + mLabelLastSummaryTime + " INTEGER"
        + ")";
    String mDbDroppingQuery = "DROP TABLE IF EXISTS " + mTableName;

    private DatabaseOpenHelper mDbHelper;
    private SQLiteDatabase mDb;

    public ResolutionListDatabaseHelper(Context context) {
        this.mDbHelper = new DatabaseOpenHelper(context);
        this.mDb = this.mDbHelper.getWritableDatabase();
    }

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

    public ArrayList<Resolution> all() {
        String query = "SELECT * FROM " + mTableName;
        Cursor result = this.mDb.rawQuery(query, null);

        ArrayList<Resolution> resolutionList = new ArrayList<Resolution>();
        String title, description;
        Integer difficulty, interval, startTime, lastSummaryTime;

        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
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

            resolutionList.add(new Resolution(title, description, difficulty, interval, startTime, lastSummaryTime));
        }

        return resolutionList;
    }

    public Resolution get(String ID) {
        String query = "SELECT * FROM " + mTableName + " WHERE _id = ?";
        Cursor result = this.mDb.rawQuery(query, new String[] { ID });
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

            return new Resolution(title, description, difficulty, interval, startTime, lastSummaryTime);
        }

        return null;
    }

    public void set(Resolution resolution) {
        ContentValues values = new ContentValues();
        values.put(mLabelTitle, resolution.getTitle());
        values.put(mLabelDescription, resolution.getDescription());
        values.put(mLabelDifficulty, resolution.getDifficulty());
        values.put(mLabelInterval, resolution.getInterval());
        values.put(mLabelStartTime, resolution.getStartTime());
        values.put(mLabelLastSummaryTime, resolution.getLastSummaryTime());

        this.mDb.insert(mTableName, null, values);
    }

    public void delete(String ID) {
        String query = "DELETE FROM " + mTableName + " WHERE _id = ?";
        this.mDb.rawQuery(query, new String[]{ID});
    }

    public void clear() {
        this.mDb.delete(mTableName, null, null);
    }

}
