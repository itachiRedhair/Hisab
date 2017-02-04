package com.rogue.test.dividenrule;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EventEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        FragmentManager fm= getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragment_event_edit_container);
        if(fragment==null){
            fragment=EventEditFragment.newInstance();
            fm.beginTransaction().add(R.id.fragment_event_edit_container,fragment).commit();
        }
    }
    public static Intent newIntent(Context c){
        Intent i= new Intent(c,EventEditActivity.class);
        return i;
    }
}
