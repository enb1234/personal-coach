package org.brunokam.personalcoach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class ResolutionListDatabaseHelper {
    private static String dbName = "ResolutionList";
    private static int dbVersion = 1;
    private static String tableName = "ResolutionList";

    private static String labelID = "_id";
    private static String labelTitle = "Title";
    private static String labelDescription = "Description";
    private static String labelDifficulty = "Difficulty";
    private static String labelInterval = "Interval";

    String dbCreationQuery = "CREATE TABLE " + tableName
        + "("
        + labelID + " INTEGER PRIMARY KEY,"
        + labelTitle + " TEXT,"
        + labelDescription + " TEXT,"
        + labelDifficulty + " INTEGER,"
        + labelInterval + " INTEGER"
        + ")";
    String dbDroppingQuery = "DROP TABLE IF EXISTS " + tableName;

    private DatabaseOpenHelper dbHelper;
    private SQLiteDatabase db;

    public ResolutionListDatabaseHelper(Context context) {
        this.dbHelper = new DatabaseOpenHelper(context);
        this.db = this.dbHelper.getWritableDatabase();
    }

    public ArrayList<Resolution> all() {
        String query = "SELECT * FROM " + tableName;
        Cursor result = this.db.rawQuery(query, null);

        ArrayList<Resolution> resolutionList = new ArrayList<Resolution>();
        String title, description;
        int difficulty, interval;

        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            title = result.getString(result.getColumnIndex(labelTitle));
            description = result.getString(result.getColumnIndex(labelDescription));
            difficulty = result.getInt(result.getColumnIndex(labelDifficulty));
            interval = result.getInt(result.getColumnIndex(labelInterval));

            resolutionList.add(new Resolution(title, description, difficulty, interval));
        }

        return resolutionList;
    }

    public Resolution get(String ID) {
        String query = "SELECT * FROM " + tableName + " WHERE _id = ?";
        Cursor result = this.db.rawQuery(query, new String[] { ID });
        result.moveToFirst();

        if (!result.isAfterLast()) {
            String title = result.getString(result.getColumnIndex(labelTitle));
            String description = result.getString(result.getColumnIndex(labelDescription));
            int difficulty = result.getInt(result.getColumnIndex(labelDifficulty));
            int interval = result.getInt(result.getColumnIndex(labelInterval));

            return new Resolution(title, description, difficulty, interval);
        }

        return null;
    }

    public void set(Resolution resolution) {
        ContentValues values = new ContentValues();
        values.put(labelTitle, resolution.getTitle());
        values.put(labelDescription, resolution.getDescription());
        values.put(labelDifficulty, resolution.getDifficulty());
        values.put(labelInterval, resolution.getInterval());

        this.db.insert(tableName, null, values);
    }

    public void delete(String ID) {
        String query = "DELETE FROM " + tableName + " WHERE _id = ?";
        this.db.rawQuery(query, new String[] { ID });
    }

    public void clear() {
        this.db.delete(tableName, null, null);
    }

    private class DatabaseOpenHelper extends SQLiteOpenHelper {
        public DatabaseOpenHelper(Context context) {
            super(context, dbName, null, dbVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(dbCreationQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(dbDroppingQuery);
            db.execSQL(dbCreationQuery);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(dbDroppingQuery);
            db.execSQL(dbCreationQuery);
        }
    }
}
