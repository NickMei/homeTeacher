package com.kayluo.pokerface.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayluo.pokerface.R;
import com.kayluo.pokerface.UI.user.LoginViewActivity;
import com.kayluo.pokerface.core.AppManager;

public class StudentInfoFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.request_student_info, container, false);

    }


}