package com.rogue.test.dividenrule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PeopleListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private RecyclerView recyclerView;
    public PeopleAdapter peopleAdapter;
    public String TAG_PEOPLE_DIALOG="poeple_dialog_tag";
    public PeopleListFragment() {
        // Required empty public constructor
    }

    public static PeopleListFragment newInstance() {
        PeopleListFragment fragment = new PeopleListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_people_list,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.people_list_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.people_list_menu_add,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_new_item_people_list:
                Intent i=PeopleEditActivity.newIntent(getActivity());
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void updateUI(){

            PeopleLab peopleLab = PeopleLab.get();
            List<People> peopleList = peopleLab.getPeoples();
            if (peopleAdapter == null) {
                peopleAdapter = new PeopleAdapter(peopleList);
                recyclerView.setAdapter(peopleAdapter);
            } else {
                peopleAdapter.notifyDataSetChanged();
            }
    }

    private class PeopleHolder extends RecyclerView.ViewHolder{
        private TextView peopleName;
        private TextView money;
        private People people;
        public PeopleHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=PeopleLab.get().getPeoples().indexOf(people);
                    FragmentManager manager=getFragmentManager();
                    PeopleDialogFragment dialog=PeopleDialogFragment.newInstance(position);
                    dialog.show(manager,TAG_PEOPLE_DIALOG);
                }
            });
            peopleName=(TextView) itemView.findViewById(R.id.people_name_textview);
            money=(TextView) itemView.findViewById(R.id.money_name_textview);
        }
        public void bindCrime(People peopledummy){
            people=peopledummy;
            peopleName.setText(people.getName());
            money.setText(Integer.toString(Math.abs(Integer.parseInt(people.getTotal()))));
            money.setTextColor(people.getColor());
        }
    }

    private class PeopleAdapter extends RecyclerView.Adapter<PeopleHolder>{
        private List<People> peopleList;
        public PeopleAdapter(List<People> peopleListDummy){
            peopleList=peopleListDummy;
        }

        @Override
        public PeopleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.list_item_people,parent,false);
            return new PeopleHolder(view);
        }
        @Override
        public void onBindViewHolder(PeopleHolder holder, int position) {
            People people=peopleList.get(position);
            holder.bindCrime(people);
        }
        @Override
        public int getItemCount() {
            return peopleList.size();
        }
    }
}
