package com.rogue.test.dividenrule;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EventListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_event);
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.event_fragment_container);
        if(fragment==null){
            fragment=EventListFragment.newInstance();
            fm.beginTransaction().add(R.id.event_fragment_container,fragment).commit();
        }
    }
    public static Intent newIntent(Context c){
        return new Intent(c,EventListActivity.class);
    }
}
