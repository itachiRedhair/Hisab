package com.rogue.test.dividenrule;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventEditFragment extends Fragment {
    Boolean flag=false;
    ListView listView;
    Button addPersonButton;
    String[] serialNumberArray;
    String[] peopleNames;
    ArrayList<View> viewsList;
    listViewAdapter listViewAdapterObj;
    EditText editText;
    Button dateButton;
    private static final String dateDialogTag="DialogDate";
    private static final int REQUEST_CODE=0;
    int i=0;
    public static EventEditFragment newInstance() {
        EventEditFragment fragment = new EventEditFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_event_edit,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_done_editing:
                wrapAllData();
                if (flag) {
                    getActivity().finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void wrapAllData() {
        if(editText.getText().toString().equals("")){
            Toast.makeText(getActivity(),"Please enter Event Name.",Toast.LENGTH_LONG).show();
            flag=false;
        }else if(dateButton.getText().toString().equals("Set a Date")){
            Toast.makeText(getActivity(),"Please enter date.",Toast.LENGTH_LONG).show();
            flag=false;
        }else {
            Event event = new Event(editText.getText().toString(), dateButton.getText().toString());
            for (int i = 0; i < listViewAdapterObj.getCount(); i++) {
                View view = viewsList.get(i);
                Spinner spinnerTemp = (Spinner) view.findViewById(R.id.spinner);
                People people = PeopleLab.get().getPeoples().get(spinnerTemp.getSelectedItemPosition());
                EditText editTextTemp = (EditText) view.findViewById(R.id.event_edit_edittext);
                Button buttonTemp = (Button) view.findViewById(R.id.event_edit_button);
                int color = buttonTemp.getCurrentTextColor();
                String status;
                if (color == Color.GREEN) {
                    status = "GREEN";
                } else {
                    status = "RED";
                }
                if(editTextTemp.getText().toString().equals("")){
                    flag=false;
                    Toast.makeText(getActivity(),"Please enter amount for specific person",Toast.LENGTH_LONG).show();
                }else {
                    ShareForAnEvent share = new ShareForAnEvent(people, editTextTemp.getText().toString(), status);
                    event.getShareForAnEventList().add(share);
                    flag=true;
                }
            }
            if(flag) {
                EventLab.get().getEventList().add(event);
                for (int i = 0; i < listViewAdapterObj.getCount(); i++) {
                    View view = viewsList.get(i);
                    Spinner spinnerTemp = (Spinner) view.findViewById(R.id.spinner);
                    PeopleLab.get().getPeoples().get(spinnerTemp.getSelectedItemPosition()).getEventList().add(event);
                }
                DataManager.updateData(getActivity());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_event_edit, container, false);
        editText=(EditText) view.findViewById(R.id.event_name_editview) ;
        dateButton=(Button)view.findViewById(R.id.event_edit_date_button);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager=getFragmentManager();
                DatePickerFragment dialog=DatePickerFragment.newInstance();
                dialog.setTargetFragment(EventEditFragment.this,REQUEST_CODE);
                dialog.show(manager,dateDialogTag);
            }
        });
        listView=(ListView) view.findViewById(R.id.event_edit_person_listview);
        addPersonButton=(Button) view.findViewById(R.id.add_person_button);
        peopleNames=new String[PeopleLab.get().getPeoples().size()];
        for(int i=0;i<PeopleLab.get().getPeoples().size();i++){
            //serialNumberArray[i]=Integer.toString(i)+".";
            peopleNames[i]=PeopleLab.get().getPeoples().get(i).getName();
        }
        listViewAdapterObj=new listViewAdapter(getActivity());
        listView.setAdapter(listViewAdapterObj);
        viewsList=new ArrayList<>();
        //
        //
        //ActionListeners
        addPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView serialNumberTextView;
                Spinner spinner;
                Button statusButton;
                EditText editText;
                LayoutInflater inflater1=getActivity().getLayoutInflater();
                View view1=inflater1.inflate(R.layout.list_view_layout_event_edit, null, false);
                spinner=(Spinner)view1.findViewById(R.id.spinner);
                spinner.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.spinner_layout,peopleNames));
                serialNumberTextView=(TextView)view1.findViewById(R.id.serial_number) ;
                serialNumberTextView.setText(Integer.toString(++i));
                statusButton=(Button)view1.findViewById(R.id.event_edit_button);
                statusButton.setTextColor(Color.GREEN);
                statusButton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        TextView textView = (TextView) v;
                                                        Boolean bool = (Boolean) v.getTag();
                                                        if (bool == null) {
                                                            bool = true;
                                                        }
                                                        if (bool == true) {
                                                            textView.setTextColor(Color.RED);
                                                            textView.setText("To Give");
                                                            bool = false;
                                                            v.setTag(bool);
                                                        } else if (bool == false) {
                                                            textView.setTextColor(Color.GREEN);
                                                            textView.setText("To Take");
                                                            bool = true;
                                                            v.setTag(bool);
                                                        }
                                                    }
                                                });

                viewsList.add(view1);
                listViewAdapterObj.add(Integer.toString(i)+"..");

            }
        });
        return view;

}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode!= Activity.RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_CODE){
            String dateString;
            Date date=(Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            dateString=new SimpleDateFormat("EEE-dd-MMM-yyyy").format(date);
            dateButton.setText(dateString);
        }
    }

    public class listViewAdapter extends ArrayAdapter<String>{
        LayoutInflater inflater;
     //   ArrayList<ArrayAdapter> arrayAdaptersList;
        listViewAdapter(Context context){
            super(context,R.layout.list_view_layout_event_edit,R.id.serial_number);
            inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    //        arrayAdaptersList=new ArrayList<>();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Log.i("akki",Integer.toString(listView.getAdapter().getCount())+"     "+Integer.toString(position));

            View view=viewsList.get(position);
           return view;
        }
    }
}
