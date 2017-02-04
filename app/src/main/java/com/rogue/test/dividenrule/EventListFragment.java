package com.rogue.test.dividenrule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class EventListFragment extends Fragment {
    private RecyclerView recyclerView;
    private EventAdapter eventAdapter;
    String EVENT_DIALOG_FRAGMENT_TAG="eventDialogFragmentTag";
    public EventListFragment() {
        // Required empty public constructor
    }

    public static EventListFragment newInstance() {
        EventListFragment fragment = new EventListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_list_event,container,false);
        recyclerView=(RecyclerView) view.findViewById(R.id.event_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }
    public void updateUI(){

        EventLab eventLab=EventLab.get();
        List<Event> eventList=eventLab.getEventList();
        if(eventAdapter==null){
            eventAdapter=new EventAdapter(eventList);
            recyclerView.setAdapter(eventAdapter);
        }else{
            eventAdapter.notifyDataSetChanged();
        }

    }
    private class EventHolder extends RecyclerView.ViewHolder{
        private TextView eventName;
        private TextView date;
        private Event eventdummy;
        public EventHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=EventLab.get().getEventList().indexOf(eventdummy);
                    //open an eventActivity displaying the details of event
                    FragmentManager manager=getFragmentManager();
                    EventDialogFragment dialog=EventDialogFragment.newInstance(position);
                    dialog.show(manager,EVENT_DIALOG_FRAGMENT_TAG);
                }
            });
            eventName=(TextView)itemView.findViewById(R.id.event_name_textview);
            LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)eventName.getLayoutParams();
            params.width=400;
            params.height=125;
            eventName.setLayoutParams(params);
            date=(TextView)itemView.findViewById(R.id.date_textview);
        }
        public void bindCrime(Event event,int position){
            eventdummy=event;
            eventName.setText(Integer.toString(position+1)+".  "+eventdummy.getName());
            eventName.setTextSize(20);

            date.setText(eventdummy.getDate());
        }
    }
    private class EventAdapter extends RecyclerView.Adapter<EventHolder>{
        public List<Event> eventList;
        public EventAdapter(List<Event> eventListDummy){
            eventList=eventListDummy;
        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            holder.bindCrime(eventList.get(position),position);
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.list_item_event,parent,false);
            return new EventHolder(view);
        }
    }
}
