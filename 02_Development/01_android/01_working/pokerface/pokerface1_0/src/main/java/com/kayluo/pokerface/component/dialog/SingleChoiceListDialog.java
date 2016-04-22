package com.kayluo.pokerface.component.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.util.Utils;

import java.util.ArrayList;

/**
 * Created by Nick on 2016-04-15.
 */
public class SingleChoiceListDialog extends DialogFragment {

    public static final String  DIALOG_BUNDLE_KEY_ARRAY = "stringArrayList";
    public static final String  DIALOG_BUNDLE_KEY_TITLE = "title";
    public RadioGroup radioGroup;
    private ArrayList<String> stringArrayList;
    private String title;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Bundle bundle = this.getArguments();
        stringArrayList = bundle.getStringArrayList(DIALOG_BUNDLE_KEY_ARRAY);
        title = bundle.getString(DIALOG_BUNDLE_KEY_TITLE);
        View customView = inflater.inflate(R.layout.dialog_single_choice_list, null);
        radioGroup = (RadioGroup) customView.findViewById(R.id.single_choice_list_radio_group);

        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, Utils.getPixels(50, getActivity()));
        for (String string : stringArrayList)
        {
            RadioButton button = new RadioButton(getActivity());
            button.setLayoutParams(params);
            button.setText(string);
            radioGroup.addView(button);
        }

        builder.setTitle(title)
                .setView(customView)
                // Add action buttons
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(SingleChoiceListDialog.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(SingleChoiceListDialog.this);
                        SingleChoiceListDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }




    // Use this instance of the interface to deliver action events
    OnDialogButtonClickListener mListener;

    // Override the Fragment.onAttach() method to instantiate the OnDialogButtonClickListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (OnDialogButtonClickListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
