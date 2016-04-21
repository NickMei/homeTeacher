package com.kayluo.pokerface.component.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kayluo.pokerface.R;


/**
 * Created by cxm170 on 2015/9/26.
 */
public class ChangePasswordDialog extends DialogFragment {

    public TextView oldPassword;
    public TextView newPassword;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customView = inflater.inflate(R.layout.dialog_change_password, null);
        oldPassword = (TextView) customView.findViewById(R.id.old_password);
        newPassword = (TextView) customView.findViewById(R.id.new_password);
        builder.setTitle("修改密码")
                .setView(customView)
                        // Add action buttons
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(ChangePasswordDialog.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick(ChangePasswordDialog.this);
                        ChangePasswordDialog.this.getDialog().cancel();
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
