package org.brunokam.personalcoach;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database {

    private static String LOG_TAG = "Database";

    public static final String DB_NAME = "PersonalCoach";
    public static final int DB_VERSION = 3;

    public static final class TABLE_NAME {
        public static final String RESOLUTION = "Resolution";
        public static final String SUMMARY = "Summary";
    }

    public static final class LABEL {
        // Resolution label names
        public static final class RESOLUTION {
            public static final String ID = "_id";
            public static final String TITLE = "Title";
            public static final String DESCRIPTION = "Description";
            public static final String DIFFICULTY = "Difficulty";
            public static final String INTERVAL = "Interval";
            public static final String START_TIME = "StartTime";
            public static final String LAST_SUMMARY_TIME = "LastSummaryTime";
        }

        // Summary label names
        public static final class SUMMARY {
            public static final String ID = "_id";
            public static final String RESOLUTION_ID = "ResolutionID";
            public static final String DATE = "Date";
            public static final String DESCRIPTION = "Description";
            public static final String PROGRESS = "Progress";
            public static final String REAL_DIFFICULTY = "RealDifficulty";
        }
    }

    private static final String DB_RESOLUTION_CREATION_QUERY =
        "CREATE TABLE " + TABLE_NAME.RESOLUTION + " ("
        + LABEL.RESOLUTION.ID + " INTEGER PRIMARY KEY,"
        + LABEL.RESOLUTION.TITLE + " TEXT,"
        + LABEL.RESOLUTION.DESCRIPTION + " TEXT,"
        + LABEL.RESOLUTION.DIFFICULTY + " INTEGER,"
        + LABEL.RESOLUTION.INTERVAL + " INTEGER,"
        + LABEL.RESOLUTION.START_TIME + " INTEGER,"
        + LABEL.RESOLUTION.LAST_SUMMARY_TIME + " INTEGER"
        + ")";
    private static final String DB_SUMMARY_CREATION_QUERY =
        "CREATE TABLE " + TABLE_NAME.SUMMARY + " ("
        + LABEL.SUMMARY.ID + " INTEGER PRIMARY KEY,"
        + LABEL.SUMMARY.RESOLUTION_ID + " INTEGER,"
        + LABEL.SUMMARY.DATE + " INTEGER,"
        + LABEL.SUMMARY.DESCRIPTION + " TEXT,"
        + LABEL.SUMMARY.PROGRESS + " INTEGER,"
        + LABEL.SUMMARY.REAL_DIFFICULTY + " INTEGER"
        + ")";

    private static final String DB_RESOLUTION_DROPPING_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME.RESOLUTION;
    private static final String DB_SUMMARY_DROPPING_QUERY = "DROP TABLE IF EXISTS " + TABLE_NAME.SUMMARY;

    private static class DatabaseOpenHelper extends SQLiteOpenHelper {

        public DatabaseOpenHelper(Context context) {
            super(context, Database.DB_NAME, null, Database.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Database.DB_RESOLUTION_CREATION_QUERY);
            db.execSQL(Database.DB_SUMMARY_CREATION_QUERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(Database.DB_RESOLUTION_DROPPING_QUERY);
            db.execSQL(Database.DB_SUMMARY_DROPPING_QUERY);

            db.execSQL(Database.DB_RESOLUTION_CREATION_QUERY);
            db.execSQL(Database.DB_SUMMARY_CREATION_QUERY);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(Database.DB_RESOLUTION_DROPPING_QUERY);
            db.execSQL(Database.DB_SUMMARY_DROPPING_QUERY);

            db.execSQL(Database.DB_RESOLUTION_CREATION_QUERY);
            db.execSQL(Database.DB_SUMMARY_CREATION_QUERY);
        }

    }

    private DatabaseOpenHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static Database mInstance = null;

    private Database(Context context) {
        this.mDbHelper = new DatabaseOpenHelper(context);
        this.mDb = this.mDbHelper.getWritableDatabase();
    }

    public static Database getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new Database(context.getApplicationContext());
        }
        return mInstance;
    }

    public SQLiteDatabase getDatabase() {
        return this.mDb;
    }

}
