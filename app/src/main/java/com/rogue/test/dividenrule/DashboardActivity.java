package com.rogue.test.dividenrule;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;

public class DashboardActivity extends AppCompatActivity{
    private static boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        if(flag==true) {
            DataManager.loadData(this);
            //Log.i("akki", "loaddata in onCreate in dashboardactivity called");
         //   Log.i("akki", String.valueOf(PeopleLab.get().getPeoples().size()) + "       "
         //           + String.valueOf(EventLab.get().getEventList().size()));
            flag=false;
        }
        FragmentManager fm=getSupportFragmentManager();
        Fragment fragment=fm.findFragmentById(R.id.fragment_dashboard_container);
        if(fragment==null){
            fragment=DashboardFragment.newInstance();
            fm.beginTransaction().add(R.id.fragment_dashboard_container,fragment).commit();
        }
    }
}
