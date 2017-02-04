package com.rogue.test.dividenrule;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class PeopleEditActivity extends AppCompatActivity {
    EditText editText;
    Boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_edit);
        editText= (EditText) findViewById(R.id.people_edit_editText);

    }
    public static Intent newIntent(Context c){
        return new Intent(c,PeopleEditActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_edit,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_done_editing){
            wrapAllData();
            if(flag.equals(true)) {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void wrapAllData(){

        if(editText.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"Please enter a name.",Toast.LENGTH_SHORT).show();
            flag=false;
        }else {
            People e=new People();
            e.setName(editText.getText().toString());
            PeopleLab.get().getPeoples().add(e);
            DataManager.updateData(this);
            flag=true;
        }
    }
}