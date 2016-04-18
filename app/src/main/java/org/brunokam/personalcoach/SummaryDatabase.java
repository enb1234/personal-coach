package org.brunokam.personalcoach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Observable;

public class SummaryDatabase extends Observable {

    private static String LOG_TAG = "SummaryDatabase";

    private static SummaryDatabase mInstance;

    private Context mContext;

    private SummaryDatabase(Context context) {
        this.mContext = context;
    }

    public static SummaryDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SummaryDatabase(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void notifyObservers() {
        this.setChanged();
        super.notifyObservers();
    }

    @Override
    public void notifyObservers(Object object) {
        this.setChanged();
        super.notifyObservers(object);
    }

    // Gets all summaries from the database
    public ArrayList<Summary> all() {
        String query = "SELECT * FROM " + Database.TABLE_NAME.SUMMARY;
        Cursor result = Database.getInstance(mContext).getDatabase().rawQuery(query, null);

        ArrayList<Summary> summaryList = new ArrayList<Summary>();
        String description;
        Integer ID, resolutionID, date, progress, realDifficulty;

        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            ID = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.ID));
            resolutionID = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.RESOLUTION_ID));
            date = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.DATE));
            description = result.getString(result.getColumnIndex(Database.LABEL.SUMMARY.DESCRIPTION));
            progress = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.PROGRESS));
            realDifficulty = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.REAL_DIFFICULTY));

            summaryList.add(new Summary(ID, resolutionID, date, description, progress, realDifficulty));
        }

        return summaryList;
    }

    // Gets all summaries from the database
    public ArrayList<Summary> all(Integer resolutionID) {
        String query = "SELECT * FROM " + Database.TABLE_NAME.SUMMARY + " WHERE " + Database.LABEL.SUMMARY.RESOLUTION_ID + " = ?";
        Cursor result = Database.getInstance(mContext).getDatabase().rawQuery(query, new String[] { resolutionID.toString() });

        ArrayList<Summary> summaryList = new ArrayList<Summary>();
        String description;
        Integer ID, date, progress, realDifficulty;

        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            ID = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.ID));
            date = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.DATE));
            description = result.getString(result.getColumnIndex(Database.LABEL.SUMMARY.DESCRIPTION));
            progress = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.PROGRESS));
            realDifficulty = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.REAL_DIFFICULTY));

            summaryList.add(new Summary(ID, resolutionID, date, description, progress, realDifficulty));
        }

        return summaryList;
    }

    // Gets summary from the database
    public Summary get(Integer ID) {
        String query = "SELECT * FROM " + Database.TABLE_NAME.SUMMARY + " WHERE _id = ?";
        Cursor result = Database.getInstance(mContext).getDatabase()
                .rawQuery(query, new String[] { ID.toString() });
        result.moveToFirst();

        if (!result.isAfterLast()) {
            String description;
            Integer resolutionID, date, progress, realDifficulty;

            resolutionID = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.RESOLUTION_ID));
            date = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.DATE));
            description = result.getString(result.getColumnIndex(Database.LABEL.SUMMARY.DESCRIPTION));
            progress = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.PROGRESS));
            realDifficulty = result.getInt(result.getColumnIndex(Database.LABEL.SUMMARY.REAL_DIFFICULTY));

            return new Summary(ID, resolutionID, date, description, progress, realDifficulty);
        }

        return null;
    }

    // Adds summary to the database
    public Summary add(SummaryEntry summaryEntry) {
        Integer date = (int) (System.currentTimeMillis() / 1000L);

        ContentValues values = new ContentValues();
        values.put(Database.LABEL.SUMMARY.RESOLUTION_ID, summaryEntry.getResolutionID());
        values.put(Database.LABEL.SUMMARY.DATE, date);
        values.put(Database.LABEL.SUMMARY.DESCRIPTION, summaryEntry.getDescription());
        values.put(Database.LABEL.SUMMARY.PROGRESS, summaryEntry.getProgress());
        values.put(Database.LABEL.SUMMARY.REAL_DIFFICULTY, summaryEntry.getRealDifficulty());

        long result = Database.getInstance(mContext).getDatabase()
                .insert(Database.TABLE_NAME.SUMMARY, null, values);

        if (result != -1) {
            Summary summary = new Summary((int) result, date, summaryEntry);
            this.notifyObservers(summary);
            return summary;
        } else {
            return null;
        }
    }

    // Updates summary which exists in the database
    public boolean update(Summary summary) {
        ContentValues values = new ContentValues();
        values.put(Database.LABEL.SUMMARY.RESOLUTION_ID, summary.getResolutionID());
        values.put(Database.LABEL.SUMMARY.DATE, summary.getDate());
        values.put(Database.LABEL.SUMMARY.DESCRIPTION, summary.getDescription());
        values.put(Database.LABEL.SUMMARY.PROGRESS, summary.getProgress());
        values.put(Database.LABEL.SUMMARY.REAL_DIFFICULTY, summary.getRealDifficulty());

        long result = Database.getInstance(mContext).getDatabase()
                .update(Database.TABLE_NAME.SUMMARY, values, "_id = ?", new String[] { summary.getID().toString() });

        if (result != -1) {
            this.notifyObservers(summary);
            return true;
        } else {
            return false;
        }
    }

    // Deletes summary from the database by given ID
    public void delete(Integer ID) {
        Database.getInstance(mContext).getDatabase()
                .delete(Database.TABLE_NAME.SUMMARY, "_id = ?", new String[]{ID.toString()});
        this.notifyObservers();
    }

    // Deletes summary from the database
    public void delete(Summary summary) {
        this.delete(summary.getID());
    }

    // Deletes all summaries from the database
    public void clear() {
        Database.getInstance(mContext).getDatabase()
                .delete(Database.TABLE_NAME.SUMMARY, null, null);
        this.notifyObservers();
    }

    // Deletes all summaries of given resolution ID from the database
    public void clear(Integer resolutionID) {
        Database.getInstance(mContext).getDatabase()
                .delete(Database.TABLE_NAME.SUMMARY, Database.LABEL.SUMMARY.RESOLUTION_ID + " = ?", new String[]{resolutionID.toString()});
        this.notifyObservers();
    }

}
