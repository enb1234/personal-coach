package org.brunokam.personalcoach;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

public class TimePreference extends DialogPreference {

    private static final String LOG_TAG = "TimePreference";

    private Integer mLastHour = 0;
    private Integer mLastMinute = 0;
    private TimePicker mTimePicker = null;

    public static int getHour(String time) {
        String[] pieces = time.split(":");

        return(Integer.parseInt(pieces[0]));
    }

    public static int getMinute(String time) {
        String[] pieces = time.split(":");

        return(Integer.parseInt(pieces[1]));
    }

    public TimePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        setPositiveButtonText(context.getResources().getString(R.string.button_set));
        setNegativeButtonText(context.getResources().getString(R.string.button_cancel));
    }

    @Override
    protected View onCreateDialogView() {
        this.mTimePicker = new TimePicker(getContext());
        this.mTimePicker.setIs24HourView(true);
        return this.mTimePicker;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        this.mTimePicker.setCurrentHour(mLastHour);
        this.mTimePicker.setCurrentMinute(mLastMinute);
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            this.mLastHour = this.mTimePicker.getCurrentHour();
            this.mLastMinute = this.mTimePicker.getCurrentMinute();

            String time = String.valueOf(this.mLastHour) + ":" + String.valueOf(this.mLastMinute);

            if (callChangeListener(time)) {
                persistString(time);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getString(index);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        String time = null;

        if (restoreValue) {
            if (defaultValue == null) {
                time = getPersistedString("12:00");
            } else {
                time = getPersistedString(defaultValue.toString());
            }
        } else {
            time = defaultValue.toString();
        }

        this.mLastHour = getHour(time);
        this.mLastMinute = getMinute(time);
    }

}
