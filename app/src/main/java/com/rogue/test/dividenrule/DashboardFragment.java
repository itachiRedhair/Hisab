package com.rogue.test.dividenrule;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class DashboardFragment extends Fragment {

    private RecyclerView recyclerViewTake;
    private RecyclerView recyclerViewGive;
    private DashboardAdapterGive dashboardAdapterGive;
    private DashboardAdapterTake dashboardAdapterTake;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;
    public String[] drawerArray={"Events","People","About"};
    List<People> toTakeList;
    List<People> toGiveList;;
    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
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
        View view=inflater.inflate(R.layout.fragment_dashboard, container, false);
        recyclerViewGive=(RecyclerView) view.findViewById(R.id.to_give_recyclerview);
        recyclerViewTake=(RecyclerView) view.findViewById(R.id.to_take_recyclerview);
        recyclerViewGive.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewTake.setLayoutManager(new LinearLayoutManager(getActivity()));
        drawerLayout=(DrawerLayout)view.findViewById(R.id.drawer_layout);

       // ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_action_name);
        drawerToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,R.string.open_drawer,R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Options");
              getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Divide n Rule");
                getActivity().invalidateOptionsMenu();
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.addDrawerListener(drawerToggle);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_name);


        drawerList=(ListView) view.findViewById(R.id.drawer_list);
        drawerList.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.simple_list_item,drawerArray));
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    //events
                    Intent i=EventListActivity.newIntent(getActivity());
                    startActivity(i);
                }else if(position==1){
                    //people
                    Intent p=PeopleListActivity.newIntent(getActivity());
                    startActivity(p);
                }else if(position==2){
                    //about
                FragmentManager fm=getFragmentManager();
                    aboutFragment fragment=aboutFragment.newInstance();
                    fragment.show(fm,"tag");
                }
            }
        });

        updateUI();
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }



    public void updateUI(){

        toTakeList=new ArrayList<>();
        toGiveList=new ArrayList<>();;
        for(int i=0;i<PeopleLab.get().getPeoples().size();i++){
            if(Integer.parseInt(PeopleLab.get().getPeoples().get(i).getTotal())>0){
                toTakeList.add(PeopleLab.get().getPeoples().get(i));
            }else if(Integer.parseInt(PeopleLab.get().getPeoples().get(i).getTotal())<0){
                toGiveList.add(PeopleLab.get().getPeoples().get(i));
            }
        }
            dashboardAdapterGive=new DashboardAdapterGive(toGiveList);
            recyclerViewGive.setAdapter(dashboardAdapterGive);


            dashboardAdapterTake=new DashboardAdapterTake(toTakeList);
            recyclerViewTake.setAdapter(dashboardAdapterTake);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.dashboard_menu_layout,menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        boolean drawerOpen=drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.menu_new_item).setVisible(!drawerOpen);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_new_item:
                Intent i=EventEditActivity.newIntent(getActivity());
                startActivity(i);
                return true;
            case android.R.id.home:
                if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("akki","in on resume");
        updateUI();
    }

    //adapter and holder for take and give recyclerviews
    private class DashboardHolderGive extends RecyclerView.ViewHolder{
        private TextView peopleName;
        private TextView money;
        public DashboardHolderGive(View itemView){
            super(itemView);
            peopleName=(TextView) itemView.findViewById(R.id.takengivelist_person_name_textview);
            money=(TextView) itemView.findViewById(R.id.takeengivelist_money_textview);
        }
        public void bindPeople(People people){
            peopleName.setText(people.getName());
            money.setText(Integer.toString(Math.abs(Integer.parseInt(people.getTotal()))));
        }

    }
    private class DashboardAdapterGive extends RecyclerView.Adapter<DashboardHolderGive>{
        List<People> peopleList;
        public DashboardAdapterGive(List<People> peopleList1){
            peopleList=peopleList1;
        }

        @Override
        public int getItemCount() {
            return peopleList.size();
        }

        @Override
        public void onBindViewHolder(DashboardHolderGive holder, int position) {
        holder.bindPeople(peopleList.get(position));
        }

        @Override
        public DashboardHolderGive onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.to_takengive_list_item,parent,false);

            return new DashboardHolderGive(view);
        }
    }
    private class DashboardHolderTake extends RecyclerView.ViewHolder{
        private TextView peopleName;
        private TextView money;
        public DashboardHolderTake(View itemView){
            super(itemView);
            peopleName=(TextView) itemView.findViewById(R.id.takengivelist_person_name_textview);
            money=(TextView) itemView.findViewById(R.id.takeengivelist_money_textview);
        }
        public void bindPeople(People people){
            peopleName.setText(people.getName());
            money.setText(Integer.toString(Math.abs(Integer.parseInt(people.getTotal()))));
        }
    }
    private class DashboardAdapterTake extends RecyclerView.Adapter<DashboardHolderTake>{
        List<People> peopleList;
        public DashboardAdapterTake(List<People> peopleList1){
            peopleList=peopleList1;
        }

        @Override
        public int getItemCount() {
            return peopleList.size();
        }

        @Override
        public void onBindViewHolder(DashboardHolderTake holder, int position) {
            holder.bindPeople(peopleList.get(position));
        }

        @Override
        public DashboardHolderTake onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.to_takengive_list_item,parent,false);
            return new DashboardHolderTake(view);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        updateUI();
    }

}
