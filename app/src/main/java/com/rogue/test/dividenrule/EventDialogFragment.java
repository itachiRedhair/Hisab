package com.rogue.test.dividenrule;

import android.app.Dialog;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class EventDialogFragment extends DialogFragment {
    private TextView eventNameView;
    private Button dateButton;
    private RecyclerView recyclerView;
    private PeopleAdapter peopleAdapter;
    public static final String ARGS_EVENT_POSITION="extraEvent";
    private int eventPosition;
    private Event event;
    public static EventDialogFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt(ARGS_EVENT_POSITION,position);
        EventDialogFragment fragment = new EventDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void updateUI(){
        eventPosition=getArguments().getInt(ARGS_EVENT_POSITION);
        event=EventLab.get().getEventList().get(eventPosition);
        eventNameView.setText(event.getName());
        dateButton.setText(event.getDate());
        List<ShareForAnEvent> shareForAnEventList = event.getShareForAnEventList();
        if (peopleAdapter == null) {
            peopleAdapter = new PeopleAdapter(shareForAnEventList);
            recyclerView.setAdapter(peopleAdapter);
        } else {
            peopleAdapter.notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v=LayoutInflater.from(getActivity()).inflate(R.layout.dialog_fragment_event,null);
        eventNameView=(TextView) v.findViewById(R.id.event_dialog_event_name_textview);
        dateButton=(Button) v.findViewById(R.id.event_dialog_date_button);
        recyclerView=(RecyclerView) v.findViewById(R.id.event_dialog_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle("Event Details:").setPositiveButton(android.R.string.ok, null).create();
    }

    private class PeopleHolder extends RecyclerView.ViewHolder{
        private TextView peopleName;
        private TextView money;
        private ShareForAnEvent shareForAnEvent;
        public PeopleHolder(View itemView){
            super(itemView);
            peopleName=(TextView) itemView.findViewById(R.id.people_name_textview);
            money=(TextView) itemView.findViewById(R.id.money_name_textview);
        }
        public void bindPeople(ShareForAnEvent shareForAnevent){
            shareForAnEvent=shareForAnevent;
            peopleName.setText("     "+shareForAnEvent.getPeople().getName());
            peopleName.setTextSize(20);
            money.setText(shareForAnEvent.getAmount());
            money.setTextSize(20);
            if(shareForAnEvent.getStatus().equals("RED")){
                money.setTextColor(Color.RED);
            }else{
                money.setTextColor(Color.GREEN);
            }
        }
    }
    private class PeopleAdapter extends RecyclerView.Adapter<PeopleHolder>{
        private List<ShareForAnEvent> shareForAnEventList;
        public PeopleAdapter(List<ShareForAnEvent> shareForAnEventListDummy){
            shareForAnEventList=shareForAnEventListDummy;
        }

        @Override
        public PeopleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.list_item_people,parent,false);
            return new PeopleHolder(view);
        }
        @Override
        public void onBindViewHolder(PeopleHolder holder, int position) {
            ShareForAnEvent shareForAnEvent=shareForAnEventList.get(position);
            holder.bindPeople(shareForAnEvent);
        }
        @Override
        public int getItemCount() {
            return shareForAnEventList.size();
        }
    }
}
//+ sign where menu comes for dashboard and afterwards whatever activity comes that
//3 dot option menu for peoplelist to add new peoples
//slide floating bar for dash people and events
//floating activity for quick details
//interaction between activities and fragments
//proper flow of data
//logic
//polish
//
//