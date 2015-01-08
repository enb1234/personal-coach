package org.brunokam.personalcoach;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

public class AddResolutionFragment extends DialogFragment {

    private AddResolutionFragmentListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Inflates and sets the layout for the dialog
        // Passes null as the parent view because its going in the dialog layout
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_resolution, null);

        // Get the dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
            .setView(view)
            .setTitle(view.getResources().getString(R.string.title_fragment_add_resolution))
            .setPositiveButton(R.string.button_done, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // Creates the resolution
                    String title = ((EditText) getDialog().findViewById(R.id.edit_text_title)).getText().toString();
                    String description = ((EditText) getDialog().findViewById(R.id.edit_text_description)).getText().toString();
                    int difficulty = ((SeekBar) getDialog().findViewById(R.id.seek_bar_difficulty)).getProgress();
                    int interval = Integer.parseInt(((EditText) getDialog().findViewById(R.id.edit_text_interval)).getText().toString());
                    int startTime = (int) (System.currentTimeMillis() / 1000L);
                    int lastSummaryTime = 0;

                    Resolution resolution = new Resolution(title, description, difficulty, interval, startTime, lastSummaryTime);
                    mListener.onAddResolutionSuccess(AddResolutionFragment.this, resolution);
                }
            })
            .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    mListener.onAddResolutionError(AddResolutionFragment.this);
                }
            });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            this.mListener = (AddResolutionFragmentListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement AddResolutionFragmentListener");
        }
    }

    public static interface AddResolutionFragmentListener {
        public void onAddResolutionSuccess(DialogFragment dialog, Resolution resolution);
        public void onAddResolutionError(DialogFragment dialog);
    }

}
