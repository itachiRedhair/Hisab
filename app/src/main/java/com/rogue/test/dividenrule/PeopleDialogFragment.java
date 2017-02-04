package com.rogue.test.dividenrule;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class PeopleDialogFragment extends DialogFragment {
    People people;
    private TextView textView;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    private static String ARGS_POSITION="peopleposition";
    private int peoplePosition;

    public static PeopleDialogFragment newInstance(int position) {
        Bundle args=new Bundle();
        args.putInt(ARGS_POSITION,position);
        PeopleDialogFragment fragment = new PeopleDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v=LayoutInflater.from(getActivity()).inflate(R.layout.fragment_people,null);
        textView=(TextView) v.findViewById(R.id.person_name_textview);
        recyclerView=(RecyclerView) v.findViewById(R.id.fragment_people_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle("Details:").setPositiveButton(android.R.string.ok,null).create();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI(){
        peoplePosition=getArguments().getInt(ARGS_POSITION);
        people=PeopleLab.get().getPeoples().get(peoplePosition);
        textView.setText(people.getName());
        if(eventAdapter==null){
            eventAdapter=new EventAdapter(PeopleLab.get().getPeoples().get(peoplePosition).getEventList());
            recyclerView.setAdapter(eventAdapter);
        }else{
            eventAdapter.notifyDataSetChanged();
        }
    }
    private class EventHolder extends RecyclerView.ViewHolder{
        private TextView eventName;
        private TextView date;
        private TextView money;
        Event event;
        public EventHolder(View itemView){
            super(itemView);
            eventName=(TextView)itemView.findViewById(R.id.event_name_textview);
            date=(TextView)itemView.findViewById(R.id.date_textview);
            money=(TextView)itemView.findViewById(R.id.money_status_in_peopledialogfragment);
        }
        public void bindEvent(Event eventDummy){
            event=eventDummy;
            eventName.setText(event.getName());
            date.setText(event.getDate());
            for(int i=0;i<event.getShareForAnEventList().size();i++){
                if(event.getShareForAnEventList().get(i).getPeople().equals(PeopleLab.get().getPeoples().get(peoplePosition))){
                    money.setText(event.getShareForAnEventList().get(i).getAmount());
                    money.setTextColor(event.getShareForAnEventList().get(i).getColor());
                }
            }
        }
    }
    private class EventAdapter extends RecyclerView.Adapter<EventHolder>{
        private ArrayList<Event> eventList;
        public EventAdapter(ArrayList<Event> eventList1){
            eventList=eventList1;
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            holder.bindEvent(eventList.get(position));
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.list_item_event,parent,false);
            return new EventHolder(view);
        }
    }
}