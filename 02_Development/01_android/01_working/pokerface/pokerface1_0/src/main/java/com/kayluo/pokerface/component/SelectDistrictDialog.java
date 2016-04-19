package com.kayluo.pokerface.component;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.component.dialog.OnDialogButtonClickListener;
import com.kayluo.pokerface.util.Utils;

import java.util.ArrayList;


/**
 * Created by Nick on 2016-01-06.
 */
public class SelectDistrictDialog extends DialogFragment {

    public RadioGroup radioGroup;
    private ArrayList<String> districtList;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        Bundle bundle = this.getArguments();
        districtList = bundle.getStringArrayList("districtList");
        View customView = inflater.inflate(R.layout.dialog_select_district, null);
        radioGroup = (RadioGroup) customView.findViewById(R.id.select_time_radio_group);

        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, Utils.getPixels(50, getActivity()));
        for (String name : districtList)
        {
            RadioButton button = new RadioButton(getActivity());
            button.setLayoutParams(params);
            button.setText(name);
            radioGroup.addView(button);
        }

        builder.setTitle("选择地区")
                .setView(customView)
                        // Add action buttons
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(SelectDistrictDialog.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(SelectDistrictDialog.this);
                        SelectDistrictDialog.this.getDialog().cancel();
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
