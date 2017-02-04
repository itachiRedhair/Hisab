package com.rogue.test.dividenrule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;


public class aboutFragment extends DialogFragment {

    public aboutFragment() {
        // Required empty public constructor
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v=LayoutInflater.from(getActivity()).inflate(R.layout.layout_about_dialog,null);
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle("About").setPositiveButton(android.R.string.ok, null).create();
    }

    // TODO: Rename and change types and number of parameters
    public static aboutFragment newInstance() {
        aboutFragment fragment = new aboutFragment();
        return fragment;
    }

}