package com.rogue.test.dividenrule;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by akshay on 12/29/2016.
 */

public class DatePickerFragment extends DialogFragment {
    public static final String ARG_DATE="date";
    public static final String EXTRA_DATE="date";
    DatePicker datePicker;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v= LayoutInflater.from(getActivity()).inflate(R.layout.fragment_date_picker,null);
        datePicker=(DatePicker)v.findViewById(R.id.dialog_date_picker);
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle("Pick a Date").
                setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year=datePicker.getYear();
                int month=datePicker.getMonth();
                int day=datePicker.getDayOfMonth();
                Date date=new GregorianCalendar(year,month,day).getTime();
                sendResult(Activity.RESULT_OK,date);
            }
        }).create();
    }
    public static DatePickerFragment newInstance(){
//        Bundle args=new Bundle();
//        args.putSerializable(ARG_DATE,date);
        DatePickerFragment fragment=new DatePickerFragment();
      //  fragment.setArguments(args);
        return fragment;
    }
    public void sendResult(int result_code,Date date){
        if(getTargetFragment()==null){
            return;
        }
        Intent intent=new Intent();
        intent.putExtra(EXTRA_DATE,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),result_code,intent);
    }
}