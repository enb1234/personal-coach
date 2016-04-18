package org.brunokam.personalcoach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Observable;

public class ResolutionDatabase extends Observable {

    private static String LOG_TAG = "ResolutionDatabase";

    private static ResolutionDatabase mInstance;

    private Context mContext;

    private ResolutionDatabase(Context context) {
        this.mContext = context;
    }

    public static ResolutionDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ResolutionDatabase(context.getApplicationContext());
        }
        return mInstance;
    }

    // Gets all resolutions from the database
    public ArrayList<Resolution> all() {
        String query = "SELECT * FROM " + Database.TABLE_NAME.RESOLUTION;
        Cursor result = Database.getInstance(mContext).getDatabase().rawQuery(query, null);

        ArrayList<Resolution> resolutionList = new ArrayList<Resolution>();
        String title, description;
        Integer ID, difficulty, interval, startTime, lastSummaryTime;

        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
            ID = result.getInt(result.getColumnIndex(Database.LABEL.RESOLUTION.ID));
            title = result.getString(result.getColumnIndex(Database.LABEL.RESOLUTION.TITLE));
            description = result.getString(result.getColumnIndex(Database.LABEL.RESOLUTION.DESCRIPTION));
            difficulty = result.getInt(result.getColumnIndex(Database.LABEL.RESOLUTION.DIFFICULTY));
            interval = result.getInt(result.getColumnIndex(Database.LABEL.RESOLUTION.INTERVAL));
            startTime = result.getInt(result.getColumnIndex(Database.LABEL.RESOLUTION.START_TIME));
            lastSummaryTime = result.getInt(result.getColumnIndex(Database.LABEL.RESOLUTION.LAST_SUMMARY_TIME));

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
        String query = "SELECT * FROM " + Database.TABLE_NAME.RESOLUTION + " WHERE _id = ?";
        Cursor result = Database.getInstance(mContext).getDatabase()
                .rawQuery(query, new String[] { ID.toString() });
        result.moveToFirst();

        if (!result.isAfterLast()) {
            String title, description;
            Integer difficulty, interval, startTime, lastSummaryTime;

            title = result.getString(result.getColumnIndex(Database.LABEL.RESOLUTION.TITLE));
            description = result.getString(result.getColumnIndex(Database.LABEL.RESOLUTION.DESCRIPTION));
            difficulty = result.getInt(result.getColumnIndex(Database.LABEL.RESOLUTION.DIFFICULTY));
            interval = result.getInt(result.getColumnIndex(Database.LABEL.RESOLUTION.INTERVAL));
            startTime = result.getInt(result.getColumnIndex(Database.LABEL.RESOLUTION.START_TIME));
            lastSummaryTime = result.getInt(result.getColumnIndex(Database.LABEL.RESOLUTION.LAST_SUMMARY_TIME));

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
        values.put(Database.LABEL.RESOLUTION.TITLE, resolutionEntry.getTitle());
        values.put(Database.LABEL.RESOLUTION.DESCRIPTION, resolutionEntry.getDescription());
        values.put(Database.LABEL.RESOLUTION.DIFFICULTY, resolutionEntry.getDifficulty());
        values.put(Database.LABEL.RESOLUTION.INTERVAL, resolutionEntry.getInterval());
        values.put(Database.LABEL.RESOLUTION.START_TIME, resolutionEntry.getStartTime());
        values.put(Database.LABEL.RESOLUTION.LAST_SUMMARY_TIME, resolutionEntry.getLastSummaryTime());

        long result = Database.getInstance(mContext).getDatabase()
                .insert(Database.TABLE_NAME.RESOLUTION, null, values);

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
        values.put(Database.LABEL.RESOLUTION.TITLE, resolution.getTitle());
        values.put(Database.LABEL.RESOLUTION.DESCRIPTION, resolution.getDescription());
        values.put(Database.LABEL.RESOLUTION.DIFFICULTY, resolution.getDifficulty());
        values.put(Database.LABEL.RESOLUTION.INTERVAL, resolution.getInterval());
        values.put(Database.LABEL.RESOLUTION.START_TIME, resolution.getStartTime());
        values.put(Database.LABEL.RESOLUTION.LAST_SUMMARY_TIME, resolution.getLastSummaryTime());

        long result = Database.getInstance(mContext).getDatabase()
                .update(Database.TABLE_NAME.RESOLUTION, values, "_id = ?", new String[] { resolution.getID().toString() });

        if (result != -1) {
            this.notifyObservers();
            return true;
        } else {
            return false;
        }
    }

    // Deletes resolution from the database by given ID
    public void delete(Integer ID) {
        Database.getInstance(mContext).getDatabase()
                .delete(Database.TABLE_NAME.RESOLUTION, "_id = ?", new String[]{ID.toString()});
        this.notifyObservers();
    }

    // Deletes resolution from the database
    public void delete(Resolution resolution) {
        this.delete(resolution.getID());
    }

    // Deletes all resolutions from the database
    public void clear() {
        Database.getInstance(mContext).getDatabase()
                .delete(Database.TABLE_NAME.RESOLUTION, null, null);
        this.notifyObservers();
    }

    @Override
    public void notifyObservers() {
        this.setChanged();
        super.notifyObservers();
    }

}
